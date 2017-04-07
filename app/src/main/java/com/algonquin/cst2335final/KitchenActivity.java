package com.algonquin.cst2335final;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


public class KitchenActivity extends AppCompatActivity {

    protected ListView listView;
    protected String[] kitchenItems = new String[]{"MicroWave", "Samsung Fridge", "Main Ceiling Light"};
    protected Button returnButton;
    protected boolean isTablet;

    protected Cursor results;
    protected KitchenDatabaseHelper dbHelper;
    protected SQLiteDatabase db;
    protected Context ctx;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);
        isTablet = (findViewById(R.id.kitchenfragmentHolder) != null);
        listView = (ListView) findViewById(R.id.theList);
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.kitchen_row, kitchenItems));
        returnButton = (Button) findViewById(R.id.kitchenReturn);
        returnButton.setOnClickListener(e -> {
            startActivityForResult(new Intent(KitchenActivity.this, StartActivity.class), 5);
        });
        ctx = this;
        Button bSet = (Button) findViewById(R.id.kitchenSetting);
        bSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressBar kitchenProg = (ProgressBar) findViewById(R.id.KitchenRefProgress);
                kitchenProg.setVisibility(View.VISIBLE);
                KitchenStatus kitchenStatus = new KitchenStatus();
                kitchenStatus.execute();
                Snackbar.make(v, "INFORMATION SAVING", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (!isTablet) {
                    switch ((int) id) {
                        case 0: //Microwave
                            Intent intentMic=new Intent(KitchenActivity.this, MicrowaveActivity.class);
                            intentMic.putExtra("ID",id);
                            startActivity(intentMic);
                            break;
                        case 1: //fridge
                            //Start the Screen_Two activity, with 10 as the result code
                            Intent intentFridge=new Intent(KitchenActivity.this, FridgeActivity.class);
                            intentFridge.putExtra("ID",id);
                            startActivityForResult(intentFridge, 5);
                            break;
                        case 2: //light
                            Intent intentLight=new Intent(KitchenActivity.this, KitchenLightActivity.class);
                            intentLight.putExtra("ID",id);
                            startActivityForResult(intentLight, 5);
                            break;

                    }
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putLong("ID", id);
                    KitchenFragment frag = new KitchenFragment(KitchenActivity.this);
                    frag.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.kitchenfragmentHolder,frag).commit();
                }
            }
        });
        final SharedPreferences prefs = getSharedPreferences("livingroomFile", Context.MODE_PRIVATE);
        //Read the number of times run in the file:
        int ifirstrun = prefs.getInt("LivingRoomFirstRun", 0);
        if (ifirstrun == 0) {
            dbHelper = new KitchenDatabaseHelper(ctx);
            db = dbHelper.getReadableDatabase();
            db.execSQL(KitchenDatabaseHelper.DROP_TABLE_MESSAGE);
            db.execSQL(KitchenDatabaseHelper.CREATE_TABLE_MESSAGE);

            ContentValues newValues1 = new ContentValues();
            newValues1.put(dbHelper.KITCHENITEM_KEY, "Lamp1Status");
            newValues1.put(dbHelper.KITCHENITEM_VALUE, "On");
            db.insert(dbHelper.TABLE_NAME, null, newValues1);

            ContentValues newValues2 = new ContentValues();
            newValues2.put(dbHelper.KITCHENITEM_KEY, "Lamp2Progress");
            newValues2.put(dbHelper.KITCHENITEM_VALUE, "50");
            db.insert(dbHelper.TABLE_NAME, null, newValues2);

            ContentValues newValues3 = new ContentValues();
            newValues3.put(dbHelper.KITCHENITEM_KEY, "Lamp3Progress");
            newValues3.put(dbHelper.KITCHENITEM_VALUE, "50");
            db.insert(dbHelper.TABLE_NAME, null, newValues3);

            ContentValues newValues4 = new ContentValues();
            newValues4.put(dbHelper.KITCHENITEM_KEY, "Lamp3Color");
            newValues4.put(dbHelper.KITCHENITEM_VALUE, "0");
            db.insert(dbHelper.TABLE_NAME, null, newValues4);

            ContentValues newValues5 = new ContentValues();
            newValues5.put(dbHelper.KITCHENITEM_KEY, "TVChannel");
            newValues5.put(dbHelper.KITCHENITEM_VALUE, "10");
            db.insert(dbHelper.TABLE_NAME, null, newValues5);

            ContentValues newValues6 = new ContentValues();
            newValues6.put(dbHelper.KITCHENITEM_KEY, "BlindsHeight");
            newValues6.put(dbHelper.KITCHENITEM_VALUE, "10");
            db.insert(dbHelper.TABLE_NAME, null, newValues6);
            //
        }

        //Open a file for storing shared preferences:
        //final SharedPreferences prefs =  getSharedPreferences("livingroomFile", Context.MODE_PRIVATE);
        //Get an editor object for writing to the file:
        SharedPreferences.Editor writer = prefs.edit();
        writer.putInt("LivingRoomFirstRun", ++ifirstrun);

        //Write the file:
        writer.commit();

    }



    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();


    }

    public class KitchenStatus extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... args) {
            String in = "";
            try {
                dbHelper = new KitchenDatabaseHelper(ctx);
                db = dbHelper.getReadableDatabase();

                //String query = String
                //      .format("SELECT * FROM %s WHERE %s=%s", livingDataHelper.TABLE_NAME, livingDataHelper.LIVINGITEM_KEY,strname);
                //results = db.rawQuery(query, null);

                results = db.query(false, dbHelper.TABLE_NAME,
                        new String[]{dbHelper.KITCHENITEM_ID, dbHelper.KITCHENITEM_KEY, dbHelper.KITCHENITEM_VALUE},
                        dbHelper.KITCHENITEM_ID + " not null",
                        null, null, null, null, null);

                int count = results.getCount();

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
