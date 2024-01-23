package edu.vassar.cmpu203.resolveandroid.view;

import java.util.List;
import java.util.UUID;

import edu.vassar.cmpu203.resolveandroid.model.User;

public interface IFriendsListViewMvc {

    public interface Listener{
        List<UUID> getFriends();
        void onClickViewPending();
        void onSearchForUser(String s, IFriendsListViewMvc view);
        void retrieveUserFriendsList(UUID uuid, IFriendsListViewMvc view);
    }

    void updateDisplay(User u, boolean canSendRequest);
}
