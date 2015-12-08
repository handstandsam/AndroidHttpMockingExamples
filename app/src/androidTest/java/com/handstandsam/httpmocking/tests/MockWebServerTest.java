package com.handstandsam.httpmocking.tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.handstandsam.httpmocking.util.AssetReaderUtil;
import com.joshskeen.weatherview.BuildConfig;
import com.joshskeen.weatherview.MainActivity;
import com.joshskeen.weatherview.R;
import com.joshskeen.weatherview.service.WeatherServiceManager;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;


@RunWith(AndroidJUnit4.class)
public class MockWebServerTest extends
        ActivityInstrumentationTestCase2<MainActivity> {

    Logger logger = LoggerFactory.getLogger(MockWebServerTest.class);

    public WeatherServiceManager mWeatherServiceManager;

    final MockWebServer mMockWebServer = new MockWebServer();

    private MainActivity activity;

    public MockWebServerTest() {
        super(MainActivity.class);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        mMockWebServer.start(BuildConfig.PORT);
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        activity = getActivity();
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        mMockWebServer.shutdown();
    }

    /**
     * Test okhttp mockwebserver
     */
    @Test
    public void testMockWebServer() {
        logger.debug("testWiremock");

        String city = "atlanta";
        String assetJson = AssetReaderUtil.asset(getActivity(), city + "-conditions.json");

        //script MockWebServer to return this JSON
        mMockWebServer.enqueue(new MockResponse().setBody(assetJson));

        String okhttpMockWebServerUrl = mMockWebServer.url("/").toString();
        logger.debug("okhttp mockserver URL: " + okhttpMockWebServerUrl);

        String serviceEndpoint = "http://" + BuildConfig.IP + ":" + BuildConfig.PORT;
        logger.debug("MockWebServer Endpoint: " + serviceEndpoint);
        activity.setWeatherServiceManager(new WeatherServiceManager(serviceEndpoint));

        onView(ViewMatchers.withId(R.id.editText)).perform(typeText(city));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText(containsString("GA"))));
    }


}
