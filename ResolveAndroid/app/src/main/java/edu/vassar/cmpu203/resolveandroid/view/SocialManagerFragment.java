package edu.vassar.cmpu203.resolveandroid.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.vassar.cmpu203.resolveandroid.databinding.FragmentSocialManagerBinding;

public class SocialManagerFragment extends Fragment implements ISocialManagerViewMvc {
    private FragmentSocialManagerBinding binding;
    private Listener listener;

    public SocialManagerFragment(Listener listener){ this.listener = listener; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = FragmentSocialManagerBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        this.binding.usernameProfile.setText(listener.getCurUsername());

        this.binding.groupsButton.setOnClickListener(v -> {
            listener.onClickGroups();
        });

        this.binding.scheduleButton.setOnClickListener(v ->{
            listener.onClickSchedule();
        });

        this.binding.friendsButton.setOnClickListener(v -> listener.onClickFriends());

        this.binding.orgsButton.setOnClickListener(v -> listener.onClickOrganizations());

    }
}