package com.global.labs.common;

import org.json.JSONArray;

/**
 * Created by Mantra on 9/9/2015.
 */
public class JsonModel  {

    JSONArray cityList;
    JSONArray areaList;
    JSONArray sportList;

    public JSONArray getAreaList() {
        return areaList;
    }

    public void setAreaList(JSONArray areaList) {
        this.areaList = areaList;
    }

    public JSONArray getCityList() {
        return cityList;
    }

    public void setCityList(JSONArray cityList) {
        this.cityList = cityList;
    }

    public JSONArray getSportList() {
        return sportList;
    }

    public void setSportList(JSONArray sportList) {
        this.sportList = sportList;
    }
}
