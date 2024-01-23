package edu.vassar.cmpu203.resolveandroid.model;

import java.io.Serializable;
import java.util.Map;

public class Profile implements Serializable {
    private String name;
    private String bio;
    private static final int maxBioChars = 150;

    public Profile(){ this.name = ""; this.bio = "";}

    public Profile(String name){
        this.name = name;
        this.bio = "";
    }

    public static Profile fromMap(Map<String, Object> map){
        Profile profile = new Profile();
        profile.name = (String) map.get("name");
        profile.bio = (String) map.get("bio");

        return profile;
    }

    public String getName(){
        return this.name;
    }

    public String getBio(){
        return this.bio;
    }

    // creating a bio
    public boolean setBio(String bio){
        // if the length of the bio is > the maximum character amount
        if(bio.length() > maxBioChars){
            return false;
        } else {
            this.bio = bio;
            return true;
        }
    }

    public static int getMaxBioChars(){
        return maxBioChars;
    }

    @Override
    public String toString(){
        return String.format("Name: %s \n Bio: %s\n", this.name, this.bio);
    }
}

