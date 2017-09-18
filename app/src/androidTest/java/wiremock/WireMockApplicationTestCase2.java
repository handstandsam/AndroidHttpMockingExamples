package wiremock;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.joshskeen.weatherview.BuildConfig;
import com.joshskeen.weatherview.MainActivity;
import com.joshskeen.weatherview.inject.InjectionFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.assertEquals;

public class WireMockApplicationTestCase2 {

    Logger logger = LoggerFactory.getLogger(WireMockApplicationTestCase2.class);

    /**
     * The @Rule - WireMockRule does NOT currently work for the ApplicationTestCase because it is not based on JUnit3 and not JUnit4 so we need to create & manage the WireMockServer ourselves
     * <p/>
     * As of 09.09.2015 - "To test an Android application object on the Android runtime you use the ApplicationTestCase class.
     * It is expected that Google will soon provide a special JUnit4 rule for testing the application object but at the moment his is not yet available."
     * <p/>
     * Reference: http://www.vogella.com/tutorials/AndroidTesting/article.html
     */
    WireMockServer wireMockServer;


    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    private Context androidTestContext;
    private Context applicationContext;


    @Before
    protected void setUp() throws Exception {
        this.androidTestContext = InstrumentationRegistry.getContext();
        this.applicationContext = InstrumentationRegistry.getTargetContext().getApplicationContext();
        wireMockServer = new WireMockServer(8080, new AndroidAssetsReadOnlyFileSource(androidTestContext.getAssets()), false);
        wireMockServer.start();
    }

    @After
    protected void tearDown() throws Exception {
        wireMockServer.stop();
    }

    /**
     * Test WireMock, but just the Http Call.  Make sure the response matches the mock we want.
     */
    public void testWiremockPlusOkHttp() throws IOException {
        logger.debug("testWiremockPlusOkHttp");

        String serviceEndpoint = "http://127.0.0.1:" + BuildConfig.PORT;
        logger.debug("WireMock Endpoint: " + serviceEndpoint);

        OkHttpClient okHttpClient = InjectionFactory.okHttpClient;

        String uri = "/hello-world";
        Request request = new Request.Builder()
                .url(serviceEndpoint + uri)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        String responseBodyStr = response.body().string();

        logger.debug("Response Body: " + responseBodyStr);
        assertEquals("hello world", responseBodyStr);
    }
}
