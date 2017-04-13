package com.algonquin.cst2335final;
/**
 * Created By Min Luo, April.12, 2017
 */
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import java.io.File;
import java.util.ArrayList;

/**
 * This class support storing and displaying items status. User can add or delete items as they like.
 */
public class LivingRoomActivity extends AppCompatActivity {

    private Context ctx;
    private ListView livingroomlist;
    private ArrayList<String> livingitems = new ArrayList<>();  // store default items astatus
    private ArrayList<String> listitems = new ArrayList<>();  // store information displayed in ListView
    private ArrayList<Integer> dynamicitems=new ArrayList<>();  // store dynamic change of items, a mirror to displayed listview
    private int itemscounter;
    protected Boolean isTablet;
    private int myLamp2Progress, myLamp3Progress, myLamp3Color, myTVChannel,myBlindsHeight;
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

        // user click refresh button could update the content of list items' status
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

        // user click help button to get introduction information of this applicatioin
        Button btintro = (Button)findViewById(R.id.livinghelpbutton);
        btintro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder livingIntro = new AlertDialog.Builder(ctx);
                LayoutInflater inflater = getLayoutInflater();
                final View view = inflater.inflate(R.layout.activity_living_help, null);
                livingIntro.setView(inflater.inflate(R.layout.activity_living_tv_dialog, null))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener(){ // click ok to return
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("OK", "OK");
                            }
                        });
                livingIntro.setView(view);
                livingIntro.create().show();
            }
        });

        ctx = this;
        bundle  = new Bundle();
        //Open a file for storing shared preferences:
        final SharedPreferences prefs = getSharedPreferences("livingroomFile", Context.MODE_PRIVATE);
        //Read the number of times run in the file:
        int ifirstrun = prefs.getInt("LivingRoomFirstRun", 0);
        if (ifirstrun <= 1){ //if the database is run on first time, set default value for database

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

            // add the items order in ArrayList
            dynamicitems.add(0,0);
            dynamicitems.add(1,1);
            dynamicitems.add(2,2);
            dynamicitems.add(3,3);
            dynamicitems.add(4,4);

            // save ArrayList size as item number
            itemscounter = dynamicitems.size();

            // when initialization, the counter and each item position is recorded into the same database
            ContentValues newValuesItemCounter = new ContentValues();
            newValuesItemCounter.put(livingDataHelper.LIVINGITEM_KEY,"itemscounter");
            newValuesItemCounter.put(livingDataHelper.LIVINGITEM_VALUE,itemscounter);
            db.insert(livingDataHelper.TABLE_NAME, null, newValuesItemCounter);

            ContentValues newValuesItem0 = new ContentValues();
            newValuesItem0.put(livingDataHelper.LIVINGITEM_KEY,"items0");
            newValuesItem0.put(livingDataHelper.LIVINGITEM_VALUE,dynamicitems.get(0));
            db.insert(livingDataHelper.TABLE_NAME, null, newValuesItem0);

            ContentValues newValuesItem1 = new ContentValues();
            newValuesItem1.put(livingDataHelper.LIVINGITEM_KEY,"items1");
            newValuesItem1.put(livingDataHelper.LIVINGITEM_VALUE,dynamicitems.get(1));
            db.insert(livingDataHelper.TABLE_NAME, null, newValuesItem1);

            ContentValues newValuesItem2 = new ContentValues();
            newValuesItem2.put(livingDataHelper.LIVINGITEM_KEY,"items2");
            newValuesItem2.put(livingDataHelper.LIVINGITEM_VALUE,dynamicitems.get(2));
            db.insert(livingDataHelper.TABLE_NAME, null, newValuesItem2);

            ContentValues newValuesItem3 = new ContentValues();
            newValuesItem3.put(livingDataHelper.LIVINGITEM_KEY,"items3");
            newValuesItem3.put(livingDataHelper.LIVINGITEM_VALUE,dynamicitems.get(3));
            db.insert(livingDataHelper.TABLE_NAME, null, newValuesItem3);

            ContentValues newValuesItem4 = new ContentValues();
            newValuesItem4.put(livingDataHelper.LIVINGITEM_KEY,"items4");
            newValuesItem4.put(livingDataHelper.LIVINGITEM_VALUE,dynamicitems.get(4));
            db.insert(livingDataHelper.TABLE_NAME, null, newValuesItem4);
     }

        //Open a file for storing shared preferences:
        //Get an editor object for writing to the file:
        SharedPreferences.Editor writer = prefs.edit();
        writer.putInt("LivingRoomFirstRun", ++ifirstrun);// use "LivingRoomFirstRun" file to store run times
        //Write the file:
        writer.commit();
        // use getValue() method to retrieve counter value
        itemscounter = Integer.parseInt(getValue("itemscounter"));
        //add item numbers into ArrayList
        for(int i=0;i<itemscounter;i++){
            dynamicitems.add(i,Integer.parseInt(getValue("items"+i)));
        }

        // By selecting Tablet or Phone display, the system choose ways to store default status
        isTablet = (findViewById(R.id.livingroomfragmentHolder)!=null); // boolean variable to tell if it's a tablet
        livingroomlist = (ListView)findViewById(R.id.livinglists);
        livingroomlist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //covert pos to itempos
                int itempos = dynamicitems.get(position);

                if(!isTablet) {//if user choose the phone
                    switch (itempos) {
                        case 0: //lamp1
                            Intent lamp1Intent = new Intent(LivingRoomActivity.this, LivingRoomMessageDetails.class);
                            lamp1Intent.putExtra("Lamp1Status",strLamp1Status);
                            lamp1Intent.putExtra("ItemID",0);
                            lamp1Intent.putExtra("IsTablet",0);
                            startActivityForResult(lamp1Intent, 5); //callback functions
                            break;
                        case 1: //lamp2
                            Intent lamp2intent = new Intent(LivingRoomActivity.this, LivingRoomMessageDetails.class);
                            lamp2intent.putExtra("Lamp2Progress", myLamp2Progress);
                            lamp2intent.putExtra("ItemID",1);
                            lamp2intent.putExtra("IsTablet",0);
                            startActivityForResult(lamp2intent, 10);
                            break;
                        case 2: //lamp3
                            Intent lamp3intent= new Intent(LivingRoomActivity.this, LivingRoomMessageDetails.class);
                            lamp3intent.putExtra("Lamp3Progress", myLamp3Progress);
                            lamp3intent.putExtra("Lamp3Color", myLamp3Color);
                            lamp3intent.putExtra("ItemID",2);
                            lamp3intent.putExtra("IsTablet",0);
                            startActivityForResult(lamp3intent, 15);
                            break;
                        case 3: // tv
                            Intent tvActivity = new Intent(LivingRoomActivity.this, LivingRoomMessageDetails.class);
                            tvActivity.putExtra("TVChannel", myTVChannel);
                            tvActivity.putExtra("ItemID",3);
                            tvActivity.putExtra("IsTablet",0);
                            startActivityForResult(tvActivity, 20);
                            break;
                        case 4: // blinds
                            Intent blindsActivity = new Intent(LivingRoomActivity.this, LivingRoomMessageDetails.class);
                            blindsActivity.putExtra("BlindsHeight", myBlindsHeight);
                            blindsActivity.putExtra("ItemID",4);
                            blindsActivity.putExtra("IsTablet",0);
                            startActivityForResult(blindsActivity, 25);
                            break;
                    }
                }else{// if application is run by the tablet
                     switch (itempos) {
                        case 0: //lamp1
                            Lamp1Activity lamp1intent = new Lamp1Activity(LivingRoomActivity.this);
                            Bundle bundle = new Bundle();
                            bundle.putString("Lamp1Status",strLamp1Status);
                            bundle.putInt("ItemID",0);
                            bundle.putInt("IsTablet",1);
                            lamp1intent.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.livingroomfragmentHolder, lamp1intent).commit();
                            break;
                        case 1://lamp2
                            Lamp2Activity lamp2intent = new Lamp2Activity(LivingRoomActivity.this);
                            bundle = new Bundle();
                            bundle.putInt("Lamp2Progress", myLamp2Progress);
                            bundle.putInt("ItemID",1);
                            bundle.putInt("IsTablet",1);
                            lamp2intent.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.livingroomfragmentHolder, lamp2intent).commit();
                            break;
                        case 2://lamp3
                            Lamp3Activity lamp3intent = new Lamp3Activity(LivingRoomActivity.this);
                            bundle = new Bundle();
                            bundle.putInt("Lamp3Progress", myLamp3Progress);
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
                            bundle.putInt("ItemID",3);
                            bundle.putInt("IsTablet",1);
                            tvintent.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.livingroomfragmentHolder, tvintent).commit();
                            break;
                        case 4: // blinds
                            BlindsActivity blindsintent = new BlindsActivity(LivingRoomActivity.this);
                            bundle = new Bundle();
                            bundle.putInt("BlindsHeight", myBlindsHeight);
                            bundle.putInt("ItemID",4);
                            bundle.putInt("IsTablet",1);
                            blindsintent.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.livingroomfragmentHolder, blindsintent).commit();
                            break;

                    }
                }
            }
        });
        // use self-defined getValue() method to read data from database
        strLamp1Status = getValue("Lamp1Status");
        myLamp2Progress = Integer.parseInt(getValue("Lamp2Progress"));
        myLamp3Progress = Integer.parseInt(getValue("Lamp3Progress"));
        myLamp3Color = Integer.parseInt(getValue("Lamp3Color"));
        myTVChannel = Integer.parseInt(getValue("TVChannel"));
        myBlindsHeight = Integer.parseInt(getValue("BlindsHeight"));

        // set values for different items and display
        livingitems.add(0,"Lamp1 is " + strLamp1Status);
        livingitems.add(1,"Lamp2 is " + myLamp2Progress + "luminance");
        livingitems.add(2, "Lamp3 is " + myLamp3Progress + " luminance and color is " + myLamp3Color);
        livingitems.add(3, "TV is tuned to channel " + myTVChannel);
        livingitems.add(4, "Blinds is tuned to  " + myBlindsHeight + "  cm high");

        // use self-defined method to display changed items layout
        displayList();
        Log.i("LivingRoomActivity", "OnCreate");

        // pop out customer dialog to support user changing items
        Button btdialog = (Button) findViewById(R.id.livingroomcustombt);
        btdialog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent customIntent = new Intent(LivingRoomActivity.this, LivingCustomizingActivity.class);
                startActivityForResult(customIntent, 100); //go to view fragment details
            }
        });

    }

    /**
     * change items
     */
    public void displayList(){
        listitems.clear();
        for(int i = 0; i<itemscounter; i++)
            listitems.add(i, "");
        for(int i = 0; i<itemscounter; i ++){
            listitems.set(i,livingitems.get(dynamicitems.get(i)));
        }
        livingroomlist.setAdapter(new ArrayAdapter<>(this, R.layout.living_row_layout, listitems ));
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
        Log.i("LivingRoomActivity", "ondestroy");
    }
    //This function gets called when another activity has finished and this activity is resuming:
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // callback function to return values between different activities according to requestcode
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
            }else if(requestCode == 100 && resultCode == 0){
                int AddOrDel = data.getIntExtra("AddOrDel",0);
                int ItemSelected = data.getIntExtra("ItemSelected",0);
                changeItems(ItemSelected,AddOrDel);

                // update database table
                putValue("itemscounter",""+itemscounter);
                for(int i=0;i<itemscounter;i++){
                    putValue("items"+i,""+dynamicitems.get(i));
                }

            }
            // refresh different items status
            putValue("Lamp1Status",strLamp1Status);
            putValue("Lamp2Progress",""+myLamp2Progress);
            putValue("Lamp3Progress",""+myLamp3Progress);
            putValue("Lamp3Color",""+myLamp3Color);
            putValue("TVChannel",""+myTVChannel);
            putValue("BlindsHeight",""+myBlindsHeight);

            // set message format to display status
            livingitems.set(0, "Lamp1 is " + strLamp1Status);
            livingitems.set(1, "Lamp2 is " + myLamp2Progress + " luminance" );
            livingitems.set(2, "Lamp3 is " + myLamp3Progress + " luminance and color is " + myLamp3Color);
            livingitems.set(3, "TV is tuned to channel " + myTVChannel);
            livingitems.set(4, "Blinds is tuned to  " + myBlindsHeight + "  cm high");

            displayList();
        }
    }

    /**
     * When using fragment, refresh status for lamp1 and display in the same activity
     * @param lamp1status, Type String
     */
    public void synclamp1(String lamp1status) {
        putValue("Lamp1Status",strLamp1Status);

        strLamp1Status = lamp1status;
        livingitems.set(0, "Lamp1 is " + strLamp1Status);

        displayList();
    }

    /**
     * When using fragment, refresh status for lamp2 and display in the same activity
     * @param lamp2progress, Type int
     */
    public void synclamp2(int lamp2progress) {
        myLamp2Progress = lamp2progress;
        putValue("Lamp2Progress",""+myLamp2Progress);

        livingitems.set(1, "Lamp2 is " + myLamp2Progress + " luminance");

        displayList();
    }

    /**
     * When using fragment, refresh status for lamp3 and display in the same activity
     * @param lamp3progress,Type int
     * @param color, Type int
     */
    public void synclamp3(int lamp3progress, int color) {
        myLamp3Progress = lamp3progress;
        myLamp3Color = color;
        putValue("Lamp3Progress",""+myLamp3Progress);
        putValue("Lamp3Color",""+myLamp3Color);

        livingitems.set(2, "Lamp3 is " + myLamp3Progress + "  luminance and color is " + myLamp3Color);

        displayList();
    }

    /**
     * When using fragment, refresh status for tv and display in the same activity
     * @param tvChannel, Type int
     */
    public void synctv(int tvChannel) {
        myTVChannel = tvChannel;
        putValue("TVChannel",""+myTVChannel);

        livingitems.set(3, "TV is tuned to channel " + myTVChannel);

        displayList();
    }

    /**
     * When using fragment, refresh status for blinds and display in the same activity
     * @param blindsHeight, Type int
     */
    public void syncblinds(int blindsHeight) {
        myBlindsHeight = blindsHeight;
        putValue("BlindsHeight",""+myBlindsHeight);

        livingitems.set(4, "Blinds is tuned to  " + myBlindsHeight + "  cm high");

        displayList();
    }

    /**
     * delete lamp1 fragment
     * @param lamp1, Type Lamp1Activity
     */
    public void removeFragmentLamp1(Lamp1Activity  lamp1){
        getSupportFragmentManager().beginTransaction().remove(lamp1).commit();
    }

    /**
     * delete lamp2 fragment
     * @param lamp2, Type Lamp2Activity
     */
    public void removeFragmentLamp2(Lamp2Activity  lamp2){
        getSupportFragmentManager().beginTransaction().remove(lamp2).commit();
    }

    /**
     * delete lamp3 fragment
     * @param lamp3, TYpe Lamp3Activity
     */
    public void removeFragmentLamp3(Lamp3Activity  lamp3){
        getSupportFragmentManager().beginTransaction().remove(lamp3).commit();
    }

    /**
     * delete tv fragment
     * @param tv, Type TVActivity
     */
    public void removeFragmentTV(TVActivity tv){
        getSupportFragmentManager().beginTransaction().remove(tv).commit();
    }

    /**
     * delete blinds fragment
     * @param blinds, Type BlindsActivity
     */
    public void removeFragmentBlinds(BlindsActivity blinds){
        getSupportFragmentManager().beginTransaction().remove(blinds).commit();
    }

    /**
     * self-defined method to read value from database
     */
    public String getValue(String strname){
        livingDataHelper = new LivingRoomDatabaseHelper(this);
        db = livingDataHelper.getReadableDatabase();

        //String query = String
        //      .format("SELECT * FROM %s WHERE %s=%s", livingDataHelper.TABLE_NAME, livingDataHelper.LIVINGITEM_KEY,strname);
        //results = db.rawQuery(query, null);
        // query existed data in table
        results = db.query(false, livingDataHelper.TABLE_NAME,
                new String[]{ livingDataHelper.LVINGITEM_ID, livingDataHelper.LIVINGITEM_KEY, livingDataHelper.LIVINGITEM_VALUE},
                livingDataHelper.LVINGITEM_ID + " not null",
                null, null, null, null, null);

        // use cursor to read through database, if key value existed, then return value column
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

    /**
     * put value into database
     * @param strname, Type String
     * @param value, String
     */
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

    /**
     * Accordiing to add or delete actions, re-adjust the counter and total number of items
     * @param itemid, Type int
     * @param addordel, Type int
     */
    public void changeItems(int itemid,int addordel) {
        // if item doesn't exist, user could not delete from dropbox show lists.
        int dynaitemid = -1;
        for (int i = 0; i < itemscounter; i++){
            if (dynamicitems.get(i) == itemid) {
                dynaitemid = i;
                break;
            }
        }
        // if user add one item and the item doesn't exist in lists, the new item is added into the listview
        if (addordel == 1 && dynaitemid < 0) {
            //if (itemscounter <= 4)
                dynamicitems.add(itemscounter++, itemid);
        } else if (addordel == -1 && dynaitemid >= 0) {// if user delete one item and the item is in the lists, the item could be deleted
            if (itemscounter >= 0)
                itemscounter--;
            for (int i = dynaitemid; i < itemscounter; i++)
                dynamicitems.set(i, dynamicitems.get(i + 1)); // the latter item take the position of previous item
        }

    }

    /**
     * get assess to database
     */
    public class LivingStatus extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... args){
            String in = "";
            try{ // read into database
                livingDataHelper = new LivingRoomDatabaseHelper(ctx); // read into database
                db = livingDataHelper.getReadableDatabase();

                results = db.query(false, livingDataHelper.TABLE_NAME,
                        new String[]{ livingDataHelper.LVINGITEM_ID, livingDataHelper.LIVINGITEM_KEY, livingDataHelper.LIVINGITEM_VALUE},
                        livingDataHelper.LVINGITEM_ID + " not null",
                        null, null, null, null, null);
                int count = results.getCount();
                // set process speed when reading through database
                results.moveToFirst();
                while( ! results.isAfterLast() ){
                    String str2 = results.getString(results.getColumnIndex(livingDataHelper.LIVINGITEM_KEY));
                    if(str2.compareTo("Lamp1Status")==0){
                        publishProgress(25); //stop when reading on lamp1
                        Thread.sleep(1000);
                    }
                    if(str2.compareTo("Lamp3Color")==0){
                        publishProgress(50); // stop when reading on lamp3
                        Thread.sleep(1000);
                    }
                    if(str2.compareTo("BlindsHeight")==0){
                        publishProgress(75);  //stop when reading on blinds
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
        // to see if the file is existing
        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        // show progressbar and make it visible
        public void onProgressUpdate(Integer... values) {

            ProgressBar progressBar = (ProgressBar) findViewById(R.id.livingProgressBar);
            progressBar.setProgress(values[0]);
            progressBar.setVisibility(View.VISIBLE);
        }
        // set progressbar invisible
        public void onPostExecute(String result) {

            ProgressBar progressBar = (ProgressBar) findViewById(R.id.livingProgressBar);
            progressBar.setVisibility(View.INVISIBLE);
        }

    }
}

