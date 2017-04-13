package com.algonquin.cst2335final;

import android.app.ListActivity;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * This class support user to add or delete items by using dropbox, displaying choice, confirming actions
 */
public class LivingCustomizingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner lvSpinner;
    private int itempos;
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_customizing);

        lvSpinner = (Spinner)findViewById(R.id.livingspinner);
        String[] items = new String[]{"Lamp1", "Lamp2", "Lamp3", "TV", "Blinds"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        lvSpinner.setAdapter(adapter);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lvSpinner.setAdapter(adapter);
        lvSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itempos = position;
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // user click change confirm button, system pops out dialog for confirming user's choice
        Button itemChange = (Button) findViewById(R.id.livingchangebutton);
        itemChange.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder customBuild = new AlertDialog.Builder(ctx);
                LayoutInflater inflater = getLayoutInflater();
                final View v = inflater.inflate(R.layout.activity_living_custom_dialog, null);
                customBuild.setView(inflater.inflate(R.layout.activity_living_custom_dialog, null))
                        .setPositiveButton("Add", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("Add", "Add");
                                TextView inputMessage = (TextView) v.findViewById(R.id.livingcustomtext);
                                Intent dataBack = new Intent();
                                dataBack.putExtra("ItemSelected", itempos);
                                dataBack.putExtra("AddOrDel", 1);
                                setResult(0, dataBack);
                                finish();

                            }
                        })
                        .setNegativeButton("Del", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int id){
                                Log.i("Del", "Del");
                                Intent dataBack = new Intent();
                                dataBack.putExtra("ItemSelected", itempos);
                                dataBack.putExtra("AddOrDel", -1);
                                setResult(0, dataBack);
                                finish();
                            }
                        })
                        .setCancelable(true)
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener(){
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

    public void onStart(){

        super.onStart();
        Log.i("CustomizingActivity", "onStart");
    }

    public void onResume(){

        super.onResume();
        Log.i("CustomizingActivity", "onResume");
    }

    public void onPause(){

        super.onPause();
        Log.i("CustomizingActivity", "onPause");
    }

    public void onStop(){

        super.onStop();
        Log.i("CustomizingActivity", "onStop");
    }

    public void onDestroy(){

        super.onDestroy();
        Log.i("CustomizingActivity", "onDestroy");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
