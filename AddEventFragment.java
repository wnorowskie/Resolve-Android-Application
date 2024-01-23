package edu.vassar.cmpu203.resolveandroid.view;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.vassar.cmpu203.resolveandroid.databinding.FragmentAddEventBinding;
import edu.vassar.cmpu203.resolveandroid.model.EventTime;
import edu.vassar.cmpu203.resolveandroid.model.Weekday;

public class AddEventFragment extends Fragment implements IAddEventViewMvc{

    FragmentAddEventBinding binding;
    private EventTime startTime;
    private EventTime endTime;
    private Listener listener;
    private Weekday day;
    private boolean dirty;


    public AddEventFragment(Listener listener){
        this.listener = listener;
        this.dirty = false;
    }

    public void setDay(Weekday day){
        this.day = day;
    }

    public boolean getDirty(){
        return this.dirty;
    }

    public void setDirty(boolean b){
        this.dirty = b;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentAddEventBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.binding.timesInput.setVisibility(View.INVISIBLE);
        int maxAlpha = this.binding.eventInfo.getBackground().getAlpha();
        this.binding.eventInfo.getBackground().setAlpha(0);
        this.binding.addButton.getBackground().setAlpha(maxAlpha);

        this.binding.newEventButton.setOnClickListener(v -> {
            this.binding.timesInput.setVisibility(View.VISIBLE);
            this.binding.newEventButton.setVisibility(View.INVISIBLE);
            this.binding.eventInfo.getBackground().setAlpha(maxAlpha);
            this.binding.getRoot().setClickable(true);
        });

        this.binding.closeAddEventButton.setOnClickListener(v -> {
            this.binding.timesInput.setVisibility(View.INVISIBLE);
            this.binding.newEventButton.setVisibility(View.VISIBLE);
            this.binding.eventInfo.getBackground().setAlpha(0);
            this.binding.getRoot().setClickable(false);
        });

        //Get the current time to display for default values
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        int format = mcurrentTime.get(Calendar.AM_PM);

        hour = hour % 12;
        if(hour == 0)
            hour = 12;

        String ampm = (format == 0 ? "AM" : "PM");

        //Create default event times
        startTime = new EventTime(hour, minute, ampm);
        endTime = new EventTime(hour , minute, ampm);
        //Add leading 0 to minutes
        String minuteString;
        if(minute < 10)
            minuteString = "0" + minute;
        else
            minuteString = Integer.toString(minute);
        String curTime = hour + ":" + minuteString + ampm;

        //Grab start time and end time buttons
        Button start = this.binding.startTimeButton;
        Button end = this.binding.endTimeButton;
        //Set the default text on start and end buttons to the current time
        start.setText(curTime);
        end.setText(curTime);

        //Bring up the timepicker dialogue
        start.setOnClickListener(v -> {
            pickTime(view, start, startTime);
        });

        end.setOnClickListener(v -> {
            pickTime(view, end, endTime);
        });

        //User clicks add event button
        this.binding.addButton.setOnClickListener(v -> {

            String name = this.binding.eventNameText.getText().toString();

            //Check if the start and end times are valid
            if((startTime.calculateTimeMins() == endTime.calculateTimeMins()) || (startTime.calculateTimeMins() > endTime.calculateTimeMins())){
                Snackbar.make(view, "Invalid times", Snackbar.LENGTH_SHORT).show();
            }
            //Check if the user gave the event a name
            else if(name.isEmpty()){
                Snackbar.make(view, "Enter event name", Snackbar.LENGTH_LONG).show();
            } else{
                EventTime startTimeTemp = new EventTime(startTime.getHour(), startTime.getMinute(), startTime.getFormat());
                EventTime endTimeTemp = new EventTime(endTime.getHour(), endTime.getMinute(), endTime.getFormat());
                Snackbar.make(view, "Event added!", Snackbar.LENGTH_SHORT).show();
                this.listener.onAddEvent(name, startTimeTemp, endTimeTemp, day, view);
                dirty = true;
            }
        });
    }

    private void pickTime(View view, Button button, EventTime eventTime){
        Calendar mcurrentTime1 = Calendar.getInstance();
        int hour1 = mcurrentTime1.get(Calendar.HOUR_OF_DAY);
        int minute1 = mcurrentTime1.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(view.getContext(), createOnSetTimeListener(button, eventTime), hour1, minute1, false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private TimePickerDialog.OnTimeSetListener createOnSetTimeListener(Button button, EventTime eventTime){
        return (timePicker, selectedHour, selectedMinute) -> {

            String format1;
            if(selectedHour < 12 ){
                format1 = "AM";
            } else if (selectedHour > 12){
                format1 = "PM";
                selectedHour-=12;
            } else {
                format1 = "PM";
            }

            if(selectedHour == 0){
                selectedHour = 12;
            }

            String minuteString1;

            if(selectedMinute < 10){
                minuteString1 = "0" + selectedMinute;
            } else {
                minuteString1 = Integer.toString(selectedMinute);
            }

            button.setText("" + selectedHour + ":" + minuteString1 + format1);
            eventTime.setHour(selectedHour);
            eventTime.setMinute(selectedMinute);
            eventTime.setFormat(format1);
        };
    }
}
