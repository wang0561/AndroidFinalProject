package com.algonquin.cst2335final;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AutoActivity extends AppCompatActivity {

    protected ListView autoActivityListView;
    protected String[] autoItems =new String[]{"Temperature Control","FM Radio Setting",
            "Google Navigation","Auto Light Setting","Help"};
    protected Button autoActivityReturnButton;
    int progress =0;
    protected static final String ACTIVITY_NAME = "AutoActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);


        autoActivityListView=(ListView)findViewById(R.id.autoactivitylistview);
        autoActivityListView.setAdapter(new ArrayAdapter<String>(this, R.layout.auto_row, autoItems ));
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

                        startActivityForResult(new Intent(AutoActivity.this, AutoFMActivity.class),5);
                        break;
                    case 2: //light

                        ActivityCompat.requestPermissions(AutoActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
/*
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
                        }*/
                        break;

                    case 3: //light
                        startActivityForResult(new Intent(AutoActivity.this, AutoLight.class ), 5);
                        break;

                    case 4: //help




                                // Create custom dialog object
                                final Dialog dialog = new Dialog(AutoActivity.this);
                                // Include dialog.xml file
                                dialog.setContentView(R.layout.auto_dialog);
                                // Set dialog title
                                dialog.setTitle("Help");

                                // set values for custom dialog components - text, image and button
                                TextView text1 = (TextView) dialog.findViewById(R.id.authorName);
                                text1.setText("Author: Bo Liu ");
                                TextView text2 = (TextView) dialog.findViewById(R.id.autoVersion);
                                text2.setText("Version: Version 1 ");
                                TextView text3 = (TextView) dialog.findViewById(R.id.instruction);
                                text3.setText("Instruction: Click on each item, it will bring you to next level");
                                Button dialogExit = (Button) dialog.findViewById(R.id.autoDialogbutton);

                                ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.autoDialogProgressBar);
                                dialog.show();

                        progressBar.setVisibility(View.VISIBLE);
                        Handler handler = new Handler();

                        new Thread(()-> {
                             progress = 0;
                            while(progress <100) {
                                progress += 10;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setProgress(progress);
                                    }
                                });
                                try {
                                    Thread.sleep(100);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                        dialogExit.setOnClickListener(v->{

                            finish();

                        });

                        break;


                }
            }


        });
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    TrackGPS gps = new TrackGPS(AutoActivity.this);
                    double longitude=0;
                    double latitude=0;
                    if(gps.canGetLocation()){

                        longitude = gps.getLongitude();
                        latitude = gps .getLatitude();

                        Toast.makeText(getApplicationContext(),"Longitude:"+Double.toString(longitude)+
                                "\nLatitude:"+Double.toString(latitude),Toast.LENGTH_SHORT).show();
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



                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(),"You Have Denied the Location service"
                            ,Toast.LENGTH_SHORT).show();

                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
}