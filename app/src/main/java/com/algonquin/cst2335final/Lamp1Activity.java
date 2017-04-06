package com.algonquin.cst2335final;

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
// Anupam Chugh, [online]Feb 25, 2016, www.journaldev.com/10324/android-snackbar-example-tutorial, [Accessed ] Mar 26, 2017


public class Lamp1Activity extends Fragment {
    private int lamp1counter;
    private CharSequence text;
    private String lamp1status;
    private int isTablet;
    private LivingRoomActivity livingroomwindow;

    public  Lamp1Activity () {}

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

        //ctx = this;
        Button lampButton = (Button) gui.findViewById(R.id.lamp1return);
        lampButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isTablet == 0) {
                    Intent dataBack = new Intent();
                    dataBack.putExtra("Lamp1Status", lamp1status);
                    getActivity().setResult(0, dataBack);
                    getActivity().finish();
                }else{
                    livingroomwindow.synclamp1(lamp1status);
                    livingroomwindow.removeFragmentLamp1(Lamp1Activity.this);
                }
            }
        });


        Switch lampSwitch = (Switch) gui.findViewById(R.id.lamp1switch);
        if(lamp1status.compareTo("On") == 0){
            lampSwitch.setChecked(true);
        }else{
            lampSwitch.setChecked(false);
        }
        lampSwitch.setSelected(true);
        lampSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int duration;

                if(isChecked){
                    text = "On";
                    duration = Toast.LENGTH_SHORT;
                }else{
                    text = "Off";
                    duration = Toast.LENGTH_SHORT;
                }
                lamp1status = text.toString();

                /*
                //Open a file for storing shared preferences:
                final SharedPreferences prefs =  getActivity().getSharedPreferences("livingroomFile", Context.MODE_PRIVATE);
                //Get an editor object for writing to the file:
                SharedPreferences.Editor writer = prefs.edit();
                writer.putString("Lamp1Status", lamp1status);
                writer.putInt("LampCounter",++ lamp1counter);

                //Write the file:
                writer.commit();*/


                Toast toast = Toast.makeText(getActivity(),"Lamp is "+ text, duration);
                toast.show();
            }
        });

        return gui;

    }

    public void onStart(){

        super.onStart();
        Log.i("Lamp1Activity", "onStart");
    }

    public void onResume(){

        super.onResume();
        Log.i("Lamp1Activity", "onResume");
    }

    public void onPause(){

        super.onPause();
        Log.i("Lamp1Activity", "onPause");
    }

    public void onStop(){

        super.onStop();
        Log.i("Lamp1Activity", "onStop");
    }

    public void onDestroy(){

        super.onDestroy();
        Log.i("Lamp1Activity", "onDestroy");
    }

}