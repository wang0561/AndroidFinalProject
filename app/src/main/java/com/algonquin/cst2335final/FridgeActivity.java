package com.algonquin.cst2335final;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Class for fridge/freezer activity
 * @author Wang,Tao
 * @version 1.0
 * */
public class FridgeActivity extends AppCompatActivity {

/**
 * onCreate method for fridge/freezer activity
 * @param savedInstanceState
 *
 * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kitchen_fragment);
        KitchenFragment frag = new KitchenFragment(null);
        Bundle bun = getIntent().getExtras();
        frag.setArguments( bun );
        getFragmentManager().beginTransaction().add(R.id.kitchenfragmentHolder,frag).commit();

    }
}
