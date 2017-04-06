package com.algonquin.cst2335final;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;


public class HouseActivity extends AppCompatActivity {
    ProgressBar progressBar;
    int progress;
    protected boolean isTablet;
    //http://www.cnblogs.com/devinzhang/archive/2012/01/20/2328334.html
    private List<String> getData(){
    List<String> data = new ArrayList<>();
        data.add("Garage");
        data.add("Home temperature");
        data.add("Outside weather");
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);
        isTablet = (findViewById(R.id.HouseFrameLayout) != null);
        progressBar = (ProgressBar)findViewById(R.id.houseProgressBar);
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
        ListView HouseListView = (ListView)findViewById(R.id.HouseListView);
        //load the data in to listView
        HouseListView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,getData()));

        HouseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isTablet){//tablet
                    if(id!=2) {
                        Log.i("House", "Customer Clicked the listView, ID is " + id + " position is " + position);
                        Bundle bundle = new Bundle();
                        bundle.putLong("ID", id);
                        ListDetailHouseFragment fragment = new ListDetailHouseFragment();
                        fragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.HouseFrameLayout, fragment).commit();
                    }else{
                        Intent intent = new Intent(HouseActivity.this, HouseWeather.class);
                        startActivity(intent,null);
                    }

                }else{//phone
                    if(id == 0){
                        Log.i("HouseActivity","Is Phone, Garage");
                        Intent intent = new Intent(HouseActivity.this, House_Fragment_garage.class);
                        startActivity(intent,null);
                    }else if (id == 1){
                        Log.i("HouseActivity","Is Phone, temp");
                        Intent intent = new Intent(HouseActivity.this, House_Fragment_Temperature.class);
                        startActivity(intent,null);

                    }else if(id ==2 ){
                        Log.i("HouseActivity","Is Phone, weather");
                        Intent intent = new Intent(HouseActivity.this, HouseWeather.class);
                        startActivity(intent,null);
                    }

                }
            }
        });
    }


}
