package edu.vassar.cs.cs203.resolve;

import java.util.ArrayList;

public class Group extends UserCollection {

    Group(String groupName, ArrayList<User> memberList){
        super(groupName, memberList);
    }

    public String toString(){return super.toString(); }

    //TODO add functionality for chat/schedules
}
