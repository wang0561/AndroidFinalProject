package com.algonquin.cst2335final;
/**
 * Created by Min Luo, Version 1.0, Date April 12, 2017
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

/**
 * This class support user to turn on and off the lamp1.
 */
public class Lamp1Activity extends Fragment {
    // set variables to store keys and their values
    private int lamp1counter;
    private CharSequence text;
    private String lamp1status;
    private int isTablet;
    private LivingRoomActivity livingroomwindow;

    //default constructor
    public  Lamp1Activity () {}

    //constructor with parameter
    public Lamp1Activity(LivingRoomActivity cw){
        livingroomwindow = cw;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bun = getArguments();
        lamp1counter = bun.getInt("Lamp1Counter");
        lamp1status = bun.getString("Lamp1Status");
        isTablet = bun.getInt("IsTablet");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View gui = inflater.inflate(R.layout.activity_lamp1, null);

        // when return button is clicked, mobile system record the status of lamp1 into database
        Button lampButton = (Button) gui.findViewById(R.id.lamp1return);
        lampButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isTablet == 0) { // when using phone, send back status to the main activity
                    Intent dataBack = new Intent();
                    dataBack.putExtra("Lamp1Status", lamp1status);
                    getActivity().setResult(0, dataBack);
                    getActivity().finish();
                }else{ // when using tablet, store and send back status to the same fragment
                    livingroomwindow.synclamp1(lamp1status); // using self-defined method to update status
                    livingroomwindow.removeFragmentLamp1(Lamp1Activity.this);
                }
            }
        });

        // user click switch button. It can change the status of lamp1 to on or off.
        Switch lampSwitch = (Switch) gui.findViewById(R.id.lamp1switch);
        if(lamp1status.compareTo("On") == 0){
            lampSwitch.setChecked(true);
        }else{
            lampSwitch.setChecked(false);
        }
        lampSwitch.setSelected(true);  // set action when user choose Switch status
        lampSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int duration;

                if(isChecked){
                    text = "On";
                    duration = Toast.LENGTH_SHORT;   // toast shows on
                }else{
                    text = "Off";
                    duration = Toast.LENGTH_SHORT;    // toast shows off
                }
                lamp1status = text.toString();

                Toast toast = Toast.makeText(getActivity(),"Lamp is "+ text, duration); // format the display message
                toast.show();
            }
        });

        return gui;

    }
    // activity lifecycle
    public void onStart(){

        super.onStart();
        Log.i("Lamp1Activity", "onStart");
    }
    // activity lifecycle
    public void onResume(){

        super.onResume();
        Log.i("Lamp1Activity", "onResume");
    }

    public void onPause(){

        super.onPause();
        Log.i("Lamp1Activity", "onPause");
    }
    // activity lifecycle
    public void onStop(){

        super.onStop();
        Log.i("Lamp1Activity", "onStop");
    }
    // activity lifecycle
    public void onDestroy(){

        super.onDestroy();
        Log.i("Lamp1Activity", "onDestroy");
    }

}