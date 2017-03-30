package com.algonquin.cst2335final;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.ProtectionDomain;

public class FridgeActivity extends AppCompatActivity {
    protected TextView textOfFridge;
    protected TextView textOfFreezer;
    protected EditText editFridge;
    protected EditText editFreezer;
    protected Button setFridgeB;
    protected Button setFreezerB;
    protected Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);
        textOfFridge=(TextView)findViewById(R.id.fridgetextview);
        textOfFreezer=(TextView)findViewById(R.id.freezertextView);
        editFridge=(EditText)findViewById(R.id.editTextfridge);
        editFreezer=(EditText)findViewById(R.id.editTextfreezer);
        setFridgeB=(Button)findViewById(R.id.buttonsetfridge);
        setFreezerB=(Button)findViewById(R.id.buttonsetfreezer);
        saveButton=(Button)findViewById(R.id.buttonSave);

        setFreezerB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                String temp=editFreezer.getText().toString();
                textOfFreezer.setText("Freezer Temp is "+temp+" °C");
            }

        });

        setFridgeB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                String temp=editFridge.getText().toString();
                textOfFridge.setText("Fridge Temp is "+temp+" °C");
            }

        });
        saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Response", "States saved");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }

        });
    }
}
