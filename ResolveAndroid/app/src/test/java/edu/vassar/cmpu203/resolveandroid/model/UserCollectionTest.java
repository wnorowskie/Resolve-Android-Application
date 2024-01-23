package edu.vassar.cmpu203.resolveandroid.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/*
 Test class of unit tests for the UserCollection
 (parent class of Group and Org) class methods.
 */
class UserCollectionTest {
    // create 2 users
    User user1 = new User("username1", "password1");
    User user2 = new User("username2", "password2");
    ArrayList<User> memberList = new ArrayList<>();
    // initialize a usercollection with an empty member list
    UserCollection userCollection = new UserCollection("ucollection", memberList);

    /*
    Unit test for UserCollection.addMember(User u) method.
     */
    @Test
    void addMember() {
        // check that adding user1 returns true
        assertTrue(userCollection.addMember(user1));
        // make sure that user1 was actually added
        assertEquals(user1, userCollection.getMembers().get(0));
        // make sure that adding user1 again returns false
        assertFalse(userCollection.addMember(user1));

        // check that adding user2 returns true
        assertTrue(userCollection.addMember(user2));
        // make sure that user2 is actually in the second index of the list
        assertEquals(user2, userCollection.getMembers().get(1));
        // check that the size of the member list is now 2
        assertEquals(2, userCollection.getMembers().size());
    }

    /*
    Unit test for UserCollection.removeMember(User u) method.
    Assumption: UserCollection.addMember(User u) works.
     */
    @Test
    void removeMember() {
        // add user1 to userCollection
        userCollection.addMember(user1);
        assertEquals(1, userCollection.getMembers().size());
        // make sure that user2 cannot be removed as they are not in the member list
        assertFalse(userCollection.removeMember(user2));
        // check that removing user1 returns true
        assertTrue(userCollection.removeMember(user1));
        // make sure that the member list's size is now 0 (user1 was actually removed)
        assertEquals(0, userCollection.getMembers().size());
    }

    /*
    Unit test for UserCollection.toString() method.
    Assumption: UserCollection.addMember(User u) works.
     */
    @Test
    void testToString() {
        // create profiles for user1 and user2 and set them respectively
        Profile profile1 = new Profile("User One");
        user1.setProfile(profile1);
        Profile profile2 = new Profile("User Two");
        user2.setProfile(profile2);
        // add both users to userCollection
        userCollection.addMember(user1);
        userCollection.addMember(user2);

        // create string that represents what the toString should look like
        String correctString = String.format("ucollection\n Members:\nUser One\nUser Two\n");
        // make sure that userCollection.toString() returns this correct string
        assertEquals(correctString, userCollection.toString());
    }
}