package com.algonquin.cst2335final;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLivingRoom = (Button) findViewById(R.id.livingroombutton);
        Button btnKitchen = (Button) findViewById(R.id.kitchenbutton);
        Button btnHouse = (Button) findViewById(R.id.housebutton);
        Button btnAuto = (Button) findViewById(R.id.autobutton);

        btnLivingRoom.setOnClickListener(e->{

            Intent intent = new Intent(MainActivity.this, LivingRoomActivity.class);
            startActivity(intent);
            startActivityForResult(intent, 5);

        });

        btnKitchen.setOnClickListener(e->{

            Intent intent = new Intent(MainActivity.this, KitchenActivity.class);
            startActivity(intent);
            startActivityForResult(intent, 5);

        });

        btnHouse.setOnClickListener(e->{

            Intent intent = new Intent(MainActivity.this, HouseActivity.class);
            startActivity(intent);
            startActivityForResult(intent, 5);

        });

        btnAuto.setOnClickListener(e->{

            Intent intent = new Intent(MainActivity.this, AutoActivity.class);
            startActivity(intent);
            startActivityForResult(intent, 5);

        });


    }
}
