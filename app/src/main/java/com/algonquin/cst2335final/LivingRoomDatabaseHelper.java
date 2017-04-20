package com.algonquin.cst2335final;
/**
 * Created by Min Luo, April 12, 2017
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class support create, drop,update database for livingroomactivity
 */
public class LivingRoomDatabaseHelper extends SQLiteOpenHelper {
    // define database name, table name, database columns including id, key, keyvalues
    private String ACTIVITY_NAME = "LivingDatabaseHelper";
    public static final String TABLE_NAME = "LivingItems";
    public static final String LVINGITEM_ID = "LivingItemID";
    public static final String LIVINGITEM_KEY = "LivingItemName";
    public static final String LIVINGITEM_VALUE = "LivingItemStatus";
    public static final String DATABASE_NAME = "LivingItem.db";
    private static final int DATABASE_VERSION = 1;

    //create a table in database using SQL
    public static final String CREATE_TABLE_MESSAGE = "CREATE TABLE "
            + TABLE_NAME + "(  " + LVINGITEM_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LIVINGITEM_KEY
            + " TEXT NOT NULL, " + LIVINGITEM_VALUE + " TEXT NOT NULL );";

    //delete a table in database using SQL
    public static final String DROP_TABLE_MESSAGE = "DROP TABLE IF EXISTS "
            + TABLE_NAME + " ;";

    // constructor with parameter
    public LivingRoomDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION );
    }

    /**
     * create database using SQL
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MESSAGE);
        ContentValues newValues1 = new ContentValues();
        newValues1.put(LIVINGITEM_KEY,"Lamp1Status");
        newValues1.put(LIVINGITEM_VALUE, "On");
        db.insert(TABLE_NAME, null, newValues1);

        ContentValues newValues2 = new ContentValues();
        newValues2.put(LIVINGITEM_KEY,"Lamp2Progress");
        newValues2.put(LIVINGITEM_VALUE, "50");
        db.insert(TABLE_NAME, null, newValues2);

        ContentValues newValues3 = new ContentValues();
        newValues3.put(LIVINGITEM_KEY,"Lamp3Progress");
        newValues3.put(LIVINGITEM_VALUE, "50");
        db.insert(TABLE_NAME, null, newValues3);

        ContentValues newValues4 = new ContentValues();
        newValues4.put(LIVINGITEM_KEY,"Lamp3Color");
        newValues4.put(LIVINGITEM_VALUE, "0");
        db.insert(TABLE_NAME, null, newValues4);

        ContentValues newValues5 = new ContentValues();
        newValues5.put(LIVINGITEM_KEY,"TVChannel");
        newValues5.put(LIVINGITEM_VALUE, "10");
        db.insert(TABLE_NAME, null, newValues5);

        ContentValues newValues6 = new ContentValues();
        newValues6.put(LIVINGITEM_KEY,"BlindsHeight");
        newValues6.put(LIVINGITEM_VALUE, "10");
        db.insert(TABLE_NAME, null, newValues6);
        Log.i(ACTIVITY_NAME, "onCreate");
    }

    /**
     *  upgrade database
     * @param db, Type SQLiteDatabase
     * @param oldVer, Type int
     * @param newVer, Type int
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL(DROP_TABLE_MESSAGE);
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldversion=" + oldVer + "  newVersion= " + newVer );
    }

    /**
     * downgrade database
     * @param db, Type SQLiteDatabase
     * @param oldVer, Type int
     * @param newVer, Type int
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL(DROP_TABLE_MESSAGE);
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldversion=" + oldVer + "  newVersion= " + newVer );
    }

    /**
     *  open database
     * @param db, Type SQLiteDatabase
     */
    @Override
    public void onOpen(SQLiteDatabase db){
        Log.i(ACTIVITY_NAME, "Calling on open " + TABLE_NAME);
    }


}
