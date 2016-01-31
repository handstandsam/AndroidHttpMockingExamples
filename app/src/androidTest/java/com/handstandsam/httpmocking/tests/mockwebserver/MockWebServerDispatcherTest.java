package com.handstandsam.httpmocking.tests.mockwebserver;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.joshskeen.weatherview.BuildConfig;
import com.joshskeen.weatherview.MainActivity;
import com.joshskeen.weatherview.R;
import com.joshskeen.weatherview.service.WeatherServiceManager;
import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

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
import static com.handstandsam.httpmocking.util.AssetReaderUtil.asset;
import static org.hamcrest.Matchers.containsString;


@RunWith(AndroidJUnit4.class)
public class MockWebServerDispatcherTest extends ActivityInstrumentationTestCase2<MainActivity> {

    Logger logger = LoggerFactory.getLogger(MockWebServerDispatcherTest.class);

    final MockWebServer mMockWebServer = new MockWebServer();

    private MainActivity activity;

    public MockWebServerDispatcherTest() {
        super(MainActivity.class);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        mMockWebServer.play(BuildConfig.PORT);
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        this.activity = getActivity();
    }

    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        mMockWebServer.shutdown();
    }

    /**
     * Test okhttp mockwebserver Dispatcher
     */
    @Test
    public void testMockWebServerDispatcher() {
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

        String okhttpMockWebServerUrl = mMockWebServer.getUrl("/").toString();
        logger.debug("okhttp mockserver URL: " + okhttpMockWebServerUrl);
        getActivity().setWeatherServiceManager(new WeatherServiceManager(okhttpMockWebServerUrl));

        onView(ViewMatchers.withId(R.id.editText)).perform(typeText("atlanta"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText(containsString("GA"))));
    }

}
