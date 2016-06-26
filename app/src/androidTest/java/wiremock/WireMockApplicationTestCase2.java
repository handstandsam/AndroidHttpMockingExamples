package wiremock;

import android.test.ApplicationTestCase;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.joshskeen.weatherview.BuildConfig;
import com.joshskeen.weatherview.inject.WeatherviewApplication;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class WireMockApplicationTestCase2 extends ApplicationTestCase<WeatherviewApplication> {

    Logger logger = LoggerFactory.getLogger(WireMockApplicationTestCase2.class);

    public WireMockApplicationTestCase2() {
        super(WeatherviewApplication.class);
    }

    /**
     * The @Rule - WireMockRule does NOT currently work for the ApplicationTestCase because it is not based on JUnit3 and not JUnit4 so we need to create & manage the WireMockServer ourselves
     * <p/>
     * As of 09.09.2015 - "To test an Android application object on the Android runtime you use the ApplicationTestCase class.
     * It is expected that Google will soon provide a special JUnit4 rule for testing the application object but at the moment his is not yet available."
     * <p/>
     * Reference: http://www.vogella.com/tutorials/AndroidTesting/article.html
     */
    WireMockServer wireMockServer;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        createApplication();
        wireMockServer = new WireMockServer(8080, new AndroidAssetsReadOnlyFileSource(getApplication().getAssets()), false);
        wireMockServer.start();
        Thread.sleep(20000);
    }

    @Override
    protected void tearDown() throws Exception {
        wireMockServer.stop();
        super.tearDown();
    }

    /**
     * Test WireMock, but just the Http Call.  Make sure the response matches the mock we want.
     */
    public void testWiremockPlusOkHttp() throws IOException {
        logger.debug("testWiremockPlusOkHttp");

        String serviceEndpoint = "http://127.0.0.1:" + BuildConfig.PORT;
        logger.debug("WireMock Endpoint: " + serviceEndpoint);

        OkHttpClient okHttpClient = new OkHttpClient();

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
