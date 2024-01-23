package edu.vassar.cmpu203.resolveandroid.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.vassar.cmpu203.resolveandroid.R;
import edu.vassar.cmpu203.resolveandroid.databinding.FragmentFriendRequestsBinding;
import edu.vassar.cmpu203.resolveandroid.model.Database;
import edu.vassar.cmpu203.resolveandroid.model.User;

public class FriendRequestFragment extends Fragment implements IFriendRequestViewMvc {
    private FragmentFriendRequestsBinding binding;
    IFriendRequestViewMvc.Listener listener;
    public FriendRequestFragment(IFriendRequestViewMvc.Listener listener){this.listener = listener;}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentFriendRequestsBinding.inflate(inflater);
        this.binding.requestList.removeView(this.binding.requestEntry);

        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        List<UUID> reqs = this.listener.getIncRequests();
        //TODO handle when requests are empty

        for (UUID uuid : reqs)
            //TODO this crashes the app, use Firestore to get users. Maybe want to change the requests to be saved by username
            this.listener.retrieveUserFriendRequests(uuid, this);
    }

    public void updateDisplay(User u){
            createNewEntry(u);
    }

    public void createNewEntry(User u){
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        inflater = inflater.cloneInContext(this.getContext());

        FragmentFriendRequestsBinding newBinding = FragmentFriendRequestsBinding.inflate(inflater);
        newBinding.requestList.removeView(newBinding.requestEntry);



        this.binding.requestList.addView(newBinding.requestEntry);

        //Create onclick listeners
        newBinding.acceptButton.setOnClickListener(v -> {
            Log.d("Accept", u.getUsername());
            this.binding.requestList.removeView(newBinding.requestEntry);
            this.listener.onAcceptRequest(u);
        });

        newBinding.denyButton.setOnClickListener(v -> {
            Log.d("Deny", u.getUsername());
            this.binding.requestList.removeView(newBinding.requestEntry);
            this.listener.onDenyRequest(u);
        });

        newBinding.name.setText(u.getUsername());

    }
}
