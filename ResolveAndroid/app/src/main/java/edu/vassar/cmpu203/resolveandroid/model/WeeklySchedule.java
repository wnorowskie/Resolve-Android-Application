package edu.vassar.cmpu203.resolveandroid.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeeklySchedule implements Serializable {
    // most of the commented out bits of code were from an array implementation of days field

    //private DailySchedule[] days;
    private List<DailySchedule> days;

    private static final String DAYS = "days";

    public WeeklySchedule(){
        /*Weekday[] weekdays = Weekday.values();
        this.days = new DailySchedule[weekdays.length];
        for(int i = 0; i < weekdays.length; i++){
            this.days[weekdays[i].getId()] = new DailySchedule(weekdays[i]);
        }
        */
        List<Weekday> weekdays = Weekday.values();
        this.days = new ArrayList<DailySchedule>();
        for (int i = 0; i < weekdays.size(); i++)
            this.days.add(new DailySchedule(weekdays.get(i)));
    }

    public Map<String, Object> toMap()
    {
        List<Map<String, Object>> weeklySchedule = new ArrayList<>();
        for(DailySchedule ds : days){
            weeklySchedule.add(ds.toMap());
        }

        Map<String, Object> ws = new HashMap<>();
        ws.put(DAYS, weeklySchedule);
        return ws;
    }

    public static WeeklySchedule fromMap(Map<String, Object> map){
        WeeklySchedule ws = new WeeklySchedule();

        List<DailySchedule> dailyScheduleList = new ArrayList<DailySchedule>();
        List<Map<String, Object>> dsMapList = (List<Map<String, Object>>) map.get(DAYS);
        for (Map<String, Object> dsMap : dsMapList)
            dailyScheduleList.add(DailySchedule.fromMap(dsMap));

        ws.days = dailyScheduleList;
        return ws;
    }

    //Get the scheudle for one day of the week
    public DailySchedule getDailySchedule(Weekday day){
        //return this.days[day.getId()];
        return this.days.get(day.getId());
    }

    //Allows us to add an event to a weekly schedule given a day
    public void addEvent(Event e, List<Weekday> w){
        for(Weekday i : w){
            //this.days[i.getId()].addEvent(e);
            this.days.get(i.getId()).addEvent(e);
        }
    }

    public boolean removeEvent(Event e) {
        boolean result = false;
        for(Weekday w : Weekday.values()){
            if(this.days.get(w.getId()).removeEvent(e)) result = true;
        }
        return result;
    }

    public List<Integer> rescheduleEvent(Event e, Weekday w, EventTime newStart, EventTime newEnd){
        //return this.days[w.getId()].rescheduleEvent(e, newStart, newEnd);
        return this.days.get(w.getId()).rescheduleEvent(e, newStart, newEnd);
    }

    public List<DailySchedule> getDays() {
        return days;
    }
}
