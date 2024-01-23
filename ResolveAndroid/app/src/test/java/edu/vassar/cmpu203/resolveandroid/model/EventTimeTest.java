package edu.vassar.cmpu203.resolveandroid.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/*
 Test class of unit tests for the EventTime class methods.
 */
class EventTimeTest {
    EventTime eventTime;

    /*
    Unit test for EventTime.equals(Object o) method.
     */
    @Test
    void testEquals() {
        // initialize eventTime
        eventTime = new EventTime(1, 30, "am");
        // make a reference copy to eventTime
        EventTime eventTimeCopy = eventTime;
        // make a new EventTime object with the same time and format
        EventTime eventTimeSame = new EventTime(1, 30, "am");
        // make an EventTime object with a different time
        EventTime eventTimeDiff = new EventTime(2, 45, "am");

        // check that eventTime equals its copy and an EventTime with the same time,
        // but not an EventTime with a different time
        assertTrue(eventTime.equals(eventTimeCopy));
        assertTrue(eventTime.equals(eventTimeSame));
        assertFalse(eventTime.equals(eventTimeDiff));
    }

    /*
    Unit test for EventTime.getTimeMins() method.
     */
    @Test
    void getTimeMins() {
        eventTime = new EventTime(1, 30, "am");
        // check that 1:30am converts to 90 minutes
        assertEquals(90, eventTime.calculateTimeMins());

        eventTime = new EventTime(12, 40, "pm");
        // check that 12:40pm converts to 760 minutes
        assertEquals(760, eventTime.calculateTimeMins());

        eventTime.setHour(3);
        //check that 3:40pm converts to 940 minutes
        assertEquals(940, eventTime.calculateTimeMins());

        eventTime = new EventTime(12, 20, "am");
        // check that 12:20am converts to 20 minutes
        assertEquals(20, eventTime.calculateTimeMins());
    }

    /*
    Unit test for EventTime.toString() method.
     */
    @Test
    void testToString() {
        eventTime = new EventTime(7, 45, "pm");
        // create a string that is what the correct toString should look like
        String correctString = "7:45pm";
        // make sure eventTime.toString() equals this string
        assertEquals(correctString, eventTime.toString());
    }
}