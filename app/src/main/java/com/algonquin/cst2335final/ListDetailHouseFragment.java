package com.algonquin.cst2335final;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * main fragment which contains two fragments for Garage and temperature
 * Created by marvi on 3/29/2017.
 */

public class ListDetailHouseFragment extends Fragment {
    /**store the id information*/
    protected long id;
    /**store the context obj*/
    protected Context context;
    /**store the database bundle for future use*/
    protected static Bundle StatementBundle;
    /**store the database bundle tempbundle for future use
     * */
    protected static Bundle tempBundle;

    /**the statement of the door*/
    protected boolean doorState;
    /**the statement of the light*/
    protected boolean lightState;

    /**boolean value to see if this is a Tablet*/
    protected static boolean isTablet;
    /**switch for the door*/
    protected Switch sw1 ;
    /**swtich for the light*/
    protected Switch sw2 ;
    /**store the image of the door*/
    protected ImageView doorImage ;
    /**store the image of the light*/
    protected ImageView lightImage;
    /**store the houseActivity(main activity for the house)*/
    protected static HouseActivity houseActivity;
    /**temperature arraylist which store all information of the temperature*/
    protected static ArrayList<String> tempList = new ArrayList<>();
    /**The arrayAdapter for the temperature arraylist*/
    protected static ArrayAdapter<String> arrayAdapter;
    /**store the statement of the door*/
    protected String tempDoorState;
    /**store the statement of the light*/
    protected String tempLightState;
    /**stores the database ID of the temperature*/
    protected static int selectedID;
    /**The list view of the temperature*/
    protected static  ListView TempListView ;
    /**the deleteButton of the temperature*/
    protected static Button deleteButton;
    /**the fragmentManger of this fragment*/
    protected static FragmentManager fragmentManager;


    /**Default constructor
     * */
    public ListDetailHouseFragment(){}

    /**
     * parameterized constructor which pass the MainActivity to others
     * @param houseActivity
     * */
    public ListDetailHouseFragment(HouseActivity houseActivity){
        this.houseActivity = houseActivity;
    }

    /**
     * Override the onCreate method and store the bundles to local'
     * @param b
     * */
    @Override
    public void onCreate(Bundle b){
        super.onCreate(b);
        isTablet =  (getActivity().findViewById(R.id.HouseFrameLayout) != null);
            Bundle bundle = getArguments();
            id = bundle.getLong("ID");
            StatementBundle = bundle.getBundle("StateData");
            tempBundle = bundle.getBundle("tempBundle");

        if(id != 1) {
            tempDoorState = StatementBundle.getString("door", null);
            tempLightState = StatementBundle.getString("light", null);
        }
  //      Log.i("tempDoorState",tempDoorState);
//        Log.i("tempLightState",tempLightState);


    }

