package com.algonquin.cst2335final;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LivingFragmentActivity extends Fragment {
    LivingRoomActivity livingroomActivity = null;

    public LivingFragmentActivity(){}

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
