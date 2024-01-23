package edu.vassar.cmpu203.resolveandroid;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
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

import edu.vassar.cmpu203.resolveandroid.controller.ControllerActivity;

/*
 This is a system test class to check the organization list page, which looks at the
 OrgListFragment and its corresponding layout.
 */
public class ManageOrgsTest {
    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /*
    System test to check that the org list page is displayed properly.
     */
    @Test
    public void testManageOrgs(){
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

        // then, move to the org list page
        Espresso.onView(ViewMatchers.withId(R.id.orgsButton))
                .perform(ViewActions.click());

        // check that we are on the org list page, and that the title text is displayed correctly
        Espresso.onView(ViewMatchers.withSubstring("Your Organizations"))
                .check(ViewAssertions.matches(ViewMatchers.withId(R.id.friendListTitle)));

        // check that the org "CMPU 203 club" is displayed
        Espresso.onView(ViewMatchers.withSubstring("CMPU 203 club"))
                .check(ViewAssertions.matches(ViewMatchers.withId(R.id.orgName)));

        // check that the org "Lavagirl fan club" is displayed
        Espresso.onView(ViewMatchers.withSubstring("Lavagirl fan club"))
                .check(ViewAssertions.matches(ViewMatchers.withId(R.id.orgName)));

    }
}
