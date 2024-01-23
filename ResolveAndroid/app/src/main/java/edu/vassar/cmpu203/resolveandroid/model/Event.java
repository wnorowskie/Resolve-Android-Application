package edu.vassar.cmpu203.resolveandroid.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Event implements Serializable {

    private EventTime startTime;
    private EventTime endTime;
    private String title;
    //In case a user want's to add info about an event
    private List<String> notes;
    private String eventColor;
    private UUID uuid;

    private static final String STARTTIME = "starttime";
    private static final String ENDTIME = "endtime";
    private static final String TITLE = "title";
    private static final String NOTES = "notes";
    private static final String EVENTCOLOR = "eventcolor";
    private static final String UUID_STRING = "uuid";

    public Event(){}

    public Event(EventTime startTime, EventTime endTime){
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = "Untitled Event";
        this.notes = new ArrayList<>();
        this.eventColor = "blue"; // default
        this.uuid = UUID.randomUUID();
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put(STARTTIME, startTime);
        map.put(ENDTIME, endTime);
        map.put(TITLE, title);
        map.put(NOTES, notes);
        map.put(EVENTCOLOR, eventColor);
        map.put(UUID_STRING, uuid.toString());

        return map;
    }

    public static Event fromMap(Map<String, Object> map){
        Event event = new Event();
        event.startTime = EventTime.fromMap((Map<String, Object>) map.get(STARTTIME));
        event.endTime =  EventTime.fromMap((Map<String, Object>) map.get(ENDTIME));
        event.title = (String) map.get(TITLE);
        event.notes = (List<String>) map.get(NOTES);
        event.eventColor = (String) map.get(EVENTCOLOR);
        event.uuid = UUID.fromString((String) map.get(UUID_STRING));

        return event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return startTime.equals(event.startTime) && endTime.equals(event.endTime) && Objects.equals(title, event.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, title, notes, eventColor);
    }

    public boolean conflictsWith(Event e){
       return (this.getStartTime().calculateTimeMins() <= e.getEndTime().calculateTimeMins()) && (this.getEndTime().calculateTimeMins() >= e.getStartTime().calculateTimeMins());
    }

    //TODO maybe add a method for getting the interval that two events conflict on

    public void setStartTime(EventTime time){
        //new start time cant be later than end time
        /*
        if(time.calculateTimeMins() > this.endTime.calculateTimeMins()){
            //TODO add logger message or handle this checking somewhere else?
            return;
        }
        
         */
        this.startTime = time;
    }

    public void setEndTime(EventTime time){
        //new end time cant be earlier than start time
        if(time.calculateTimeMins() < this.startTime.calculateTimeMins()){
            //TODO add logger message or handle this checking somewhere else?
            return;
        }
        this.endTime = time;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setNotes(List<String> notes){
        this.notes = notes;
    }

    public String getTitle(){
        return this.title;
    }

    public EventTime getStartTime(){
        return this.startTime;
    }

    public EventTime getEndTime(){
        return this.endTime;
    }

    public void addNote(String note){
        this.notes.add(note);
    }

    public void removeNote(String note){
        if(this.notes.contains(note)){
            this.notes.remove(note);
        }
    }

    public List<String> getNotes(){
        return this.notes;
    }

    public String getEventColor(){return this.eventColor; }

    public UUID getUuid() {
        return uuid;
    }
}
