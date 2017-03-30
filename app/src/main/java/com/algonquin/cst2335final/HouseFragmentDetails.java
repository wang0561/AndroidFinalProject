package com.algonquin.cst2335final;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HouseFragmentDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_fragment_details);

        //fragment part
        ListDetailHouseFragment fragment = new ListDetailHouseFragment();
        Bundle bun = getIntent().getExtras();
        fragment.setArguments(bun);
        getFragmentManager().beginTransaction().add(R.id.HouseFragmentHolder,fragment).commit();
    }
}
