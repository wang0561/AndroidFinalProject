package com.algonquin.cst2335final;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class Lamp2Activity extends AppCompatActivity {
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp2);

        ctx = this;
        Button lampButton = (Button) findViewById(R.id.lamp2logout);
        lampButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });

        final SeekBar lampDim = (SeekBar) findViewById(R.id.lamp2seekBar);
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
        Log.i("Lamp2Activity", "onStart");
    }

    public void onResume(){

        super.onResume();
        Log.i("Lamp2Activity", "onResume");
    }

    public void onPause(){

        super.onPause();
        Log.i("Lamp2Activity", "onPause");
    }

    public void onStop(){

        super.onStop();
        Log.i("Lamp2Activity", "onStop");
    }

    public void onDestroy(){

        super.onDestroy();
        Log.i("Lamp2Activity", "onDestroy");
    }

}
