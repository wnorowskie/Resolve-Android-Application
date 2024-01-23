package edu.vassar.cmpu203.resolveandroid.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.vassar.cmpu203.resolveandroid.databinding.FragmentFriendListBinding;
import edu.vassar.cmpu203.resolveandroid.model.Controller;
import edu.vassar.cmpu203.resolveandroid.model.Database;
import edu.vassar.cmpu203.resolveandroid.model.User;

public class FriendListFragment extends Fragment implements IFriendsListViewMvc {

    private IFriendsListViewMvc.Listener friendListListener;
    private ISocialManagerViewMvc.Listener socialListener;
    private FragmentFriendListBinding binding;
    private List<UUID> shownUsers;

    public FriendListFragment(IFriendsListViewMvc.Listener friendListListener, ISocialManagerViewMvc.Listener socialListener){
        this.friendListListener = friendListListener;
        this.socialListener = socialListener;
        this.shownUsers = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentFriendListBinding.inflate(inflater);
        this.binding.friendsList.removeView(this.binding.friendEntry);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        clearFriendList();
        populateFriendList();

        this.binding.findFriend.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if(hasFocus) {
                clearFriendList();
            } else {
                binding.findFriend.restoreDefaultFocus();
                clearFriendList();
                populateFriendList();
                shownUsers = new ArrayList<>();
            }
        });

        this.binding.friendsListParent.setOnClickListener(v ->
        {
            binding.findFriend.clearFocus();
            binding.findFriend.setIconified(true);
        });

        this.binding.findFriend.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                clearFriendList();
                getUserFromSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        this.binding.viewPendingButton.setOnClickListener(v -> {
            this.friendListListener.onClickViewPending();
        });
    }

    private void getUserFromSearch(String s){
        friendListListener.onSearchForUser(s, this);
    }

    private void populateFriendList(){
        List<UUID> friends = this.friendListListener.getFriends();
        Log.d("List", "getting friends");

        //List<User> friendsUser = new ArrayList<User>();
        /*for(int i = 0; i < friends.size(); i++){
            friendsUser.add(Database.getUserRepo().get(friends.get(i)));
        }

        for(User u : friendsUser){
            createNewEntry(u, false);
            // Log.d("Friend", u.getUsername());
        }

         */

        for (UUID uuid : friends) {
            this.friendListListener.retrieveUserFriendsList(uuid, this);
        }

    }

    @Override
    public void updateDisplay(User u, boolean canSendRequest) {
        if(!shownUsers.contains(u.getUuid())){
                createNewEntry(u, canSendRequest);
                shownUsers.add(u.getUuid());
                binding.findFriend.setQuery("", false);
        }
    }

    private void clearFriendList(){
        this.shownUsers.clear();
        this.binding.friendsList.removeAllViewsInLayout();
    }

    private View createNewEntry(User u, boolean canSendRequest){
        LayoutInflater inflater = LayoutInflater.from(this.binding.getRoot().getContext());
        inflater = inflater.cloneInContext(this.binding.getRoot().getContext());

        FragmentFriendListBinding newBinding = FragmentFriendListBinding.inflate(inflater);
        newBinding.friendsList.removeView(newBinding.friendEntry);
        newBinding.name.setText(u.getUsername());
        this.binding.friendsList.addView(newBinding.friendEntry);

        if(canSendRequest){
            newBinding.sendRequestButton.setVisibility(View.VISIBLE);
            newBinding.sendRequestButton.setOnClickListener(v -> {
                this.socialListener.onRequestFriend(u);
                newBinding.sendRequestButton.setVisibility(View.INVISIBLE);
            });
        } else {
            newBinding.sendRequestButton.setVisibility(View.VISIBLE);
            newBinding.sendRequestButton.setText("Schedule");
            newBinding.sendRequestButton.setOnClickListener(v -> {
                this.socialListener.onViewFriendSchedule(u);
            });
        }

        return newBinding.friendEntry;
    }
}
