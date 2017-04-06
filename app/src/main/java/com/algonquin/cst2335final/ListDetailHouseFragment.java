package com.algonquin.cst2335final;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by marvi on 3/29/2017.
 */

public class ListDetailHouseFragment extends Fragment {
    protected long id;
    protected Context context;
    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);

        Bundle bundle = getArguments();
        id = bundle.getLong("ID");

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container,Bundle savedInstanceState){
        View gui;
        TextView textView;

        if(id == 0){

            Log.i("ListDetailHouseFragment","in inflater");

            gui = inflater.inflate(R.layout.activity_house__fragment_garage,null);
            Switch sw1 = (Switch)gui.findViewById(R.id.HouseGarageDoorSwitch);
            Switch sw2 = (Switch)gui.findViewById(R.id.HouseGarageLightSwitch);
            Button state = (Button)gui.findViewById(R.id.HouseViewStateButton);

            sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton a, boolean isChecked){
                    CharSequence text;
                    int duration;
                    if(sw1.isChecked()){
                        text = "Switch is ON";
                        duration = Toast.LENGTH_SHORT;
                    }else{
                        text = "Switch is OFF";
                        duration = Toast.LENGTH_SHORT;
                    }
                    Toast toast = Toast.makeText(getActivity(),text,duration);
                    toast.show();
                }
            });
            sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton a, boolean isChecked){
                    Snackbar.make(gui.findViewById(R.id.house_fragment_garage),"Light state Changed",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                }
            });

            state.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //dialog
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setTitle("HI");
                    builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //donothing
                        }
                    });
                    builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //donothing
                        }
                    });
                    AlertDialog dialog1 = builder1.create();
                    dialog1.show();
                }
            });


            //Garage
        }else if (id==1){
            gui = inflater.inflate(R.layout.activity_house__fragment__temperature,null);
            //Temperature
        }else {
            gui = inflater.inflate(R.layout.house_weather_layout,null);


            //weather
        }
        return gui;
    }

}
