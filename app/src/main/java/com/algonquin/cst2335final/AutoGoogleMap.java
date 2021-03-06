/**
 * @version 1.0
 * @(#)ActivityDate.java 1.0 2017/04/19
 * this is a part of project for CST2335_010 Android final Project;
 * */

package com.algonquin.cst2335final;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by beaul on 2017-03-30.
 */

public class AutoGoogleMap extends AppCompatActivity {

    private Button b_get;
    private TrackGPS gps;
    double longitude;
    double latitude;

    /**
     * method onCreate creates activity
     *  @param savedInstanceState is bundle
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_start);
        b_get = (Button)findViewById(R.id.get);



        b_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gps = new TrackGPS(AutoGoogleMap.this);


                if(gps.canGetLocation()){


                    longitude = gps.getLongitude();
                    latitude = gps .getLatitude();

                    Toast.makeText(getApplicationContext(),
                            "Longitude:"+Double.toString(longitude)+"\nLatitude:"+Double.toString(latitude),
                            Toast.LENGTH_SHORT).show();
                }
                else
                {

                    gps.showSettingsAlert();
                }

            }
        });
    }
    /**
     * method onDestroy destroys the activity

     * */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gps.stopUsingGPS();
    }
}