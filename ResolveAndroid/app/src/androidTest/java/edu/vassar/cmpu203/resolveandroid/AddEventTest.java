package edu.vassar.cmpu203.resolveandroid;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import edu.vassar.cmpu203.resolveandroid.controller.ControllerActivity;
import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.widget.TimePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
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
 This is a system test class to check the add event page,
 related to the AddEventFragment and corresponding layout.
  */
public class AddEventTest {
    @org.junit.Rule
    public ActivityScenarioRule<ControllerActivity> activityRule =
            new ActivityScenarioRule<>(ControllerActivity.class);

    /*
    System test to check various processes related to adding an event.
     */
    @Test
    public void testAddEvent(){
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

        // then, click on the schedule page where we'll try adding events with differing characteristics
        Espresso.onView(ViewMatchers.withId(R.id.scheduleButton))
                .perform(click());

        Espresso.onView(ViewMatchers.withId(R.id.newEventButton))
                .perform(click());

        // with no days selected, clicking the add button should display a snackbar informing the user
        Espresso.onView(ViewMatchers.withSubstring("From"))
                .check(ViewAssertions.matches(ViewMatchers.withSubstring("From")));


        // press button to pick start time
        ViewInteraction startTimeButtonPress = Espresso.onView(ViewMatchers.withId(R.id.startTimeButton))
                .perform(click());

        // set start time to be 3:30pm
        ViewInteraction changeTime = Espresso.onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
            .perform(PickerActions.setTime(15, 30));

        // click ok in TimePicker screen
        ViewInteraction clickOk = Espresso.onView(withText("OK"))
                .perform(ViewActions.click());

        // press button to change end time
        ViewInteraction endTimeButtonPress = Espresso.onView(ViewMatchers.withId(R.id.endTimeButton))
                .perform(ViewActions.click());

        // set end time to 2:30pm (invalid, before start time)
        changeTime.perform(PickerActions.setTime(14, 30));

        clickOk.perform(ViewActions.click());

        // try to add this event
        Espresso.onView(ViewMatchers.withId(R.id.addButton))
                .perform(click());

        // make sure invalid time snackbar pops up
        Espresso.onView(ViewMatchers.withSubstring("Invalid times"))
                .check(ViewAssertions.matches(ViewMatchers.withSubstring("Invalid times")));

        // change start time to 11:30pm
        startTimeButtonPress.perform(ViewActions.click());

        changeTime.perform(PickerActions.setTime(23, 30));

        clickOk.perform(ViewActions.click());

        // change end time to 1:30am (invalid, wrapping day) (also start time before end time)
        endTimeButtonPress.perform(ViewActions.click());

        changeTime.perform(PickerActions.setTime(1, 30));

         clickOk.perform(ViewActions.click());

         // try to add this event and make sure snackbar pops up
        Espresso.onView(ViewMatchers.withId(R.id.addButton))
                .perform(click());

        Espresso.onView(ViewMatchers.withSubstring("Invalid times"))
                .check(ViewAssertions.matches(ViewMatchers.withSubstring("Invalid times")));

        // set start time to 1:30pm
        startTimeButtonPress.perform(ViewActions.click());

        changeTime.perform(PickerActions.setTime(13, 30));

        clickOk.perform(ViewActions.click());

        // set end time to 3:50pm
        endTimeButtonPress.perform(ViewActions.click());

        changeTime.perform(PickerActions.setTime(15, 50));

        clickOk.perform(ViewActions.click());

        // try to add this event
        Espresso.onView(ViewMatchers.withId(R.id.addButton))
                .perform(click());

        // make sure snackbar pops up telling us to enter a name for this event
        Espresso.onView(ViewMatchers.withSubstring("Enter event name"))
                .check(ViewAssertions.matches(ViewMatchers.withSubstring("Enter event name")));

        // type "class" into the event name text field
        Espresso.onView(ViewMatchers.withId(R.id.eventNameText))
                .perform(ViewActions.typeText("class"));

        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());

        // now try to add that valid event
        Espresso.onView(ViewMatchers.withId(R.id.addButton))
                .perform(click());

        // snackbar should pop up saying that the event has been added
        Espresso.onView(ViewMatchers.withSubstring("Event added!"))
                .check(ViewAssertions.matches(ViewMatchers.withSubstring("Event added!")));

        // if we try to add it again, a snackbar should pop up saying that this is a duplicate event
        Espresso.onView(ViewMatchers.withId(R.id.addButton))
                .perform(click());

        Espresso.onView(ViewMatchers.withSubstring("Duplicate Event"))
                .check(ViewAssertions.matches(ViewMatchers.withSubstring("Duplicate Event")));
    }

}