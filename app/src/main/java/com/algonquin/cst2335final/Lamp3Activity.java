package com.algonquin.cst2335final;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

// Anupam Chugh, [online]Feb 25, 2016, www.journaldev.com/10324/android-snackbar-example-tutorial, [Accessed ] Mar 26, 2017


public class Lamp3Activity extends Fragment {
    private Context ctx;
    private int lamp3counter;
    private int lamp3progress;
    private int lamp3color;
    private int isTablet;
    ColorPicker colorDialog;
    View gui;

    private LivingRoomActivity livingroomwindow;

    public  Lamp3Activity () {}

    public Lamp3Activity(LivingRoomActivity cw){
        livingroomwindow = cw;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bun = getArguments();

        lamp3counter = bun.getInt("Lamp3Counter",0);
        lamp3progress = bun.getInt("Lamp3Progress",0);
        lamp3color = bun.getInt("Lamp3Color", 0);
        isTablet = bun.getInt("IsTablet", 0);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        gui = inflater.inflate(R.layout.activity_lamp3, null);

        colorDialog = new ColorPicker(getActivity(), 255,255,255);


        TextView textView = (TextView)gui.findViewById(R.id.colorlamptext);
        textView.setTextColor(lamp3color);
        Button lampButton = (Button) gui.findViewById(R.id.livinglampbutton);
        lampButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*
                final SharedPreferences prefs = getActivity().getSharedPreferences("livingroomFile", Context.MODE_PRIVATE);
                //Get an editor object for writing to the file:
                SharedPreferences.Editor writer = prefs.edit();
                writer.putInt("Lamp3Progress", lamp3progress);
                writer.putInt("Lamp3Counter",++ lamp3counter);
                writer.putInt("Lamp3Color", lamp3color);
                //Write the file:
                writer.commit();
                */
                if(isTablet == 0) {
                    Intent dataBack = new Intent();
                    dataBack.putExtra("Lamp3Progress", lamp3progress);
                    dataBack.putExtra("Lamp3Color", lamp3color);
                    getActivity().setResult(0, dataBack);
                    getActivity().finish();
                }else{
                    livingroomwindow.synclamp3(lamp3progress, lamp3color);
                    livingroomwindow.removeFragmentLamp3(Lamp3Activity.this);
                }
            }
        });

        Button colorlampbutton = (Button) gui.findViewById(R.id.colorlampbutton);
        colorlampbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {// choose color dialog
                colorDialog.show();
                colorDialog.setCallback(new ColorPickerCallback() {
                    @Override
                    public void onColorChosen(@ColorInt int color) {
                        // Do whatever you want
                        TextView textView = (TextView)gui.findViewById(R.id.colorlamptext);
                        textView.setTextColor(color);
                        lamp3color = color;
                        Log.d("Alpha", Integer.toString(Color.alpha(color)));
                        Log.d("Red", Integer.toString(Color.red(color)));
                        Log.d("Green", Integer.toString(Color.green(color)));
                        Log.d("Blue", Integer.toString(Color.blue(color)));
                        colorDialog.dismiss();
                    }
                });
            }
        });


        final SeekBar lampDim = (SeekBar) gui.findViewById(R.id.lampDimmable);
        lampDim.setProgress(lamp3progress);
        lampDim.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                update(lampDim.getRootView());
                lamp3progress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


            public void update(View v){
                Snackbar snackbar = Snackbar
                        .make(v, "Lamp is tuned", Snackbar.LENGTH_LONG)
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
        Log.i("Lamp3Activity", "onStart");
    }

    public void onResume(){

        super.onResume();
        Log.i("Lamp3Activity", "onResume");
    }

    public void onPause(){

        super.onPause();
        Log.i("Lamp3Activity", "onPause");
    }

    public void onStop(){

        super.onStop();
        Log.i("Lamp3Activity", "onStop");
    }

    public void onDestroy(){

        super.onDestroy();
        Log.i("Lamp3Activity", "onDestroy");
    }


}

