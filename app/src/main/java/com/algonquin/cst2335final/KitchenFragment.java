package com.algonquin.cst2335final;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by Wang on 4/5/2017.
 */

public class KitchenFragment extends Fragment  {
    protected KitchenActivity kitchenActivity;
    protected String id;
    protected Context context;
    protected EditText inputTime;
    protected Button start;
    protected ToggleButton stop;
    protected Button cancel;
    protected Button exit;
    protected TextView show;
    protected Boolean isPause=false;
    protected Boolean isCancel=false;
    protected long remainTime=0;
    protected Switch aSwitch;
    protected ImageButton image;
    protected TextView textOfFridge;
    protected TextView textOfFreezer;
    protected EditText editFridge;
    protected EditText editFreezer;
    protected Button setFridgeB;
    protected Button setFreezerB;
    protected Button saveButton;
    protected ProgressBar progressBar;
    protected int progressStatus;
    Handler handler=new Handler();
    protected boolean isTablet;
    protected double tempofFridge;
    protected double tempofFreezer;
    protected String lightStatus;
    protected int processofLight;


    public KitchenFragment(){}
    public KitchenFragment( KitchenActivity kitchenActivity){this.kitchenActivity=kitchenActivity;}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        id=bundle.getString("ID");
        tempofFridge=bundle.getDouble("Fridgetemp");
        tempofFreezer=bundle.getDouble("FreezerTemp");
        lightStatus=bundle.getString("lightStatus");
        processofLight=bundle.getInt("progressOfLight");
        isTablet=bundle.getBoolean("isTablet");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View gui=null;

