package com.algonquin.cst2335final;
/**
 * Created by Min Luo, Version 1.0, April 12, 2017
 */


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class support use turn on/off and choose TV channel.
 */
public class TVActivity extends Fragment { // define activity as fragment
    private int tvChannel, tvCounter;
    private View gui;
    private  int isTablet;
    private LivingRoomActivity livingroomwindow;

    // default constructor
    public  TVActivity () {}

    // constructor with parameter
    public TVActivity(LivingRoomActivity cw){
        livingroomwindow = cw;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get parameter from activity
        Bundle bun = getArguments();
        tvChannel = bun.getInt("TVChannel", 0);
        tvCounter = bun.getInt("TVCounter", 0);
        isTablet = bun.getInt("IsTablet", 0);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        gui = inflater.inflate(R.layout.activity_tv, null);

        TextView input = (TextView)gui.findViewById(R.id.tvchannel);
        input.setText(String.valueOf(tvChannel));

        // when user click tvbutton, save status to database
        Button tvButton = (Button) gui.findViewById(R.id.tvbutton);
        tvButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TextView input = (TextView)gui.findViewById(R.id.tvchannel);
                tvChannel = Integer.parseInt(input.getText().toString());

                if(isTablet == 0) { // when using phone, return status change to main activity
                    Intent dataBack = new Intent();
                    dataBack.putExtra("TVChannel", tvChannel);
                    dataBack.putExtra("TVCounter", tvCounter);
                    getActivity().setResult(0, dataBack);
                    getActivity().finish();
                }else{ // when using Tablet, return status change
                    livingroomwindow.synctv(tvChannel); //self-defined method to update
                    livingroomwindow.removeFragmentTV(TVActivity.this);
                }
            }
        });

        // use Switch for TV control
        Switch lampSwitch = (Switch) gui.findViewById(R.id.switchTV);
        lampSwitch.setSelected(true);
        lampSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CharSequence text;
                int duration;

                if(isChecked){
                    text = "Home TV is On";
                    duration = Toast.LENGTH_SHORT;
                }else{
                    text = "Home TV is Off";
                    duration = Toast.LENGTH_SHORT;
                }
                // set Toast display
                Toast toast = Toast.makeText(getActivity(), text, duration);
                toast.show();
            }

        });

        // when user click center button, input channel value and save changes
        Button custombt = (Button)gui.findViewById(R.id.imageViewCenter);
        custombt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create dialog to display
                AlertDialog.Builder customConfirm = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View v = inflater.inflate(R.layout.activity_living_tv_dialog, null);
                customConfirm.setView(inflater.inflate(R.layout.activity_living_tv_dialog, null))
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener(){ // set confirm button
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("Confirm", "Confirm");
                                EditText inputMessage = (EditText) v.findViewById(R.id.livingtvchannel);
                                Intent dataBack = new Intent();
                                input.setText(inputMessage.getText());//get input value from text field
                             }
                        })
                        .setCancelable(true)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){ // set cancel button
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("Cancel", "Cancel");
                            }
                        });
                // display dialog
                customConfirm.setView(v);
                customConfirm.create().show();
            }
        });

        return gui;
    }
    // activity lifecycle
    public void onStart(){
        super.onStart();
        Log.i("TVActivity", "onStart");
    }
    // activity lifecycle
    public void onResume(){

        super.onResume();
        Log.i("TVActivity", "onResume");
    }
    // activity lifecycle
    public void onPause(){

        super.onPause();
        Log.i("TVActivity", "onPause");
    }
    // activity lifecycle
    public void onStop()
    {
        super.onStop();
        Log.i("TVActivity", "onStop");
    }
    // activity lifecycle
    public void onDestroy(){

        super.onDestroy();
        Log.i("TVActivity", "ondestroy");
    }
}