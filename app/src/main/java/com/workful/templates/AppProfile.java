package com.workful.templates;

/**
 * Created by Cristian on 6/30/2017.
 */
import java.util.ArrayList;

public class AppProfile {

    private ArrayList<SkillLvl> skills = new ArrayList<SkillLvl>();

    private byte[] image_bytes;
    private Profile profile;


    public byte[] getImage_bytes() {
        return image_bytes;
    }
    public void setImage_bytes(byte[] image_bytes) {
        this.image_bytes = image_bytes;
    }

    public ArrayList<SkillLvl> getSkills() {
        return skills;
    }
    public void setSkills(ArrayList<SkillLvl> skills) {
        this.skills = skills;
    }
    public Profile getProfile() {
        return profile;
    }
    public void setProfile(Profile p) {
        this.profile = p;
    }



}
