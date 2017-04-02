package com.algonquin.cst2335final;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



/**
 * Created by beaul on 2017-03-30.
 */

public class AutoDatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "FMControl";
    public static final String CHANNEL_ID = "FMChannelID";
    public static final String CHANNEL_NAME = "ChannelName";
    public static final String CHANNEL_Description= "ChannelDescriptionName";

    private static final String DATABASE_NAME = "Auto.d";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "( " + CHANNEL_ID
            + " integer primary key autoincrement, " + CHANNEL_NAME
            + " text not null "+ CHANNEL_Description +" text not null);" ;



    public AutoDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION );
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(DATABASE_CREATE);
        Log.i("ChatDatabaseHelper", "Calling onCreate");


    };


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
    };



}
