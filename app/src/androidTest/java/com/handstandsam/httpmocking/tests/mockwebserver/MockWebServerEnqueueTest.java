package com.handstandsam.httpmocking.tests.mockwebserver;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.handstandsam.httpmocking.util.AssetReaderUtil;
import com.joshskeen.weatherview.BuildConfig;
import com.joshskeen.weatherview.MainActivity;
import com.joshskeen.weatherview.R;
import com.joshskeen.weatherview.service.WeatherServiceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

public class MockWebServerEnqueueTest {

    Logger logger = LoggerFactory.getLogger(MockWebServerEnqueueTest.class);

    final MockWebServer mMockWebServer = new MockWebServer();

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = activityRule.getActivity();
        mMockWebServer.start(BuildConfig.PORT);
        //script MockWebServer to return this JSON
        String assetJson = AssetReaderUtil.asset(activity, "atlanta-conditions.json");
        mMockWebServer.enqueue(new MockResponse().setBody(assetJson));

        String okhttpMockWebServerUrl = mMockWebServer.url("/").toString();
        logger.debug("okhttp mockserver URL: " + okhttpMockWebServerUrl);

        String serviceEndpoint = "http://127.0.0.1:" + BuildConfig.PORT;
        logger.debug("MockWebServer Endpoint: " + serviceEndpoint);
        activity.setWeatherServiceManager(new WeatherServiceManager(serviceEndpoint));

    }

    @After
    public void tearDown() throws Exception {
        mMockWebServer.shutdown();
    }

    /**
     * Test okhttp mockwebserver Enqueue
     */
    @Test
    public void testMockWebServerEnqueue() {
        activity = activityRule.getActivity();
        logger.debug("testMockWebServerEnqueue");

        onView(ViewMatchers.withId(R.id.editText)).perform(replaceText("atlanta"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText(containsString("GA"))));
    }
}
