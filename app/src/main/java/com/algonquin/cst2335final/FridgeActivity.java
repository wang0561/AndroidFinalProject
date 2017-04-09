package com.algonquin.cst2335final;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.ProtectionDomain;

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
