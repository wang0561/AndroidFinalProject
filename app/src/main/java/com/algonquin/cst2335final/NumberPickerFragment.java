package com.algonquin.cst2335final;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * Created by marvi on 4/10/2017.
 */

public class NumberPickerFragment extends Fragment {
    protected int hour ;
    protected int min  ;
    protected static HouseActivity houseActivity;
    protected int temp ;
    protected Bundle input;

    public NumberPickerFragment(){
    }
    public NumberPickerFragment(HouseActivity houseActivity){
        this.houseActivity =houseActivity;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        input = getArguments();
        if (input != null) {
            hour = input.getInt("hour", 0);
            min = input.getInt("min", 0);
            temp = input.getInt("temp", 16);

        }
    }
    @Override
    public void onAttach(Context ctx){
        super.onAttach(ctx);
        Log.i("NumberPickerFragment","In on Attach");

    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState){
        View gui;
        gui = inflater.inflate(R.layout.activity_house_number_pickers,null);
        Log.i("NumberPickerFragment","In onCreateView");
        getNumberPickers(gui);
        return gui;
    }

    public void getNumberPickers(View gui){
        NumberPicker hourPicker = (NumberPicker)gui.findViewById(R.id.house_hour_picker);
        NumberPicker minPicker = (NumberPicker)gui.findViewById(R.id.house_min_picker);
        NumberPicker tempPicker = (NumberPicker)gui.findViewById(R.id.house_temp_picker);
        TextView result =(TextView)gui.findViewById(R.id.housePickersResultTextView);
        Button submitButton = (Button)gui.findViewById(R.id.HouseNumberPickerSubmitButton);



        //hourPicker
        hourPicker.setMaxValue(23);
        hourPicker.setMinValue(0);
        hourPicker.setWrapSelectorWheel(true);
        hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hour = newVal;
                result.setText("The time is "+hour+ ":" + min +"\nThe temperature is :"+temp);
            }
        });


        //minPicker
        minPicker.setMinValue(0);
        minPicker.setMaxValue(59);
        minPicker.setWrapSelectorWheel(true);
        minPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                min = newVal;
                result.setText("The time is "+hour+ ":" + min +"\nThe temperature is :"+temp);
            }
        });

        //tempPicker
        tempPicker.setMaxValue(30);
        tempPicker.setMinValue(16);
        tempPicker.setWrapSelectorWheel(true);
        tempPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                temp=newVal;
                result.setText("The time is "+hour+ ":" + min +"\nThe temperature is :"+temp);

            }
        });
        //initial & load default value
     //   Log.i("Pickers","hour"+hour+"min"+min+"temp"+temp);
        hourPicker.setValue(hour);
        minPicker.setValue(min);
        tempPicker.setValue(temp);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert data into database
                houseActivity.putTempValue(hour,min,temp);


                Bundle tempBundle =new Bundle();
                Log.i("Upload","hour"+hour+"min"+min+"temp"+temp);

                tempBundle.putInt("hour",hour);
                tempBundle.putInt("min",min);
                tempBundle.putInt("temp",temp);
                tempBundle.putLong("ID", 1);

                tempBundle.putBundle("tempBundle",tempBundle);
                /// / Log.i("setResult",getActivity().toString());
                ListDetailHouseFragment fragment = new ListDetailHouseFragment();
                fragment.setArguments(tempBundle);
                getFragmentManager().beginTransaction().replace(R.id.HouseFrameLayout, fragment).commit();
             //   getActivity().setResult(Activity.RESULT_OK,intent);
//                getParentFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
//                Log.i("requestCode",getTargetRequestCode()+"");
//                getFragmentManager().popBackStack();
             //   activity.removeFragment();

                getFragmentManager().beginTransaction().remove(NumberPickerFragment.this).commit();




            }
        });
    }

    @Override
    public String toString(){
        return "The time is "+hour+ ":" + min +"\nThe temperature is :"+temp;
    }
}
