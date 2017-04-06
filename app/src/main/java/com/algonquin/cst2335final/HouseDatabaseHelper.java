package com.algonquin.cst2335final;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by marvi on 4/5/2017.
 */

public class HouseDatabaseHelper extends SQLiteOpenHelper {
    final static String DATABASE_NAME = "House.db";
    final static int VERSION_NUM=1;
    final static String TABLE_STATE = "State";
    final static String TABLE_TEMPERATURE = "tempe";
    final static String KEY_ID= "_id";
    final static String KEY_DEVICE= "device";
    final static String KEY_STATE = "state";
    final static String KEY_TIME = "time";
    final static String KEY_TEMP="temperature";
    final static String DATABASE_STATE_CREATE = "CREATE TABLE "+TABLE_STATE+ " ( "+KEY_ID +"INTEGER PRIMARY KEY AUTOINCREMENT , "+KEY_DEVICE+ " TEXT, " + KEY_STATE + " TEXT);";
    final static String DATABASE_SCHEDULE_CREATE = "CREATE TABLE "+TABLE_TEMPERATURE+" ( "+KEY_ID +"INTEGER PRIMARY KEY AUTOINCREMENT , " +KEY_TIME +"TEXT, " +KEY_TEMP +" TEXT);";
    public HouseDatabaseHelper(Context ctx){
        super(ctx,DATABASE_NAME,null,VERSION_NUM);
    }
    @Override
    public void  onCreate(SQLiteDatabase db){
        try {
            db.execSQL(DATABASE_STATE_CREATE);
          //  db.execSQL(DATABASE_SCHEDULE_CREATE);
            Log.i("HouseDatabaseHelper","Calling onCreate");
        }catch (Exception e){
            e.printStackTrace();
        };

    }
    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVer,int newVer){
        Log.w(HouseDatabaseHelper.class.getName(),"Upgrading database from "+oldVer+" to "+newVer);
       // db.execSQL("DROP TABLE IF EXISTS "+TABLE_TEMPERATURE);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_STATE);
        onCreate(db);
        Log.i("Housedatabasehelper", "Upgrading");
    }
}
