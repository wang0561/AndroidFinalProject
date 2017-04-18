package com.algonquin.cst2335final;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * the activity of the numberPickers which can access the fragment of the temperature
 * @author Chen
 * @version 1.0
 * */
public class House_Fragment_Detail_Temperature extends AppCompatActivity {
    Bundle tempBundle;

    /**Override the onCreate method which can access the fragment of temperature
     * @param savedInstanceState*/
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
        Log.i("HousFragmentTemperature","start transaction");

        getFragmentManager().beginTransaction().replace(R.id.housefragmentholder,fragment).commit();


    }

}
