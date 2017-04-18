package com.algonquin.cst2335final;
/**
 * Created by Min Luo, Version 1.0, April 02, 2017
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * This class manage fragment message
 */
public class LivingRoomMessageDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room_message_details);// load corresponded XML

        //get message from other activity
        Intent i = getIntent();
        int itemid = i.getIntExtra("ItemID",0);

        //display fragement content according to ItemID which might be choosed by user
        switch(itemid){
            case 0:
                // load fragment1 ( lamp1 ) in Tablet
                Lamp1Activity frag1 = new Lamp1Activity();
                Bundle bun = getIntent().getExtras();
                frag1.setArguments(bun);
                getSupportFragmentManager().beginTransaction().add(R.id.livingroomfragmentHolder, frag1).commit();// find fragment layout in XML
                break;
            case 1:
                // load fragment2 ( lamp2 ) in Tablet
                Lamp2Activity frag2 = new Lamp2Activity();
                bun = getIntent().getExtras();
                frag2.setArguments(bun);
                getSupportFragmentManager().beginTransaction().add(R.id.livingroomfragmentHolder, frag2).commit(); // find fragment layout in XML
                break;
            case 2:
                // load fragment3( lamp3 ) in Tablet
                Lamp3Activity frag3 = new Lamp3Activity();
                bun = getIntent().getExtras();
                frag3.setArguments(bun);
                getSupportFragmentManager().beginTransaction().add(R.id.livingroomfragmentHolder,frag3).commit(); // find fragment layout in XML
                break;
            case 3:
                // load fragment4 ( TV ) in Tablet
                TVActivity frag4 = new TVActivity();
                bun = getIntent().getExtras();
                frag4.setArguments(bun);
                getSupportFragmentManager().beginTransaction().add(R.id.livingroomfragmentHolder,frag4).commit(); // find fragment layout in XML
                break;
            case 4:
                // load fragment5 ( Blinds ) in Tablet
                BlindsActivity frag5 = new BlindsActivity();
                bun = getIntent().getExtras();
                frag5.setArguments(bun);
                getSupportFragmentManager().beginTransaction().add(R.id.livingroomfragmentHolder,frag5).commit(); // find fragment layout in XML
                break;

        }
    }
}
