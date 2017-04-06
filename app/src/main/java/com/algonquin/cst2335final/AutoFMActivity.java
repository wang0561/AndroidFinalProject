package com.algonquin.cst2335final;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.fragment;

public class AutoFMActivity extends AppCompatActivity {

    private final static String ACTIVITY_NAME = "AutoFMActivity" ;
    ListView listView;
    EditText fmChannelDesc;
    Button btnAutoSetChannel;
    ArrayList< String> autoChannelArrayList;
    Cursor autoChannelCursor;
    FrameLayout autoFrameLayout;
    boolean isAutoTablet;
    SQLiteDatabase autoChannelDB;

    AutoFMFragment autoFMFragment;
    FragmentTransaction fragTransaction;
    AutoFMChannelAdapter autoFMChannelAdapter;
    AutoDatabaseHelper autoDatabaseHelper;
    public static AutoFMActivity instance;
    FragmentManager fm;
    EditText channelName;
    //HashMap<String , String> hash;
    SeekBar autofFMTuneseekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_fm);

        instance = this;
        listView = (ListView) findViewById(R.id.autoFMListview);
        channelName = (EditText) findViewById(R.id.autoChannelNameEditText);
        btnAutoSetChannel = (Button) findViewById(R.id.autofmChSet);
        autoFMChannelAdapter = new AutoFMChannelAdapter(this);
        autoChannelArrayList = new ArrayList<>();
        refreshMessages();

        listView.setAdapter(autoFMChannelAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bun = new Bundle();
                bun.putLong("ID", l );//l is the database ID of selected item
                String message = (String)(listView.getItemAtPosition(i));

                bun.putString("Message",message);
                bun.putBoolean("isTablet",isAutoTablet);

                //step 2, if a tablet, insert fragment into FrameLayout, pass data
                if(isAutoTablet) {

                    AutoFMFragment autoFMFragment = new AutoFMFragment(AutoFMActivity.this);
                    autoFMFragment.setArguments(bun);
                    fragTransaction= getFragmentManager().beginTransaction();
                    fragTransaction.add(R.id.autoFMFramLayout, autoFMFragment).commit();

                }
                //step 3 if a phone, transition to empty Activity that has FrameLayout
                else //isPhone
                {
                    Intent intnt = new Intent(AutoFMActivity.this, AutoFMPhoneFrameLayout.class);

                    intnt.putExtra("ID" , l); //pass the Database ID to next activity
                    intnt.putExtra("Message",message);
                    startActivityForResult(intnt , 10); //go to view fragment details

                }
            }
        });

        isAutoTablet = (findViewById(R.id.autoFMFramLayout) != null); //find out if this is a phone or tablet

        autofFMTuneseekBar = (SeekBar) findViewById(R.id.autofmtuneseekBar);
        autofFMTuneseekBar.setProgress(880);
        autofFMTuneseekBar.setMax(1080);

        autofFMTuneseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 10;
                progress = progress * 10;
               // autofFMTuneseekBar.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private void refreshMessages(){

        autoDatabaseHelper = new AutoDatabaseHelper(this);
        autoChannelDB = autoDatabaseHelper.getWritableDatabase();
        autoChannelCursor = autoChannelDB.rawQuery("select Channel from AutoFMChannelTable", null);
        int rows = autoChannelCursor.getCount();
        autoChannelCursor.moveToFirst();
        autoChannelArrayList.clear();

        while(!autoChannelCursor.isAfterLast() ) {
           // hash.put(autoChannelCursor.getString(autoChannelCursor.getColumnIndex(AutoDatabaseHelper.CHANNEL_ID)),
           //         autoChannelCursor.getString(autoChannelCursor.getColumnIndex(AutoDatabaseHelper.CHANNEL_NAME)));
            autoChannelArrayList.add(autoChannelCursor.getString(autoChannelCursor.getColumnIndex(AutoDatabaseHelper.CHANNEL_NAME)));
            autoChannelCursor.moveToNext();
        }
        autoFMChannelAdapter.notifyDataSetChanged();
    }


    private class AutoFMChannelAdapter extends ArrayAdapter<String> {

        public AutoFMChannelAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {return autoChannelArrayList.size();}

        public long getItemId(int position)
        {
            autoChannelCursor.moveToPosition(position);

            return autoChannelCursor.getLong( autoChannelCursor.getColumnIndex(AutoDatabaseHelper.CHANNEL_NAME));
        }

        @Override
        public String getItem(int position) {
            return autoChannelArrayList.get(position).toString();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            // View view= new View();
            LayoutInflater inflater = AutoFMActivity.this.getLayoutInflater();

            View displayChannelInfo = null;
            displayChannelInfo = inflater.inflate(R.layout.display_fm_infor, null);
            TextView tv2 = (TextView) displayChannelInfo.findViewById(R.id.autodisochanlinfr);
            tv2.setText(getItem(position));

            return displayChannelInfo;
        }
    }
}
