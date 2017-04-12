package com.algonquin.cst2335final;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class House_Fragment_Detail_Temperature extends AppCompatActivity {
    Bundle tempBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_fragment_holder);
        ListDetailHouseFragment fragment = new ListDetailHouseFragment();
        Bundle bun = getIntent().getExtras();
        if(tempBundle!=null){
            bun.putBundle("tempBundle",tempBundle);
        }
        fragment.setArguments(bun);
        Log.i("HousFragmentTemperature","startt ransaction");
       // Intent intent = new Intent(House_Fragment_Detail_Temperature.this,HouseNumberPickers.class);
       // startActivityForResult(intent,5);
        getFragmentManager().beginTransaction().replace(R.id.housefragmentholder,fragment).commit();


    }



    public void addItemToTheList(Bundle bundle){
        if(bundle!= null) {
            tempBundle = bundle;
//            int hour = bundle.getInt("hour", 0);
//            int min = bundle.getInt("min",0);
//            int temp = bundle.getInt("temp",0);
        }
    }

}
