package com.algonquin.cst2335final;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class LivingRoomDatabaseHelper extends SQLiteOpenHelper {

    private String ACTIVITY_NAME = "LivingDatabaseHelper";

    public static final String TABLE_NAME = "LivingItems";
    public static final String LVINGITEM_ID = "LivingItemID";
    public static final String LIVINGITEM_KEY = "LivingItemName";
    public static final String LIVINGITEM_VALUE = "LivingItemStatus";
    public static final String DATABASE_NAME = "LivingItem.db";
    private static final int DATABASE_VERSION = 1;

    public static final String CREATE_TABLE_MESSAGE = "CREATE TABLE "
            + TABLE_NAME + "(  " + LVINGITEM_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + LIVINGITEM_KEY
            + " TEXT NOT NULL, " + LIVINGITEM_VALUE + " TEXT NOT NULL );";

    public static final String DROP_TABLE_MESSAGE = "DROP TABLE IF EXISTS "
            + TABLE_NAME + " ;";

    public LivingRoomDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MESSAGE);
        Log.i(ACTIVITY_NAME, "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL(DROP_TABLE_MESSAGE);
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldversion=" + oldVer + "  newVersion= " + newVer );
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer){
        db.execSQL(DROP_TABLE_MESSAGE);
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldversion=" + oldVer + "  newVersion= " + newVer );
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        Log.i(ACTIVITY_NAME, "Calling on open " + TABLE_NAME);
    }


}
