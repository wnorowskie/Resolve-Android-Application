package edu.vassar.cmpu203.resolveandroid.view;

import androidx.fragment.app.Fragment;

import edu.vassar.cmpu203.resolveandroid.model.Event;

public interface IEventDescriptionFragmentMvc {
    public interface Listener{
        void closeEventDescription(Fragment f);
        void deleteEventFromSchedule(Event event);
    }
}
