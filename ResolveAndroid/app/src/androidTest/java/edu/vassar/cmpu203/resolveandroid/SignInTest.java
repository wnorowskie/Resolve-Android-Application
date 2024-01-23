package edu.vassar.cmpu203.resolveandroid;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;

import androidx.constraintlayout.motion.widget.Key;
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

import edu.vassar.cmpu203.resolveandroid.controller.ControllerActivity;

/*
 This is a system test class to check the sign up process, which looks at the
 SignUpFragment and its corresponding layout.
 */
public class SignInTest {
    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /*
    System test to check the sign in process.
     */
    @Test
    public void testSignIn() {
        // first get from sign up to sign in
        Espresso.onView(withId(R.id.signInPageButton)).perform(ViewActions.click());

        SystemClock.sleep(2000);

        // first, click the sign up button while the username and password fields are blank
        ViewInteraction button = Espresso.onView(withId(R.id.signInButton))
                .perform(ViewActions.click());

        // snackbar should pop up informing the user of this
        Espresso.onView(ViewMatchers.withSubstring("fail"))
                .check(ViewAssertions.matches(withId(R.id.failMessage)));

        // then, type in a username
        ViewInteraction username = Espresso.onView(withId(R.id.usernameEditText))
                .perform(ViewActions.typeText("username"));

        // in order to press the sign up button, we must close the keyboard
        // as otherwise the test will crash
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());

        // press sign up button again
        button.perform(ViewActions.click());

        // the same snackbar should show up as one field is still blank
        Espresso.onView(ViewMatchers.withSubstring("fail"))
                .check(ViewAssertions.matches(withId(R.id.failMessage)));

        // now type in username and password
        username.perform(ViewActions.typeText("erictest"));

        Espresso.onView(withId(R.id.passwordEditText))
                .perform(ViewActions.typeText("passtest"));

        // keyboard must be closed as otherwise the test will crash
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());

        button.perform(ViewActions.click());

        SystemClock.sleep(2000);


        // check for "username" text to make sure you've moved past the sign up screen
        Espresso.onView(ViewMatchers.withSubstring("erictest"))
                .check(ViewAssertions.matches(withId(R.id.usernameProfile)));
    }
}