    /**Override the onAttach method and get the context of the activity
     * @param context*/
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.context = context;

    }

    /**Override the onResume method and write log for debugging*/
    @Override
    public void onResume(){
        super.onResume();
        Log.i("ListDetailHouseFragment","onResume");
    //    checkState(sw1,sw2,tempDoorState,tempLightState,doorImage,lightImage);

    }

    /**Override the onCreateView method and write the listeners of buttons
     * @param inflater
     * @param container
     * @param savedInstanceState
     * */
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container,Bundle savedInstanceState){
        View gui;

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


/**stores the listener of the garage
 * @param gui*/
    public void getGarageView(View gui){
        Log.i("ListDetailHouseFragment","getGarageView");
         sw1 = (Switch)gui.findViewById(R.id.HouseGarageDoorSwitch);
         sw2 = (Switch)gui.findViewById(R.id.HouseGarageLightSwitch);
         doorImage = (ImageView)gui.findViewById(R.id.houseDoorImag);
         lightImage=(ImageView)gui.findViewById(R.id.houseLightImag);
      //  Button garageSubmitB = (Button)gui.findViewById(R.id.houseGarageSubmitButton);

        checkState(sw1,sw2,tempDoorState,tempLightState,doorImage,lightImage);

//        garageSubmitB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              //  getFragmentManager().beginTransaction().remove()
//            }
//        });

        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton a, boolean isChecked){
                int text;
                int duration;
                if(sw1.isChecked()){
                    text = R.string.houseDoorON;
                    duration = Toast.LENGTH_SHORT;
                    sw2.setChecked(true);
                    doorImage.setImageResource(R.drawable.dooropen48);

                }else{
                    text = R.string.houseDoorOFF;
                    duration = Toast.LENGTH_SHORT;
                    doorImage.setImageResource(R.drawable.doorclose48);

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
                    HouseActivity.putValue("door",Boolean.toString(sw1.isChecked()));
                    Log.i("FragmentResult","door"+Boolean.toString(sw1.isChecked()));
                    HouseActivity.putValue("light",Boolean.toString(sw2.isChecked()));
                    Log.i("FragmentResult","light"+Boolean.toString(sw2.isChecked()));

                }
                HouseActivity.checkDeviceState();
            }
        });//okk
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
                    HouseActivity.putValue("light",Boolean.toString(sw2.isChecked()));
                    Log.i("FragmentResult","light"+Boolean.toString(sw2.isChecked()));

                }
                HouseActivity.checkDeviceState();

            }
        });


    }
    /**stores the listener of the Temperature
     * @param gui*/
    public void getTempView(View gui){
        //action for temp View

        Button addButton = (Button)gui.findViewById(R.id.houseAddButton);
         deleteButton =(Button)gui.findViewById(R.id.houseDeleteButton);
        deleteButton.setEnabled(false);
        TempListView = (ListView)gui.findViewById(R.id.houseTempListView);
        int hour;
        int min;
        int temp;

        //action for ListView
//        if (tempBundle!= null){
//                hour = tempBundle.getInt("hour");
//            min = tempBundle.getInt("min");
//            temp = tempBundle.getInt("temp");
//            tempList.add("time: "+hour+":"+min+" -> "+temp+" `C");
//
//        }

        ArrayList<HouseTempArray> resultList = HouseActivity.readTempSQL();

//                tempList = new ArrayList<>();
//               // Log.i("ArrayList","Hour is "+resultList.get(1).getHour());
//                for(int i =0;i<resultList.size();i++){
//                    tempList.add("Time is: "+resultList.get(i).getHour()+":"+resultList.get(i).getMin()+"-> "+resultList.get(i).getTemp()+"`C");
//                //    Log.i("ArrayList For Loop","index is "+i+"result temp:"+resultList.get(i).getTemp());
//
//                }
        setArrayListContent(getActivity());
        fragmentManager =getFragmentManager();
//        arrayAdapter =new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,tempList);
//        TempListView.setAdapter(arrayAdapter);


        //action for addButton
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("HouseTempActivity","addButton clicked");
                NumberPickerFragment fragment = new NumberPickerFragment();
                getFragmentManager().beginTransaction().replace(R.id.numberPickerFrameLayout,fragment).commit();
            }
        });

        //action for deletebutton

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("ID", "ID is " + selectedID);

                        HouseActivity.deleteDataRecord(selectedID, resultList);
                        tempList.remove(selectedID);
                        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, tempList);
                        TempListView.setAdapter(arrayAdapter);
                        //drop the fragment
                        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.numberPickerFrameLayout)).commit();
                        deleteButton.setEnabled(false);
                }
            });



    }

    /**method for check the statement of the switchs
     * @param  sw1
     * @param sw2
     * @param tempDoorState
     * @param tempLightState
     * @param doorImage
     * @param lightImage*/
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
            doorImage.setImageResource(R.drawable.dooropen48);
        }else{
            doorImage.setImageResource(R.drawable.doorclose48);
        }
        //https://icons8.com/web-app/for/androidL/light
        if(sw2.isChecked())
        {
            lightImage.setImageResource(R.drawable.lighton48);
        }else{
            lightImage.setImageResource(R.drawable.lightoff48);

        }
    }
    /**method for refresh the listView
     * @param Activity*/
    public static void setArrayListContent(Activity Activity){
        ArrayList<HouseTempArray> resultList = HouseActivity.readTempSQL();

        tempList = new ArrayList<>();
        // Log.i("ArrayList","Hour is "+resultList.get(1).getHour());
        for(int i =0;i<resultList.size();i++){
            tempList.add("Time is: "+resultList.get(i).getHour()+":"+resultList.get(i).getMin()+"-> "+resultList.get(i).getTemp()+"`C");
            //    Log.i("ArrayList For Loop","index is "+i+"result temp:"+resultList.get(i).getTemp());
        }

        arrayAdapter =new ArrayAdapter<>(Activity,android.R.layout.simple_list_item_1,tempList);
        TempListView.setAdapter(arrayAdapter);
        TempListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deleteButton.setEnabled(true);
                NumberPickerFragment pickerFragment = new NumberPickerFragment();
                Bundle resultBun = new Bundle();
                selectedID = (int)id;
                resultBun.putLong("tempID",id);
                resultBun.putInt("hour",resultList.get((int) id).getHour());
                resultBun.putInt("min",resultList.get((int) id).getMin());
                resultBun.putInt("temp",resultList.get((int) id).getTemp());
                resultBun.putBoolean("isTablet",isTablet);
                pickerFragment.setArguments(resultBun);
                fragmentManager.beginTransaction().replace(R.id.numberPickerFrameLayout,pickerFragment).commit();

            }


        });
    }

}

//    @Override
//    public void onActivityResult(int requestCode,int resultCode,Intent data){
//        //if(requestCode)
//    }


