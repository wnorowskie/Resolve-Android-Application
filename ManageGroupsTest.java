package edu.vassar.cmpu203.resolveandroid;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.google.android.material.snackbar.Snackbar;

import java.util.Random;
import java.lang.Math;

import edu.vassar.cmpu203.resolveandroid.controller.ControllerActivity;

/*
 This is a system test class to check the "groups" page, which looks at the
 GroupViewFragment and its corresponding layout.
 */
public class ManageGroupsTest {
    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /*
    System test to make sure that groups are displayed correctly.
     */
    @Test
    public void testManageGroups() {
        // some issues with data retrieval surronding the username, may have to adjust to repeatedly
        // run tests. All of these tests can be manually done by launching the app, creating a new
        // account and going into the different layouts from the SocialManagerFragment
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

        // then, move to group view page
        Espresso.onView(ViewMatchers.withId(R.id.groupsButton))
                .perform(ViewActions.click());

        // check to make sure that we are now on the group view page and that the title
        // displays the correct text
        Espresso.onView(ViewMatchers.withSubstring("Your Groups"))
                .check(ViewAssertions.matches(ViewMatchers.withId(R.id.yourGroups)));

        SystemClock.sleep(7000);

        // check that the group "team 1c" text is displayed
        Espresso.onView(ViewMatchers.withSubstring("Team 1c"))
                .check(ViewAssertions.matches(ViewMatchers.withId(R.id.groupName)));
    }
}
