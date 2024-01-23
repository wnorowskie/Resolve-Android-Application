package edu.vassar.cmpu203.resolveandroid.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/*
 Test class of unit tests for the DailySchedule class methods.
 */
class DailyScheduleTest {
    // create an event and a saturday schedule for use in the tests
    EventTime startTime = new EventTime(3, 45, "pm");
    EventTime endTime = new EventTime(5, 50, "pm");
    Event e = new Event(startTime, endTime);
    Weekday day = Weekday.SATURDAY;
    DailySchedule dailySchedule = new DailySchedule(day);

    /*
    Unit test for DailySchedule.getConflicts(Event e) method.
     */
    @Test
    void getConflicts() {
        // first, add event e to dailySchedule's list of events
        dailySchedule.events.add(e);
        // create new event with a conflicting time (overlaps with e)
        EventTime startTime2 = new EventTime(4, 45, "pm");
        EventTime endTime2 = new EventTime(6, 40, "pm");
        Event e2 = new Event(startTime2, endTime2);

        // create a list of integers to compare to the result of getConflicts()
        List<Integer> list1 = new ArrayList<>();
        list1.add(0);
        // since adding the second event would create a conflict, getConflicts()
        // should return 1 conflict (list with 0, as e is the first element)
        assertEquals(list1, dailySchedule.getConflicts(e2));

        // add event e2 to dailySchedule's event list
        dailySchedule.events.add(e2);
        // create third event that conflicts with both
        EventTime startTime3 = new EventTime(3, 25, "pm");
        EventTime endTime3 = new EventTime(9, 40, "pm");
        Event e3 = new Event(startTime3, endTime3);

        list1.add(1);
        // since dailySchedule should now have 2 conflicts, getConflicts() should
        // return a list with 0 and 1 (e and e2 are elements 1 and 2)
        assertEquals(list1, dailySchedule.getConflicts(e3));
    }

    /*
    Unit test for DailySchedule.addEvent(Event e) method.
     */
    @Test
    void addEvent() {
        // first, create empty array list
        List<Integer> list1 = new ArrayList<>();
        // adding event e to dailySchedule should have no conflicts,
        // so it should return an empty list of integers
        assertTrue(dailySchedule.addEvent(e).equals(list1));
        // make sure that e has actually been added
        assertEquals(e, dailySchedule.events.get(0));

        // create new event that conflicts with e
        EventTime startTime2 = new EventTime(4, 45, "pm");
        EventTime endTime2 = new EventTime(6, 40, "pm");
        Event e2 = new Event(startTime2, endTime2);

        list1.add(0);
        // adding e2 to dailySchedule should have a conflict with event 0 (e)
        assertEquals(list1, dailySchedule.addEvent(e2));
        // make sure that e2 has been added and e is still there as well
        assertEquals(e, dailySchedule.events.get(0));
        assertEquals(e2, dailySchedule.events.get(1));
    }

    /*
    Unit test for DailySchedule.removeEvent(Event e) method.
    Assumption: DailySchedule.addEvent(Event e) method works.
     */
    @Test
    void removeEvent() {
        // first, add event e to dailySchedule.
        dailySchedule.addEvent(e);
        // since e is present in dailySchedule's event list,
        // dailySchedule.removeEvent(e) should return true.
        assertTrue(dailySchedule.removeEvent(e));

        // check that event e was actually removed
        assertEquals(0, dailySchedule.events.size());
        assertFalse(dailySchedule.removeEvent(e));
    }

    /*
    Unit test for DailySchedule.rescheduleEvent(Event e, EventTime startTime,
    EventTime endTime) method.
    Assumption: DailySchedule.addEvent(Event e) method works.
     */
    @Test
    void rescheduleEvent() {
        // rescheduling event e should return null as it has not yet been added
        assertEquals(null, dailySchedule.rescheduleEvent(e, startTime, endTime));
        dailySchedule.addEvent(e);

        // create new event with new start and end time
        EventTime startTime2 = new EventTime(4, 45, "pm");
        EventTime endTime2 = new EventTime(6, 40, "pm");
        Event e2 = new Event(startTime2, endTime2);

        List<Integer> list1 = new ArrayList<>();
        // rescheduling event e to have the new start and end times
        // should have no conflicts, thus returning empty list
        assertEquals(list1, dailySchedule.rescheduleEvent(e, startTime2, endTime2));
        // check that dailySchedule's event list contains an event with startTime2
        // and endTime2, and that it no longer contains event 1
        assertTrue(dailySchedule.events.contains(e2));
        assertFalse(dailySchedule.events.contains(e));
    }
}