        if(id.toLowerCase().contains("micro")||id.toLowerCase().contains("微波炉")){
            gui=inflater.inflate(R.layout.activity_microwave,null);
            microwaveAction(gui);
        }
        else if(id.toLowerCase().contains("fridge")||id.toLowerCase().contains("freezer")||id.toLowerCase().contains("冰")){
            gui=inflater.inflate(R.layout.activity_fridge,null);
            fridgeAction(gui);
        }else if (id.toLowerCase().contains("light")||id.toLowerCase().contains("灯")){
            gui=inflater.inflate(R.layout.activity_kitchen_light,null);
            lightAction(gui);
        }
        return gui;
    }

    private void lightAction(View view) {

        Button backB=(Button)view.findViewById(R.id.KitchenLightBack);
        backB.setOnClickListener((v)->{
            if(isTablet){
                kitchenActivity.getValuefromfragment("lightStatus",""+lightStatus);
                kitchenActivity.getValuefromfragment("progressOfLight",""+processofLight);
                kitchenActivity.removeFragment(KitchenFragment.this);
            }else {
            Intent back=new Intent();
            back.putExtra("lightStatus",lightStatus);//(string,string)
            back.putExtra("progressOfLight",processofLight);//(string,int)
            getActivity().setResult(0,back);
            getActivity().finish();}
        });
        SeekBar lightSeekBar= (SeekBar)view.findViewById(R.id.kitchenLightProgress) ;
        lightSeekBar.setProgress(processofLight);

        lightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                processofLight=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        aSwitch=(Switch)view.findViewById(R.id.switchkitchenlight);
        image=(ImageButton) view.findViewById(R.id.imagekitchenlight);

       aSwitch.setSelected(true);
        if(lightStatus.compareTo("on")==0){
            aSwitch.setChecked(true);
            image.setImageResource(R.drawable.kitchenon);
            lightSeekBar.setEnabled(true);
        }
        else{aSwitch.setChecked(false);
            image.setImageResource(R.drawable.kitchenoff);
            lightSeekBar.setEnabled(false);
        }

        aSwitch.setOnCheckedChangeListener(

                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                        CharSequence text;
                        int duration;
                        if (aSwitch.isChecked()) {
                            lightSeekBar.setEnabled(true);
                            lightStatus="on";
                            text = getString(R.string.kitchenlightison);
                            duration = Toast.LENGTH_SHORT;
                            image.setImageResource(R.drawable.kitchenon);

                        } else {
                            lightStatus="off";
                            lightSeekBar.setEnabled(false);
                            text = getString(R.string.kitchenlightisoff);
                            duration = Toast.LENGTH_LONG;
                            image.setImageResource(R.drawable.kitchenoff);

                        }
                        Toast toast = Toast.makeText(view.getContext(), text, duration);
                        toast.show();
                    }
                }
        );
    }

    private void fridgeAction(View gui){

        textOfFridge=(TextView)gui.findViewById(R.id.fridgetextview);
        textOfFreezer=(TextView)gui.findViewById(R.id.freezertextView);
        editFridge=(EditText)gui.findViewById(R.id.editTextfridge);
        editFreezer=(EditText)gui.findViewById(R.id.editTextfreezer);
        setFridgeB=(Button)gui.findViewById(R.id.buttonsetfridge);
        setFreezerB=(Button)gui.findViewById(R.id.buttonsetfreezer);
        saveButton=(Button)gui.findViewById(R.id.buttonSave);
        progressBar=(ProgressBar)gui.findViewById(R.id.fridgeprogressbar);

        textOfFreezer.setText(getActivity().getString(R.string.freezertempchagetext)+tempofFreezer+" °C");
        textOfFridge.setText(getActivity().getString(R.string.fridgetempchangetext)+tempofFridge+" °C");
        setFreezerB.setOnClickListener(new View.OnClickListener(){
        //SET THE ACTION OF BUTTON
            @Override
            public void onClick(View view){
                String temp=editFreezer.getText().toString();
                textOfFreezer.setText(getActivity().getString(R.string.freezertempchagetext)+temp+" °C");
                tempofFreezer=Double.parseDouble(temp);
            }

        });

        setFridgeB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                String temp=editFridge.getText().toString();
                textOfFridge.setText(getActivity().getString(R.string.fridgetempchangetext)+temp+" °C");
                tempofFridge=Double.parseDouble(temp);
                Log.i("fridge temp",""+tempofFridge);
            }

        });
        saveButton.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            progressStatus=0;
            new Thread(() -> {
                while(progressStatus<100){
                    progressStatus+=10;
                    handler.post(new Runnable(){
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                    try{
                        Thread.sleep(100);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    if(progressStatus>90){
                       if(isTablet){
                           kitchenActivity.getValuefromfragment("Fridgetemp",""+tempofFridge);
                           kitchenActivity.getValuefromfragment("FreezerTemp",""+tempofFreezer);
                           kitchenActivity.removeFragment(KitchenFragment.this);
                       }
                       else{
                           Intent backdata=new Intent();
                        backdata.putExtra("Fridgetemp",tempofFridge);
                           Log.i("testingggggg",""+tempofFridge);
                        backdata.putExtra("FreezerTemp",tempofFreezer);
                           Log.i("testingggggg",""+tempofFreezer);
                        getActivity().setResult(0,backdata);
                        getActivity().finish();}}
                }
            }).start();
            Snackbar.make(gui,R.string.fridgesnackbartext,Snackbar.LENGTH_LONG).setAction("Action",null).show();

        });

    }
    private void microwaveAction(View view) {
        ImageView imgOfMic=(ImageView)view.findViewById(R.id.imageOfmicrowave);
        inputTime=(EditText)view.findViewById(R.id.editTime);
        start=(Button)view.findViewById(R.id.buttonMicStart);
        stop=(ToggleButton)view.findViewById(R.id.buttonMicStop);
        cancel=(Button)view.findViewById(R.id.buttonMicCancel);
        exit=(Button)view.findViewById(R.id.buttonMicexit) ;
        show=(TextView)view.findViewById(R.id.timeShow);

        cancel.setEnabled(false);
        stop.setEnabled(false);
        //start button listener
        start.setOnClickListener((v)->{
            imgOfMic.setImageResource(R.drawable.microwaveon);
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
                    show.setText(R.string.microtimefinishtext);
                    imgOfMic.setImageResource(R.drawable.microwaveoff);
                    start.setEnabled(true);
                    cancel.setEnabled(false);
                    stop.setEnabled(false);
                     Vibrator vibrator=(Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                     vibrator.vibrate(500);
                     vibrator.cancel();
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
        stop.setOnClickListener((v)->{
            if(stop.isChecked()){
                isPause=true;
                stop.setText(R.string.microresumebuttontext);
            }else{
                isPause=false;
                long timeSet= remainTime;
                long interval=1000;
                new CountDownTimer(timeSet,interval){
                    @Override
                    public void onFinish(){
                        show.setText(R.string.microtimefinishtext);
                        imgOfMic.setImageResource(R.drawable.microwaveoff);
                        start.setEnabled(true);
                        cancel.setEnabled(false);
                        stop.setEnabled(false);
                       Vibrator vibrator=(Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                       vibrator.vibrate(500);
                       vibrator.cancel();
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
        });
        //cancel button listener
        cancel.setOnClickListener((v)->{
            imgOfMic.setImageResource(R.drawable.microwaveoff);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage(R.string.micro_dialog_message).setTitle(R.string.micro_dialog_title).setPositiveButton(R.string.micro_ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //USER CLICK OK
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("Response", "My information to share");
                            getActivity().setResult(Activity.RESULT_OK, resultIntent);
                            getActivity().finish();

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
