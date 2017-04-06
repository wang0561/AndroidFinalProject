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
        setContentView(R.layout.activity_microwave);
        inputTime=(EditText)findViewById(R.id.editTime);
        start=(Button)findViewById(R.id.buttonMicStart);
        stop=(ToggleButton)findViewById(R.id.buttonMicStop);
        cancel=(Button)findViewById(R.id.buttonMicCancel);
        exit=(Button)findViewById(R.id.buttonMicexit) ;
        show=(TextView)findViewById(R.id.timeShow);

        cancel.setEnabled(false);
        stop.setEnabled(false);
        //start button listener
        start.setOnClickListener((v)->{
            start.setEnabled(false);
            cancel.setEnabled(true);
            stop.setEnabled(true);
            isPause=false;
            isCancel=false;

            long timeSet= 1000*Long.parseLong(inputTime.getText().toString());
            long interval=1000;
            new CountDownTimer(timeSet,interval){
                @Override
                public void onFinish(){
                    show.setText("Time up");
//                     Vibrator vibrator=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
//                     vibrator.vibrate(500);
//                     vibrator.cancel();
                }
                @Override
                public void onTick(long milluntilfinished){
                    if(isPause || isCancel){
                        cancel();
                    }else{
                        show.setText(""+milluntilfinished/1000);
                        remainTime=milluntilfinished;
                    }
                }
            }.start();
        });
        //stop button listener
        stop.setOnClickListener(new View.OnClickListener()


        { public void onClick(View view){
            if(stop.isChecked()){
                isPause=true;
                stop.setText("resume");
            }else{
                isPause=false;
                long timeSet= remainTime;
                long interval=1000;
                new CountDownTimer(timeSet,interval){
                    @Override
                    public void onFinish(){
                        show.setText("Time up");
//                       Vibrator vibrator=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
//                       vibrator.vibrate(500);
//                       vibrator.cancel();
                    }
                    @Override
                    public void onTick(long milluntilfinished){
                        if(isPause || isCancel){
                            cancel();
                        }else{
                            show.setText(""+milluntilfinished/1000);
                            remainTime=milluntilfinished;
                        }
                    }
                }.start();
            }
        }});
        //cancel button listener
        cancel.setOnClickListener((v)->{
            isCancel=true;
            show.setText("0");
            start.setEnabled(true);
            cancel.setEnabled(false);
            stop.setEnabled(false);
        });

        inputTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                update();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                update();
            }

            @Override
            public void afterTextChanged(Editable s) {
                update();
            }
            public void update(){
                show.setText(inputTime.getText());
            }

        });

        exit.setOnClickListener((v)->{
            AlertDialog.Builder builder = new AlertDialog.Builder(MicrowaveActivity.this);
            builder.setMessage(R.string.micro_dialog_message).setTitle(R.string.micro_dialog_title).setPositiveButton(R.string.micro_ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //USER CLICK OK
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("Response", "My information to share");
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();

                        }
                    }).setNegativeButton(R.string.micro_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //user click cancel

                }
            }).show();
        });
    }
}
