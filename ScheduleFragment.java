package edu.vassar.cmpu203.resolveandroid.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.List;

import edu.vassar.cmpu203.resolveandroid.R;
import edu.vassar.cmpu203.resolveandroid.databinding.FragmentScheduleBinding;
import edu.vassar.cmpu203.resolveandroid.model.Event;
import edu.vassar.cmpu203.resolveandroid.model.User;
import edu.vassar.cmpu203.resolveandroid.model.Weekday;

public class ScheduleFragment extends Fragment implements IScheduleViewMvc{

    private static final int HOUR_DP = 100;
    private FragmentScheduleBinding binding;
    private EventDescriptionFragment eventDescriptionFragment = null;
    @Nullable
    private AddEventFragment addEventFragment;
    private int curDay = 1;
    private float scale;
    private User user;
    Listener listener;

    public ScheduleFragment(Listener listener) {
        this.listener = listener;
        this.user = null;
    }

    public ScheduleFragment(Listener listener, User u){
        this.listener = listener;
        this.user = u;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentScheduleBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    public void setAddEventButtonFragment(Fragment f){
        this.addEventFragment = (AddEventFragment) f;
        this.addEventFragment.setDay(Weekday.values().get(curDay));
    }
/*
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(this.addEventFragment != null) {
            this.getActivity().getSupportFragmentManager().beginTransaction().remove(this.addEventFragment).commit();
        }

    }

 */

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.scale = getResources().getDisplayMetrics().density;

        this.binding.prevDayButton.setOnClickListener(view1 -> {
            this.curDay = (this.curDay + (7 - 1)) % 7;
            if(this.eventDescriptionFragment != null && this.eventDescriptionFragment.isAdded()){
                this.eventDescriptionFragment.close();
                this.eventDescriptionFragment = null;
            }
            displayEventsForCurDay();
            this.binding.eventListScroll.smoothScrollTo(0, (int)this.binding.hourText6.getY());
            if(this.addEventFragment != null) {
                this.addEventFragment.setDay(Weekday.values().get(curDay));
            }
        });

        this.binding.nextDayButton.setOnClickListener(v -> {
            this.curDay = (this.curDay + 1) % 7;
            if(this.eventDescriptionFragment != null && this.eventDescriptionFragment.isAdded()){
                this.eventDescriptionFragment.close();
                this.eventDescriptionFragment = null;
            }
            displayEventsForCurDay();
            this.binding.eventListScroll.smoothScrollTo(0, (int)this.binding.hourText6.getY());
            if(this.addEventFragment != null) {
                this.addEventFragment.setDay(Weekday.values().get(curDay));
            }
        });

        this.binding.getRoot().post(() -> {
            displayEventsForCurDay();
            this.binding.eventListScroll.smoothScrollTo(0, (int)this.binding.hourText6.getY());
        });

        if(this.addEventFragment != null) {
            Handler handler = new Handler();
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    if (addEventFragment.getDirty()) {
                        displayEventsForCurDay();
                        addEventFragment.setDirty(false);
                    }
                    handler.postDelayed(this, 200);
                }
            };

            handler.postDelayed(r, 200);
        }
    }


    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        displayEventsForCurDay();
    }

    private void displayEventsForCurDay(){
        this.binding.dayText.setText(Weekday.values().get(curDay).getName());
        this.binding.eventListLayout.removeAllViews();
        List<Event> events = this.listener.loadEventsForDay(this.curDay, this.user);
        displayEvents(events);
    }

    private int timeMinsToDp(int mins){
        double hours = mins/60.0;
        return (int) (hours * HOUR_DP);
    }

    private void displayEvents(List<Event> events){
        for(Event e : events){
            addEventToScreen(e);
        }
    }

    private void addEventToScreen(Event e){
        int startDp = timeMinsToDp(e.getStartTime().calculateTimeMins());
        int endDp = timeMinsToDp(e.getEndTime().calculateTimeMins());
        int len = endDp - startDp;
        int scaledHeight = (int)(scale * len);

        //Get screen pixel density

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(this.binding.eventListLayout.getWidth(), scaledHeight);
        params.setMargins(0, (int)(scale * startDp), 0, 0);


        Button eventView = new Button(this.binding.getRoot().getContext());

        eventView.setOnClickListener(v -> {
            if(this.eventDescriptionFragment == null || !this.eventDescriptionFragment.isVisible()){
                this.eventDescriptionFragment = (EventDescriptionFragment) listener.onClickEvent(e, eventView, binding, this.user == null);
                this.binding.eventListScroll.smoothScrollTo(0, (int)eventView.getY());
            }
        });

        eventView.setGravity(Gravity.NO_GRAVITY);
        eventView.setTransformationMethod(null);
        eventView.setLayoutParams(params);
        eventView.setText(e.getTitle());
        eventView.setPadding(25, 15, 0, 0);
        eventView.setBackground(createEventBlock(scaledHeight));

        this.binding.eventListLayout.addView(eventView);
    }

    private GradientDrawable createEventBlock(int height){
        GradientDrawable block = new GradientDrawable();
        block.setShape(GradientDrawable.RECTANGLE);
        block.setCornerRadius(35f);
        block.setColor(ContextCompat.getColor(this.binding.getRoot().getContext(), R.color.colorPrimary));
        block.setAlpha(80);
        block.setSize(0, height);
        return block;
    }
}
