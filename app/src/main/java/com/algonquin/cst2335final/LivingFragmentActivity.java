package com.algonquin.cst2335final;
/**
 * Created By Min Luo, March 27, 2017
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * This class create fragment for LivingRoom Activity
 */
public class LivingFragmentActivity extends Fragment {
    LivingRoomActivity livingroomActivity = null;

    // default constructor
    public LivingFragmentActivity(){}

    // constructor with parameter
    public LivingFragmentActivity(LivingRoomActivity lv) { livingroomActivity = lv; }

    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        Bundle bun = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View livinggui = inflater.inflate(R.layout.activity_lamp1, null);

        return livinggui;
    }


}
