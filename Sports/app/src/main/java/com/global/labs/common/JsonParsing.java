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

            if (inerobj.has("imageURL")) {
                JSONArray imagearray = inerobj.optJSONArray("imageURL");
                List<String> images = new ArrayList<>();
                for (int x = 0; x < imagearray.length(); x++) {
                    images.add(imagearray.optString(x));
                }
                model.setImageurls(images);
            }

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

    public JsonModel Maindata(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JsonModel model = new JsonModel();
            model.setAreaList(jsonObject.optJSONArray("areaList"));
            model.setCityList(jsonObject.optJSONArray("cityList"));
            model.setSportList(jsonObject.optJSONArray("sportList"));
            return model;
        } catch (JSONException ex) {
            return null;
        }
    }


    public List<String> jsonarraytolist(JSONArray array) {
        List<String> list = new ArrayList<>();
        try {
            for (int i = 0; i < array.length(); i++) {
                list.add(array.getString(i));
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<ReviewModel> mReviewList(String jsonstr) {

        try {
            List<ReviewModel> list = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(jsonstr);
            JSONArray reviewarray = jsonObject.optJSONArray("reviews");
            for (int i = 0; reviewarray.length() > i; i++) {
                ReviewModel model = new ReviewModel();
                JSONObject inerobj = reviewarray.optJSONObject(i);
                model.setGroundId(inerobj.optString("groundId"));
                model.setTitle(inerobj.optString("title"));
                model.setReview(inerobj.optString("review"));
                model.setRating(inerobj.optString("rating"));
                model.setUserName(inerobj.optString("userName"));
                model.setUserId(inerobj.optString("userId"));
                model.setCreatedAt(inerobj.optString("createdAt"));
                model.setUpdatedAt(inerobj.optString("updatedAt"));
                model.setId(inerobj.optString("id"));
                model.setOwnReview(inerobj.optString("ownReview"));
                list.add(model);
            }

            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
