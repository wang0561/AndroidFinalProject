package com.algonquin.cst2335final;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class TVActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);

        Button tvButton = (Button) findViewById(R.id.tvbutton);
        tvButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setResult(0);
                finish();
            }
        });

        Switch lampSwitch = (Switch) findViewById(R.id.switchTV);
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

                Toast toast = Toast.makeText(TVActivity.this, text, duration);
                toast.show();
           }

    });

    }

    public void onStart(){
        super.onStart();
        Log.i("TVActivity", "onStart");
    }

    public void onResume(){

        super.onResume();
        Log.i("TVActivity", "onResume");
    }

    public void onPause(){

        super.onPause();
        Log.i("TVActivity", "onPause");
    }

    public void onStop()
    {
        super.onStop();
        Log.i("TVActivity", "onStop");
    }

    public void onDestroy(){

        super.onDestroy();
        Log.i("TVActivity", "ondestroy");
    }
}