package edu.vassar.cmpu203.resolveandroid;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Test;

import java.util.Random;

import edu.vassar.cmpu203.resolveandroid.controller.ControllerActivity;

/*
 This is a system test class to check the schedule age, which
 tests the ScheduleFragment and its corresponding layouts.
 */
public class ScheduleTest {
    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /*
    System test to check aspects related to the user's schedule.
     */
    @Test
    public void ScheduleTest(){

        double rand = Math.random();
        String user = String.valueOf(rand);
        // now type in username and password
        ViewInteraction username = Espresso.onView(withId(R.id.usernameEditText))
                .perform(ViewActions.typeText(user));

        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText))
                .perform(ViewActions.typeText("passtest"));

        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.signUpButton)).perform(click());

        SystemClock.sleep(2000);

        // check for "username" text to make sure you've moved past the sign up screen
        Espresso.onView(ViewMatchers.withSubstring(user))
                .check(ViewAssertions.matches(ViewMatchers.withId(R.id.usernameProfile)));
        // select schedule
        Espresso.onView(ViewMatchers.withId(R.id.scheduleButton)).perform(click());

        // check on schedule page
        Espresso.onView(ViewMatchers.withSubstring("Monday"))
                .check(ViewAssertions.matches(ViewMatchers.withId(R.id.dayText)));
    }
}
