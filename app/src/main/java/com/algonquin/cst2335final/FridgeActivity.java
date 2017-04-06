package com.algonquin.cst2335final;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.ProtectionDomain;

public class FridgeActivity extends AppCompatActivity {
    protected TextView textOfFridge;
    protected TextView textOfFreezer;
    protected EditText editFridge;
    protected EditText editFreezer;
    protected Button setFridgeB;
    protected Button setFreezerB;
    protected Button saveButton;
    protected ProgressBar progressBar;
    protected int progressStatus;
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);
        textOfFridge=(TextView)findViewById(R.id.fridgetextview);
        textOfFreezer=(TextView)findViewById(R.id.freezertextView);
        editFridge=(EditText)findViewById(R.id.editTextfridge);
        editFreezer=(EditText)findViewById(R.id.editTextfreezer);
        setFridgeB=(Button)findViewById(R.id.buttonsetfridge);
        setFreezerB=(Button)findViewById(R.id.buttonsetfreezer);
        saveButton=(Button)findViewById(R.id.buttonSave);
        progressBar=(ProgressBar)findViewById(R.id.fridgeprogressbar);

        SharedPreferences sharedPreferences=getSharedPreferences("FreezerTemp", Context.MODE_PRIVATE);
        textOfFreezer.setText("Freezer Temp is "+sharedPreferences.getString("FreezerTemp","")+" °C");
        SharedPreferences sharedPreferences1=getSharedPreferences("FridgeTemp",Context.MODE_PRIVATE);
        textOfFridge.setText("Fridge Temp is "+sharedPreferences1.getString("FridgeTemp","")+" °C");

        setFreezerB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                String temp=editFreezer.getText().toString();
                textOfFreezer.setText("Freezer Temp is "+temp+" °C");
                SharedPreferences preferences=getSharedPreferences("FreezerTemp",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("FreezerTemp",editFreezer.getText().toString());
                editor.commit();
            }

        });

        setFridgeB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                String temp=editFridge.getText().toString();
                textOfFridge.setText("Fridge Temp is "+temp+" °C");
                SharedPreferences preferences=getSharedPreferences("FridgeTemp",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("FridgeTemp",editFridge.getText().toString());
                editor.commit();
            }

        });
        saveButton.setOnClickListener(view -> {
           progressBar.setVisibility(View.VISIBLE);
            progressStatus=0;
            new Thread(() -> {
                while(progressStatus<100){
                    progressStatus+=10;
                    handler.post(new Runnable(){
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                    try{
                        Thread.sleep(100);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                }
            }).start();

            Snackbar.make(view,"TEMPERATURE SAVED SUCCESSFUL",Snackbar.LENGTH_LONG).setAction("Action",null).show();

        });
    }
}
