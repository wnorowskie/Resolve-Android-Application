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

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

import edu.vassar.cmpu203.resolveandroid.controller.ControllerActivity;

/*
 This is a system test class to check the friends list and friend request pages, which
 tests the FriendListFragment, FriendRequestFragment and their corresponding layouts.
 */
public class ManageFriendsTest {
    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    
    /*
    System test to check aspects related to friends.
     */
    @Test
    public void testManageFriends(){
        // some issues with data retrieval surronding the username, may have to adjust to repeatedly
        // run tests. All of these tests can be manually done by launching the app, creating a new
        // account and going into the different layouts from the SocialManagerFragment
        
        double rand = Math.random();
        String user = String.valueOf(rand);
        // now type in username and password
        ViewInteraction username = Espresso.onView(withId(R.id.usernameEditText))
                .perform(ViewActions.typeText(user));

        Espresso.onView(ViewMatchers.withId(R.id.passwordEditText))
                .perform(ViewActions.typeText("test"));

        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.signUpButton)).perform(click());

        SystemClock.sleep(2000);

        // check for "username" text to make sure you've moved past the sign up screen
        Espresso.onView(ViewMatchers.withSubstring(user))
                .check(ViewAssertions.matches(ViewMatchers.withId(R.id.usernameProfile)));

        // then, move to friends list page
        Espresso.onView(ViewMatchers.withId(R.id.friendsButton))
                .perform(ViewActions.click());

        // check that we're on the friends list page and that the title displays the correct text
        Espresso.onView(ViewMatchers.withSubstring("Your Friends"))
                .check(ViewAssertions.matches(ViewMatchers.withId(R.id.friendListTitle)));

        // move to friend requests page
        Espresso.onView(ViewMatchers.withId(R.id.viewPendingButton))
                .perform(ViewActions.click());


        // check that the two pending requests show up to the user
        Espresso.onView(ViewMatchers.withSubstring("thisisausern"))
                .check(ViewAssertions.matches(Matchers.anyOf(ViewMatchers.withId(R.id.name))));

        Espresso.onView(ViewMatchers.withSubstring("chloe"))
                .check(ViewAssertions.matches(Matchers.anyOf(ViewMatchers.withId(R.id.name))));

        // click on accept button corresponding to chloe
        Espresso.onView(Matchers.allOf(
                ViewMatchers.withId(R.id.acceptButton),
                ViewMatchers.withParent(
                        Matchers.allOf(ViewMatchers.withId(R.id.rightHorizontal),
                                ViewMatchers.hasSibling(
                                        Matchers.allOf(ViewMatchers.withId(R.id.groupsEntry), ViewMatchers.withChild(ViewMatchers.withSubstring("chloe")))
                                )))))
                .perform(ViewActions.click());

        // click on deny button for "thisisausern" (only one on screen at this point)
        Espresso.onView(ViewMatchers.withId(R.id.denyButton))
                .perform(ViewActions.click());

        // go back to friends list page
        Espresso.pressBack();

        // check that chloe is in the friends list
        Espresso.onView(ViewMatchers.withSubstring("chloe"))
                .check(ViewAssertions.matches(ViewMatchers.withId(R.id.name)));
    }
}
