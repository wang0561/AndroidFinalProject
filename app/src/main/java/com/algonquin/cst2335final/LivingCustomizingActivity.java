package com.algonquin.cst2335final;
/**
 * Created by Min Luo, Version 1.0, April 10, 2017
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * This class support user to add or delete items by using dropbox, displaying choice, confirming actions
 */
public class LivingCustomizingActivity extends AppCompatActivity{
    private Spinner lvSpinner; // create a Spinner for a dropbox using
    private int itempos;  // use to store temporary position of items
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_customizing);

        // Spinner used to show lists of five items
        lvSpinner = (Spinner)findViewById(R.id.livingspinner);
        String[] items = new String[]{"Lamp1", "Lamp2", "Lamp3", "TV", "Blinds"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        lvSpinner.setAdapter(adapter);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lvSpinner.setAdapter(adapter);
        lvSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // when user selected items
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itempos = position; // record the selected position
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // user click living items change button, system pops out dialog for confirming user's choice
        Button itemChange = (Button) findViewById(R.id.livingchangebutton);
        itemChange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Create diaglog to confirm user's choice
                AlertDialog.Builder customBuild = new AlertDialog.Builder(ctx);
                LayoutInflater inflater = getLayoutInflater();
                final View v = inflater.inflate(R.layout.activity_living_custom_dialog, null);
                customBuild.setView(inflater.inflate(R.layout.activity_living_custom_dialog, null))
                        .setPositiveButton("Add", new DialogInterface.OnClickListener(){ // set Add button for change confirm
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("Add", "Add");
                                TextView inputMessage = (TextView) v.findViewById(R.id.livingcustomtext);
                                Intent dataBack = new Intent(); //send back data
                                dataBack.putExtra("ItemSelected", itempos);  // record position
                                dataBack.putExtra("AddOrDel", 1); // record action--add
                                setResult(0, dataBack);
                                finish();

                            }
                        })
                        .setNegativeButton("Del", new DialogInterface.OnClickListener(){ // set Delete button
                            @Override
                            public void onClick(DialogInterface dialog, int id){
                                Log.i("Del", "Del");
                                Intent dataBack = new Intent(); // send back data
                                dataBack.putExtra("ItemSelected", itempos); // record position
                                dataBack.putExtra("AddOrDel", -1); // record action--delete
                                setResult(0, dataBack);
                                finish();
                            }
                        })
                        .setCancelable(true)
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener(){ // set Cancel change button
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("Cancel", "Cancel");
                                TextView inputMessage = (TextView) v.findViewById(R.id.livingcustomtext);

                            }
                        });
                customBuild.setView(v);
                customBuild.create().show();
            }
        });

    }
    // activity lifecycle
    public void onStart(){

        super.onStart();
        Log.i("CustomizingActivity", "onStart");
    }
    // activity lifecycle
    public void onResume(){

        super.onResume();
        Log.i("CustomizingActivity", "onResume");
    }
    // activity lifecycle
    public void onPause(){

        super.onPause();
        Log.i("CustomizingActivity", "onPause");
    }
    // activity lifecycle
    public void onStop(){

        super.onStop();
        Log.i("CustomizingActivity", "onStop");
    }
    // activity lifecycle
    public void onDestroy(){

        super.onDestroy();
        Log.i("CustomizingActivity", "onDestroy");
    }

}
