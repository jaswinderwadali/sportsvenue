package com.global.labs.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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


    public void InsertMainTable(SeachModel data) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Constants.AREA, data.getArea());
        value.put(Constants.CITY, data.getCity());
        value.put(Constants.GAMENAME, data.getSport());
        value.put(Constants.ID, data.getId());

        double ss = db.update(Constants.TABLENAME, value,
                Constants.ID + " = " + data.getId(), null);
        if (ss == 0.0) {
            db.insert(Constants.TABLENAME, null, value);
        }
        db.close();
        System.out.println(ss);

    }

    public SeachModel getMaintabledata(String UID) throws Exception {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor crs = db.rawQuery("SELECT * FROM " + Constants.TABLENAME + " WHERE "
                + Constants.ID + "=" + UID + "", null);
        SeachModel data = null;

        if (crs != null) {
            if (crs.moveToFirst()) {
                while (crs.isAfterLast() == false) {

                    data = new SeachModel();
                    data.setCity(crs.getString(crs.getColumnIndex(Constants.CITY)));
                    data.setId(crs.getString(crs.getColumnIndex(Constants.ID)));
                    data.setArea((crs.getString(crs.getColumnIndex(Constants.AREA))));
                    data.setSport(crs.getString(crs.getColumnIndex(Constants.GAMENAME)));
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
