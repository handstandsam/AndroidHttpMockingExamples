package com.handstandsam.httpmocking.tests.wiremock;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.joshskeen.weatherview.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.handstandsam.httpmocking.util.AssetReaderUtil.asset;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class WireMockApplicationTestCase {

    Logger logger = LoggerFactory.getLogger(WireMockApplicationTestCase.class);

    private Context applicationContext;

    /**
     * The @Rule - WireMockRule does NOT currently work for the ApplicationTestCase because it is not based on JUnit3 and not JUnit4 so we need to create & manage the WireMockServer ourselves
     * <p/>
     * As of 09.09.2015 - "To test an Android application object on the Android runtime you use the ApplicationTestCase class.
     * It is expected that Google will soon provide a special JUnit4 rule for testing the application object but at the moment his is not yet available."
     * <p/>
     * Reference: http://www.vogella.com/tutorials/AndroidTesting/article.html
     */
    WireMockServer wireMockServer = new WireMockServer(BuildConfig.PORT);

    @Before
    public void setUp() {
        applicationContext = InstrumentationRegistry.getTargetContext().getApplicationContext();
        wireMockServer.start();
    }

    @After
    public void tearDown() throws Exception {
        wireMockServer.stop();
    }

    /**
     * Test WireMock, but just the Http Call.  Make sure the response matches the mock we want.
     */
    @Test
    public void testWiremockPlusOkHttp() throws IOException {
        logger.debug("testWiremockPlusOkHttp");

        String uri = "/api/840dbdf2737a7ff9/conditions/q/CA/atlanta.json";

        String jsonBody = asset(applicationContext, "atlanta-conditions.json");
        assertFalse(jsonBody.isEmpty());
        wireMockServer.stubFor(get(urlMatching(uri))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(jsonBody)));

        String serviceEndpoint = "http://127.0.0.1:" + BuildConfig.PORT;
        logger.debug("WireMock Endpoint: " + serviceEndpoint);

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(serviceEndpoint + uri)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        assertEquals(jsonBody, response.body().string());
    }
}
