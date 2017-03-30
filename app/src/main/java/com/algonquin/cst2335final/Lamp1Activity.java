package com.algonquin.cst2335final;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
// Anupam Chugh, [online]Feb 25, 2016, www.journaldev.com/10324/android-snackbar-example-tutorial, [Accessed ] Mar 26, 2017


public class Lamp1Activity extends AppCompatActivity {
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp1);

        ctx = this;
        Button lampButton = (Button) findViewById(R.id.lamp1return);
        lampButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });


        Switch lampSwitch = (Switch) findViewById(R.id.lamp1switch);
        lampSwitch.setSelected(true);
        lampSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CharSequence text;
                int duration;

                if(isChecked){
                    text = "Lamp is On";
                    duration = Toast.LENGTH_SHORT;
                }else{
                    text = "Lamp is Off";
                    duration = Toast.LENGTH_SHORT;
                }

                Toast toast = Toast.makeText(Lamp1Activity.this, text, duration);
                toast.show();
            }
        });



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
