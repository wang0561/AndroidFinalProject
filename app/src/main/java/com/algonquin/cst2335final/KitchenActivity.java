package com.algonquin.cst2335final;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class KitchenActivity extends AppCompatActivity {

    protected ListView listView;

    protected ArrayList<String> nameList=new ArrayList<>();
    protected Button returnButton;
    protected boolean isTablet;

    protected Cursor results;
    protected KitchenDatabaseHelper dbHelper;
    protected SQLiteDatabase db;
    protected Context ctx;
    //item key
    protected String fridgetemp="Fridgetemp";
    protected String freezertemp="FreezerTemp";
    protected String lightStatus="lightStatus";
    protected String progressLight="progressOfLight";
    //item value
    protected double fridgeTemp=5;
    protected double freezerTemp=-10;
    protected String lightOfKitchen="off";
    protected int progressofLight=0;
    protected SharedPreferences prefs;
    protected Set<String> set=new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);
        prefs=getSharedPreferences("listview",Context.MODE_PRIVATE);
        set=prefs.getStringSet("key",new HashSet<String>());
        Log.i("set value",set.toString());
        if(set.isEmpty()){
            set.add("Microwave");
            set.add("Samsung Fridge");
            set.add("Samsung Freezer");
            set.add("Main Ceiling Light");
        }
        nameList= new ArrayList<>(set);

        ctx = this;

        Button buttonAdd=(Button)findViewById(R.id.buttonofkitchenitemadd);
        //code for user to add a new device.
        buttonAdd.setOnClickListener((view)->{
            Log.i("kitchen","additem");
            String [] itemselect=new String[]{getString(R.string.defaultfreezer),
                    getString(R.string.defaultfridge),getString(R.string.defaultmicrowave),getString(R.string.defaultkitchenlight)};
           //ask user which item will be added.
            AlertDialog.Builder builder=new AlertDialog.Builder(ctx);
            LayoutInflater inflater = getLayoutInflater();
            final View v = inflater.inflate(R.layout.kitchen_spinner_dialog, null);
            Spinner spinner=(Spinner)v.findViewById(R.id.kitchenItemSpinner) ;
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemselect);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            //confirm to add the type of item
            Button confirm=(Button)v.findViewById(R.id.buttonOfkitchenItemSpinner);
            confirm.setOnClickListener((view1)->{
                AlertDialog.Builder builder1=new AlertDialog.Builder(ctx);
                LayoutInflater inflater1 =getLayoutInflater();
                final View v1=inflater1.inflate(R.layout.kitchen_itemname_enter_dialog,null);
                builder1.setView(v1).setPositiveButton("ok",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //exit to main activity
                    }
                }).create().show();
                EditText edtext=(EditText)v1.findViewById(R.id.editOfkichenitemadd);
                Button confirm1=(Button)v1.findViewById(R.id.kitchenitemnameconfirm);
                confirm1.setOnClickListener((view2)->{
                    nameList.add(edtext.getText().toString());
                    listView.setAdapter(new ArrayAdapter<>(this, R.layout.kitchen_row, nameList));});


            });

            builder.setView(v).setPositiveButton(R.string.micro_ok,new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //exit to main activity
                }
            }).create().show();
        });
       //code for user to delete a device.
       Button buttonDel=(Button)findViewById(R.id.kitchenitemdeletebutton);
        buttonDel.setOnClickListener((view)->{
            AlertDialog.Builder builder=new AlertDialog.Builder(ctx);
            LayoutInflater inflater = getLayoutInflater();
            final View v = inflater.inflate(R.layout.kitchen_spinner_dialog, null);
            builder.setView(v).setPositiveButton(R.string.micro_ok,new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    //exit to main activity
                }
            }).create().show();
            Spinner spinner=(Spinner)v.findViewById(R.id.kitchenItemSpinner) ;
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nameList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            TextView text=(TextView)v.findViewById(R.id.kitchenspinnertextview);
            text.setText(R.string.textofkitchenselectdel);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Button confirm=(Button)v.findViewById(R.id.buttonOfkitchenItemSpinner);
                    confirm.setOnClickListener((view1)->{
                        nameList.remove((int)id);
                        listView.setAdapter(new ArrayAdapter<>(v.getContext(),R.layout.kitchen_row, nameList));

                });

                  }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });

        });

        Button helpB= (Button)findViewById(R.id.kitchenhelp);

        isTablet = (findViewById(R.id.kitchenfragmentHolder) != null);
        listView = (ListView) findViewById(R.id.theList);
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.kitchen_row, nameList));
        returnButton = (Button) findViewById(R.id.kitchenReturn);
        returnButton.setOnClickListener(e -> {
            startActivityForResult(new Intent(KitchenActivity.this, StartActivity.class), 5);
            finish();
        });


       final SharedPreferences prefs = getSharedPreferences("KitchenFile", Context.MODE_PRIVATE);

        int i = prefs.getInt("KitchenFirstRun", 0);
       if (i <=1){
            //open a new db
          createDBfirstTime();


        }

         //record the times to enter database
       SharedPreferences.Editor writer = prefs.edit();
        writer.putInt("KitchenFirstRun", ++i);

        writer.commit();
        Log.i("times to create db",""+i);

            // get all data from database
        freezerTemp=Double.parseDouble(getDBValue(freezertemp));
        fridgeTemp=Double.parseDouble(getDBValue(fridgetemp));
        lightOfKitchen=getDBValue(lightStatus);
        progressofLight=Integer.parseInt(getDBValue(progressLight));


        Button bSet = (Button) findViewById(R.id.kitchenSetting);
        helpB.setOnClickListener((v)->{
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setMessage(R.string.kichenhelp_dialog_message).setTitle(R.string.kichenhelp_title).setPositiveButton(R.string.micro_ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //USER CLICK OK


                        }
                    }).show();

        });
        bSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar kitchenProg = (ProgressBar) findViewById(R.id.KitchenRefProgress);
                kitchenProg.setVisibility(View.VISIBLE);
                KitchenStatus kitchenStatus = new KitchenStatus();
                kitchenStatus.execute();
                Snackbar.make(v, R.string.kitchensnacktext, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String listID=nameList.get((int)id);
                Log.i("conteent isssss",listID);
                if (!isTablet) {//phone

                    if(listID.toLowerCase().contains("micro")||listID.toLowerCase().contains("微波炉")){
                        Intent intentMic=new Intent(KitchenActivity.this, MicrowaveActivity.class);
                           intentMic.putExtra("ID",listID);
                           startActivityForResult(intentMic,5);
                    }
                    else if(listID.toLowerCase().contains("fridge")||listID.toLowerCase().contains("freezer")||listID.toLowerCase().contains("冰")){
                        Intent intentFridge=new Intent(KitchenActivity.this, FridgeActivity.class);
                            intentFridge.putExtra("ID",listID);
                            intentFridge.putExtra(fridgetemp,fridgeTemp);
                            Log.i("fridge temp",""+fridgeTemp);
                            intentFridge.putExtra(freezertemp,freezerTemp);
                            startActivityForResult(intentFridge,10);
                    }else if (listID.toLowerCase().contains("light")||listID.toLowerCase().contains("灯")){
                        Intent intentLight=new Intent(KitchenActivity.this, KitchenLightActivity.class);
                            intentLight.putExtra("ID",listID);
                            intentLight.putExtra(lightStatus,lightOfKitchen);
                            intentLight.putExtra(progressLight,progressofLight);
                            startActivityForResult(intentLight, 15);
                    }
                } else {//tablet
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", listID);
                    bundle.putDouble(fridgetemp,fridgeTemp);
                    bundle.putDouble(freezertemp,freezerTemp);
                    bundle.putInt(progressLight,progressofLight);
                    bundle.putString(lightStatus,lightOfKitchen);
                    bundle.putBoolean("isTablet",isTablet);
                    KitchenFragment frag = new KitchenFragment(KitchenActivity.this);
                    frag.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.kitchenfragmentHolder,frag).commit();
                }
            }
        });



    }
    public void putDBValue(String key, String value){
        dbHelper = new KitchenDatabaseHelper(ctx);
        db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.KITCHENITEM_KEY,key);
        contentValues.put(dbHelper.KITCHENITEM_VALUE, value);


        try {
            db.update(dbHelper.TABLE_NAME, contentValues, dbHelper.KITCHENITEM_KEY + "='" + key +"'", null);
        }catch(Exception e) {

        }
    }
    public String getDBValue(String itemkey){
        dbHelper = new KitchenDatabaseHelper(ctx);
        db = dbHelper.getReadableDatabase();

        results = db.query(false, dbHelper.TABLE_NAME,
                new String[]{ dbHelper.KITCHENITEM_ID, dbHelper.KITCHENITEM_KEY, dbHelper.KITCHENITEM_VALUE},
                dbHelper.KITCHENITEM_ID + " not null",
                null, null, null, null, null);

        results.moveToFirst();
        while( ! results.isAfterLast() ){

            String key = results.getString(results.getColumnIndex(dbHelper.KITCHENITEM_KEY));
            String value = results.getString(results.getColumnIndex(dbHelper.KITCHENITEM_VALUE));
            if(itemkey.compareTo(key)==0){
                return value;
            }
            results.moveToNext();
        }
        return null;
    }

    public void onActivityResult(int requestCode,int resultCode, Intent data){
        if(data!=null){
            if(requestCode == 10  && resultCode == 0){
                //get the changed status from fridge activity
               fridgeTemp=data.getDoubleExtra(fridgetemp,0);
                Log.i("fridge temp",""+fridgeTemp);
                freezerTemp=data.getDoubleExtra(freezertemp,0);
            }else if(requestCode == 15 &&resultCode == 0){
                //get the changed status from light activity
              lightOfKitchen=data.getStringExtra(lightStatus);
                progressofLight=data.getIntExtra(progressLight,0);
            }
            //put all values of data into the database
            putDBValue(freezertemp,""+freezerTemp);
            putDBValue(fridgetemp,""+fridgeTemp);
            putDBValue(lightStatus,""+lightOfKitchen);
            putDBValue(progressLight,""+progressofLight);
        }
    }

    @Override
    public void onStart() {
        super.onStart();


    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i("ondestroy calling","yes");
        writeSetintoShare();
        Log.i("current set value",set.toString());

    }
    @Override
    public void finish(){
        super.finish();
        Log.i("onfinish calling","yes");
        writeSetintoShare();
        Log.i("current set value",set.toString());
    }
    @Override
    public void onStop(){
    super.onStop();
        Log.i("on stop .......","yes");
    }
    public void writeSetintoShare(){
        prefs=getSharedPreferences("listview",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        set.clear();
        set=new HashSet<>(nameList);
        editor.putStringSet("key",set);
        editor.commit();
    }
    public void createDBfirstTime(){
        dbHelper = new KitchenDatabaseHelper(ctx);
        db = dbHelper.getReadableDatabase();
        db.execSQL(KitchenDatabaseHelper.DROP_TABLE_MESSAGE);
        db.execSQL(KitchenDatabaseHelper.CREATE_TABLE_MESSAGE);
        //set the default values for the iteme key and item value
        ContentValues newValues1 = new ContentValues();
        newValues1.put(KitchenDatabaseHelper.KITCHENITEM_KEY,fridgetemp);
        newValues1.put(KitchenDatabaseHelper.KITCHENITEM_VALUE, fridgeTemp);
        db.insert(KitchenDatabaseHelper.TABLE_NAME, null, newValues1);

        ContentValues newValues2 = new ContentValues();
        newValues2.put(KitchenDatabaseHelper.KITCHENITEM_KEY,freezertemp);
        newValues2.put(KitchenDatabaseHelper.KITCHENITEM_VALUE, freezerTemp);
        db.insert(KitchenDatabaseHelper.TABLE_NAME, null, newValues2);

        ContentValues newValues3 = new ContentValues();
        newValues3.put(KitchenDatabaseHelper.KITCHENITEM_KEY,lightStatus);
        newValues3.put(KitchenDatabaseHelper.KITCHENITEM_VALUE, lightOfKitchen);
        db.insert(KitchenDatabaseHelper.TABLE_NAME, null, newValues3);

        ContentValues newValues4 = new ContentValues();
        newValues4.put(KitchenDatabaseHelper.KITCHENITEM_KEY,progressLight);
        newValues4.put(KitchenDatabaseHelper.KITCHENITEM_VALUE, progressofLight);
        db.insert(KitchenDatabaseHelper.TABLE_NAME, null, newValues4);
    }
    public void removeFragment(KitchenFragment fragment){
        getFragmentManager().beginTransaction().remove(fragment).commit();
    }
    public void getValuefromfragment(String key,String value){
        putDBValue(key,value);
        freezerTemp=Double.parseDouble(getDBValue(freezertemp));
        fridgeTemp=Double.parseDouble(getDBValue(fridgetemp));
        lightOfKitchen=getDBValue(lightStatus);
        progressofLight=Integer.parseInt(getDBValue(progressLight));
    }
    public class KitchenStatus extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... args) {
            String in = "";
            try {
                dbHelper = new KitchenDatabaseHelper(ctx);
                db = dbHelper.getReadableDatabase();


                results = db.query(false, dbHelper.TABLE_NAME,
                        new String[]{dbHelper.KITCHENITEM_ID, dbHelper.KITCHENITEM_KEY, dbHelper.KITCHENITEM_VALUE},
                        dbHelper.KITCHENITEM_ID + " not null",
                        null, null, null, null, null);


                results.moveToFirst();
                while (!results.isAfterLast()) {

                    publishProgress(25);
                    Thread.sleep(1000);

                    results.moveToNext();
                }
                publishProgress(100);
                Thread.sleep(1000);
            } catch (Exception me) {
                Log.e("AsyncTask", "Malformed URL:" + me.getMessage());
            }

            return in;
        }

        public void onProgressUpdate(Integer... values) {

            ProgressBar progressBar = (ProgressBar) findViewById(R.id.KitchenRefProgress);
            progressBar.setProgress(25);
            progressBar.setVisibility(View.VISIBLE);
        }

        public void onPostExecute(String result) {


            ProgressBar progressBar = (ProgressBar) findViewById(R.id.KitchenRefProgress);
            progressBar.setVisibility(View.INVISIBLE);
            Toast toast = Toast.makeText(KitchenActivity.this, "INFROMATION SAVED SUCCESSFUL", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
