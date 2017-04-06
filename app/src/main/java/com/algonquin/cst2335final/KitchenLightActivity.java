package com.algonquin.cst2335final;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

public class KitchenLightActivity extends AppCompatActivity {
       protected Switch aSwitch;
    protected ImageButton image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_light);
        aSwitch=(Switch)findViewById(R.id.switchkitchenlight);
        image=(ImageButton) findViewById(R.id.imagekitchenlight);
        SharedPreferences prefs=getSharedPreferences("KitchenLight",Context.MODE_PRIVATE);
        if(prefs.getString("Light","").compareTo("on")==0){
            aSwitch.setChecked(true);
            image.setImageResource(R.drawable.kitchenon);
        }
        else{aSwitch.setChecked(false);
            image.setImageResource(R.drawable.kitchenoff);}
        aSwitch.setSelected(true);


        aSwitch.setOnCheckedChangeListener(

                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        CharSequence text;
                        int duration;
                        SharedPreferences preferences=getSharedPreferences("KitchenLight",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();
                        if (aSwitch.isChecked()) {
                            editor.putString("Light","on");
                            editor.commit();
                            text = "Light is On";
                            duration = Toast.LENGTH_SHORT;
                            image.setImageResource(R.drawable.kitchenon);
                        } else {
                            editor.putString("Light","off");
                            editor.commit();
                            text = "Light is OFF";
                            duration = Toast.LENGTH_LONG;
                           image.setImageResource(R.drawable.kitchenoff);

                        }
                        Toast toast = Toast.makeText(KitchenLightActivity.this, text, duration);
                        toast.show();
                    }
                }
        );
    }
}
