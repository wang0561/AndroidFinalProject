package com.algonquin.cst2335final;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class KitchenActivity extends AppCompatActivity {

    protected ListView listView;
    protected String[] kitchenItems =new String[]{"MicroWave","Samsung Fridge","Main Ceiling Light"};
    protected Button returnButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);
        listView=(ListView)findViewById(R.id.theList);
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.kitchen_row, kitchenItems ));
        returnButton=(Button)findViewById(R.id.kitchenReturn);
        returnButton.setOnClickListener(e->{
            startActivityForResult(new Intent(KitchenActivity.this, StartActivity.class),5);
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                switch(position)
                {
                    case 0: //Microwave
                        startActivityForResult(new Intent(KitchenActivity.this, MicrowaveActivity.class),5);
                        break;
                    case 1: //fridge
                        //Start the Screen_Two activity, with 10 as the result code

                        startActivityForResult(new Intent(KitchenActivity.this, FridgeActivity.class),5);
                        break;
                    case 2: //light
                        startActivityForResult(new Intent(KitchenActivity.this, KitchenLightActivity.class ), 5);
                        break;

                }
            }
        });
    }
}
