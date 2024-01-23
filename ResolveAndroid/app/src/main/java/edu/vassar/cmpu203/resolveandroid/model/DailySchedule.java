package edu.vassar.cmpu203.resolveandroid.model;

import androidx.annotation.CheckResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DailySchedule implements Serializable {

    List<Event> events;
    Weekday day;

    private static final String EVENTS = "events";
    private static final String DAY = "day";

    public DailySchedule(){}

    public DailySchedule(Weekday day){
        this.day = day;
        this.events = new ArrayList<>();
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> dailySchedule = new ArrayList<>();
        for (Event e : events){
            dailySchedule.add(e.toMap());
        }

        map.put(EVENTS, dailySchedule);
        map.put(DAY, day);

        return map;
    }

    public static DailySchedule fromMap(Map<String, Object> map){
        DailySchedule ds = new DailySchedule();

        List<Map<String, Object>> eventMapList = ((List<Map<String, Object>>) map.get(EVENTS));
        List<Event> eventList = new ArrayList<Event>();
        for (Map<String, Object> eventMap : eventMapList){
            eventList.add(Event.fromMap(eventMap));
        }
        ds.events = eventList;
        ds.day = Weekday.fromMap((Map<String, Object>) map.get(DAY));

        return ds;
    }

    /*
     * @return list of indices of all conflicting events to use when telling the user which or how many events conflict. If list is empty, no conflicts
     */
    public List<Integer> getConflicts(Event e){
        List<Integer> conflicts = new ArrayList<>();

        for(int i = 0; i < this.events.size(); i++){
            if(events.get(i).conflictsWith(e)){
                conflicts.add(i);
            }
        }

        return conflicts;
    }

    //Return list of conflicting events if there are any, otherwise add the event to schedule
    public List<Integer> addEvent(Event e){
        List<Integer> conflicts = this.getConflicts(e);
        this.events.add(e);

        return conflicts;
    }
    //Return false if for whatever reason the event we're trying to remove isn't already in the schedule
    public boolean removeEvent(Event e){
        if(this.events.contains(e)){
            this.events.remove(e);
            return true;
        }
        return false;
    }

    //Return null if the event being modified isn't already on the schedule, return a list of indices representing each conflicting event otherwise.
    //Like with getConflcits, if the list is empty there are no conflicting events
    //TODO check if new start and end times are valid (also this method might be better in the controller but not sure right now)
    public List<Integer> rescheduleEvent(Event oldEvent, EventTime newStart, EventTime newEnd){
        if(this.events.contains(oldEvent)){

            //Make a new event with the old event's fields except for the start and end time, and attempt to add it to the schedule.
            Event newEvent = new Event(newStart, newEnd);
            newEvent.setTitle(oldEvent.getTitle());
            newEvent.setNotes(oldEvent.getNotes());

            removeEvent(oldEvent);

            return addEvent(newEvent);
        }
        return null;
    }

    public List<Event> getEvents(){
        return this.events;
    }

    public Weekday getDay(){ return this.day;}
}
