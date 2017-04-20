package com.algonquin.cst2335final;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**Database helper which could create and drop the database
 * Created by marvi on 4/5/2017.
 * @author chen
 * @version 1.0
 */

public class HouseDatabaseHelper extends SQLiteOpenHelper {
   /**name of the database*/
    final static String DATABASE_NAME = "House.db";
    /**version number*/
    final static int VERSION_NUM= 1;
    /**Table name of the statement*/
    final static String TABLE_STATE = "State_Table";
    /**Table name of the temperature*/
    final static String TABLE_TEMPERATURE = "tempe";
    /**Name of the database id*/
    final static String KEY_ID= "_id";
    /**Name of the device column*/
    final static String KEY_DEVICE= "device";
    /**Name of the statement column*/
    final static String KEY_STATE = "state";
    /**Name of the hour column*/
    final static String KEY_HOUR = "hour";
    /**Name of the min column*/
    final static String KEY_MIN="min";
    /**Name of the temperature column*/
    final static String KEY_TEMP="temp";
    /**query of the create statement table */
    final static String DATABASE_STATE_CREATE = "CREATE TABLE "+TABLE_STATE+ " ( "+KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+KEY_DEVICE+ " TEXT, " + KEY_STATE + " TEXT);";
    /**query of the create schedule table*/
    final static String DATABASE_SCHEDULE_CREATE = "CREATE TABLE "+TABLE_TEMPERATURE+" ( "+KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , " +KEY_HOUR +" INTEGER, "+ KEY_MIN+" INTEGER, " +KEY_TEMP +" INTEGER);";
    /**parameterized constructor which alos create the database
     * @param ctx*/
    public HouseDatabaseHelper(Context ctx){
        super(ctx,DATABASE_NAME,null,VERSION_NUM);
    }
    @Override
    public void  onCreate(SQLiteDatabase db){

            db.execSQL(DATABASE_STATE_CREATE);

            ContentValues cv = new ContentValues();
            cv.put(KEY_DEVICE,"door");
            cv.put(KEY_STATE,"false");
            db.insert(TABLE_STATE,"false",cv);

              ContentValues cv1 = new ContentValues();
              cv1.put(KEY_DEVICE,"light");
               cv1.put(KEY_STATE,"false");
               db.insert(TABLE_STATE,"false",cv1);

            db.execSQL(DATABASE_SCHEDULE_CREATE);
            Log.i("HouseDatabaseHelper","Calling onCreate");


    }
    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVer,int newVer){
        Log.w(HouseDatabaseHelper.class.getName(),"Upgrading database from "+oldVer+" to "+newVer);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_TEMPERATURE);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_STATE);
        onCreate(db);
        Log.i("Housedatabasehelper", "Upgrading");
    }
}
