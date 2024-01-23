package edu.vassar.cmpu203.resolveandroid;

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

import java.util.Random;

import edu.vassar.cmpu203.resolveandroid.controller.ControllerActivity;
import edu.vassar.cmpu203.resolveandroid.model.User;

/*
 This is a system test class to check the sign up process, which looks at the
 SignUpFragment and its corresponding layout.
 */
public class SignUpTest {
    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /*
    System test to check the sign up process.
     */
    @Test
    public void testSignUp() {
        // first, click the sign up button while the username and password fields are blank
        Espresso.onView(ViewMatchers.withId(R.id.signUpButton))
                .perform(ViewActions.click());

        // snackbar should pop up informing the user of this
        Espresso.onView(ViewMatchers.withSubstring("Fields cannot be blank"))
                .check(ViewAssertions.matches(ViewMatchers.withId(R.id.failureMessage)));

        // then, type in a username
        ViewInteraction username = Espresso.onView(ViewMatchers.withId(R.id.usernameEditText))
                .perform(ViewActions.typeText("username"));

        // in order to press the sign up button, we must close the keyboard
        // as otherwise the test will crash
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());

        // press sign up button again
        Espresso.onView(ViewMatchers.withId(R.id.signUpButton)).perform(ViewActions.click());

        // the same snackbar should show up as one field is still blank
        Espresso.onView(ViewMatchers.withSubstring("Fields cannot be blank"))
                .check(ViewAssertions.matches(ViewMatchers.withId(R.id.failureMessage)));

        double rand = Math.random();
        String user = String.valueOf(rand);
        // now type in username and password
        ViewInteraction perform = username.perform(ViewActions.typeText(user));

        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText))
                .perform(ViewActions.typeText("passtest"));

        // keyboard must be closed as otherwise the test will crash
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.signUpButton)).perform(ViewActions.click());

        SystemClock.sleep(2000);

        // check for "username" text to make sure you've moved past the sign up screen
        Espresso.onView(ViewMatchers.withSubstring(user))
                .check(ViewAssertions.matches(ViewMatchers.withId(R.id.usernameProfile)));
    }
}