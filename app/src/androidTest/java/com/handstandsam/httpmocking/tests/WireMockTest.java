package com.handstandsam.httpmocking.tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.joshskeen.weatherview.BuildConfig;
import com.joshskeen.weatherview.MainActivity;
import com.joshskeen.weatherview.R;
import com.joshskeen.weatherview.service.WeatherServiceManager;

import org.junit.Before;
import org.junit.Rule;
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
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.handstandsam.httpmocking.util.AssetReaderUtil.asset;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
public class WireMockTest extends
        ActivityInstrumentationTestCase2<MainActivity> {

    Logger logger = LoggerFactory.getLogger(WireMockTest.class);

    private MainActivity activity;

    public WireMockTest() {
        super(MainActivity.class);
    }

    private WireMockConfiguration config = wireMockConfig()
            .port(Integer.valueOf(BuildConfig.PORT));

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(config);

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        activity = getActivity();
    }

    /**
     * Test WireMock
     */
    @Test
    public void testWiremock() {
        logger.debug("testWiremock");

        WireMock.configureFor(BuildConfig.IP, BuildConfig.PORT);
        String city = "atlanta";
        String jsonBody = asset(activity, city + "-conditions.json");
        stubFor(get(urlMatching("/api/.*"))
                .atPriority(5)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(jsonBody)));

        String serviceEndpoint = "http://" + BuildConfig.IP + ":" + BuildConfig.PORT;
        logger.debug("WireMock Endpoint: " + serviceEndpoint);
        activity.setWeatherServiceManager(new WeatherServiceManager(serviceEndpoint));

        onView(ViewMatchers.withId(R.id.editText)).perform(typeText(city));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText(containsString("GA"))));
    }

    /**
     * Test Real API
     */
    @Test
    public void testRealAPI() {
        logger.debug("testRealAPI");

        String city = "atlanta";
        onView(ViewMatchers.withId(R.id.editText)).perform(typeText(city));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText(containsString("GA"))));
    }

}
