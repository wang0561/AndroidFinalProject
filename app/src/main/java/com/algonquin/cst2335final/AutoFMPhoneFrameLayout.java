/**
 * @version 1.0
 * @(#)ActivityDate.java 1.0 2017/04/19
 * this is a part of project for CST2335_010 Android final Project;
 * */

package com.algonquin.cst2335final;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * This class is autmobile FM management Fragment class which for the phone not the tablet to call
 * fragment class to create and close the fragment activites.
 * @version 1.0
 * @author BO
 */

public class AutoFMPhoneFrameLayout extends AppCompatActivity {

    /**
     * method onCreate creates activity
     *  @param savedInstanceState is bundle
     * */

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
