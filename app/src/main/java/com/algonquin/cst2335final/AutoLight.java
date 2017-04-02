package com.algonquin.cst2335final;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

public class AutoLight extends AppCompatActivity {

    protected Switch autoLightSwitch;
    protected ImageButton autoImageButton;
    protected Button autoExitButton ;
    protected SeekBar autoLightDim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_light);


        autoLightSwitch=(Switch)findViewById(R.id.autoswitchlight);
        autoImageButton=(ImageButton) findViewById(R.id.autoimagebutton);

        autoLightSwitch.setOnCheckedChangeListener(

               /* new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                     */
                (v, b)->{
                        CharSequence text;
                        int duration;
                        if (autoLightSwitch.isChecked()) {
                            text = "Auto Light High";
                            duration = Toast.LENGTH_SHORT;
                            autoImageButton.setImageResource(R.drawable.autolighthigh);
                        } else {
                            text = "Auto light Normal";
                            duration = Toast.LENGTH_LONG;
                            autoImageButton.setImageResource(R.drawable.autolightnormal);

                        }
                        Toast toast = Toast.makeText(AutoLight.this, text, duration);
                        toast.show();
                    });




        autoExitButton = (Button) findViewById(R.id.autolightexit);
        autoExitButton.setOnClickListener(v->{

                setResult(0);
                finish();
        });

        autoLightDim = (SeekBar) findViewById(R.id.autolightseekBar);
        autoLightDim.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                {
                update(autoLightDim.getRootView());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


            public void update(View v){
                Snackbar snackbar = Snackbar
                        .make(v, "Auto Dimmable light  is tuned", Snackbar.LENGTH_LONG)
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
}
