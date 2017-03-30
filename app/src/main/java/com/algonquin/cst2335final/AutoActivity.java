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
import android.widget.Toast;

public class AutoActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "AutoActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.autotoolbar);
  //      setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.autofab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public boolean onCreateOptionsMenu (Menu m){

        getMenuInflater().inflate(R.menu.toolbar_auto, m );
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem mi){

        int id =mi.getItemId();

        switch( id ){

            case R.id.autotemperature:
                Intent intentLivingRoom = new Intent(AutoActivity.this, AutoTempActivity.class);
                startActivityForResult(intentLivingRoom, 5);
                break;

            case R.id.autoradio:
                Intent intentKitchen = new Intent(AutoActivity.this, KitchenActivity.class);
                startActivityForResult(intentKitchen, 5);
                break;

            case R.id.autogps:
               /* Intent intentHouse= new Intent(AutoActivity.this, AutoGoogleMap.class);
                startActivityForResult(intentHouse, 5);

                */
                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                break;

            case R.id.autolight:
                Intent intentAuto= new Intent(AutoActivity.this, KitchenActivity.class);
                startActivityForResult(intentAuto, 5);
                break;

            case R.id.autohelp:

                Toast.makeText(this, "CST2355 Final Project Automobile part by Bo Liu", Toast.LENGTH_LONG).show();
                break;
        }

        return true;
    }

}