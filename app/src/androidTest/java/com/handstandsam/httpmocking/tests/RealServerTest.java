package com.handstandsam.httpmocking.tests;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.joshskeen.weatherview.BuildConfig;
import com.joshskeen.weatherview.MainActivity;
import com.joshskeen.weatherview.R;

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
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.hamcrest.Matchers.containsString;


@RunWith(AndroidJUnit4.class)
public class RealServerTest extends
        ActivityInstrumentationTestCase2<MainActivity> {
    Logger logger = LoggerFactory.getLogger(RealServerTest.class);

    private MainActivity activity;

    public RealServerTest() {
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
