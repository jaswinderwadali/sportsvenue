package com.global.labs.common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mantra on 9/3/2015.
 */
public class JsonParsing {


    public static List<SeachModel> SearchParsing(String json) throws JSONException {
        List<SeachModel> list = new ArrayList<>();
        JSONObject obj = new JSONObject(json);
        JSONArray array = obj.getJSONArray("grounds");
        for (int i = 0; array.length() > i; i++) {
            JSONObject inerobj = array.getJSONObject(i);
            SeachModel model = new SeachModel();
            model.setSport(inerobj.optString("sport"));
            model.setGroundName(inerobj.optString("groundName"));
            model.setArea(inerobj.optString("area"));
            model.setGroundInfo(inerobj.optString("groundInfo"));
            model.setAddress(inerobj.optString("address"));
            model.setCity(inerobj.optString("city"));

            JSONObject mlatlong = inerobj.optJSONObject("location");
            model.setLat(mlatlong.optString("x"));
            model.setMlong(mlatlong.optString("y"));

            model.setRating(inerobj.optString("rating"));
            model.setRatingCount(inerobj.optString("ratingCount"));
            model.setPhoneNum(inerobj.optString("phoneNum"));
            model.setUpdatedAt(inerobj.optString("updatedAt"));
            model.setCreatedAt(inerobj.optString("createdAt"));
            model.setId(inerobj.optString("id"));
            list.add(model);

        }
        return list;
    }

    static public Boolean HasData(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray array = obj.getJSONArray("grounds");
            if (array.length() > 0) return true;
            else return false;
        } catch (Exception e) {
            return false;
        }
    }


}
