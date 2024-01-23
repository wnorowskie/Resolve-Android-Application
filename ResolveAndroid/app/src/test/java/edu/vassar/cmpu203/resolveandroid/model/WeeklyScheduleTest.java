package edu.vassar.cmpu203.resolveandroid.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/*
 Test class of unit tests for the WeeklySchedule class methods.
 */
class WeeklyScheduleTest {
    // create a new event
    EventTime startTime = new EventTime(6, 30, "pm");
    EventTime endTime = new EventTime(7, 45, "pm");
    Event e = new Event(startTime, endTime);

    // create a different event (does not conflict with previous event)
    EventTime startTime2 = new EventTime(2, 50, "pm");
    EventTime endTime2 = new EventTime(4, 35, "pm");
    Event e2 = new Event(startTime2, endTime2);

    // create list of weekdays and weeklyschedule object
    List<Weekday> weekdayList = new ArrayList<>();
    WeeklySchedule weeklySchedule = new WeeklySchedule();

    /*
    Unit test for WeeklySchedule.addEvent(Event e, List<Weekday> weekdayList) method.
    Assumption: DailySchedule.addEvent(Event e) works.
     */
    @Test
    void addEvent() {
        // add wed. and fri. to weekdayList
        weekdayList.add(Weekday.WEDNESDAY);
        weekdayList.add(Weekday.FRIDAY);
        // add event e to weeklySchedule for wed. and fri.
        weeklySchedule.addEvent(e, weekdayList);

        // create a new daily schedule for wed. and add event e
        DailySchedule wedSchedule = new DailySchedule(Weekday.WEDNESDAY);
        wedSchedule.addEvent(e);
        // check that this schedule and weeklySchedule's wed. schedule are equal
        // (i.e. both have event e at index 0)
        assertEquals(wedSchedule.events.get(0), weeklySchedule.getDailySchedule(Weekday.WEDNESDAY).events.get(0));

        // add e2 to weeklySchedule on wed. and fri. as well
        weeklySchedule.addEvent(e2, weekdayList);

        // create new fri. schedule
        DailySchedule newFriSchedule = new DailySchedule(Weekday.FRIDAY);
        // add e and e2 to this schedule
        newFriSchedule.addEvent(e);
        newFriSchedule.addEvent(e2);
        // make sure that the element at the second index of the new fri. schedule and
        // weeklySchedule's fri. schedule are the same (e2)
        assertEquals(newFriSchedule.events.get(1), weeklySchedule.getDailySchedule(Weekday.FRIDAY).events.get(1));
    }

    /*
    Unit test for WeeklySchedule.removeEvent(Event e, Weekday day) method.
    Assumptions: WeeklySchedule.addEvent(Event e, List<Weekday> weekdayList,
    DailySchedule.addEvent(Event e), DailySchedule.removeEvent(Event e) methods work.
     */
    @Test
    void removeEvent() {
        // first, add events e and e2 to weeklySchedule on wed. and fri.
        weekdayList.add(Weekday.WEDNESDAY);
        weekdayList.add(Weekday.FRIDAY);
        weeklySchedule.addEvent(e, weekdayList);
        weeklySchedule.addEvent(e2, weekdayList);

        // check that removing e2 from weeklyschedule returns true as it was present previously
        assertTrue(weeklySchedule.removeEvent(e2));
        // after removal, the same method call should remove false
        assertFalse(weeklySchedule.removeEvent(e2));
        // check that event e2 was removed on both days
        assertEquals(1, weeklySchedule.getDailySchedule(Weekday.FRIDAY).events.size());
        assertEquals(1, weeklySchedule.getDailySchedule(Weekday.WEDNESDAY).events.size());

        // check that only event e2 was removed from Friday's schedule (originally had e and e2)
        assertFalse(weeklySchedule.getDailySchedule(Weekday.FRIDAY).events.contains(e2));
        assertTrue(weeklySchedule.getDailySchedule(Weekday.FRIDAY).events.contains(e));
    }

    /*
    Unit test for WeeklySchedule.rescheduleEvent(Event e, Weekday w,
    EventTime startTime, EventTime endTime) method.
    Assumption: WeeklySchedule.addEvent(Event e, List<Weekday> weekdayList),
    DailySchedule.addEvent(Event e), DailySchedule.removeEvent(Event e),
    DailySchedule.rescheduleEvent(Event e, EventTime startTime, EventTime endTime) methods work.
     */
    @Test
    void rescheduleEvent() {
        // add events e and e2 to weeklySchedule on wed. and fri.
        weekdayList.add(Weekday.WEDNESDAY);
        weekdayList.add(Weekday.FRIDAY);
        weeklySchedule.addEvent(e, weekdayList);
        weeklySchedule.addEvent(e2, weekdayList);

        // should return null if the event to be rescheduled does not exist on that day
        assertEquals(null, weeklySchedule.rescheduleEvent(e, Weekday.SUNDAY, startTime, endTime));

        // create event times to reschedule event e on wed.
        EventTime startTime3 = new EventTime(8, 30, "pm");
        EventTime endTime3 = new EventTime(10, 45, "pm");
        weeklySchedule.rescheduleEvent(e, Weekday.WEDNESDAY, startTime3, endTime3);

        // check that start time of e is now the new start time
        assertEquals(startTime3, weeklySchedule.getDailySchedule(Weekday.WEDNESDAY).events.get(1).getStartTime());
        // check to make sure that there are still 2 events on Wednesday as there were originally
        assertEquals(2, weeklySchedule.getDailySchedule(Weekday.WEDNESDAY).events.size());
    }
}