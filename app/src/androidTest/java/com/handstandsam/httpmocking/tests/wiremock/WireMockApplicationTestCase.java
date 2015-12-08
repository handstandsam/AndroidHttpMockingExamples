package com.handstandsam.httpmocking.tests.wiremock;

import android.test.ApplicationTestCase;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.joshskeen.weatherview.BuildConfig;
import com.joshskeen.weatherview.inject.WeatherviewApplication;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.handstandsam.httpmocking.util.AssetReaderUtil.asset;

public class WireMockApplicationTestCase extends ApplicationTestCase<WeatherviewApplication> {

    Logger logger = LoggerFactory.getLogger(WireMockApplicationTestCase.class);

    public WireMockApplicationTestCase() {
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
    WireMockServer wireMockServer = new WireMockServer(BuildConfig.PORT);

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        wireMockServer.start();
        createApplication();
    }

    @After
    @Override
    public void tearDown() throws Exception {
        wireMockServer.stop();
        super.tearDown();
    }

    /**
     * Test WireMock, but just the Http Call.  Make sure the response matches the mock we want.
     */
    public void testWiremockHttp() throws IOException {
        logger.debug("testWiremockHttp");

        String uri = "/api/840dbdf2737a7ff9/api840dbdf2737a7ff9/conditions/q/CA/atlanta.json";

        String jsonBody = asset(getApplication(), "atlanta-conditions.json");
        assertTrue(jsonBody.length() > 0);
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
