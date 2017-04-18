package com.algonquin.cst2335final;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
/**
 * Main activity of the house part
 * @author Chen
 * @version 1.0*/
public class HouseActivity extends AppCompatActivity {

    /**the progressBar*/
    protected ProgressBar progressBar;
    /**the progress of the progressBar*/
    protected int progress;
    /**check if this is a tablet*/
    protected static boolean isTablet;

    /**store the context of the activity*/
    protected Context context;

    /**databaseHelper to create the database*/
    protected static HouseDatabaseHelper myhelper;

    /**device name for passing the information*/
    protected static String tempDevice;
    /**statement name for passing the information*/
    protected static String tempState;
    /**statement bundle for passing the information*/
    protected static Bundle tempStatementBundle;


    /**getData which get the adapter of the arraylist
     * @return data*/
    //http://www.cnblogs.com/devinzhang/archive/2012/01/20/2328334.html
    private List<String> getData(){
    List<String> data = new ArrayList<>();
        data.add("Garage");
        data.add("Home temperature");
        data.add("Outside weather");
        data.add("Help");
        return data;
    }

    /**Override the onCreate method to start the application
     * @param savedInstanceState*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);
        final SharedPreferences pref = getSharedPreferences("counter",MODE_PRIVATE);
        myhelper = new HouseDatabaseHelper(this);
        context =this;
        isTablet = (findViewById(R.id.HouseFrameLayout) != null);
        progressBar = (ProgressBar)findViewById(R.id.houseProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        new Thread(()-> {
            progress = 0;
            while(progress <100) {
                progress += 10;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(progress);
                    }
                });
                try {
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();


        //run the sql to check the statement of the devices
        checkDeviceState();


        ListView HouseListView = (ListView)findViewById(R.id.HouseListView);
        //load the data in to listView
        HouseListView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,getData()));

        HouseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isTablet){//tablet
                    if(id < 2) {
                        Log.i("House", "Customer Clicked the listView, ID is " + id + " position is " + position);
                        Bundle bundle = new Bundle();
                        bundle.putLong("ID", id);
                        bundle.putBundle("StateData",tempStatementBundle);
                        ListDetailHouseFragment fragment = new ListDetailHouseFragment(HouseActivity.this);
                        fragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.HouseFrameLayout, fragment).commit();
                    }else if (id ==2){
                        Intent intent = new Intent(HouseActivity.this, HouseWeather.class);
                        startActivity(intent,null);
                    }else{
                        //help dialog
                        //build the help dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("HELP");
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogView =inflater.inflate(R.layout.house_help_dialog,null);
                        builder.setView(dialogView);
                        builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.show();
                    }

                }else{//phone
                    if(id == 0){
                        Log.i("HouseActivity","Is Phone, Garage");
                        Intent intent = new Intent(HouseActivity.this, House_Fragment_Detail_garage.class);
                        intent.putExtra("ID",id);
                        intent.putExtra("StateData",tempStatementBundle);
                        startActivityForResult(intent,0);
                    }else if (id == 1){
                        Log.i("HouseActivity","Is Phone, temp");
                        Intent intent = new Intent(HouseActivity.this, House_Fragment_Detail_Temperature.class);
                        intent.putExtra("ID",id);

                        startActivityForResult(intent,1);

                    }else if(id ==2 ){
                        Log.i("HouseActivity","Is Phone, weather");
                        Intent intent = new Intent(HouseActivity.this, HouseWeather.class);
                        intent.putExtra("ID",id);
                        startActivityForResult(intent,2);
                    }else {
                        Log.i("HouseActivity", "Is Phone, Help");
                        //help dialog
                        //build the help dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("HELP");
                        LayoutInflater inflater = getLayoutInflater();
                        View dialogView =inflater.inflate(R.layout.house_help_dialog,null);
                        builder.setView(dialogView);
                        builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            //donothing
                            }
                        });

                        builder.show();

                    }

                }
            }
        });
    }

    /**
     * catch the data which return by startActivityforresult
     * @param requestCode
     * @param resultCode
     * @param data
     *
     * */
    public void onActivityResult(int requestCode, int resultCode, Intent data){
            if (data != null){
                if (requestCode == 0 && resultCode ==0) {
                    Log.i("onActivityResult", data.getStringExtra("state"));
                    Log.i("onActivityResult", data.getStringExtra("device"));

                    if(data.getStringExtra("device").equals("door")&&data.getStringExtra("state").equals("true")){
                    putValue("door","true");
                        putValue("light","true");
                    }else{
                        putValue(data.getStringExtra("device"),data.getStringExtra("state"));

                    }

                }
                }
    }
    /**Override the onResume method which can also check the device statement*/
    @Override
    public void onResume(){
        super.onResume();
        //check device statement again when resume
        checkDeviceState();
    }

