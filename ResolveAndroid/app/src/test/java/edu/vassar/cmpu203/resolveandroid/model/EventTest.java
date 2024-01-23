package edu.vassar.cmpu203.resolveandroid.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/*
 Test class of unit tests for the Event class methods.
 */
class EventTest {
    private Event e;

    /*
    Unit test for Event.equals(Object o) method.
     */
    @Test
    void testEquals() {
        // first, create times for event e
        EventTime startTime = new EventTime(3, 10, "pm");
        EventTime endTime = new EventTime(4, 25, "pm");
        e = new Event(startTime, endTime);

        // then create a complete copy of e
        Event eCopy = e;
        // create a new event with the same start and end time
        Event eSameTime = new Event(startTime, endTime);
        // create a new event with a different end time
        Event eDiffTime = new Event(startTime, startTime);
        // check that e is equal to its copy and a new event with the same start and
        // end times, but not an event with a different end time
        assertTrue(e.equals(eCopy));
        assertTrue(e.equals(eSameTime));
        assertFalse(e.equals(eDiffTime));
    }

    /*
    Unit test for Event.conflictsWith(Event e) method.
     */
    @Test
    void conflictsWith() {
        // create start and end times for event e
        EventTime startTime = new EventTime(5, 50, "am");
        EventTime endTime = new EventTime(7, 40, "am");

        // create start and end times for new event
        EventTime startTime2 = new EventTime(6, 30, "am");
        EventTime endTime2 = new EventTime(8, 46, "am");

        // set e and e2 to have their respective start and end times
        e = new Event(startTime, endTime);
        Event e2 = new Event(startTime2, endTime2);
        // as there is overlap between the times, calling conflictsWith should return true
        assertTrue(e.conflictsWith(e2));

        // create third event that does not conflict
        EventTime startTime3 = new EventTime(9, 31, "pm");
        EventTime endTime3 = new EventTime(11, 41, "pm");
        Event e3 = new Event(startTime3, endTime3);
        // check that conflictsWith returns false when this new event is an argument
        assertFalse(e.conflictsWith(e3));

        // create an event that overlaps the start time of e but not the end time
        EventTime startTime4 = new EventTime(4, 41, "am");
        EventTime endTime4 = new EventTime(6, 50, "am");
        Event e4 = new Event(startTime4, endTime4);
        // check to make sure that e and e4 conflict
        assertTrue(e.conflictsWith(e4));
    }

    /*
    Unit test for Event.setStartTime(EventTime startTime) method.
     */
    /* This functionality is now checked somewhere else
    @Test
    void setStartTime() {
        // create start and end times for e
        EventTime startTime = new EventTime(2, 40, "pm");
        EventTime endTime = new EventTime(3, 40, "pm");
        e = new Event(startTime, endTime);

        // create a start time that would be after e's end time (invalid)
        EventTime newStartTimeWrong = new EventTime(4, 40, "pm");
        // try to set e's start time to invalid start time
        e.setStartTime(newStartTimeWrong);
        // check that the start time did not actually change
        assertFalse(e.getStartTime().equals(newStartTimeWrong));
        assertTrue(e.getStartTime().equals(startTime));

        // create a new start time that would be valid for e and set e to have it
        EventTime newStartTimeRight = new EventTime(2, 45, "pm");
        e.setStartTime(newStartTimeRight);
        // check that e's start time has changed
        assertEquals(newStartTimeRight, e.getStartTime());
    }

     */

    /*
    Unit test for Event.setEndTime(EventTime endTime)
     */
    @Test
    void setEndTime() {
        // create start and end times for e
        EventTime startTime = new EventTime(7, 30, "am");
        EventTime endTime = new EventTime(8, 30, "am");
        e = new Event(startTime, endTime);

        // create invalid end time for e (occurs before its start time)
        EventTime newEndTimeWrong = new EventTime(6, 30, "am");
        // try to set e's end time to this
        e.setEndTime(newEndTimeWrong);
        // check that e's end time did not actually change
        assertFalse(e.getEndTime().equals(newEndTimeWrong));
        assertTrue(e.getEndTime().equals(endTime));

        // this new end time is "pm" on purpose to make sure that events can go from am to pm
        EventTime newEndTimeRight = new EventTime(9, 30, "pm");
        // set e's end time to this new valid time
        e.setEndTime(newEndTimeRight);
        // make sure e's end time changed
        assertEquals(newEndTimeRight, e.getEndTime());
    }
}