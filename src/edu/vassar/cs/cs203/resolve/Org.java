package edu.vassar.cs.cs203.resolve;

import java.util.ArrayList;
import java.util.Arrays;

public class Org extends UserCollection{
    private User leader;
    private Profile profile;
    private ArrayList<User> adminList;

    Org(String name, User leader){
        super(name, new ArrayList<>(Arrays.asList(leader)));
        this.leader = leader;
        this.adminList = (ArrayList<User>)this.getMembers().clone();
    }

    public Profile getProfile() {
        return this.profile;
    }

    public void setProfile(Profile profile){
        this.profile = profile;
    }

    public ArrayList<User> getAdminList(){
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
