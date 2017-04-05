package com.algonquin.cst2335final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LivingRoomMessageDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room_message_details);

        Intent i = getIntent();
        int itemid = i.getIntExtra("ItemID",0);

        switch(itemid){
            case 0:
                Lamp1Activity frag1 = new Lamp1Activity();
                Bundle bun = getIntent().getExtras();
                frag1.setArguments(bun);
                getSupportFragmentManager().beginTransaction().add(R.id.livingroomfragmentHolder, frag1).commit();
                break;
            case 1:
                Lamp2Activity frag2 = new Lamp2Activity();
                bun = getIntent().getExtras();
                frag2.setArguments(bun);
                getSupportFragmentManager().beginTransaction().add(R.id.livingroomfragmentHolder, frag2).commit();
                break;
            case 2:
                Lamp3Activity frag3 = new Lamp3Activity();
                bun = getIntent().getExtras();
                frag3.setArguments(bun);
                getSupportFragmentManager().beginTransaction().add(R.id.livingroomfragmentHolder,frag3).commit();
                break;
            case 3:
                TVActivity frag4 = new TVActivity();
                bun = getIntent().getExtras();
                frag4.setArguments(bun);
                getSupportFragmentManager().beginTransaction().add(R.id.livingroomfragmentHolder,frag4).commit();
                break;
            case 4:
                BlindsActivity frag5 = new BlindsActivity();
                bun = getIntent().getExtras();
                frag5.setArguments(bun);
                getSupportFragmentManager().beginTransaction().add(R.id.livingroomfragmentHolder,frag5).commit();
                break;

        }
    }
}
