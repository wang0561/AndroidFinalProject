package com.algonquin.cst2335final;

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

public class BlindsActivity extends Fragment {
    private int blindsHeight;
    private int blindsCounter;
    private int isTablet;
    private LivingRoomActivity livingroomwindow;

    public  BlindsActivity () {}

    public BlindsActivity(LivingRoomActivity cw){
        livingroomwindow = cw;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bun = getArguments();

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
                /*
                final SharedPreferences prefs = getActivity().getSharedPreferences("livingroomFile", Context.MODE_PRIVATE);
                //Get an editor object for writing to the file:
                SharedPreferences.Editor writer = prefs.edit();
                writer.putInt("BlindsHeight", blindsHeight);
                writer.putInt("BlindsCounter",++ blindsCounter);
                //Write the file:
                writer.commit();
                */
                if(isTablet == 0) {
                    Intent dataBack = new Intent();
                    dataBack.putExtra("BlindsHeight", blindsHeight);
                    getActivity().setResult(0, dataBack);
                    getActivity().finish();
                }else{
                    livingroomwindow.syncblinds(blindsHeight);
                    livingroomwindow.removeFragmentBlinds(BlindsActivity.this);
                }

            }
        });

        final SeekBar blindsDim = (SeekBar) gui.findViewById(R.id.blindsseekbar);
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
                Snackbar snackbar = Snackbar
                        .make(v, "Lamp is tuned to " + blindsHeight, Snackbar.LENGTH_LONG)
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

    public void onStart(){
        super.onStart();
        Log.i("BlindsActivity", "onStart");
    }

    public void onResume(){

        super.onResume();
        Log.i("BlindsActivity", "onResume");
    }

    public void onPause(){

        super.onPause();
        Log.i("BlindsActivity", "onPause");
    }

    public void onStop()
    {
        super.onStop();
        Log.i("BlindsActivity", "onStop");
    }

    public void onDestroy(){

        super.onDestroy();
        Log.i("BlindsActivity", "ondestroy");
    }

}

