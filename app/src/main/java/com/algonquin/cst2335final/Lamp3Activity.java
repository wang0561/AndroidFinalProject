package com.algonquin.cst2335final;
/**
 * Created by Min Luo, Version 1.0, Date April 12, 2017
 */

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

/**
 * This activity support user to change lamp3 color and turn on/off
 * [1] StackOverflow, [Online]http://stackoverflow.com/questions/6980906/android-color-picker, [Accessed March 24, 2017]
 */
public class Lamp3Activity extends Fragment { // set activity to fragment
    private Context ctx;
    private int lamp3counter;
    private int lamp3progress;
    private int lamp3color;
    private int isTablet;
    ColorPicker colorDialog;
    View gui;

    private LivingRoomActivity livingroomwindow;

    // default constructor
    public  Lamp3Activity () {}

    // constructor with parameter
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

                if(isTablet == 0) { // when using phone, send back tatus to the main activity
                    Intent dataBack = new Intent();
                    dataBack.putExtra("Lamp3Progress", lamp3progress);
                    dataBack.putExtra("Lamp3Color", lamp3color);
                    getActivity().setResult(0, dataBack);
                    getActivity().finish();
                }else{ // when using Tablet, store and send back status to the same fragment
                    livingroomwindow.synclamp3(lamp3progress, lamp3color);// use self-defined method to update status
                    livingroomwindow.removeFragmentLamp3(Lamp3Activity.this);
                }
            }
        });

        // user click button to confirm status change
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

        // create Seekbar to change lamp status
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
            // create Snackbar to show update message
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
    //activity lifecycle
    public void onStart(){

        super.onStart();
        Log.i("Lamp3Activity", "onStart");
    }
    //activity lifecycle
    public void onResume(){

        super.onResume();
        Log.i("Lamp3Activity", "onResume");
    }
    //activity lifecycle
    public void onPause(){

        super.onPause();
        Log.i("Lamp3Activity", "onPause");
    }
    //activity lifecycle
    public void onStop(){

        super.onStop();
        Log.i("Lamp3Activity", "onStop");
    }
    //activity lifecycle
    public void onDestroy(){

        super.onDestroy();
        Log.i("Lamp3Activity", "onDestroy");
    }


}

