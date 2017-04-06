package com.algonquin.cst2335final;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



/**
 * Created by beaul on 2017-03-30.
 */

public class AutoDatabaseHelper extends SQLiteOpenHelper {


    private final static String ACTIVITY_NAME = "AutoDatabaseHelper" ;
    public static final String TABLE_NAME = "AutoFMChannelTable";
    public static final String AutoChannel_ID = "AutoChanlId";
    //public static final String CHANNEL_ID = "FMChannelID";
    public static final String CHANNEL_NAME = "Channel";

    private static final String DATABASE_NAME = "Auto.d";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "( " + AutoChannel_ID
            + " integer primary key autoincrement, "   + CHANNEL_NAME +" text not null);" ;


    private static final String[] fmChannelNamelist = {"88.5M LIVE",  "91.5M CBC", "94.5M New Country",
            "101.7M Rebel","106.1M Chez", "106.9M Jump"};

   // private static final String[] fmChannelIDList = {"88.5","91.5", "94.5", "101.7","106.1","106.9"};




    public AutoDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION );
    }

    public void onCreate(SQLiteDatabase db){


        db.execSQL(DATABASE_CREATE);
        for(int i =0;i<fmChannelNamelist.length;i++) {
            ContentValues contentValues = new ContentValues();
           // contentValues.put(CHANNEL_ID, fmChannelIDList[i]);
            contentValues.put(CHANNEL_NAME, fmChannelNamelist[i]);

            db.insert(TABLE_NAME, null, contentValues);
        }

        Log.i(ACTIVITY_NAME, "Calling onCreate");


    };


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
    };



}
