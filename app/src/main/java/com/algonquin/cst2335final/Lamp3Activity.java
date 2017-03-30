package com.algonquin.cst2335final;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
// Anupam Chugh, [online]Feb 25, 2016, www.journaldev.com/10324/android-snackbar-example-tutorial, [Accessed ] Mar 26, 2017


public class Lamp3Activity extends AppCompatActivity implements ColorPickerDialog.OnColorChangedListener{
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp3);

        ctx = this;
        Button lampButton = (Button) findViewById(R.id.livinglampbutton);
        lampButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });

        Button colorlampbutton = (Button) findViewById(R.id.colorlampbutton);
        colorlampbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {// choose color dialog
                ColorPickerDialog color = new ColorPickerDialog(ctx,( ColorPickerDialog.OnColorChangedListener)ctx, "picker", Color.BLACK,Color.WHITE);
                color.show();
            }
        });


        final SeekBar lampDim = (SeekBar) findViewById(R.id.lampDimmable);
        lampDim.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                update(lampDim.getRootView());
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

    @Override
    public void colorChanged(String key, int color) {
        // TODO Auto-generated method stub
        TextView textView = (TextView)findViewById(R.id.colorlamptext);
        textView.setTextColor(color);
    }
}
