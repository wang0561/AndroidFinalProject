package com.algonquin.cst2335final;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marvi on 3/29/2017.
 */

public class ListDetailHouseFragment extends Fragment {
    protected long id;
    protected Context context;
    protected Bundle StatementBundle;
    protected Bundle tempBundle;
    protected boolean doorState;
    protected boolean lightState;
    protected boolean isTablet;
    protected Switch sw1 ;
    protected Switch sw2 ;
    protected ImageView doorImage ;
    protected ImageView lightImage;
    protected HouseActivity houseActivity = null;
    protected ArrayList<String> tempList = new ArrayList<>();
    protected ArrayList<Long> tempIdList;
    protected String tempDoorState;
    protected String tempLightState;
    public ListDetailHouseFragment(){}
    public ListDetailHouseFragment(HouseActivity houseActivity){
        this.houseActivity = houseActivity;
    }

    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);

        Bundle bundle = getArguments();
        id = bundle.getLong("ID");
        StatementBundle = bundle.getBundle("StateData");
        tempBundle = bundle.getBundle("tempBundle");
        isTablet =  (getActivity().findViewById(R.id.HouseFrameLayout) != null);
        tempDoorState=StatementBundle.getString("door",null);
        tempLightState = StatementBundle.getString("light",null);
  //      Log.i("tempDoorState",tempDoorState);
