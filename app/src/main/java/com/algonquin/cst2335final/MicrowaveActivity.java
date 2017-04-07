package com.algonquin.cst2335final;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MicrowaveActivity extends AppCompatActivity {
    protected EditText inputTime;
    protected Button start;
    protected ToggleButton stop;
    protected Button cancel;
    protected Button exit;
    protected TextView show;
    protected Boolean isPause=false;
    protected Boolean isCancel=false;
    protected long remainTime=0;
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
