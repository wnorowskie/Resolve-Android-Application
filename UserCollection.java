package edu.vassar.cmpu203.resolveandroid.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserCollection implements Serializable {

    protected String name;
    protected int numMembers;
    protected List<User> memberList;
    protected UUID uuid;

    private static final String NAME = "name";
    private static final String NUMMEMBERS = "nummembers";
    private static final String MEMBERLIST = "memberlist";
    private static final String UUID_STRING = "uuid";

    public UserCollection(){}

    // constructor method
    public UserCollection(String name, ArrayList<User> memberList){
        this.name = name;
        this.memberList = memberList;
        this.uuid = UUID.randomUUID();
        this.numMembers = memberList.size();
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put(NAME, name);
        map.put(NUMMEMBERS, numMembers);

        // should save list of UUID strings instead of users?
        // // List<String> memberListStrings = new ArrayList<String>();
        // // for(User u : memberList) memberListStrings.add(u.getUuid().toString());

        List<Map<String, Object>> userMapList = new ArrayList<Map<String, Object>>();
        for(User u: memberList) userMapList.add(u.toMap());

        map.put(MEMBERLIST, userMapList);
        map.put(UUID_STRING, uuid.toString());

        return map;
    }

    public static UserCollection fromMap(Map<String, Object> map){
        UserCollection userCollection = new UserCollection();
        userCollection.name = (String) map.get(NAME);
        userCollection.numMembers = (int)(long) map.get(NUMMEMBERS);

        List<User> userList = new ArrayList<User>();
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get(MEMBERLIST);
        for (Map<String, Object> userMap : mapList) userList.add(User.fromMap(userMap));
        userCollection.memberList = userList;
        userCollection.uuid = UUID.fromString((String) map.get(UUID_STRING));

        return userCollection;
    }

    public String getName(){
        return this.name;
    }

    public UUID getUUID(){
        return this.uuid;
    }

    public int getNumMembers(){return this.numMembers; }

    public List<User> getMembers(){
        return this.memberList;
    }

    // checks if a user is a member of a group
    public boolean isMember(User user){
        return this.memberList.contains(user);
    }

    // add member to a group
    public boolean addMember(User user){
        // check if user is already a member of the group
        if (isMember(user)){
            // if so, return false
            return false;
        } else{
            // if not already a member, add user to member list
            this.memberList.add(user);
            // increment number of users by 1
            this.numMembers++;
            return true;
        }
    }

    // remove member from a group
    public boolean removeMember(User user){
        // check if user is a member of the group
        if (isMember(user)){
            // if so, remove user
            this.memberList.remove(user);
            // decrement the number of members by 1
            this.numMembers--;
            return true;
        } else{
            return false;
        }
    }

    @Override
    public String toString(){

        String str = String.format("%s\n Members:\n", this.name);
        int acc = 0;

        for(User u : this.memberList){

            str += String.format("%s\n", u.getProfile().getName());
            acc++;

        }
        int numLeft = this.numMembers - acc;

        if (numLeft > 0) {
            str += String.format("and %d more...", numLeft);
        }
        return str;
    }

}