//        Log.i("tempLightState",tempLightState);


    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public void onResume(){
        super.onResume();
        Log.i("ListDetailHouseFragment","onResume");
    //    checkState(sw1,sw2,tempDoorState,tempLightState,doorImage,lightImage);

    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container,Bundle savedInstanceState){
        View gui;
        TextView textView;

        if(id == 0){

            Log.i("ListDetailHouseFragment","in inflater");
            gui = inflater.inflate(R.layout.activity_house__fragment_garage,null);
            getGarageView(gui);

            //Garage
        }else if (id==1){
            Log.i("ListDetailHouseFragment","in inflater of the temperature");

            gui = inflater.inflate(R.layout.house_fragment_temperature,null);

            getTempView(gui);
            //Temperature
        }else {
            gui = inflater.inflate(R.layout.house_weather_layout,null);


            //weather
        }
        return gui;
    }



    public void getGarageView(View gui){
        Log.i("ListDetailHouseFragment","getGarageView");
         sw1 = (Switch)gui.findViewById(R.id.HouseGarageDoorSwitch);
         sw2 = (Switch)gui.findViewById(R.id.HouseGarageLightSwitch);
         doorImage = (ImageView)gui.findViewById(R.id.houseDoorImag);
         lightImage=(ImageView)gui.findViewById(R.id.houseLightImag);


        checkState(sw1,sw2,tempDoorState,tempLightState,doorImage,lightImage);

        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton a, boolean isChecked){
                int text;
                int duration;
                if(sw1.isChecked()){
                    text = R.string.houseDoorON;
                    duration = Toast.LENGTH_SHORT;
                    sw2.setChecked(true);
                    doorImage.setImageResource(R.drawable.dooropened48);

                }else{
                    text = R.string.houseDoorOFF;
                    duration = Toast.LENGTH_SHORT;
                    doorImage.setImageResource(R.drawable.doorclosed48);

                }
                Toast toast = Toast.makeText(getActivity(),text,duration);
                toast.show();

                //return the result

                if(!isTablet) {
                    Intent result = new Intent();
                    result.putExtra("device", "door");
                    result.putExtra("state", Boolean.toString(sw1.isChecked()));
                    getActivity().setResult(0, result);
                }else{
                    houseActivity.putValue("door",Boolean.toString(sw1.isChecked()));
                    Log.i("FragmentResult","door"+Boolean.toString(sw1.isChecked()));
                    houseActivity.putValue("light",Boolean.toString(sw2.isChecked()));
                    Log.i("FragmentResult","light"+Boolean.toString(sw2.isChecked()));

                }
            }
        });
        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton a, boolean isChecked){
                if(sw2.isChecked()) {
                    lightImage.setImageResource(R.drawable.lighton48);
                    Snackbar.make(gui.findViewById(R.id.house_fragment_garage), R.string.houseLightON, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                }else {
                    lightImage.setImageResource(R.drawable.lightoff48);
                    Snackbar.make(gui.findViewById(R.id.house_fragment_garage), R.string.houseLightOFF, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }

                //return the result

                if(!isTablet) {
                    Intent result = new Intent();
                    result.putExtra("device", "light");
                    result.putExtra("state", Boolean.toString(sw2.isChecked()));
                    getActivity().setResult(0, result);
                }else{
                    houseActivity.putValue("light",Boolean.toString(sw2.isChecked()));
                    Log.i("FragmentResult","light"+Boolean.toString(sw2.isChecked()));

                }
            }
        });


    }
    public void getTempView(View gui){
        //action for temp View

        Button addButton = (Button)gui.findViewById(R.id.houseAddButton);
        Button deleteButton =(Button)gui.findViewById(R.id.houseDeleteButton);
        ListView TempListView = (ListView)gui.findViewById(R.id.houseTempListView);
        int hour;
        int min;
        int temp;


        //action for ListView
        if (tempBundle!= null){
                hour = tempBundle.getInt("hour");
            min = tempBundle.getInt("min");
            temp = tempBundle.getInt("temp");
            tempList.add("time: "+hour+":"+min+" -> "+temp+" `C");

        }
            if (tempList.isEmpty()) {
                tempList.add("Please Add a New Record");
            }

        TempListView.setAdapter(new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,tempList));
        TempListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //tempBundle.putLong("tempID",id);
                NumberPickerFragment pickerFragment = new NumberPickerFragment();
                pickerFragment.setArguments(tempBundle);
                getFragmentManager().beginTransaction().replace(R.id.numberPickerFrameLayout,pickerFragment).commit();

            }
        });

        //action for addButton
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("HouseTempActivity","addButton clicked");
                NumberPickerFragment fragment = new NumberPickerFragment();
                getFragmentManager().beginTransaction().replace(R.id.numberPickerFrameLayout,fragment).commit();
            }
        });


    }

    public void checkState(Switch sw1,Switch sw2,String tempDoorState,String tempLightState,ImageView doorImage,ImageView lightImage){
        try {
            if (tempDoorState.equals("true")) {
                doorState = true;
            } else {
                doorState = false;
            }
        }catch (Exception e){
            doorState = false;
            Log.i("checkState","inDoorException");
        }
        try {
            if (tempLightState.equals("true")) {
                lightState = true;
            } else {
                lightState = false;
            }
        }catch (Exception e){
            lightState = false;
            Log.i("checkState","inLightException");

        }

        sw1.setChecked(doorState);
        sw2.setChecked(lightState);

        //https://icons8.com/web-app/for/androidL/door
        if(sw1.isChecked()){
            doorImage.setImageResource(R.drawable.dooropened48);
        }else{
            doorImage.setImageResource(R.drawable.doorclosed48);
        }
        //https://icons8.com/web-app/for/androidL/light
        if(sw2.isChecked())
        {
            lightImage.setImageResource(R.drawable.lighton48);
        }else{
            lightImage.setImageResource(R.drawable.lightoff48);

        }
    }

//    @Override
//    public void onActivityResult(int requestCode,int resultCode,Intent data){
//        //if(requestCode)
//    }



    private class tempListAdapter extends ArrayAdapter<String>{
        public tempListAdapter(Context ctx){
            super(ctx,0);
        }
        public int getCount(){
            return tempList.size();
        }

        public long getItemId(int position){
            return tempIdList.get(position);
        }

        public String getItem(int position){
            return tempList.get(position);
        }
    }
}
