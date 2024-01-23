package edu.vassar.cmpu203.resolveandroid.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Org extends UserCollection implements Serializable {
    private User leader;
    private Profile profile;
    private List<User> adminList;
    private WeeklySchedule schedule;

    private static final String LEADER = "leader";
    private static final String PROFILE = "profile";
    private static final String ADMINLIST = "adminlist";
    private static final String WEEKLYSCHEDULE = "weeklyschedule";

    public Org(){}

    public Org(String name, User leader){
        super(name, new ArrayList<>(Arrays.asList(leader)));
        this.leader = leader;
        this.adminList = (ArrayList<User>) (((ArrayList<User>) this.getMembers()).clone());
        this.schedule = new WeeklySchedule();
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = super.toMap();
        map.put(LEADER, leader.toMap());
        map.put(PROFILE, profile);

        List<Map<String, Object>> userMapList = new ArrayList<Map<String, Object>>();
        for(User u: adminList) userMapList.add(u.toMap());

        map.put(ADMINLIST, userMapList);
        map.put(WEEKLYSCHEDULE, schedule.toMap());
        return map;
    }

    public static Org fromMap(Map<String, Object> map){
        UserCollection userCollection = UserCollection.fromMap(map);
        Org org = new Org();
        org.name = userCollection.getName();
        org.numMembers = userCollection.getNumMembers();
        org.memberList = userCollection.getMembers();
        org.uuid = userCollection.getUUID();

        org.leader =  User.fromMap((Map<String, Object>)map.get(LEADER));
        org.profile = (Profile) map.get(PROFILE);

        List<User> userList = new ArrayList<User>();
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) map.get(ADMINLIST);
        for (Map<String, Object> userMap : mapList) userList.add(User.fromMap(userMap));
        org.adminList = userList;
        org.schedule = WeeklySchedule.fromMap((Map<String, Object>)map.get(WEEKLYSCHEDULE));
        return org;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public void setProfile(Profile profile){
        this.profile = profile;
    }

    public List<User> getAdminList(){
        return this.adminList;
    }

    // adding an admin to the organization
    public boolean addAdmin(User user){
        // only add an admin if they are not already in the admin list
        if (this.adminList.contains(user))
            return false;
        else {
            this.adminList.add(user);
            return true;
        }
    }

    public WeeklySchedule getSchedule(){return this.schedule; }

    // removing an admin
    public boolean removeAdmin(User user){
        // if user is an admin, remove them
        if (this.adminList.contains(user)){
            this.adminList.remove(user);
            return true;
        }
        return false;
    }

    // check if user is an admin of org
    public boolean isAdmin(User user){
        return this.adminList.contains(user);
    }

    // check if user is the leader of org
    public boolean isLeader(User user){
        return this.leader.equals(user);
    }

    public User getLeader(User user){
        return this.leader;
    }

    @Override
    public String toString(){
        return String.format("Organization Name: %s \n Profile: \n %s", this.getName(), this.profile);
    }
}

