package com.algonquin.cst2335final;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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

        Button setB=(Button)findViewById(R.id.autoSetting);
        setB.setOnClickListener((v)->{

            AlertDialog.Builder builder = new AlertDialog.Builder(AutoActivity.this);
            builder.setMessage(R.string.micro_dialog_message).setTitle(R.string.micro_dialog_title).setPositiveButton(R.string.micro_ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //USER CLICK OK
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("Response", "My information to share");
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