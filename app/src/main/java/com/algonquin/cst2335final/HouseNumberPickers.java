package com.algonquin.cst2335final;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * the activity of the numberPickers which can access the fragment of the numberPicker
 * @author Chen
 * @version 1.0
 * */
public class HouseNumberPickers extends AppCompatActivity {

    /**
     * Override the onCreate Method which could access the fragment
     * @param savedInstanceState
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_fragment_temperature);
        NumberPickerFragment fragment= new NumberPickerFragment();
        Bundle bun = getIntent().getExtras();
        fragment.setArguments(bun);
        getFragmentManager().beginTransaction().replace(R.id.numberPickerFrameLayout,fragment).commit();
    }

}
