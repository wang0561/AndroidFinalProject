package com.algonquin.cst2335final;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * the activity of the numberPickers which can access the fragment of the garage
 * @author Chen
 * @version 1.0
 * */
public class House_Fragment_Detail_garage extends AppCompatActivity {

    /**Override the onCreate method which could access the garage fragment*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_fragment_holder);
        ListDetailHouseFragment fragment = new ListDetailHouseFragment();
        Bundle bun = getIntent().getExtras();
        fragment.setArguments(bun);
        getFragmentManager().beginTransaction().replace(R.id.housefragmentholder,fragment).commit();


    }
}
