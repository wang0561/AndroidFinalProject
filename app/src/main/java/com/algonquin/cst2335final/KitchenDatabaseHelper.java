package com.algonquin.cst2335final;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Wang on 4/6/2017.
 * @author Wang,Tao
 * @Version 1.0
 */

public class KitchenDatabaseHelper extends SQLiteOpenHelper {
    /**
     * activity name
     * */
    private String ACTIVITY_NAME = "KitchenDatabaseHelper";
/**
 * table name
 * */
    public static final String TABLE_NAME = "KitchenItems";
    /**
     * kitchen item id
     * */
    public static final String KITCHENITEM_ID = "kitchenItemID";
    /**
     * kitchen item key name
     * */
    public static final String KITCHENITEM_KEY = "kitchenItemName";
    /**
     * kitchen value
     * */
    public static final String KITCHENITEM_VALUE = "kitchenItemStatus";
    /**
     * database name
     * */
    public static final String DATABASE_NAME = "kitchenItem.db";
    /**
     *
     * database version number
     * */
    private static final int DATABASE_VERSION = 1;
     /**
      * create table query
      * */
    public static final String CREATE_TABLE_MESSAGE = "CREATE TABLE "
            + TABLE_NAME + "(  " + KITCHENITEM_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KITCHENITEM_KEY
            + " TEXT NOT NULL, " + KITCHENITEM_VALUE + " TEXT NOT NULL );";
     /**
      * drop talbe query
      * */
    public static final String DROP_TABLE_MESSAGE = "DROP TABLE IF EXISTS "
            + TABLE_NAME + " ;";
/**
 * constructor
 * @param  ctx current context
 *
 * */
    public KitchenDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION );
    }
/**
 *method for creating database
 * @param db database name
 * */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MESSAGE);
        ContentValues newValues1 = new ContentValues();
        newValues1.put(KitchenDatabaseHelper.KITCHENITEM_KEY,"Fridgetemp");
        newValues1.put(KitchenDatabaseHelper.KITCHENITEM_VALUE, 5);
        db.insert(KitchenDatabaseHelper.TABLE_NAME, null, newValues1);

        ContentValues newValues2 = new ContentValues();
        newValues2.put(KitchenDatabaseHelper.KITCHENITEM_KEY,"FreezerTemp");
        newValues2.put(KitchenDatabaseHelper.KITCHENITEM_VALUE, -10);
        db.insert(KitchenDatabaseHelper.TABLE_NAME, null, newValues2);

        ContentValues newValues3 = new ContentValues();
        newValues3.put(KitchenDatabaseHelper.KITCHENITEM_KEY,"lightStatus");
        newValues3.put(KitchenDatabaseHelper.KITCHENITEM_VALUE, "off");
        db.insert(KitchenDatabaseHelper.TABLE_NAME, null, newValues3);

        ContentValues newValues4 = new ContentValues();
        newValues4.put(KitchenDatabaseHelper.KITCHENITEM_KEY,"progressOfLight");
        newValues4.put(KitchenDatabaseHelper.KITCHENITEM_VALUE, 0);
        db.insert(KitchenDatabaseHelper.TABLE_NAME, null, newValues4);
        Log.i(ACTIVITY_NAME, "onCreate");
    }
   /**
    * method for update database version
    * @param db
    * @param oldVer
    * @param newVer
    *
    * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL(DROP_TABLE_MESSAGE);
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldversion=" + oldVer + "  newVersion= " + newVer );
    }
    /**
     * method for down grade database
     * @param db
     * @param oldVer
     * @param newVer
     *
     * */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL(DROP_TABLE_MESSAGE);
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldversion=" + oldVer + "  newVersion= " + newVer );
    }
/**
 * method for open database
 * @param db
 *
 * */
    @Override
    public void onOpen(SQLiteDatabase db){
        Log.i(ACTIVITY_NAME, "Calling on open " + TABLE_NAME);
    }
}
