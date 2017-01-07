package com.handstandsam.httpmocking.tests.mockwebserver;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.joshskeen.weatherview.BuildConfig;
import com.joshskeen.weatherview.MainActivity;
import com.joshskeen.weatherview.R;
import com.joshskeen.weatherview.service.WeatherServiceManager;
import com.squareup.spoon.Spoon;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.handstandsam.httpmocking.util.AssetReaderUtil.asset;
import static org.hamcrest.Matchers.containsString;


public class MockWebServerDispatcherTest {

    Logger logger = LoggerFactory.getLogger(MockWebServerDispatcherTest.class);

    final MockWebServer mMockWebServer = new MockWebServer();

    private MainActivity activity;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        mMockWebServer.start(BuildConfig.PORT);
    }

    @After
    public void tearDown() throws Exception {
        mMockWebServer.shutdown();
    }

    /**
     * Test okhttp mockwebserver Dispatcher
     */
    @Test
    public void testMockWebServerDispatcher() {
        activity = activityRule.getActivity();
        logger.debug("testMockWebServerDispatcher");

        //Use a dispatcher
        final Dispatcher dispatcher = new Dispatcher() {

            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
                if (request.getPath().equals("/api/840dbdf2737a7ff9/conditions/q/CA/atlanta.json")) {
                    String jsonBody = asset(activity, "atlanta-conditions.json");
                    return new MockResponse().setResponseCode(200).setBody(jsonBody);
                }
                return new MockResponse().setResponseCode(404);
            }
        };
        mMockWebServer.setDispatcher(dispatcher);

        String okhttpMockWebServerUrl = mMockWebServer.url("/").toString();
        logger.debug("okhttp mockserver URL: " + okhttpMockWebServerUrl);
        activity.setWeatherServiceManager(new WeatherServiceManager(okhttpMockWebServerUrl));

        Spoon.screenshot(activity, "one");
        onView(ViewMatchers.withId(R.id.editText)).perform(typeText("atlanta"));
        onView(withId(R.id.button)).perform(click());
        Spoon.screenshot(activity, "two");
        onView(withId(R.id.textView)).check(matches(withText(containsString("GA"))));
    }

}
