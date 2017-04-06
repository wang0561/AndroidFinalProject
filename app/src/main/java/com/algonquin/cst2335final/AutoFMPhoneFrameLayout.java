package com.algonquin.cst2335final;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AutoFMPhoneFrameLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_fmphone_frame_layout);

        AutoFMFragment autoFrag =new AutoFMFragment(null);
        Bundle bun = getIntent().getExtras();
        autoFrag.setArguments(bun);
        FragmentTransaction autoFragTransaction = getFragmentManager().beginTransaction();
        autoFragTransaction.add(R.id.autoFMFramLayout,autoFrag ).commit();
    }
}
