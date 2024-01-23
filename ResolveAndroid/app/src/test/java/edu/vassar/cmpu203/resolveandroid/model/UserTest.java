package edu.vassar.cmpu203.resolveandroid.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
 Test class of unit tests for the User class.
 */
class UserTest {
    // create 2 users, user and friend
    User user = new User("username", "password");
    User friend = new User("friend", "friend");
    // create empty array list of users
    private List<UUID> emptyList = new ArrayList<>();

    /*
    Unit test for User.addPendingRequest(User u) method.
     */
    @Test
    void addPendingRequest() {
        // check that user's friends list is empty
        assertEquals(emptyList, user.getFriendsList());
        // send a request to friend
        user.addPendingRequest(friend);

        // create user array lists to compare to
        List<UUID> outList = new ArrayList<>(), inList = new ArrayList<>();
        outList.add(friend.getUuid());
        inList.add(user.getUuid());
        // check that user's outgoing pending requests list has only friend
        assertEquals(outList, user.getOutPendingRequests());
        // check that friend's incoming pending requests list has only user
        assertEquals(inList, friend.getIncPendingRequests());
    }

    /*
    Unit test for User.removePendingRequest(User u) method.
    Assumption: User.addPendingRequest(User u) method works.
     */
    @Test
    void removePendingRequest() {
        // send request to friend
        user.addPendingRequest(friend);
        // remove that pending request to friend
        user.removePendingRequest(friend);
        // make sure that user's outgoing request list and friend's
        // incoming request list are now empty
        assertEquals(emptyList, user.getOutPendingRequests());
        assertEquals(emptyList, friend.getIncPendingRequests());
    }

    /*
    Unit test for User.denyRequest(User u) method.
    Assumption: User.addPendingRequest(User u) method works.
     */
    @Test
    void denyRequest() {
        // check that friend cannot deny request that does not yet exist
        assertFalse(friend.denyRequest(user));
        // user sends request to friend
        user.addPendingRequest(friend);
        // check that friend.denyRequest(user) returns true
        assertTrue(friend.denyRequest(user));
        // check that user's outgoing requests and friend's incoming requests are now empty
        assertEquals(emptyList, user.getOutPendingRequests());
        assertEquals(emptyList, friend.getIncPendingRequests());
    }

    /*
    Unit test for User.acceptRequest(User u) method.
    Assumptions: User.addPendingRequest(User u), User.addFriend(User u)
    (called within acceptRequest) methods work.
     */
    @Test
    void acceptRequest() {
        // check that friend cannot accept request that does not exist yet
        assertFalse(friend.acceptRequest(user));
        // have user send request to friend
        user.addPendingRequest(friend);
        // check that friend can now accept request from user
        assertTrue(friend.acceptRequest(user));
        // check that their relevant incoming and outgoing request lists are now empty
        assertEquals(emptyList, user.getOutPendingRequests());
        assertEquals(emptyList, friend.getIncPendingRequests());
    }

    /*
    Unit test for User.addFriend(User u) method.
     */
    @Test
    void addFriend() {
        // user and friend both add each other as friends
        user.addFriend(friend);
        friend.addFriend(user);

        // create new lists of users to be compared to
        ArrayList<UUID> uFriendList = new ArrayList<>();
        ArrayList<UUID> fFriendList = new ArrayList<>();
        uFriendList.add(friend.getUuid());
        fFriendList.add(user.getUuid());
        // check that user's friends list contains only friend
        assertEquals(uFriendList, user.getFriendsList());
        // check that friend's friends list contains only user
        assertEquals(fFriendList, friend.getFriendsList());
    }

    /*
    Unit test for User.removeFriend(User u) method.
    Assumption: User.addFriend(User u) works.
     */
    @Test
    void removeFriend() {
        // check that user cannot remove someone who is not in their friends list
        assertFalse(user.removeFriend(friend));

        // user and friend both add each other as friends
        user.addFriend(friend);
        friend.addFriend(user);
        // check that user can now remove friend from their friends list
        assertTrue(user.removeFriend(friend));

        // check that both friends lists are now empty
        assertEquals(emptyList, user.getFriendsList());
        assertEquals(emptyList, friend.getFriendsList());
    }

    /*
    Unit test for User.addOrg(Org o) method.
     */
    @Test
    void addOrg() {
        // create org with friend as the leader
        Org org = new Org("org1", friend);
        // check that user's org list is still empty
        assertEquals(emptyList, user.getOrgList());
        // add user to the org
        user.addOrg(org);

        // create list of UUIDs to compare to user's org list
        ArrayList<UUID> orgList = new ArrayList<>();
        // add org to this list
        orgList.add(org.getUUID());
        // check that the UUID lists are the same
        assertEquals(orgList, user.getOrgList());
        // make sure that you cannot add user again
        assertFalse(user.addOrg(org));
    }

    /*
    Unit test for User.leaveOrg(Org o) method.
    Assumption: User.addOrg(Org o) method works.
     */
    @Test
    void leaveOrg() {
        // create new org with friend as the leader
        Org org = new Org("org1", friend);
        // make sure that user cannot leave an org they are not a part of
        assertFalse(user.leaveOrg(org));

        // add user to org
        user.addOrg(org);
        // check that user can now leave said org
        assertTrue(user.leaveOrg(org));

        // check that user's org list is now equal to an empty org list
        ArrayList<UUID> emptyOrgList = new ArrayList<>();
        assertEquals(emptyOrgList, user.getOrgList());
    }

    /*
    Unit test for User.addGroup(Group g) method.
     */
    @Test
    void addGroup() {
        // create an array list of users and add friend to it
        ArrayList<User> groupMembers = new ArrayList<>();
        groupMembers.add(friend);

        // create a group with this member list
        Group group = new Group("group1", groupMembers);
        assertEquals(emptyList, user.getGroupList());
        // check that user can be added to this group
        assertTrue(user.addGroup(group));

        // create array list of UUIDs to be compared to
        ArrayList<UUID> groupList = new ArrayList<>();
        // add new group's id to this list
        groupList.add(group.getUUID());
        // check that user's group list and this list are equal (i.e. user is now in this group)
        assertEquals(groupList, user.getGroupList());
        // make sure that user cannot be added again
        assertFalse(user.addGroup(group));
    }

    /*
    Unit test for User.leaveGroup(Group g) method.
    Assumption: User.addGroup(Group g) method works.
     */
    @Test
    void leaveGroup() {
        // create array list of users and add friend to ut
        ArrayList<User> groupMembers = new ArrayList<>();
        groupMembers.add(friend);

        // create new group with this member list
        Group group = new Group("group1", groupMembers);
        // check that user cannot leave a group they are not yet in
        assertFalse(user.leaveGroup(group));

        // add user to group
        user.addGroup(group);
        groupMembers.add(user);
        // check that user can now leave the group
        assertTrue(user.leaveGroup(group));

        // create an empty list of UUID's to be compared to
        ArrayList<String> emptyGroupList = new ArrayList<>();
        // check that user's group list and this list are now equal (i.e. user has left the group)
        assertEquals(emptyGroupList, user.getGroupList());
    }

    /*
    Unit test for User.toString() method.
    Assumption: Profile.toString() method works.
    (This was not tested itself as it is very simple and similar to
    other toString methods tested)
     */
    @Test
    void testToString() {
        // create profile for user
        Profile p = new Profile("user1");
        user.setProfile(p);

        // create string to compare user.toString() to
        String correctString = String.format("Username: username \n Profile: %s \n", p.toString());
        // make sure that these strings are equal
        assertEquals(correctString, user.toString());
    }
}