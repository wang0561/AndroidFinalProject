package com.algonquin.cst2335final;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HouseNumberPickers extends AppCompatActivity {
    NumberPickerFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_fragment_temperature);
        fragment= new NumberPickerFragment();
        Bundle bun = getIntent().getExtras();
        fragment.setArguments(bun);
        getFragmentManager().beginTransaction().replace(R.id.numberPickerFrameLayout,fragment).commit();
    }

    public void removeFrag(){
        getFragmentManager().beginTransaction().remove(fragment).commit();
    }
}
