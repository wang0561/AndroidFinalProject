/**
 * @version 1.0
 * @(#)ActivityDate.java 1.0 2017/04/19
 * this is a part of project for CST2335_010 Android final Project;
 * */
package com.algonquin.cst2335final;

/**
 * This class is autmobile FM management Fragment class which create and close the fragment activites.
 * @version 1.0
 * @author BO
 */

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class AutoFMFragment extends Fragment {

    AutoFMActivity aFMa = null;

    Context parent;
    Long id;
    String ChanlName,ChanlNo;
    Bundle bun;
    boolean isTablet;

    public AutoFMFragment() {}

    public AutoFMFragment(AutoFMActivity c) {

        this.aFMa= c;

    }

    /**
     * method onCreate create the activity
     * @param savedInstanceState is bundle
        * */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bun = getArguments();
        id = bun.getLong("ID");
        ChanlName = bun.getString("ChanlName");
        ChanlNo = bun.getString("ChanlNo");

       // isTablet = bun.getBoolean("isTablet");
    }

    /**
     * method onCreateView inflate the fragment view of the selected item
     * @param inflater is layout inflater
     *  @param container is view group
     *  @param savedInstanceState is bundle
     * */

   @Override
   public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

       View gui = inflater.inflate(R.layout.activity_auto_fmframent, null);

       TextView fmChannelNo = (TextView)gui.findViewById(R.id.autofmfrtv1);
       fmChannelNo.setText("FM Channel No: " +ChanlNo);

       TextView fmChannelName = (TextView)gui.findViewById(R.id.autofmfrtv2);

       fmChannelName.setText("FM Channel Name: " + ChanlName);

       Button deleteButton = (Button)gui.findViewById(R.id.autofmfrbtn1);
       deleteButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               if (aFMa==null){
                   Intent intent = new Intent();
                   intent.putExtra("ID" ,id);
                   getActivity().setResult(Activity.RESULT_OK, intent);

                  // getActivity().setResult(5, intent);
                   getActivity().finish();
               }
               else{
                   aFMa.deleteDb(id);

                  // cw.removeFragment();


                   aFMa.removeFrag();

               }
           }
       });
       return gui;

   }
}
