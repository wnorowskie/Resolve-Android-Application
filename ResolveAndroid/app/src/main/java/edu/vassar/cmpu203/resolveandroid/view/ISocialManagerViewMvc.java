package edu.vassar.cmpu203.resolveandroid.view;

import java.util.ArrayList;

import edu.vassar.cmpu203.resolveandroid.model.Database;
import edu.vassar.cmpu203.resolveandroid.model.Group;
import edu.vassar.cmpu203.resolveandroid.model.Org;
import edu.vassar.cmpu203.resolveandroid.model.User;

public interface ISocialManagerViewMvc {

    public interface Listener {

        public void onRequestFriend(User u);

        public void onCreateGroup(String groupname, ArrayList<User> memberList, ISocialManagerViewMvc socialManagerViewMvc);

        public void onLeaveGroup(Group group, ISocialManagerViewMvc socialManagerViewMvc);

        public void onAddOrg(Org org, ISocialManagerViewMvc socialManagerViewMvc);

        public void onLeaveOrg(Org org, ISocialManagerViewMvc socialManagerViewMvc);

        public void onClickGroups();

        public void onClickSchedule();

        public void onClickFriends();

        public void onClickOrganizations();

        public void onReturntoManager();

        public String getCurUsername();

        void onViewFriendSchedule(User u);

    }
}
