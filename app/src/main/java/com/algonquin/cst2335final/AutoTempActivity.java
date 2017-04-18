/**
 * @version 1.0
 * @(#)ActivityDate.java 1.0 2017/04/19
 * this is a part of project for CST2335_010 Android final Project;
 * */

package com.algonquin.cst2335final;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

/**
 * This class is autmobile temperature management class
 * @version 1.0
 * @author BO
 */
public class AutoTempActivity extends AppCompatActivity {


    protected static final String ACTIVITY_NAME = "AutoTempActivity";
    Context context = this;
    SharedPreferences sharedPref;
    SharedPreferences.Editor sharedPrefEditor;

    /**
     * method onCreate creates activity
     *  @param savedInstanceState is bundle
     * */
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

        btnTempDisplay.setOnClickListener(  v->{

                String autoTempLow = sharedPref.getString("tempLow", "");
                String autoTempHigh = sharedPref.getString("tempHigh", "");

                Intent autoTempIntent = new Intent(this, AutoTempDisplay.class);
                autoTempIntent.putExtra("tempLow",autoTempLow);
                autoTempIntent.putExtra("tempHigh",autoTempHigh);

                startActivity(autoTempIntent);

            });

        btnTempExit.setOnClickListener(v->{

            AlertDialog.Builder builder = new AlertDialog.Builder(AutoTempActivity.this);
            builder.setMessage("Do you want to exit?").setTitle(R.string.micro_dialog_title).setPositiveButton(R.string.micro_ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //USER CLICK OK
                            Intent resultIntent = new Intent();
                            //resultIntent.putExtra("Response", "My information to share");
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();

                        }
                    }).setNegativeButton(R.string.micro_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //user click cancel

                }
            }).show();

        });

    }
}
