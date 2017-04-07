package com.algonquin.cst2335final;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LivingRoomActivity extends AppCompatActivity {
    String current, min, max, iconName;
    Bitmap image;

    private Context ctx;
    private ListView livingroomlist;
    private String[] livingroom = {"Lamp On/Off", "Lamp Dimmable", "Lamp Colorful", "TV", "Window Blinds"};
    protected Boolean isTablet;
    private int myLamp1Counter,myLamp2Counter, myLamp2Progress, myLamp3Progress, myLamp3Counter, myLamp3Color, myTVChannel, myTVCounter, myBlindsCounter, myBlindsHeight;
    private String strLamp1Status;
    LivingFragmentActivity livingroomfrag;
    Bundle bundle;
    protected static LivingRoomDatabaseHelper livingDataHelper;
    protected SQLiteDatabase db;
    protected Cursor results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room);

        Button btrefresh = (Button) findViewById(R.id.livingroomrefreshbt);
        btrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar livingProg = (ProgressBar) findViewById(R.id.livingProgressBar);
                livingProg.setVisibility(View.VISIBLE);
                LivingStatus livingStatus = new LivingStatus();
                livingStatus.execute();
            }
        });

        ctx = this;
        bundle  = new Bundle();
        //Open a file for storing shared preferences:
        final SharedPreferences prefs = getSharedPreferences("livingroomFile", Context.MODE_PRIVATE);
        //Read the number of times run in the file:
        int ifirstrun = prefs.getInt("LivingRoomFirstRun", 0);

        if (ifirstrun <= 1){

            livingDataHelper = new LivingRoomDatabaseHelper(ctx);
            db = livingDataHelper.getReadableDatabase();
            db.execSQL(LivingRoomDatabaseHelper.DROP_TABLE_MESSAGE);
            db.execSQL(LivingRoomDatabaseHelper.CREATE_TABLE_MESSAGE);

            ContentValues newValues1 = new ContentValues();
            newValues1.put(livingDataHelper.LIVINGITEM_KEY,"Lamp1Status");
            newValues1.put(livingDataHelper.LIVINGITEM_VALUE, "On");
            db.insert(livingDataHelper.TABLE_NAME, null, newValues1);

            ContentValues newValues2 = new ContentValues();
            newValues2.put(livingDataHelper.LIVINGITEM_KEY,"Lamp2Progress");
            newValues2.put(livingDataHelper.LIVINGITEM_VALUE, "50");
            db.insert(livingDataHelper.TABLE_NAME, null, newValues2);

            ContentValues newValues3 = new ContentValues();
            newValues3.put(livingDataHelper.LIVINGITEM_KEY,"Lamp3Progress");
            newValues3.put(livingDataHelper.LIVINGITEM_VALUE, "50");
            db.insert(livingDataHelper.TABLE_NAME, null, newValues3);

            ContentValues newValues4 = new ContentValues();
            newValues4.put(livingDataHelper.LIVINGITEM_KEY,"Lamp3Color");
            newValues4.put(livingDataHelper.LIVINGITEM_VALUE, "0");
            db.insert(livingDataHelper.TABLE_NAME, null, newValues4);

            ContentValues newValues5 = new ContentValues();
            newValues5.put(livingDataHelper.LIVINGITEM_KEY,"TVChannel");
            newValues5.put(livingDataHelper.LIVINGITEM_VALUE, "10");
            db.insert(livingDataHelper.TABLE_NAME, null, newValues5);

            ContentValues newValues6 = new ContentValues();
            newValues6.put(livingDataHelper.LIVINGITEM_KEY,"BlindsHeight");
            newValues6.put(livingDataHelper.LIVINGITEM_VALUE, "10");
            db.insert(livingDataHelper.TABLE_NAME, null, newValues6);
            //
        }

        //Open a file for storing shared preferences:
        //final SharedPreferences prefs =  getSharedPreferences("livingroomFile", Context.MODE_PRIVATE);
        //Get an editor object for writing to the file:
        SharedPreferences.Editor writer = prefs.edit();
        writer.putInt("LivingRoomFirstRun", ++ifirstrun);

        //Write the file:
        writer.commit();



        isTablet = (findViewById(R.id.livingroomfragmentHolder)!=null); // boolean variable to tell if it's a tablet
        livingroomlist = (ListView)findViewById(R.id.livinglists);
        livingroomlist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                if(!isTablet) {//phone
                    switch (position) {
                        case 0: //lamp1
                            Intent lamp1Intent = new Intent(LivingRoomActivity.this, LivingRoomMessageDetails.class);


                            strLamp1Status = getValue("Lamp1Status");
                            //myLamp1Counter = Integer.parseInt(getValue("Lamp1Counter"));

                            lamp1Intent.putExtra("Lamp1Status",strLamp1Status);
                            lamp1Intent.putExtra("Lamp1Counter", myLamp1Counter);
                            lamp1Intent.putExtra("ItemID",0);
                            lamp1Intent.putExtra("IsTablet",0);
                            startActivityForResult(lamp1Intent, 5); //go to view fragment details
                            break;
                        case 1: //lamp2
                            Intent lamp2intent = new Intent(LivingRoomActivity.this, LivingRoomMessageDetails.class);
                            lamp2intent.putExtra("Lamp2Progress", myLamp2Progress);
                            lamp2intent.putExtra("Lamp2Counter", myLamp2Counter);
                            lamp2intent.putExtra("ItemID",1);
                            lamp2intent.putExtra("IsTablet",0);
                            startActivityForResult(lamp2intent, 10);
                            break;
                        case 2: //lamp3
                            Intent lamp3intent= new Intent(LivingRoomActivity.this, LivingRoomMessageDetails.class);
                            lamp3intent.putExtra("Lamp3Progress", myLamp3Progress);
                            lamp3intent.putExtra("Lamp3Counter", myLamp3Counter);
                            lamp3intent.putExtra("Lamp3Color", myLamp3Color);
                            lamp3intent.putExtra("ItemID",2);
                            lamp3intent.putExtra("IsTablet",0);
                            startActivityForResult(lamp3intent, 15);
                            break;
                        case 3: // tv
                            Intent tvActivity = new Intent(LivingRoomActivity.this, LivingRoomMessageDetails.class);
                            tvActivity.putExtra("TVChannel", myTVChannel);
                            tvActivity.putExtra("TVCounter", myTVCounter);
                            tvActivity.putExtra("ItemID",3);
                            tvActivity.putExtra("IsTablet",0);
                            startActivityForResult(tvActivity, 20);
                            break;
                        case 4: // blinds
                            Intent blindsActivity = new Intent(LivingRoomActivity.this, LivingRoomMessageDetails.class);
                            blindsActivity.putExtra("BlindsHeight", myBlindsHeight);
                            blindsActivity.putExtra("BlindsCounter", myBlindsCounter);
                            blindsActivity.putExtra("ItemID",4);
                            blindsActivity.putExtra("IsTablet",0);
                            startActivityForResult(blindsActivity, 25);
                            break;
                    }
                }else{// tablet
                    //livingroomfrag = new LivingFragmentActivity(LivingRoomActivity.this);
                    //    livingroomfrag.setArguments(bundle);
                    switch (position) {
                        case 0: //lamp1
                            Lamp1Activity lamp1intent = new Lamp1Activity(LivingRoomActivity.this);
                            Bundle bundle = new Bundle();
                            bundle.putString("Lamp1Status",strLamp1Status);
                            bundle.putInt("Lamp1Counter", myLamp1Counter);
                            bundle.putInt("ItemID",0);
                            bundle.putInt("IsTablet",1);
                            lamp1intent.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.livingroomfragmentHolder, lamp1intent).commit();
                            break;
                        case 1://lamp2

                            Lamp2Activity lamp2intent = new Lamp2Activity(LivingRoomActivity.this);
                            bundle = new Bundle();
                            bundle.putInt("Lamp2Progress", myLamp2Progress);
                            bundle.putInt("Lamp2Counter", myLamp2Counter);
                            bundle.putInt("ItemID",1);
                            bundle.putInt("IsTablet",1);
                            lamp2intent.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.livingroomfragmentHolder, lamp2intent).commit();
                            break;

                        case 2://lamp3

                            Lamp3Activity lamp3intent = new Lamp3Activity(LivingRoomActivity.this);
                            bundle = new Bundle();
                            bundle.putInt("Lamp3Progress", myLamp3Progress);
                            bundle.putInt("Lamp3Counter", myLamp3Counter);
                            bundle.putInt("Lamp3Color", myLamp3Color);
                            bundle.putInt("ItemID",2);
                            bundle.putInt("IsTablet",1);
                            lamp3intent.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.livingroomfragmentHolder, lamp3intent).commit();
                            break;
                        case 3://tv
                            TVActivity tvintent = new TVActivity(LivingRoomActivity.this);
                            bundle = new Bundle();
                            bundle.putInt("TVChannel", myTVChannel);
                            bundle.putInt("TVCounter", myTVCounter);
                            bundle.putInt("ItemID",3);
                            bundle.putInt("IsTablet",1);
                            tvintent.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.livingroomfragmentHolder, tvintent).commit();
                            break;
                        case 4: // blinds
                            BlindsActivity blindsintent = new BlindsActivity(LivingRoomActivity.this);
                            bundle = new Bundle();
                            bundle.putInt("BlindsHeight", myBlindsHeight);
                            bundle.putInt("BlindsCounter", myBlindsCounter);
                            bundle.putInt("ItemID",4);
                            bundle.putInt("IsTablet",1);
                            blindsintent.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.livingroomfragmentHolder, blindsintent).commit();
                            break;

                    }
                }
            }
        });

        //Open a file for storing shared preferences:
        //final SharedPreferences prefs = getSharedPreferences("livingroomFile", Context.MODE_PRIVATE);
        //Read the number of times run in the file:
        //strLamp1Status = prefs.getString("Lamp1Status", "Off");


   try {
       strLamp1Status = getValue("Lamp1Status");
       myLamp2Progress = Integer.parseInt(getValue("Lamp2Progress"));
       myLamp3Progress = Integer.parseInt(getValue("Lamp3Progress"));
       myLamp3Color = Integer.parseInt(getValue("Lamp3Color"));
       myTVChannel = Integer.parseInt(getValue("TVChannel"));
       myBlindsHeight = Integer.parseInt(getValue("BlindsHeight"));
   }catch (Exception e){

   }


        strLamp1Status = getValue("Lamp1Status");
        myLamp2Progress = Integer.parseInt(getValue("Lamp2Progress"));
        myLamp3Progress = Integer.parseInt(getValue("Lamp3Progress"));
        myLamp3Color = Integer.parseInt(getValue("Lamp3Color"));
        myTVChannel = Integer.parseInt(getValue("TVChannel"));
        myBlindsHeight = Integer.parseInt(getValue("BlindsHeight"));



        //myLamp2Progress = prefs.getInt("Lamp2Progress", 0);
        //myLamp3Progress = prefs.getInt("Lamp3Progress", 0);
        //myLamp3Color = prefs.getInt("Lamp3Color", 0);
        //myTVChannel = prefs.getInt("TVChannel", 0);
        //myBlindsHeight = prefs.getInt("BlindsHeight", 0);

        livingroom[0] = "Lamp1 is " + strLamp1Status;
        livingroom[1] = "Lamp2 is " + myLamp2Progress;
        livingroom[2] = "Lamp3 is " + myLamp3Progress + " and color is " + myLamp3Color;
        livingroom[3] = "TV is tuned to channel " + myTVChannel;
        livingroom[4] = "Blinds is tuned to  " + myBlindsHeight + "  cm high";

        myLamp1Counter = prefs.getInt("Lamp1Counter", 0);
        myLamp2Counter = prefs.getInt("Lamp2Counter", 0);
        myLamp3Counter = prefs.getInt("Lamp3Counter", 0);
        myTVCounter = prefs.getInt("TVCounter", 0);
        myBlindsCounter = prefs.getInt("BlindsCounter", 0);

        livingroomlist.setAdapter(new ArrayAdapter<>(this, R.layout.living_row_layout, livingroom ));
        Log.i("LivingRoomActivity", "OnCreate");

        Button btdialog = (Button) findViewById(R.id.livingroomcustombt);
        btdialog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder customBuild = new AlertDialog.Builder(ctx);
                LayoutInflater inflater = getLayoutInflater();
                final View v = inflater.inflate(R.layout.activity_living_custom_dialog, null);
                customBuild.setView(inflater.inflate(R.layout.activity_living_custom_dialog, null))
                        .setPositiveButton("YES", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("Yes", "Yes");
                                EditText inputMessage = (EditText) v.findViewById(R.id.livingcustomtext);
                                //Log.i("Text is " + inputMessage.getText().toString());

                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int id){
                                Log.i("No", "No");
                            }
                        });
                customBuild.setView(v);
                customBuild.create().show();
            }
        });

    }

    public void onStart(){
        super.onStart();
        Log.i("LivingRoomActivity", "onStart");
    }

    public void onResume(){

        super.onResume();
        Log.i("LivingRoomActivity", "onResume");
    }

    public void onPause(){

        super.onPause();
        Log.i("LivingRoomActivity", "onPause");
    }

    public void onStop()
    {
        super.onStop();

        Log.i("LivingRoomActivity", "onStop");
    }

    public void onDestroy(){

        super.onDestroy();
        /*
        //Open a file for storing shared preferences:
        final SharedPreferences prefs = getSharedPreferences("livingroomFile", Context.MODE_PRIVATE);
        //Get an editor object for writing to the file:
        SharedPreferences.Editor writer = prefs.edit();

        writer.putString("Lamp1Status", strLamp1Status);

        writer.putInt("LampCounter",myLamp1Counter);
        //Write the file:
        writer.commit();
        */
        Log.i("LivingRoomActivity", "ondestroy");
    }
    //This function gets called when another activity has finished and this activity is resuming:
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(data != null)
        {
            String fromOtherActivity = data.getStringExtra("Status");
            Log.d("LivingRoomActivity", "Back from  other activity: " + fromOtherActivity);


            if(requestCode == 5 && resultCode == 0){
                strLamp1Status = data.getStringExtra("Lamp1Status");
            }else if(requestCode == 10 && resultCode == 0){
                myLamp2Progress = data.getIntExtra("Lamp2Progress",0);
            }else if (requestCode == 15 && resultCode == 0){
                myLamp3Progress = data.getIntExtra("Lamp3Progress", 0);
                myLamp3Color = data.getIntExtra("Lamp3Color",0);
            }else if (requestCode == 20 && resultCode == 0){
                myTVChannel = data.getIntExtra("TVChannel", 0);
            }else if(requestCode == 25 && resultCode == 0) {
                myBlindsHeight = data.getIntExtra("BlindsHeight", 0);
            }

            putValue("Lamp1Status",strLamp1Status);
            putValue("Lamp2Progress",""+myLamp2Progress);
            putValue("Lamp3Progress",""+myLamp3Progress);
            putValue("Lamp3Color",""+myLamp3Color);
            putValue("TVChannel",""+myTVChannel);
            putValue("BlindsHeight",""+myBlindsHeight);
            //
            putValue("Lamp1Counter",""+myLamp1Counter);

            livingroom[0] = "Lamp1 is " + strLamp1Status;
            livingroom[1] = "Lamp2 is " + myLamp2Progress + " degree";
            livingroom[2] = "Lamp3 is " + myLamp3Progress + " and color is " + myLamp3Color;
            livingroom[3] = "TV is tuned to channel " + myTVChannel;
            livingroom[4] = "Blinds is tuned to  " + myBlindsHeight + "  meters height";

            livingroomlist.setAdapter(new ArrayAdapter<>(this, R.layout.living_row_layout, livingroom ));

        }
    }

    public void synclamp1(String lamp1status) {
        putValue("Lamp1Status",strLamp1Status);
        //
        putValue("Lamp1Counter",""+myLamp1Counter);

        strLamp1Status = lamp1status;
        livingroom[0] = "Lamp1 is " + strLamp1Status;

        livingroomlist.setAdapter(new ArrayAdapter<>(this, R.layout.living_row_layout, livingroom ));
    }


    public void synclamp2(int lamp2progress) {
        myLamp2Progress = lamp2progress;
        putValue("Lamp2Progress",""+myLamp2Progress);

        livingroom[1] = "Lamp2 is " + myLamp2Progress + " degree";

        livingroomlist.setAdapter(new ArrayAdapter<>(this, R.layout.living_row_layout, livingroom ));
    }

    public void synclamp3(int lamp3progress, int color) {
        myLamp3Progress = lamp3progress;
        myLamp3Color = color;
        putValue("Lamp3Progress",""+myLamp3Progress);
        putValue("Lamp3Color",""+myLamp3Color);

        livingroom[2] = "Lamp3 is " + myLamp3Progress + " and color is " + myLamp3Color;

        livingroomlist.setAdapter(new ArrayAdapter<>(this, R.layout.living_row_layout, livingroom ));
    }

    public void synctv(int tvChannel) {
        myTVChannel = tvChannel;
        putValue("TVChannel",""+myTVChannel);

        livingroom[3] = "TV is tuned to channel " + myTVChannel;

        livingroomlist.setAdapter(new ArrayAdapter<>(this, R.layout.living_row_layout, livingroom ));
    }

    public void syncblinds(int blindsHeight) {
        myBlindsHeight = blindsHeight;
        putValue("BlindsHeight",""+myBlindsHeight);

        //livingroom[0] = "Lamp1 is " + strLamp1Status;
        //livingroom[1] = "Lamp2 is " + myLamp2Progress + " degree";
        //livingroom[2] = "Lamp3 is " + myLamp3Progress + " and color is " + myLamp3Color;
        //livingroom[3] = "TV is tuned to channel " + myTVChannel;
        livingroom[4] = "Blinds is tuned to  " + myBlindsHeight + "  meters height";

        livingroomlist.setAdapter(new ArrayAdapter<>(this, R.layout.living_row_layout, livingroom ));
    }

    public void removeFragmentLamp1(Lamp1Activity  lamp1){
        getSupportFragmentManager().beginTransaction().remove(lamp1).commit();
    }

    public void removeFragmentLamp2(Lamp2Activity  lamp2){
        getSupportFragmentManager().beginTransaction().remove(lamp2).commit();
    }

    public void removeFragmentLamp3(Lamp3Activity  lamp3){
        getSupportFragmentManager().beginTransaction().remove(lamp3).commit();
    }

    public void removeFragmentTV(TVActivity tv){
        getSupportFragmentManager().beginTransaction().remove(tv).commit();
    }

    public void removeFragmentBlinds(BlindsActivity blinds){
        getSupportFragmentManager().beginTransaction().remove(blinds).commit();
    }

    public String getValue(String strname){
        livingDataHelper = new LivingRoomDatabaseHelper(this);
        db = livingDataHelper.getReadableDatabase();

        //String query = String
          //      .format("SELECT * FROM %s WHERE %s=%s", livingDataHelper.TABLE_NAME, livingDataHelper.LIVINGITEM_KEY,strname);
        //results = db.rawQuery(query, null);

        results = db.query(false, livingDataHelper.TABLE_NAME,
                new String[]{ livingDataHelper.LVINGITEM_ID, livingDataHelper.LIVINGITEM_KEY, livingDataHelper.LIVINGITEM_VALUE},
                livingDataHelper.LVINGITEM_ID + " not null",
                null, null, null, null, null);

        int count = results.getCount();

        results.moveToFirst();
        while( ! results.isAfterLast() ){
            String str1 = results.getString(results.getColumnIndex(livingDataHelper.LVINGITEM_ID));
            String str2 = results.getString(results.getColumnIndex(livingDataHelper.LIVINGITEM_KEY));
            String str3 = results.getString(results.getColumnIndex(livingDataHelper.LIVINGITEM_VALUE));
            if(strname.compareTo(str2)==0){
                return str3;
            }
            results.moveToNext();
        }
        return null;
    }

    public void putValue(String strname, String value){
        livingDataHelper = new LivingRoomDatabaseHelper(this);
        db = livingDataHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(livingDataHelper.LIVINGITEM_KEY,strname);
        newValues.put(livingDataHelper.LIVINGITEM_VALUE, value);
        //db.insert(livingDataHelper.TABLE_NAME, null, newValues);

        try {
            db.update(livingDataHelper.TABLE_NAME, newValues, livingDataHelper.LIVINGITEM_KEY + "='" + strname+"'", null);
        }catch(Exception e) {
            //db.insert(livingDataHelper.TABLE_NAME, null, newValues);
        }
    }


        }
    }

    public void synclamp1(String lamp1status) {
        putValue("Lamp1Status",strLamp1Status);
        //
        putValue("Lamp1Counter",""+myLamp1Counter);

        strLamp1Status = lamp1status;
        livingroom[0] = "Lamp1 is " + strLamp1Status;

        livingroomlist.setAdapter(new ArrayAdapter<>(this, R.layout.living_row_layout, livingroom ));
    }


    public void synclamp2(int lamp2progress) {
        myLamp2Progress = lamp2progress;
        putValue("Lamp2Progress",""+myLamp2Progress);

        livingroom[1] = "Lamp2 is " + myLamp2Progress + " degree";

        livingroomlist.setAdapter(new ArrayAdapter<>(this, R.layout.living_row_layout, livingroom ));
    }

    public void synclamp3(int lamp3progress, int color) {
        myLamp3Progress = lamp3progress;
        myLamp3Color = color;
        putValue("Lamp3Progress",""+myLamp3Progress);
        putValue("Lamp3Color",""+myLamp3Color);

        livingroom[2] = "Lamp3 is " + myLamp3Progress + " and color is " + myLamp3Color;

        livingroomlist.setAdapter(new ArrayAdapter<>(this, R.layout.living_row_layout, livingroom ));
    }

    public void synctv(int tvChannel) {
        myTVChannel = tvChannel;
        putValue("TVChannel",""+myTVChannel);

        livingroom[3] = "TV is tuned to channel " + myTVChannel;

        livingroomlist.setAdapter(new ArrayAdapter<>(this, R.layout.living_row_layout, livingroom ));
    }

    public void syncblinds(int blindsHeight) {
        myBlindsHeight = blindsHeight;
        putValue("BlindsHeight",""+myBlindsHeight);

        //livingroom[0] = "Lamp1 is " + strLamp1Status;
        //livingroom[1] = "Lamp2 is " + myLamp2Progress + " degree";
        //livingroom[2] = "Lamp3 is " + myLamp3Progress + " and color is " + myLamp3Color;
        //livingroom[3] = "TV is tuned to channel " + myTVChannel;
        livingroom[4] = "Blinds is tuned to  " + myBlindsHeight + "  meters height";

        livingroomlist.setAdapter(new ArrayAdapter<>(this, R.layout.living_row_layout, livingroom ));
    }

    public void removeFragmentLamp1(Lamp1Activity  lamp1){
        getSupportFragmentManager().beginTransaction().remove(lamp1).commit();
    }

    public void removeFragmentLamp2(Lamp2Activity  lamp2){
        getSupportFragmentManager().beginTransaction().remove(lamp2).commit();
    }

    public void removeFragmentLamp3(Lamp3Activity  lamp3){
        getSupportFragmentManager().beginTransaction().remove(lamp3).commit();
    }

    public void removeFragmentTV(TVActivity tv){
        getSupportFragmentManager().beginTransaction().remove(tv).commit();
    }

    public void removeFragmentBlinds(BlindsActivity blinds){
        getSupportFragmentManager().beginTransaction().remove(blinds).commit();
    }

    public String getValue(String strname){
        livingDataHelper = new LivingRoomDatabaseHelper(this);
        db = livingDataHelper.getReadableDatabase();

        //String query = String
          //      .format("SELECT * FROM %s WHERE %s=%s", livingDataHelper.TABLE_NAME, livingDataHelper.LIVINGITEM_KEY,strname);
        //results = db.rawQuery(query, null);

        results = db.query(false, livingDataHelper.TABLE_NAME,
                new String[]{ livingDataHelper.LVINGITEM_ID, livingDataHelper.LIVINGITEM_KEY, livingDataHelper.LIVINGITEM_VALUE},
                livingDataHelper.LVINGITEM_ID + " not null",
                null, null, null, null, null);

        int count = results.getCount();

        results.moveToFirst();
        while( ! results.isAfterLast() ){
            String str1 = results.getString(results.getColumnIndex(livingDataHelper.LVINGITEM_ID));
            String str2 = results.getString(results.getColumnIndex(livingDataHelper.LIVINGITEM_KEY));
            String str3 = results.getString(results.getColumnIndex(livingDataHelper.LIVINGITEM_VALUE));
            if(strname.compareTo(str2)==0){
                return str3;
            }
            results.moveToNext();
        }
        return null;
    }

    public void putValue(String strname, String value){
        livingDataHelper = new LivingRoomDatabaseHelper(this);
        db = livingDataHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(livingDataHelper.LIVINGITEM_KEY,strname);
        newValues.put(livingDataHelper.LIVINGITEM_VALUE, value);
        //db.insert(livingDataHelper.TABLE_NAME, null, newValues);

        try {
            db.update(livingDataHelper.TABLE_NAME, newValues, livingDataHelper.LIVINGITEM_KEY + "='" + strname+"'", null);
        }catch(Exception e) {
            //db.insert(livingDataHelper.TABLE_NAME, null, newValues);
        }
    }


    public class LivingStatus extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... args){
            String in = "";
            try{
                livingDataHelper = new LivingRoomDatabaseHelper(ctx);
                db = livingDataHelper.getReadableDatabase();

                //String query = String
                //      .format("SELECT * FROM %s WHERE %s=%s", livingDataHelper.TABLE_NAME, livingDataHelper.LIVINGITEM_KEY,strname);
                //results = db.rawQuery(query, null);

                results = db.query(false, livingDataHelper.TABLE_NAME,
                        new String[]{ livingDataHelper.LVINGITEM_ID, livingDataHelper.LIVINGITEM_KEY, livingDataHelper.LIVINGITEM_VALUE},
                        livingDataHelper.LVINGITEM_ID + " not null",
                        null, null, null, null, null);

                int count = results.getCount();

                results.moveToFirst();
                while( ! results.isAfterLast() ){
                    String str2 = results.getString(results.getColumnIndex(livingDataHelper.LIVINGITEM_KEY));
                    if(str2.compareTo("Lamp1Status")==0){
                        publishProgress(25);
                        Thread.sleep(1000);
                    }
                    if(str2.compareTo("Lamp3Color")==0){
                        publishProgress(50);
                        Thread.sleep(1000);
                    }
                    if(str2.compareTo("BlindsHeight")==0){
                        publishProgress(75);
                        Thread.sleep(1000);
                    }
                    results.moveToNext();
                }
                publishProgress(100);
                Thread.sleep(1000);
            } catch (Exception me) {
                Log.e("AsyncTask", "Malformed URL:" + me.getMessage());
            }

            return in;
        }

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }


        public Bitmap getImage(URL url) {

            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        public Bitmap getImage(String urlString) {
            try {
                URL url = new URL(urlString);
                return getImage(url);
            } catch (MalformedURLException e) {
                return null;
            }
        }

        public void onProgressUpdate(Integer... values) {

            ProgressBar progressBar = (ProgressBar) findViewById(R.id.livingProgressBar);
            progressBar.setProgress(values[0]);
            progressBar.setVisibility(View.VISIBLE);
        }

        public void onPostExecute(String result) {

            /*
            TextView currentTempView = (TextView) findViewById(R.id.currentTemp);
            currentTempView.setText("Current:" + current);

            TextView minTempView = (TextView) findViewById(R.id.minTemp);
            minTempView.setText("Min:" + min);

            TextView maxTempView = (TextView) findViewById(R.id.maxTemp);
            maxTempView.setText("Max:" + max);

            ImageView imageView = (ImageView) findViewById(R.id.weatherImageView);
            imageView.setImageBitmap(image);*/

            ProgressBar progressBar = (ProgressBar) findViewById(R.id.livingProgressBar);
            progressBar.setVisibility(View.INVISIBLE);
        }

    }
}

