package edu.vassar.cmpu203.resolveandroid.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Weekday implements Serializable {

    //Added string names in case we need them a lot and don't want to hardcode them in a ton of places
    //The IDs are used when initializing a weekly schedule as indices into the array of daily schedules
    public static Weekday SUNDAY = new Weekday(0, "Sunday");
    public static Weekday MONDAY = new Weekday(1, "Monday");
    public static Weekday TUESDAY = new Weekday(2, "Tuesday");
    public static Weekday WEDNESDAY = new Weekday(3, "Wednesday");
    public static Weekday THURSDAY = new Weekday(4, "Thursday");
    public static Weekday FRIDAY = new Weekday(5, "Friday");
    public static Weekday SATURDAY = new Weekday(6, "Saturday");


    protected int id;
    protected String name;

    public Weekday(){}

    Weekday(int id, String name){
        this.id = id;
        this.name = name;
    }

    public static Weekday fromMap(Map<String, Object> map){
        Weekday weekday = new Weekday();
        weekday.id = (int)(long)map.get("id");
        weekday.name = (String) map.get("name");

        return weekday;
    }

    public static List<Weekday> values(){
        List<Weekday> weekdayList = new ArrayList<Weekday>();
        weekdayList.add(SUNDAY); weekdayList.add(MONDAY); weekdayList.add(TUESDAY);
        weekdayList.add(WEDNESDAY); weekdayList.add(THURSDAY); weekdayList.add(FRIDAY);
        weekdayList.add(SATURDAY);

        return weekdayList;
    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return this.id;
    }
}
