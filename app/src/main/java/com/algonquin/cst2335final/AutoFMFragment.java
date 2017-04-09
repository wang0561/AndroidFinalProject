package com.algonquin.cst2335final;



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
    String message;
    Bundle bun;
    boolean isTablet;

    public AutoFMFragment() {}

    public AutoFMFragment(AutoFMActivity c) {

        this.aFMa= c;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bun = getArguments();
        id = bun.getLong("ID");
        message = bun.getString("Message");
       // isTablet = bun.getBoolean("isTablet");
    }

   @Override
   public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

       View gui = inflater.inflate(R.layout.activity_auto_fmframent, null);

       TextView messageText = (TextView)gui.findViewById(R.id.autofmfrtv1);
       messageText.setText(message);

       TextView messageID = (TextView)gui.findViewById(R.id.autofmfrtv2);

        messageID.setText(String.valueOf(id));

       Button deleteButton = (Button)gui.findViewById(R.id.autofmfrbtn1);
       deleteButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               if (aFMa==null){
                   Intent intent = new Intent();
                   intent.putExtra("id" ,id);
                   getActivity().setResult(5, intent);
                   getActivity().finish();
               }
               else{
                   //cw.deleteDbMessage(id);

                  // cw.removeFragment();


                   aFMa.removeFrag();

               }
           }
       });
       return gui;

   }
}
