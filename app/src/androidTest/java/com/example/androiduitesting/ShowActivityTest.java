package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShowActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new
            ActivityScenarioRule<MainActivity>(MainActivity.class);

    private void addAndClickCity(String cityName) {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText(cityName));
        onView(withId(R.id.button_confirm)).perform(click());
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());
    }

    @Test
    public void testActivitySwitch() {
        addAndClickCity("Edmonton");
        // If ShowActivity launched, button_back will be visible
        onView(withId(R.id.button_back)).check(matches(isDisplayed()));
    }

    @Test
    public void testCityNameConsistency() {
        addAndClickCity("Vancouver");
        onView(withId(R.id.text_city_name)).check(matches(withText("Vancouver")));
    }

    @Test
    public void testBackButton() {
        addAndClickCity("Tokyo");
        onView(withId(R.id.button_back)).perform(click());
        // After going back, MainActivity's elements should be visible again
        onView(withId(R.id.city_list)).check(matches(isDisplayed()));
    }

}
