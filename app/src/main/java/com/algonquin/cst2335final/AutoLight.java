/**
 * @version 1.0
 * @(#)ActivityDate.java 1.0 2017/04/19
 * this is a part of project for CST2335_010 Android final Project;
 * */

package com.algonquin.cst2335final;

import android.content.Context;
import android.content.SharedPreferences;
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

/**
 * This class is autmobile light managment class.
 * @version 1.0
 * @author BO
 */

public class AutoLight extends AppCompatActivity {

    protected Switch autoLightSwitch;
    protected ImageButton autoImageButton;
    protected Button autoExitButton;
    protected SeekBar autoLightDim;
    boolean autoSwitchChecked;
    int autoLightSeekbarProgress;
    SharedPreferences autoLightsharedPref;
    SharedPreferences.Editor autoLightsharedPrefEditor;

    /**
     * method onCreate creates activity
     *  @param savedInstanceState is bundle
     * */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_light);


        autoLightSwitch = (Switch) findViewById(R.id.autoswitchlight);

        autoImageButton = (ImageButton) findViewById(R.id.autoimagebutton);
        autoLightsharedPref = this.getSharedPreferences("autoLightShare", Context.MODE_PRIVATE);
        autoLightsharedPrefEditor = autoLightsharedPref.edit();

        boolean preAutoSwitchChecked = autoLightsharedPref.getBoolean("autoSwitchChecked", false);
        int preAutoLightSeekbarProgress = autoLightsharedPref.getInt("autoLightSeekbarProgress", 0);



        setAutoLight (preAutoSwitchChecked) ;
        autoLightSwitch.setOnCheckedChangeListener(

               /* new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                     */
                (v, b) -> {
                   // CharSequence text;
                   // int duration;
                    autoSwitchChecked = autoLightSwitch.isChecked();
                    autoLightsharedPrefEditor.putBoolean("autoSwitchChecked", autoSwitchChecked);
                    autoLightsharedPrefEditor.commit();
                    setAutoLight(autoSwitchChecked);

                });

        autoExitButton = (Button) findViewById(R.id.autolightexit);
        autoExitButton.setOnClickListener(v ->
        {
            setResult(0);
            finish();
        });

        autoLightDim = (SeekBar) findViewById(R.id.autolightseekBar);
        autoLightDim.setProgress(preAutoLightSeekbarProgress);
        autoLightDim.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()

                {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        update(autoLightDim.getRootView());
                        autoLightSeekbarProgress = progress;
                        autoLightsharedPrefEditor.putInt("autoLightSeekbarProgress", autoLightSeekbarProgress);
                        autoLightsharedPrefEditor.commit();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }


                    public void update(View v) {
                        Snackbar snackbar = Snackbar
                                .make(v, "Auto Dimmable light  is tuned", Snackbar.LENGTH_LONG)
                                .setAction("UNDO", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Snackbar snackbar1 = Snackbar.make(view,
                                                "Volume "+autoLightSeekbarProgress+" is stored", Snackbar.LENGTH_SHORT);
                                        snackbar1.show();
                                    }
                                });
                        snackbar.show();
                    }
                });

    }
    /**
     * method setAutoLight set auto light  switch
     *  @param autoLightSwitchStatus is status of the switch
     * */
        private void setAutoLight(boolean autoLightSwitchStatus){
            CharSequence text;
            int duration;

            autoLightSwitch.setChecked(autoLightSwitchStatus);
            if (autoLightSwitchStatus) {

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
        }

}


