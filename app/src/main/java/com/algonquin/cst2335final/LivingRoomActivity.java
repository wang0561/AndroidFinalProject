package com.algonquin.cst2335final;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LivingRoomActivity extends AppCompatActivity {
    private Context ctx;
    private ListView livingroomlist;
    private String[] livingroom = {"Lamp On/Off", "Lamp Dimmable", "Lamp Colorful", "TV", "Window Blinds"};
    protected Boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room);
        ctx = this;
        //isTablet = (findViewById(R.id.fragmentHolder)!=null); // boolean variable to tell if it's a tablet
        livingroomlist = (ListView)findViewById(R.id.livinglists);
        livingroomlist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //if(!isTablet) {//phone
                    switch (position) {
                        case 0: //lamp1
                            Intent lamp1intent = new Intent(ctx, Lamp1Activity.class);
                            lamp1intent.putExtra("Status", "Off");
                            startActivityForResult(lamp1intent, 5);
                            break;
                        case 1: //lamp2
                            Intent lamp2intent = new Intent(ctx, Lamp2Activity.class);
                            lamp2intent.putExtra("Status", "Off");
                            startActivityForResult(lamp2intent, 10);
                            break;
                        case 2: //lamp3
                            Intent lamp3intent= new Intent(ctx, Lamp3Activity.class);
                            lamp3intent.putExtra("Status", "Off");
                            startActivityForResult(lamp3intent, 15);
                            break;
                        case 3: // tv
                            Intent tvActivity = new Intent(ctx, TVActivity.class);
                            tvActivity.putExtra("TV State", "Off");
                            startActivityForResult(tvActivity, 20);
                            break;
                        case 4: // blinds
                            Intent blindsActivity = new Intent(ctx, BlindsActivity.class);
                            blindsActivity.putExtra("Blinds Status", "Off");
                            startActivityForResult(blindsActivity, 15);
                            break;
                    }
               // }else{// tablet
              //  }
            }
        });

        livingroomlist.setAdapter(new ArrayAdapter<>(this, R.layout.living_row_layout, livingroom ));
        Log.i("LivingRoomActivity", "OnCreate");
    }

    public void onStart(){
        super.onStart();
        Log.i("LivingRoomActivity", "onStart");
    }

    public void onResume(){

        super.onResume();
        Log.i("LivingRoomActivity", "onResume");
    }

    public void onPause(){

        super.onPause();
        Log.i("LivingRoomActivity", "onPause");
    }

    public void onStop()
    {
        super.onStop();
        Log.i("LivingRoomActivity", "onStop");
    }

    public void onDestroy(){

        super.onDestroy();
        Log.i("LivingRoomActivity", "ondestroy");
    }
    //This function gets called when another activity has finished and this activity is resuming:
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(data != null)
        {
            String fromOtherActivity = data.getStringExtra("Status");
            Log.d("LivingRoomActivity", "Back from  other activity: " + fromOtherActivity);
        }
    }
}
