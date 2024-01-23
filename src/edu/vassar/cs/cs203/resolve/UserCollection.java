package edu.vassar.cs.cs203.resolve;

import java.util.ArrayList;
import java.util.UUID;

public class UserCollection {

    private String name;
    private int numMembers;
    private ArrayList<User> memberList;
    private UUID uuid;

    // constructor method
    public UserCollection(String name, ArrayList<User> memberList){
        this.name = name;
        this.memberList = memberList;
        this.uuid = UUID.randomUUID();
        this.numMembers = memberList.size();
    }

    public String getName(){
        return this.name;
    }

    public UUID getUUID(){
        return this.uuid;
    }

    public ArrayList<User> getMembers(){
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
