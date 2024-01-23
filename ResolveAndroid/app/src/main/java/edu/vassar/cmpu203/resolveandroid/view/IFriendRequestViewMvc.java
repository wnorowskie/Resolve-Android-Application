package edu.vassar.cmpu203.resolveandroid.view;

import java.util.List;
import java.util.UUID;

import edu.vassar.cmpu203.resolveandroid.model.User;

public interface IFriendRequestViewMvc {

    public interface Listener{
        List<UUID> getIncRequests();
        void onAcceptRequest(User newFriend);
        void onDenyRequest(User deniedFriend);
        void onReturnToFriendList();

        void retrieveUserFriendRequests(UUID uuid, IFriendRequestViewMvc view);
    }

    void updateDisplay(User u);
}
