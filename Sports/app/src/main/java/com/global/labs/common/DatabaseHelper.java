package com.global.labs.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;

/**
 * Created by Mantra on 9/8/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    static DatabaseHelper helper = null;

    private DatabaseHelper(Context context) {
        super(context, Constants.DATABASENAME, null, Constants.DATABASEVERSION);
    }

    public static DatabaseHelper getInstance(Context ctx) {

        if (helper == null)
            helper = new DatabaseHelper(ctx);
        return helper;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Constants.TABLENAME + "(" + Constants.CITY + " TEXT," + Constants.AREA
                + " TEXT," + Constants.GAMENAME + " TEXT," + Constants.ID + " TEXT );");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }


    public void InsertMainTable(JsonModel data) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Constants.AREA, data.getAreaList().toString());
        value.put(Constants.CITY, data.getCityList().toString());
        value.put(Constants.GAMENAME, data.getSportList().toString());
        value.put(Constants.ID, "1");

        double ss = db.update(Constants.TABLENAME, value,
                Constants.ID + " = 1", null);
        if (ss == 0.0) {
            db.insert(Constants.TABLENAME, null, value);
        }
        db.close();
        System.out.println(ss);

    }

    public JsonModel getMaintabledata(String UID) throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor crs = db.rawQuery("SELECT * FROM " + Constants.TABLENAME + " WHERE "
                + Constants.ID + "=" + UID + "", null);
        JsonModel data = null;

        if (crs != null) {
            if (crs.moveToFirst()) {
                while (crs.isAfterLast() == false) {
                    data = new JsonModel();
                    data.setCityList(new JSONArray(crs.getString(crs.getColumnIndex(Constants.CITY))));
                    data.setAreaList(new JSONArray(crs.getString(crs.getColumnIndex(Constants.AREA))));
                    data.setSportList(new JSONArray(crs.getString(crs.getColumnIndex(Constants.GAMENAME))));
                    crs.moveToNext();
                }
            }
            crs.close();
            db.close();
            return data;
        } else {
            return null;
        }

    }


}
