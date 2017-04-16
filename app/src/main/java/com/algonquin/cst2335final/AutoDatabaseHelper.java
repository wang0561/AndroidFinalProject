/**
 * @version 1.0
 * @(#)ActivityDate.java 1.0 2017/04/19
 * this is a part of project for CST2335_010 Android final Project;
 * */
package com.algonquin.cst2335final;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



/**
 * This class is autmobile database help which creat the database and tables to store for  example
 * auto FM channels
 * @version 1.0
 * @author BO
 */
public class AutoDatabaseHelper extends SQLiteOpenHelper {


    private final static String ACTIVITY_NAME = "AutoDatabaseHelper" ;
    public static final String TABLE_NAME = "AutoFMChannelTable";
    public static final String AutoChannel = "AutoChannel";
    public static final String CHANNEL_ID = "FMChannelID";
    public static final String CHANNEL_NAME = "ChannelName";

    private static final String DATABASE_NAME = "AutoDatabase";
    private static final int DATABASE_VERSION = 1 ;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "( " + CHANNEL_ID
            + " integer primary key autoincrement, " + AutoChannel + " text not null, "
            + CHANNEL_NAME +" text not null " +  ");" ;


    private static final String[] fmChannelNamelist = {" LIVE",  " CBC", " New Country",
            " Rebel"," Chez", " Jump"};

    private static final String[] fmChannelIDList = {"88.5","91.5", "94.5", "101.7","106.1","106.9"};



/**
 * onRequestPermissionsResult method  check the permisssion from user to access GPS location
 * @param ctx context
 * */

    public AutoDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION );
    }

    public void onCreate(SQLiteDatabase db){


        db.execSQL(DATABASE_CREATE);
        for(int i =0;i<fmChannelNamelist.length;i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(AutoChannel, fmChannelIDList[i]);
            contentValues.put(CHANNEL_NAME, fmChannelNamelist[i]);

            db.insert(TABLE_NAME, null, contentValues);
        }

        Log.i(ACTIVITY_NAME, "Calling onCreate");


    };

    /**
     * onUpgrade method  when a newer version required database requires, his method will be called
     * @param db database
     *  @param oldVersion old version
     *  @param newVersion new Version
     * */

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
    };



}
