package com.global.labs.common;

import java.util.List;

/**
 * Created by Mantra on 9/3/2015.
 */
public class SeachModel {
    String sport;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getGroundInfo() {
        return groundInfo;
    }

    public void setGroundInfo(String groundInfo) {
        this.groundInfo = groundInfo;
    }

    public String getGroundName() {
        return groundName;
    }

    public void setGroundName(String groundName) {
        this.groundName = groundName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getMlong() {
        return mlong;
    }

    public void setMlong(String mlong) {
        this.mlong = mlong;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(String ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    String groundName;
    String area;
    String groundInfo;
    String address;
    String city;
    String lat;
    String mlong;
    String rating;
    String ratingCount;
    String phoneNum;
    String createdAt;
    String updatedAt;
    String id;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    Double distance;

    public List<String> getImageurls() {
        return Imageurls;
    }

    public void setImageurls(List<String> imageurls) {
        Imageurls = imageurls;
    }

    List<String> Imageurls;


}
