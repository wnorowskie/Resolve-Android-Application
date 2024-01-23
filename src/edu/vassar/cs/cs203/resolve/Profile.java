package edu.vassar.cs.cs203.resolve;

public class Profile {
    private String name;
    private String bio;
    private static final int maxBioChars = 150;

    Profile(String name){
        this.name = name;
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
