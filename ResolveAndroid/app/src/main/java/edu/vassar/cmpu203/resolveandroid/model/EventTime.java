package edu.vassar.cmpu203.resolveandroid.model;

import android.util.Log;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class EventTime implements Serializable {

    private int hour;
    private int minute;
    private String format;

    public EventTime(){}

    public EventTime(int hour, int minutes, String format){
        this.hour = hour;
        this.minute = minutes;
        this.format = format;
    }

    public static EventTime fromMap(Map<String, Object> map){
        EventTime et = new EventTime();

        et.hour = (int)(long) map.get("hour");
        et.minute = (int)(long) map.get("minute");
        et.format = (String) map.get("format");

        return et;
    }

    public int getHour(){
        return this.hour;
    }

    public int getMinute(){
        return this.minute;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute){
        this.minute = minute;
    }

    //Using this for testing duplicate events
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventTime eventTime = (EventTime) o;
        return hour == eventTime.hour && minute == eventTime.minute && Objects.equals(format, eventTime.format);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute, format);
    }

    public void setFormat(String format){
        this.format = format;
    }

    //Used mostly for testing whether one time is greater or less than another
    public int calculateTimeMins(){
        int totalHours;
        totalHours = this.hour;

        //TODO test this to make sure it covers all conditions
        if(this.format.toLowerCase(Locale.ROOT).equals("pm") && totalHours < 12){
            totalHours += 12;
        }
        if(this.format.toLowerCase(Locale.ROOT).equals("am") && totalHours == 12){
            totalHours = 0;
        }
        return (totalHours * 60) + this.minute;

    }

    public String getFormat(){
        return this.format;
    }

    @Override
    public String toString(){
        String minString = "";
        if(this.getMinute() < 10){
            minString = "0" + (this.getMinute());
        } else {
            minString = Integer.toString(this.getMinute());
        }
        return String.format("%s:%s%s", this.getHour(), minString, this.getFormat());
    }
}
