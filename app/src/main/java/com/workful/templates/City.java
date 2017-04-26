package com.workful.templates;

/**
 * Created by Cristian on 4/26/2017.
 */

import com.example.cristian.workful20.CommonFields;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class City implements CommonFields {
    private int cityId;
    private String name;
    private int regionId;

    public int getId(){
        return cityId;}
    public String getName(){
        return name;}
    public int getRegionId(){
        return regionId;}

    public void setCityId(int cityId){
        this.cityId = cityId;
    }
    public void setCityName(String cityName){
        this.name = cityName;
    }
    public void setRegionId(int regionId){
        this.regionId = regionId;
    }
}
