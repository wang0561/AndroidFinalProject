package com.algonquin.cst2335final;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class FridgeActivity extends AppCompatActivity {


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
