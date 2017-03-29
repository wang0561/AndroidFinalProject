package com.algonquin.cst2335final;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class BlindsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blinds);

        Button blindsButton = (Button) findViewById(R.id.buttonblinds);
        blindsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });

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

