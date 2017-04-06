package com.algonquin.cst2335final;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class House_Fragment_garage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house__fragment_garage);
        Switch sw = (Switch)findViewById(R.id.HouseGarageDoorSwitch);
        Switch sw2 = (Switch)findViewById(R.id.HouseGarageLightSwitch);
        Button state = (Button)findViewById(R.id.HouseViewStateButton);


        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton a, boolean isChecked){
                CharSequence text;
                int duration;
                if(sw.isChecked()){
                    text = "Switch is ON";
                    duration = Toast.LENGTH_SHORT;
                }else{
                    text = "Switch is OFF";
                    duration = Toast.LENGTH_SHORT;
                }
                Toast toast = Toast.makeText(House_Fragment_garage.this,text,duration);
                toast.show();
            }
        });

        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton a, boolean isChecked){
                Snackbar.make(findViewById(R.id.house_fragment_garage),"Light state Changed",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }
        });

        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog
                AlertDialog.Builder builder1 = new AlertDialog.Builder(House_Fragment_garage.this);
                builder1.setTitle("HI");
                builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //donothing
                    }
                });
                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //donothing
                    }
                });
                AlertDialog dialog1 = builder1.create();
                dialog1.show();
            }
});
    }
}
