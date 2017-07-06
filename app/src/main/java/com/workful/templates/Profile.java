package com.workful.templates;


import java.io.Serializable;
import java.util.ArrayList;

public class Profile implements Serializable{
    private String name, surname, title, imgPath, city, category, email, telephone, description;
    private int id;
    private ArrayList<SkillLvl> skills;


    public void addSkills(ArrayList<SkillLvl> arrayList){
        skills.addAll(arrayList);
    }

    public ArrayList<SkillLvl> getSkills(){
        return skills;
    }

    public String getDescription() {
        return description;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
