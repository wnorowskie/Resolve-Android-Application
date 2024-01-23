package edu.vassar.cmpu203.resolveandroid.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.vassar.cmpu203.resolveandroid.databinding.FragmentEventInfoBinding;
import edu.vassar.cmpu203.resolveandroid.databinding.FragmentScheduleBinding;
import edu.vassar.cmpu203.resolveandroid.model.Event;

public class EventDescriptionFragment extends Fragment {

    private IEventDescriptionFragmentMvc.Listener listener;
    private Event event;
    private View creatorView;
    private FragmentEventInfoBinding binding;
    private FragmentScheduleBinding scheduleBinding;
    private boolean canEditSchedule;

    public EventDescriptionFragment(IEventDescriptionFragmentMvc.Listener listener, Event event, View creatorView, FragmentScheduleBinding scheduleBinding, boolean canEditSchedule){
        this.scheduleBinding = scheduleBinding;
        this.listener = listener;
        this.event = event;
        this.creatorView = creatorView;
        this.canEditSchedule = canEditSchedule;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentEventInfoBinding.inflate(inflater);
        if(!canEditSchedule)
            removeDeleteButton();
        return this.binding.getRoot();
    }

    public void removeDeleteButton(){
        this.binding.eventDescButtons.removeView(this.binding.delButton);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        float scale = getResources().getDisplayMetrics().density;
        int eventWidth = this.creatorView.getWidth();
        int width = this.binding.eventDesc.getWidth();
        int xPos = (((Math.max(eventWidth, width) - Math.min(eventWidth, width)) / 2)) - (int)(scale * 75);

        //TODO get rid of these constants
        int scaledX = (int)(scale * xPos);
        int scaledY = this.creatorView.getHeight() + this.scheduleBinding.linearLayout5.getHeight();
        this.binding.eventDesc.setY(scaledY);
        this.binding.eventDesc.setX(xPos);

        this.binding.eventName.setText(this.event.getTitle());
        String timesString = this.event.getStartTime().toString() + " - " + this.event.getEndTime();
        this.binding.eventTimes.setText(timesString);
        String notes = "";
        for(String n : this.event.getNotes()){
            notes += " - " + n + "\n";
        }
        this.binding.eventNotes.setText(notes);

        this.binding.closeButton.setOnClickListener(v -> {
            close();
        });

        this.binding.delButton.setOnClickListener(v -> {
            listener.deleteEventFromSchedule(this.event);
            this.creatorView.setVisibility(View.INVISIBLE);
            close();
        });
    }

    public void close(){
        listener.closeEventDescription(this);
    }
}
