package com.algonquin.cst2335final;
/**
 * Created By Min Luo, Version 1.0, Date April 12, 2017
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
 * This class support user to control length of the blinds
 */
public class BlindsActivity extends Fragment { // set this activity to fragment
    private int blindsHeight;
    private int blindsCounter;
    private int isTablet;
    private LivingRoomActivity livingroomwindow;

    // default constructor
    public  BlindsActivity () {}

    // constructor with parameter
    public BlindsActivity(LivingRoomActivity cw){
        livingroomwindow = cw;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bun = getArguments();

        // set default blinds status when activity created
        blindsHeight = bun.getInt("BlindsHeight", 0);
        blindsCounter = bun.getInt("BlindsCounter", 0);
        isTablet = bun.getInt("IsTablet", 0);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View gui = inflater.inflate(R.layout.activity_blinds, null);

        Button blindsButton = (Button) gui.findViewById(R.id.buttonblinds);
        blindsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                 if(isTablet == 0) { // when using phone, send back status to main activity
                    Intent dataBack = new Intent();
                    dataBack.putExtra("BlindsHeight", blindsHeight);
                    getActivity().setResult(0, dataBack);
                    getActivity().finish();
                }else{  // when using Tablet, send back status to the same fragment
                    livingroomwindow.syncblinds(blindsHeight); // use self-defined method to send back status
                    livingroomwindow.removeFragmentBlinds(BlindsActivity.this);
                }

            }
        });

        final SeekBar blindsDim = (SeekBar) gui.findViewById(R.id.blindsseekbar); // create seekbar to show blinds height
        blindsDim.setProgress(blindsHeight);
        blindsDim.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                update(blindsDim.getRootView());
                blindsHeight = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


            public void update(View v){
                Snackbar snackbar = Snackbar   // use Snackbar to inform user status change
                        .make(v, "Lamp is tuned to " + blindsHeight, Snackbar.LENGTH_LONG)  // set format of message
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
    //lifecycle of activity
    public void onStart(){
        super.onStart();
        Log.i("BlindsActivity", "onStart");
    }
    //lifecycle of activity
    public void onResume(){

        super.onResume();
        Log.i("BlindsActivity", "onResume");
    }
    //lifecycle of activity
    public void onPause(){

        super.onPause();
        Log.i("BlindsActivity", "onPause");
    }

    //lifecycle of activity
    public void onStop()
    {
        super.onStop();
        Log.i("BlindsActivity", "onStop");
    }

    //lifecycle of activity
    public void onDestroy(){

        super.onDestroy();
        Log.i("BlindsActivity", "ondestroy");
    }

}

