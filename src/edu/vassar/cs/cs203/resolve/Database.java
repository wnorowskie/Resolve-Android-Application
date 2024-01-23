package edu.vassar.cs.cs203.resolve;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Database {
    private static Map<String, User> userRepo;
    private static Map<UUID, Group> groupRepo;
    private static Map<UUID, Org> orgRepo;

    public static void init(){
        userRepo = new HashMap<>();
        groupRepo = new HashMap<>();
        orgRepo = new HashMap<>();
    }

    // user repo
    public static Map<String, User> getUserRepo(){
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
        userRepo.put(user.getUsername(), user);
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
