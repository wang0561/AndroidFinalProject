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


public class HouseActivity extends AppCompatActivity {
    ProgressBar progressBar;
    int progress;
    protected boolean isTablet;
    protected Context context;
    protected HouseDatabaseHelper myhelper;

    protected Cursor cursor;
    protected String tempDevice;
    protected String tempState;
    protected Bundle tempStatementBundle;


    //http://www.cnblogs.com/devinzhang/archive/2012/01/20/2328334.html
    private List<String> getData(){
    List<String> data = new ArrayList<>();
        data.add("Garage");
        data.add("Home temperature");
        data.add("Outside weather");
        data.add("Help");
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);
        final SharedPreferences pref = getSharedPreferences("counter",MODE_PRIVATE);

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

        boolean test = pref.getBoolean("isFirstTime",true);

        SharedPreferences.Editor editor  = pref.edit();
        editor.putBoolean("isFirstTime",false);
        //check if this is the first time for database
        if(test){
            try {
            //    initialSQL();
            }catch (Exception e){
                e.printStackTrace();
            }
            }
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

    @Override
    public void onResume(){
        super.onResume();
        //check device statement again when resume
        checkDeviceState();
    }

    public void putValue(String strname, String value){
        myhelper = new HouseDatabaseHelper(this);
        final SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(myhelper.KEY_DEVICE,strname);
        newValues.put(myhelper.KEY_STATE, value);
       // db.insert(myhelper.TABLE_STATE, null, newValues);

        try {
            db.update(myhelper.TABLE_STATE, newValues, myhelper.KEY_DEVICE + "='" + strname+"'", null);
            Log.i("database update","1"+strname+"2"+value);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void putTempValue(int hour, int min, int temp){
        myhelper = new HouseDatabaseHelper(this);
        final SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(myhelper.KEY_HOUR,hour);
        newValues.put(myhelper.KEY_MIN, min);
        newValues.put(myhelper.KEY_TEMP,temp);
        // db.insert(myhelper.TABLE_STATE, null, newValues);
        db.insert(myhelper.TABLE_TEMPERATURE,null,newValues);
        Log.i("inDatabase","Hour"+hour+"min"+min+"temp"+temp);
    }

    public ArrayList<HouseTempArray> readTempSQL(){
        ArrayList<HouseTempArray> list = new ArrayList<>();
        myhelper = new HouseDatabaseHelper(this);
        final SQLiteDatabase db = myhelper.getReadableDatabase();
        cursor = db.query(false,myhelper.TABLE_TEMPERATURE,new String[]{myhelper.KEY_ID,myhelper.KEY_HOUR,myhelper.KEY_MIN,myhelper.KEY_TEMP},null,null,null,null,null,null);
        cursor.moveToFirst();
        HouseTempArray tempArray;
        while (! cursor.isAfterLast()){
            tempArray = new HouseTempArray();
            tempArray.setHour(cursor.getInt(cursor.getColumnIndex(myhelper.KEY_HOUR)));
            tempArray.setMin(cursor.getInt(cursor.getColumnIndex(myhelper.KEY_MIN)));
            tempArray.setTemp(cursor.getInt(cursor.getColumnIndex(myhelper.KEY_TEMP)));
            tempArray.setId(cursor.getInt(cursor.getColumnIndex(myhelper.KEY_ID)));
          //  Log.i("Cursor","Hour is " +tempArray.getHour());
            list.add(tempArray);
         //   Log.i("CursorArray","Hour is " +list.get(0).getHour());

            cursor.moveToNext();
        }

        return list;
    }

    public ArrayList<HouseTempArray> deleteDataRecord(int id,ArrayList<HouseTempArray>inputList){

        ArrayList<HouseTempArray> list = new ArrayList<>();
        myhelper = new HouseDatabaseHelper(this);
        final SQLiteDatabase db =myhelper.getWritableDatabase();

        db.delete(myhelper.TABLE_TEMPERATURE,"_id = "+inputList.get(id).getId(),null);

        Log.i("SQLDELETE","Query deleted");
        cursor = db.query(false,myhelper.TABLE_TEMPERATURE,new String[]{myhelper.KEY_ID,myhelper.KEY_HOUR,myhelper.KEY_MIN,myhelper.KEY_TEMP},null,null,null,null,null,null);
        cursor.moveToFirst();
        HouseTempArray tempArray;
        while (! cursor.isAfterLast()){
            tempArray = new HouseTempArray();
            tempArray.setHour(cursor.getInt(cursor.getColumnIndex(myhelper.KEY_HOUR)));
            tempArray.setMin(cursor.getInt(cursor.getColumnIndex(myhelper.KEY_MIN)));
            tempArray.setTemp(cursor.getInt(cursor.getColumnIndex(myhelper.KEY_TEMP)));
            int DatabaseId = cursor.getInt(cursor.getColumnIndex(myhelper.KEY_ID));
            tempArray.setId(DatabaseId);
              Log.i("Cursor","Hour is " +tempArray.getHour());
            list.add(tempArray);
               Log.i("CursorArray","Hour is " +list.get(0).getHour());
               Log.i("CursorDatabaseID", "DatabaseID is "+DatabaseId);

            cursor.moveToNext();
        }
        return list;
    }

    public void checkDeviceState(){

        //SQL PART
        myhelper = new HouseDatabaseHelper(this);
        final SQLiteDatabase db = myhelper.getReadableDatabase();
        cursor = db.query(false,myhelper.TABLE_STATE,new String[]{myhelper.KEY_DEVICE,myhelper.KEY_STATE},null,null,null,null,null,null);
        cursor.moveToFirst();
        tempStatementBundle = new Bundle();
        tempStatementBundle.putBoolean("isTablet",isTablet);
        while (! cursor.isAfterLast()){
            tempDevice = cursor.getString(cursor.getColumnIndex(myhelper.KEY_DEVICE));
            tempState = cursor.getString(cursor.getColumnIndex(myhelper.KEY_STATE));
          Log.i("IN Cursor",""+tempDevice+tempState);
           if(tempDevice.equals("door")) {
               tempStatementBundle.putString("door", tempState);
               Log.i("inDatabase Door", tempState);
           } else if(tempDevice.equals("light")) {
               tempStatementBundle.putString("light", tempState);
               Log.i("inDatabase Light", tempState);

           }
               cursor.moveToNext();
        }

    }




}
