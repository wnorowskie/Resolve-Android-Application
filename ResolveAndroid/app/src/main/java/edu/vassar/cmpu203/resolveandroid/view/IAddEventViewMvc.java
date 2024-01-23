package edu.vassar.cmpu203.resolveandroid.view;

import android.view.View;

import androidx.fragment.app.Fragment;

import java.util.List;

import edu.vassar.cmpu203.resolveandroid.model.EventTime;
import edu.vassar.cmpu203.resolveandroid.model.Weekday;

public interface IAddEventViewMvc {

    public interface Listener {
        void onAddEvent(String title, EventTime startTime, EventTime endTime, Weekday w, View view);
    }
}
