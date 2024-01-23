package edu.vassar.cmpu203.resolveandroid.view;

import android.view.View;

import androidx.fragment.app.Fragment;

import java.util.List;

import edu.vassar.cmpu203.resolveandroid.databinding.FragmentScheduleBinding;
import edu.vassar.cmpu203.resolveandroid.model.Event;
import edu.vassar.cmpu203.resolveandroid.model.User;

public interface IScheduleViewMvc {
    public interface Listener{
        List<Event> loadEventsForDay(int day, User u);
        Fragment onClickEvent(Event event, View creator, FragmentScheduleBinding binding, boolean canEdit);
        void onScrollEventList(Fragment f);
    }
}
