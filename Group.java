package edu.vassar.cmpu203.resolveandroid.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Group extends UserCollection implements Serializable {

    public Group(){}

    public Group(String groupName, ArrayList<User> memberList){
        super(groupName, memberList);
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = super.toMap();
        return map;
    }

    public static Group fromMap(Map<String, Object> map){
        UserCollection userCollection = UserCollection.fromMap(map);
        Group group = new Group();
        group.name = userCollection.getName();
        group.numMembers = userCollection.getNumMembers();
        group.memberList = userCollection.getMembers();
        group.uuid = userCollection.getUUID();

        return group;
    }

    public String toString(){return super.toString(); }

    //TODO add functionality for chat/schedules
}

