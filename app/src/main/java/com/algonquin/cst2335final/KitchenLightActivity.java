package com.algonquin.cst2335final;

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
        aSwitch.setOnCheckedChangeListener(

                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        CharSequence text;
                        int duration;
                        if (aSwitch.isChecked()) {
                            text = "Light is On";
                            duration = Toast.LENGTH_SHORT;
                            image.setImageResource(R.drawable.kitchenon);
                        } else {
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
