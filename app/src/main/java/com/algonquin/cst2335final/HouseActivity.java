package com.algonquin.cst2335final;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HouseActivity extends AppCompatActivity {

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

        ListView HouseListView = (ListView)findViewById(R.id.HouseListView);
        //load the data in to listView
        HouseListView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,getData()));

        HouseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("House","Customer Clicked the listView, ID is "+id+"position is "+position);
                Bundle bundle = new Bundle();
                bundle.putLong("ID",id);
                ListDetailHouseFragment fragment = new ListDetailHouseFragment();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.HouseFrameLayout,fragment).commit();
            }
        });
    }
}
