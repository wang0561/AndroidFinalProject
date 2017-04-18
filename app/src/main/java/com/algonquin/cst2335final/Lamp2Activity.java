package com.algonquin.cst2335final;

/**
 * Created by Min Luo, Version 1.0, Date April 12, 2017
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

/**
 * This class support user to drag the bar to set the lightness of lamp2
 */
public class Lamp2Activity extends Fragment {  // set activity to fragment
    private int lamp2counter;
    private int lamp2progress;
    private int isTablet;
    private LivingRoomActivity livingroomwindow;

    //default constructor
    public  Lamp2Activity () {}

    //constructor with parameter
    public Lamp2Activity(LivingRoomActivity cw){
        livingroomwindow = cw;
    }

    // when activity is run, store the initial status into database
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bun = getArguments();
        lamp2counter = bun.getInt("Lamp2Counter");
        lamp2progress = bun.getInt("Lamp2Progress");
        isTablet = bun.getInt("IsTablet", 0);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View gui = inflater.inflate(R.layout.activity_lamp2, null);

        // when user click button, the mobile system record status in database
        Button lampButton = (Button) gui.findViewById(R.id.lamp2logout);
        lampButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isTablet == 0) {  // if using phone, send back status to main activity
                    Intent dataBack = new Intent();
                    dataBack.putExtra("Lamp2Progress", lamp2progress);
                    getActivity().setResult(0, dataBack);
                    getActivity().finish();
                }else{   // if using Tablet, show the record status in the same fragment
                    livingroomwindow.synclamp2(lamp2progress); // self-defined method to update status
                    livingroomwindow.removeFragmentLamp2(Lamp2Activity.this);
                }
            }
        });

        // define a snackbar to display lamp2 status
        final SeekBar lampDim = (SeekBar) gui.findViewById(R.id.lamp2seekBar);
        lampDim.setProgress(lamp2progress);
        lampDim.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                update(lampDim.getRootView());
                lamp2progress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            // using snackbar to display status
            public void update(View v){
                Snackbar snackbar = Snackbar
                        .make(v, "Lamp is tuned to " + lamp2progress, Snackbar.LENGTH_LONG)  // format display message
                        .setAction("UNDO", new View.OnClickListener(){
                            @Override
                            public void onClick(View view){
                                Snackbar snackbar1 = Snackbar.make(view, "Message is stored", Snackbar.LENGTH_SHORT);
                                snackbar1.show();
                            }
                        });
                snackbar.show();
            }
        });
        return gui;
    }
    // activity Lifcycles
    public void onStart(){

        super.onStart();
        Log.i("Lamp2Activity", "onStart");
    }
    // activity Lifcycles
    public void onResume(){

        super.onResume();
        Log.i("Lamp2Activity", "onResume");
    }
    // activity Lifcycles
    public void onPause(){

        super.onPause();
        Log.i("Lamp2Activity", "onPause");
    }
    // activity Lifcycles
    public void onStop(){

        super.onStop();
        Log.i("Lamp2Activity", "onStop");
    }
    // activity Lifcycles
    public void onDestroy(){

        super.onDestroy();
        Log.i("Lamp2Activity", "onDestroy");
    }

}

