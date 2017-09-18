package com.handstandsam.httpmocking.tests;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jakewharton.espresso.OkHttp3IdlingResource;
import com.joshskeen.weatherview.MainActivity;
import com.joshskeen.weatherview.R;
import com.joshskeen.weatherview.inject.InjectionFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;


@RunWith(AndroidJUnit4.class)
public class RealServerTest {

    Logger logger = LoggerFactory.getLogger(RealServerTest.class);

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);


    IdlingResource idlingResource;

    @Before
    public void setUp() {
        idlingResource =
                OkHttp3IdlingResource.create("OkHttp", InjectionFactory.okHttpClient);
        Espresso.registerIdlingResources(idlingResource);
    }

    @After
    public void tearDown() {
        Espresso.unregisterIdlingResources(idlingResource);
    }

    /**
     * Test Real API
     */
    @Test
    public void testRealAPI() {
        logger.debug("testRealAPI");

        onView(ViewMatchers.withId(R.id.editText)).perform(replaceText("atlanta, ga"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText(containsString("GA"))));
    }

}
