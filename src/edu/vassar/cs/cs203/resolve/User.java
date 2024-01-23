package edu.vassar.cs.cs203.resolve;

import java.util.ArrayList;
import java.util.UUID;

public class User {

    private String username;
    private String password;
    //If a user adds a friend, both users are automatically friends so we only need one friends list per user
    private ArrayList<User> friendsList;
    //Using UUID for group/org list allows fast lookups and means that we don't need to store a copy of each org/group for each user
    private ArrayList<UUID> orgList;
    private ArrayList<UUID> groupList;
    //If we keep track of number of friends we don't have to compute friendsList.length() each time we want the number
    private int numFriends;
    // Creating 2 different pending request lists allows us to show both incoming pending requests (other users --> this user)
    // and outgoing pending requests (this user --> other users)
    private ArrayList<User> incPendingRequests;
    private ArrayList<User> outPendingRequests;
    private Profile profile;

    User(String username, String password){
        this.username = username;
        this.password = password;
        this.friendsList = new ArrayList<>();
        this.incPendingRequests = new ArrayList<>();
        this.outPendingRequests = new ArrayList<>();
        this.orgList = new ArrayList<>();
        this.groupList = new ArrayList<>();
        this.numFriends = 0;
    }

    public String getPassword(){
        return this.password;
    }
    public String getUsername() { return this.username; }

    // Controller will implement functionality for creating a user, will separately get profile info and add it to the user
    public void setProfile(Profile prof){
        this.profile = prof;
    }

    // Controller will get the profile to display to user
    public Profile getProfile(){
        return this.profile;
    }

    // To be used in the controller/textui to display list of incoming pending requests
    public ArrayList<User> getIncPendingRequests(){
        return this.incPendingRequests;
    }
    // To be used in the controller/textui to display list of outgoing pending requests
    public ArrayList<User> getOutPendingRequests(){
        return this.outPendingRequests;
    }
    // To be used in the controller/textui to display list of friends
    public ArrayList<User> getFriendsList(){
        return this.friendsList;
    }

    public boolean addPendingRequest(User user){
        // Adds an outgoing pending request to this user's list
        // Only add a request to the list if there isn't already a pending request
        // Return value will be used to tell the user whether sending the request was successful
        if(user.equals(this)){
            //Can't send friend request to yourself
            return false;
        }
        if (this.outPendingRequests.contains(user) || this.friendsList.contains(user)) {
            return false;
        } else { // add other user to the this user's outgoing pending requests, and add this user to other user's incoming pending requests
            this.outPendingRequests.add(user);
            user.incPendingRequests.add(this);
            return true;
        }
    }

    public boolean removePendingRequest(User user){
        // Removes an outgoing pending request from this user's list
        if (this.outPendingRequests.contains(user)){
            this.outPendingRequests.remove(user);
            user.incPendingRequests.remove(this);
            return true;
        } else{
            return false;
        }
    }

    public boolean denyRequest(User user){
        // User should only be able to deny a request that's already an incoming pending request
        // Use return value to give feedback to the user
        if(this.incPendingRequests.contains(user)){
            //If there is an incoming pending request from the other user, remove it. (Remove this user from the other user's outgoing pending requests as well?)
            this.incPendingRequests.remove(user);
            user.outPendingRequests.remove(this);
            return true;
        } else {
            return false;
        }
    }

    public boolean acceptRequest(User user){
        // Can only accept an incoming request if the request is already pending
        if(this.incPendingRequests.contains(user)){
            this.incPendingRequests.remove(user);
            user.outPendingRequests.remove(this);
            //Add friend to both user's friend list
            this.addFriend(user);
            user.addFriend(this);
            return true;
        } else {
            return false;
        }
    }

    public boolean addFriend(User user){
        if(user.equals(this)){
            return false;
        }
        if(this.friendsList.contains(user)){
            //If the two users aren't already friends add to friend list
            return false;
        } else {
            //If the two users are already friends something went wrong
            this.friendsList.add(user);
            this.numFriends++;
            return true;
        }
    }

    public boolean removeFriend(User user){
        //Allow user to remove a friend from their friends list - remove from both users
        if(this.friendsList.contains(user)){
            this.friendsList.remove(user);
            user.friendsList.remove(this);
            this.numFriends--;
            return true;
        } else {
            return false;
        }
    }

    public boolean addOrg(Org org){
        if (this.orgList.contains(org.getUUID())){
            return false;
        } else {
            this.orgList.add(org.getUUID());
            return true;
        }
    }

    public boolean addGroup(Group group){
        if (this.groupList.contains(group.getUUID())){
            return false;
        } else {
            this.groupList.add(group.getUUID());
            return true;
        }
    }

    public ArrayList<UUID> getOrgList(){
        return this.orgList;
    }

    public ArrayList<UUID> getGroupList(){
        return this.groupList;
    }

    public int getNumFriends(){
        return this.numFriends;
    }

    @Override
    public String toString(){
        return String.format("Username: %s \n Profile: %s \n", this.username, this.profile.toString());
    }
}

