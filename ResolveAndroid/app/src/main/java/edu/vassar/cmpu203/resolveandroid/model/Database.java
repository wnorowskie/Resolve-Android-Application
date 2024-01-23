package edu.vassar.cmpu203.resolveandroid.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Database implements Serializable {
    private static Map<UUID, User> userRepo;
    private static Map<UUID, Group> groupRepo;
    private static Map<UUID, Org> orgRepo;

    public Database(){}

    public static void init(){
        userRepo = new HashMap<>();
        groupRepo = new HashMap<>();
        orgRepo = new HashMap<>();
    }

    // user repo
    public static Map<UUID, User> getUserRepo(){
        return userRepo;
    }

    // group repo
    public static Map<UUID, Group> getGroupRepo(){
        return groupRepo;
    }

    // org repo
    public static Map<UUID, Org> getOrgRepo(){
        return orgRepo;
    }

    // add user to user repo
    public static void addUser(User user){
        userRepo.put(user.getUuid(), user);
    }

    // add org to org repo
    public static void addOrg(Org org){
        orgRepo.put(org.getUUID(), org);
    }

    // add group to group repo
    public static void addGroup(Group group){
        groupRepo.put(group.getUUID(), group);
    }
}

