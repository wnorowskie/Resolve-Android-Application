package edu.vassar.cmpu203.resolveandroid.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class User implements Serializable {

    private String username;
    private AuthKey authKey;

    private UUID uuid;

    //If a user adds a friend, both users are automatically friends so we only need one friends list per user
    //this will likely cause issues for firestore as well
    // private List<User> friendsList;
    private List<UUID> friendsList;

    //Using UUID for group/org list allows fast lookups and means that we don't need to store a copy of each org/group for each user
    private List<UUID> orgList;
    private List<UUID> groupList;
    //If we keep track of number of friends we don't have to compute friendsList.length() each time we want the number
    private int numFriends;
    // Creating 2 different pending request lists allows us to show both incoming pending requests (other users --> this user)
    // and outgoing pending requests (this user --> other users)

    // these 2 are causing issues in firestore, change to lists of user IDs
    // private List<User> incPendingRequests;
    // private List<User> outPendingRequests;
    private List<UUID> incPendingRequests;
    private List<UUID> outPendingRequests;

    private Profile profile;
    //Each user has a weekly schedule
    private WeeklySchedule schedule;

    private static final String USERNAME = "username";
    private static final String AUTHKEY = "authkey";
    private static final String UUID_STRING = "uuid";
    private static final String FRIENDSLIST = "friendslist";
    private static final String ORGLIST = "orglist";
    private static final String GROUPLIST = "grouplist";
    private static final String NUMFRIENDS = "numfriends";
    private static final String INCPENDINGREQUESTS = "incpendingrequests";
    private static final String OUTPENDINGREQUESTS = "outpendingrequests";
    private static final String PROFILE = "profile";
    private static final String SCHEDULE = "schedule";

    public User(){}

    public User(String username, String password){
        this.username = username;
        this.authKey = new AuthKey(password);
        this.friendsList = new ArrayList<>();
        this.incPendingRequests = new ArrayList<>();
        this.outPendingRequests = new ArrayList<>();
        this.orgList = new ArrayList<>();
        this.groupList = new ArrayList<>();
        this.numFriends = 0;
        this.schedule = new WeeklySchedule();

        this.profile = new Profile();
        this.uuid = UUID.randomUUID();
    }

    public Map<String, Object> toMap(){
        // call WeeklySchedule toMap
        Map<String, Object> map = new HashMap<>();
        map.put(USERNAME, username);
        map.put(AUTHKEY, authKey);
        map.put(UUID_STRING, uuid.toString());

        List<String> friendsListStrings = new ArrayList<>(), orgListStrings = new ArrayList<>(),
                groupListStrings = new ArrayList<>(), incPendingRequestStrings = new ArrayList<>(),
                outPendingRequestStrings = new ArrayList<>();
        for(UUID uuid : friendsList) friendsListStrings.add(uuid.toString());
        for(UUID uuid : orgList) orgListStrings.add(uuid.toString());
        for(UUID uuid : groupList) groupListStrings.add(uuid.toString());
        for(UUID uuid : incPendingRequests) incPendingRequestStrings.add(uuid.toString());
        for(UUID uuid : outPendingRequests) outPendingRequestStrings.add(uuid.toString());

        map.put(FRIENDSLIST, friendsListStrings);
        map.put(ORGLIST, orgListStrings);
        map.put(GROUPLIST, groupListStrings);
        map.put(NUMFRIENDS, numFriends);
        map.put(INCPENDINGREQUESTS, incPendingRequestStrings);
        map.put(OUTPENDINGREQUESTS, outPendingRequestStrings);
        map.put(PROFILE, profile);
        map.put(SCHEDULE, schedule.toMap());

        return map;
    }

    public static User fromMap(Map<String, Object> map){
        User user = new User();

        user.username = (String) map.get(USERNAME);
        user.authKey =  AuthKey.fromMap((Map<String, Object>) map.get(AUTHKEY));
        user.uuid = UUID.fromString((String) map.get(UUID_STRING));

        List<String> friendsListStrings = (ArrayList<String>) map.get(FRIENDSLIST), orgListStrings = (ArrayList<String>) map.get(ORGLIST),
                groupListStrings = (ArrayList<String>) map.get(GROUPLIST), incPendingRequestStrings = (ArrayList<String>) map.get(INCPENDINGREQUESTS),
                outPendingRequestStrings = (ArrayList<String>) map.get(OUTPENDINGREQUESTS);

        List<UUID> friendsListUUIDs = new ArrayList<UUID>(), orgListUUIDs = new ArrayList<UUID>(),
                groupListUUIDs = new ArrayList<UUID>(), incPendingRequestUUIDs = new ArrayList<UUID>(),
                outPendingRequestUUIDs = new ArrayList<UUID>();

        for(String uuid : friendsListStrings) friendsListUUIDs.add(UUID.fromString(uuid));
        for(String uuid : orgListStrings) orgListUUIDs.add(UUID.fromString(uuid));
        for(String uuid : groupListStrings) groupListUUIDs.add(UUID.fromString(uuid));
        for(String uuid : incPendingRequestStrings) incPendingRequestUUIDs.add(UUID.fromString(uuid));
        for(String uuid : outPendingRequestStrings) outPendingRequestUUIDs.add(UUID.fromString(uuid));

        user.friendsList = friendsListUUIDs;
        user.orgList = orgListUUIDs;
        user.groupList = groupListUUIDs;
        user.numFriends = (int)(long) map.get(NUMFRIENDS);
        user.incPendingRequests = incPendingRequestUUIDs;
        user.outPendingRequests = outPendingRequestUUIDs;
        user.profile = Profile.fromMap((Map<String, Object>) map.get(PROFILE));
        user.schedule = WeeklySchedule.fromMap((Map<String, Object>) map.get(SCHEDULE));

        return user;
    }

    public String getUsername() { return this.username; }
    public AuthKey getAuthKey() { return this.authKey; }

    public boolean validatePassword(String password){
        return this.authKey.validatePassword(password);
    }

    // Controller will implement functionality for creating a user, will separately get profile info and add it to the user
    public void setProfile(Profile prof){
        this.profile = prof;
    }

    // Controller will get the profile to display to user
    public Profile getProfile(){
        return this.profile;
    }

    // To be used in the controller/textui to display list of incoming pending requests
    //public List<User> getIncPendingRequests(){
    public List<UUID> getIncPendingRequests(){
        return this.incPendingRequests;
    }
    // To be used in the controller/textui to display list of outgoing pending requests
    // public List<User> getOutPendingRequests(){
    public List<UUID> getOutPendingRequests(){
        return this.outPendingRequests;
    }
    // To be used in the controller/textui to display list of friends
    //public List<User> getFriendsList(){

    public List<UUID> getFriendsList() {
        return friendsList;
    }

    public boolean addPendingRequest(User user){
        // Adds an outgoing pending request to this user's list
        // Only add a request to the list if there isn't already a pending request
        // Return value will be used to tell the user whether sending the request was successful
        if(user.equals(this)){
            //Can't send friend request to yourself
            return false;
        }
        if (this.outPendingRequests.contains(user.getUuid()) || this.friendsList.contains(user.getUuid())) {
            return false;
        } else { // add other user to the this user's outgoing pending requests, and add this user to other user's incoming pending requests
            this.outPendingRequests.add(user.getUuid());
            user.incPendingRequests.add(this.getUuid());
            return true;
        }
    }

    public boolean removePendingRequest(User user){
        // Removes an outgoing pending request from this user's list
        if (this.outPendingRequests.contains(user.getUuid())){
            this.outPendingRequests.remove(user.getUuid());
            user.incPendingRequests.remove(this.getUuid());
            return true;
        } else{
            return false;
        }
    }

    public boolean denyRequest(User user){
        // User should only be able to deny a request that's already an incoming pending request
        // Use return value to give feedback to the user
        if(this.incPendingRequests.contains(user.getUuid())){
            //If there is an incoming pending request from the other user, remove it. (Remove this user from the other user's outgoing pending requests as well?)
            this.incPendingRequests.remove(user.getUuid());
            user.outPendingRequests.remove(this.getUuid());
            return true;
        } else {
            return false;
        }
    }

    public boolean acceptRequest(User user){
        // Can only accept an incoming request if the request is already pending
        if(this.incPendingRequests.contains(user.getUuid())){
            this.incPendingRequests.remove(user.getUuid());
            user.outPendingRequests.remove(this.getUuid());
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
        if(this.friendsList.contains(user.getUuid())){
            //If the two users aren't already friends add to friend list
            return false;
        } else {
            //If the two users are already friends something went wrong
            this.friendsList.add(user.getUuid());
            user.friendsList.add(this.getUuid());
            this.numFriends++;
            user.numFriends++;
            return true;
        }
    }

    public boolean removeFriend(User user){
        //Allow user to remove a friend from their friends list - remove from both users
        if(this.friendsList.contains(user.getUuid())){
            this.friendsList.remove(user.getUuid());
            user.friendsList.remove(this.getUuid());
            this.numFriends--;
            user.numFriends--;
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

    public boolean leaveOrg(Org org){
        if(this.orgList.contains(org.getUUID())){
            this.orgList.remove(org.getUUID());
            return true;
        } else {
            return false;
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

    public boolean leaveGroup(Group group){
        if (this.groupList.contains(group.getUUID())){
            this.groupList.remove(group.getUUID());
            return true;
        } else {
            return false;
        }
    }

    public WeeklySchedule getSchedule(){
        return this.schedule;
    }

    public List<UUID> getOrgList(){
        return this.orgList;
    }

    public List<UUID> getGroupList(){
        return this.groupList;
    }

    public int getNumFriends(){
        return this.numFriends;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString(){
        return String.format("Username: %s \n Profile: %s \n", this.username, this.profile.toString());
    }
}


