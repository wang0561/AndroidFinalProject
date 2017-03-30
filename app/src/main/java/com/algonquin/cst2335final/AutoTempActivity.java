package com.algonquin.cst2335final;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AutoTempActivity extends AppCompatActivity {


    protected static final String ACTIVITY_NAME = "AutoTempActivity";
    Context context = this;
    SharedPreferences sharedPref;
    SharedPreferences.Editor sharedPrefEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_temp);

        Button btnTempSet = (Button) findViewById(R.id.autotempbutton1);
        Button btnTempDisplay = (Button) findViewById(R.id.autotempbutton2);
        Button btnTempExit = (Button) findViewById(R.id.autotempbutton3);

        EditText editTextTempLow = (EditText) findViewById(R.id.autotempeditText1);
        EditText editTextTempHigh = (EditText) findViewById(R.id.autotempeditText2);
        sharedPref = this.getSharedPreferences("temperature", Context.MODE_PRIVATE);

        btnTempSet.setOnClickListener(v->{
        String autoTempLow = editTextTempLow.getText().toString();
        String autoTempHigh = editTextTempHigh.getText().toString();
            sharedPrefEditor  =  sharedPref.edit();
            sharedPrefEditor.putString("tempLow",autoTempLow );
            sharedPrefEditor.putString("tempHigh",autoTempHigh);
            sharedPrefEditor.commit();
            Intent autoTempIntent = new Intent(this, AutoTempDisplay.class);
            autoTempIntent.putExtra("tempLow",autoTempLow);
            autoTempIntent.putExtra("tempHigh",autoTempHigh);

            startActivity(autoTempIntent);

        });

        btnTempDisplay.setOnClickListener(
            v-> {

                String autoTempLow = sharedPref.getString("tempLow", "");
                String autoTempHigh = sharedPref.getString("tempHigh", "");

                Intent autoTempIntent = new Intent(this, AutoTempDisplay.class);
                autoTempIntent.putExtra("tempLow",autoTempLow);
                autoTempIntent.putExtra("tempHigh",autoTempHigh);

                startActivity(autoTempIntent);

            });

        btnTempExit.setOnClickListener(v->{
            finish();

        });

    }
}
