package edu.vassar.cmpu203.resolveandroid.model;

import java.io.Serializable;
import java.util.*;

public class Controller implements Serializable {

    public Controller(){}

    // create a new user
    public static User createUser(String uname, String pass, String name, String bio){
        User u = new User(uname, pass);
        Profile p = new Profile(name);

        if(p.setBio(bio)){
            // set profile
            u.setProfile(p);
            // add user to database
            Database.addUser(u);
            return u;
        }
        return null;

    }

    /*
     * @return null if startTime >= endTime so we can check user input
     */
    public Event createEvent(EventTime startTime, EventTime endTime){
        if(startTime.calculateTimeMins() >= endTime.calculateTimeMins()){
            return null;
        } else {
            return new Event(startTime, endTime);
        }
    }

    /*
     * @return null if startTime >= endTime so we can check user input
     */
    public Event createEvent(EventTime startTime, EventTime endTime, String title){
        Event ret = createEvent(startTime, endTime);
        if(ret != null){
            ret.setTitle(title);
        }

        return ret;
    }

    // used for logging in
    /*public static User validateCreds(String username, String password){
        // if username and password are valid, return user
        if(userExists(username)){
            User u = getUser(username);
            if(u.getPassword().equals(password)){
                return u;
            }
        }
        // if username and/or password are invalid, return null
        return null;
    }
    */



    // get user
    public static User getUser(String username){
        // check if user exists in user repo
        if(userExists(username)){
            return Database.getUserRepo().get(username);
        } else
            return null;
    }

    // check if user exists in user repo
    public static boolean userExists(String username){
        return Database.getUserRepo().containsKey(username);
    }

    // check if organization exists in org repo
    private static boolean orgExists(UUID uuid){
        return Database.getOrgRepo().containsKey(uuid);
    }

    // check if group exists in group repo
    private static boolean groupExists(UUID uuid){
        return Database.getGroupRepo().containsKey(uuid);
    }

    // get org
    public static Org getOrg(UUID uuid){
        // check if group exists
        if(orgExists(uuid)){
            return Database.getOrgRepo().get(uuid);
        }
        return null;
    }

    // create a group
    public static void startGroup(User user, String name){
        Group group = new Group(name, new ArrayList<>(Arrays.asList(user)));
        // add group to user
        user.addGroup(group);
        // add group to database
        Database.addGroup(group);
    }

    // create an organization
    public static boolean startOrg(String name, User user){
        Org newOrg = new Org(name, user);
        Profile p = new Profile(name);
        // set a default bio for now
        p.setBio("Default bio - adding functionality to edit profile in iteration 2");
        // set profile
        newOrg.setProfile(p);
        // add org to user
        user.addOrg(newOrg);
        // add org to database
        Database.addOrg(newOrg);
        return true;
    }
    // search for organization

    public static Org searchOrg(String orgName){
        for(Org o : Database.getOrgRepo().values()){
            // if organization name exactly equals an organization in the org repo, return that organization
            if (o.getName().equals(orgName)) {
                return o;
            }
        }
        return null;
    }
}

