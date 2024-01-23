package edu.vassar.cmpu203.resolveandroid.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/*
 Test class of unit tests for the Org class methods.
 */
class OrgTest {
    // create 2 users and a new organization with user1 as the leader
    User user1 = new User("username1", "password1");
    User user2 = new User("username2", "password2");
    ArrayList<User> memberList = new ArrayList<>();
    Org org = new Org("org1", user1);

    /*
    Unit test for Org.addAdmin(User u) method.
     */
    @Test
    void addAdmin() {
        // user1 is already an admin as the leader of the org, so addAdmin should return false
        assertFalse(org.addAdmin(user1));
        assertEquals(user1, org.getAdminList().get(0));

        // as user2 is not an admin yet, adding them should return true
        assertTrue(org.addAdmin(user2));
        // check that user2 is now in the admin list
        assertEquals(user2, org.getAdminList().get(1));
        // make sure that adding user2 again returns false
        assertFalse(org.addAdmin(user2));
    }

    /*
    Unit test for Org.removeAdmin(User u) method.
    Assumption: Org.addAdmin(User u) method works.
     */
    @Test
    void removeAdmin() {
        // check that user2 cannot be removed from admin list if they are not yet an admit
        assertFalse(org.removeAdmin(user2));
        // add user2 to admin list
        org.addAdmin(user2);
        assertEquals(2, org.getAdminList().size());

        // check that user2 is in admin list
        assertEquals(user2, org.getAdminList().get(1));
        // removeAdmin(user2) should now return true
        assertTrue(org.removeAdmin(user2));
        // check that the size of the admin list went down (user 2 was actually removed)
        assertEquals(1, org.getAdminList().size());
    }

    /*
    Unit test for Org.toString() method.
     */
    @Test
    void testToString() {
        // create profile to set as org's profile
        Profile orgProfile = new Profile("org1");
        org.setProfile(orgProfile);

        // create a string representing what the toString should look like
        String correctString = String.format("Organization Name: org1 \n Profile: \n %s", orgProfile);
        // make sure org.toString() equals this correct string
        assertEquals(correctString, org.toString());
    }
}