    /**
     * a method which could access the database and insert query
     * @param strname
     * @param value*/
    public static void putValue(String strname, String value){
        final SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(HouseDatabaseHelper.KEY_DEVICE,strname);
        newValues.put(HouseDatabaseHelper.KEY_STATE, value);
       // db.insert(myhelper.TABLE_STATE, null, newValues);

        try {
            db.update(HouseDatabaseHelper.TABLE_STATE, newValues, HouseDatabaseHelper.KEY_DEVICE + "='" + strname+"'", null);
            Log.i("database update","1"+strname+"2"+value);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * a method which could access the database and insert query for temperature
     * @param hour
     * @param min
     * @param temp*/
    public static void putTempValue(int hour, int min, int temp){
        final SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(HouseDatabaseHelper.KEY_HOUR,hour);
        newValues.put(HouseDatabaseHelper.KEY_MIN, min);
        newValues.put(HouseDatabaseHelper.KEY_TEMP,temp);
        // db.insert(myhelper.TABLE_STATE, null, newValues);
        db.insert(HouseDatabaseHelper.TABLE_TEMPERATURE,null,newValues);
        Log.i("inDatabase","Hour"+hour+"min"+min+"temp"+temp);
    }

    /**
     *      * a method which could access the database and read query for temperature
     *      @return Arraylist(result)
     * */
    public static ArrayList<HouseTempArray> readTempSQL(){
        ArrayList<HouseTempArray> list = new ArrayList<>();
        final SQLiteDatabase db = myhelper.getReadableDatabase();
        Cursor cursorRead;
        cursorRead = db.query(false,HouseDatabaseHelper.TABLE_TEMPERATURE,new String[]{HouseDatabaseHelper.KEY_ID,HouseDatabaseHelper.KEY_HOUR,HouseDatabaseHelper.KEY_MIN,HouseDatabaseHelper.KEY_TEMP},null,null,null,null,null,null);
        cursorRead.moveToFirst();
        HouseTempArray tempArray;
        while (! cursorRead.isAfterLast()){
            tempArray = new HouseTempArray();
            tempArray.setHour(cursorRead.getInt(cursorRead.getColumnIndex(HouseDatabaseHelper.KEY_HOUR)));
            tempArray.setMin(cursorRead.getInt(cursorRead.getColumnIndex(HouseDatabaseHelper.KEY_MIN)));
            tempArray.setTemp(cursorRead.getInt(cursorRead.getColumnIndex(HouseDatabaseHelper.KEY_TEMP)));
            tempArray.setId(cursorRead.getInt(cursorRead.getColumnIndex(HouseDatabaseHelper.KEY_ID)));
          //  Log.i("Cursor","Hour is " +tempArray.getHour());
            list.add(tempArray);
         //   Log.i("CursorArray","Hour is " +list.get(0).getHour());

            cursorRead.moveToNext();
        }
        cursorRead.close();

        return list;
    }
/**a method which can access database and delete the records
 * @param id
 * @param inputList*/
    public static ArrayList<HouseTempArray> deleteDataRecord(int id,ArrayList<HouseTempArray>inputList){

        ArrayList<HouseTempArray> list = new ArrayList<>();
        final SQLiteDatabase db =myhelper.getWritableDatabase();

        db.delete(HouseDatabaseHelper.TABLE_TEMPERATURE,"_id = "+inputList.get(id).getId(),null);

        Log.i("SQLDELETE","Query deleted");
        Cursor cursorTemp;
        cursorTemp = db.query(false,HouseDatabaseHelper.TABLE_TEMPERATURE,new String[]{HouseDatabaseHelper.KEY_ID,HouseDatabaseHelper.KEY_HOUR,HouseDatabaseHelper.KEY_MIN,HouseDatabaseHelper.KEY_TEMP},null,null,null,null,null,null);
        cursorTemp.moveToFirst();
        HouseTempArray tempArray;
        while (! cursorTemp.isAfterLast()){
            tempArray = new HouseTempArray();
            tempArray.setHour(cursorTemp.getInt(cursorTemp.getColumnIndex(myhelper.KEY_HOUR)));
            tempArray.setMin(cursorTemp.getInt(cursorTemp.getColumnIndex(myhelper.KEY_MIN)));
            tempArray.setTemp(cursorTemp.getInt(cursorTemp.getColumnIndex(myhelper.KEY_TEMP)));
            int DatabaseId = cursorTemp.getInt(cursorTemp.getColumnIndex(myhelper.KEY_ID));
            tempArray.setId(DatabaseId);
              Log.i("Cursor","Hour is " +tempArray.getHour());
            list.add(tempArray);
               Log.i("CursorArray","Hour is " +list.get(0).getHour());
               Log.i("CursorDatabaseID", "DatabaseID is "+DatabaseId);

            cursorTemp.moveToNext();
        }
        cursorTemp.close();
        return list;
    }


/**a method which can access the database and check the statement for garage*/
    public static void checkDeviceState(){

        //SQL PART

        final SQLiteDatabase db = myhelper.getReadableDatabase();
        Cursor cursorGarage;
        cursorGarage = db.query(false,HouseDatabaseHelper.TABLE_STATE,new String[]{HouseDatabaseHelper.KEY_DEVICE,HouseDatabaseHelper.KEY_STATE},null,null,null,null,null,null);
        cursorGarage.moveToFirst();
        tempStatementBundle = new Bundle();
        tempStatementBundle.putBoolean("isTablet",isTablet);
        while (! cursorGarage.isAfterLast()){
            tempDevice = cursorGarage.getString(cursorGarage.getColumnIndex(HouseDatabaseHelper.KEY_DEVICE));
            tempState = cursorGarage.getString(cursorGarage.getColumnIndex(HouseDatabaseHelper.KEY_STATE));
          Log.i("IN Cursor",""+tempDevice+tempState);
           if(tempDevice.equals("door")) {
               tempStatementBundle.putString("door", tempState);
               Log.i("inDatabase Door", tempState);
           } else if(tempDevice.equals("light")) {
               tempStatementBundle.putString("light", tempState);
               Log.i("inDatabase Light", tempState);

           }
            cursorGarage.moveToNext();
        }
        cursorGarage.close();

    }




}
