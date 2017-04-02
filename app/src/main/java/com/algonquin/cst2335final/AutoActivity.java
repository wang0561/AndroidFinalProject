package com.algonquin.cst2335final;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class AutoActivity extends AppCompatActivity {

    protected ListView autoActivityListView;
    protected String[] autoItems =new String[]{"Temperature","FM Radio","Google Navigation","Auto Light"};
    protected Button autoActivityReturnButton;

    protected static final String ACTIVITY_NAME = "AutoActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);


        autoActivityListView=(ListView)findViewById(R.id.autoactivitylistview);
        autoActivityListView.setAdapter(new ArrayAdapter<String>(this, R.layout.kitchen_row, autoItems ));
        autoActivityReturnButton=(Button)findViewById(R.id.autoAvtivityReturn);
        autoActivityReturnButton.setOnClickListener(e->{
            startActivityForResult(new Intent(this, StartActivity.class),5);
        });

        autoActivityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch(position)
                {

                    case 0:
                        startActivityForResult(new Intent(AutoActivity.this, AutoTempActivity.class),5);
                        break;
                    case 1:
                        //Start the Screen_Two activity, with 10 as the result code

                        startActivityForResult(new Intent(AutoActivity.this, FridgeActivity.class),5);
                        break;
                    case 2: //light

                        TrackGPS gps = new TrackGPS(AutoActivity.this);
                        double longitude=75.6972;
                        double latitude=45.4215;
                        if(gps.canGetLocation()){

                            longitude = gps.getLongitude();
                            latitude = gps .getLatitude();

                            Toast.makeText(getApplicationContext(),"Longitude:"+Double.toString(longitude)+"\nLatitude:"+Double.toString(latitude),Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            gps.showSettingsAlert();
                        }


                        // Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                        Uri gmmIntentUri = Uri.parse("geo:"+latitude+","+longitude);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(mapIntent);
                        }
                        break;

                    case 3: //light
                        startActivityForResult(new Intent(AutoActivity.this, AutoLight.class ), 5);
                        break;


                }
            }


        });

    }


}