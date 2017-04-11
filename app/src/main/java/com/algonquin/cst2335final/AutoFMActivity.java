package com.algonquin.cst2335final;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.fragment;

public class AutoFMActivity extends AppCompatActivity {

    private final static String ACTIVITY_NAME = "AutoFMActivity" ;
    ListView listView;
    EditText autoChannel;
    EditText autochannelName;
    Button btnAutoAddChannel;
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

    String fmChanlName, fmChanlNo;

    boolean autoFMSwitchChecked;
    int autoVolSeekbarProgress;
    SharedPreferences autoFMsharedPref;
    SharedPreferences.Editor autoFMsharedPrefEditor;
    protected Switch autoFMMuteSwitch;
    SeekBar autofFMVolseekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_fm);
        instance = this;

        autoFMMuteSwitch = (Switch) findViewById(R.id.autfmswitch1);

        autoFMsharedPref = this.getSharedPreferences("autoLightShare", Context.MODE_PRIVATE);
        autoFMsharedPrefEditor = autoFMsharedPref.edit();

        boolean preAutoSwitchChecked = autoFMsharedPref.getBoolean("autoFMSwitch", false);
        setAutoFm (preAutoSwitchChecked) ;
        autoFMMuteSwitch.setOnCheckedChangeListener(

               /* new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                     */
                (v, b) -> {
                    // CharSequence text;
                    // int duration;
                    autoFMSwitchChecked = autoFMMuteSwitch.isChecked();
                    autoFMsharedPrefEditor.putBoolean("autoFMSwitch", autoFMSwitchChecked);
                    autoFMsharedPrefEditor.commit();
                    setAutoFm(autoFMSwitchChecked);

                });

        listView = (ListView) findViewById(R.id.autofmlistview);
        autoChannel = (EditText) findViewById(R.id.autoFMChannenNo);
        autochannelName = (EditText) findViewById(R.id.autoChannelNameEditText);
        btnAutoAddChannel = (Button) findViewById(R.id.autofmChSet);
        autoFMChannelAdapter = new AutoFMChannelAdapter(this);
        autoChannelArrayList = new ArrayList<>();
        refreshMessages();

        listView.setAdapter(autoFMChannelAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bun = new Bundle();
                bun.putLong("ID", l );//l is the database ID of selected item
               // String message = (String)(listView.getItemAtPosition(i));

                bun.putString("ChanlName",fmChanlName);
                bun.putString("ChanlNo",fmChanlNo);

                bun.putBoolean("isTablet",isAutoTablet);

                //step 2, if a tablet, insert fragment into FrameLayout, pass data
                if(isAutoTablet) {

                    removeFrag();

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
                    intnt.putExtra("ChanlName",fmChanlName);
                    intnt.putExtra("ChanlNo",fmChanlNo);
                    startActivityForResult(intnt , 10); //go to view fragment details

                }

            }
        });

        isAutoTablet = (findViewById(R.id.autoFMFramLayout) != null); //find out if this is a phone or tablet



        btnAutoAddChannel.setOnClickListener(

                (View v) -> {
                    String chanName = autochannelName.getText().toString();
                    String chan = autoChannel.getText().toString();
                    //arrayList.add(s);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("AutoChannel", chan);
                    contentValues.put("ChannelName", chanName);

                    autoChannelDB.insert("AutoFMChannelTable", null , contentValues);
                    // messageAdapter.notifyDataSetChanged();
                    autochannelName.setText("");
                    autoChannel.setText("");
                    refreshMessages();
                });

        autoVolSeekbarProgress = autoFMsharedPref.getInt("autoFMSeekbarProgress", 0);
        autofFMVolseekBar = (SeekBar) findViewById(R.id.autofmtuneseekBar);
        autofFMVolseekBar.setProgress(autoVolSeekbarProgress);
        autofFMVolseekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener()

                {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        update(autofFMVolseekBar.getRootView());
                        autoVolSeekbarProgress = progress;
                        autoFMsharedPrefEditor.putInt("autoFMSeekbarProgress", autoVolSeekbarProgress);
                        autoFMsharedPrefEditor.commit();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }


                    public void update(View v) {
                        Snackbar snackbar = Snackbar
                                .make(v, "Auto Volume  is tuned", Snackbar.LENGTH_LONG)
                                .setAction("UNDO", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Snackbar snackbar1 = Snackbar.make(view,
                                                "Volume  is stored", Snackbar.LENGTH_SHORT);
                                        snackbar1.show();
                                    }
                                });
                        snackbar.show();
                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            Bundle b = data.getExtras();

            if (requestCode == 10) {
                if (resultCode == Activity.RESULT_OK) {
                    //String result=data.getStringExtra("Message");
                    long id = (long) b.get("ID");
                    int id1 = (int) id;
                    //  deleteQuery(result);
                    //arrayList.remove(id1);
                    autoChannelDB.delete(" AutoFMChannelTable ", "FMChannelID " + "= " + id, null);
                    refreshMessages();
                   // messageAdapter.notifyDataSetChanged();
                    //deleteQuery(id);
                }
            }
        }
        catch (Exception e){
            //getFragmentManager().popBackStack();
            refreshMessages();
        }


    }//onActivityResult

    public   void deleteDb( long id ){

        autoChannelDB.delete(autoDatabaseHelper.TABLE_NAME, autoDatabaseHelper.CHANNEL_ID + "=" + id, null);
        //db.execSQL("DELETE FROM " + "Message"+ " WHERE "+" KEY_ID " +"= "+id);
        //arrayList.remove(id);
        refreshMessages();

       // removeFrag();
        /*
        messageAdapter.notifyDataSetChanged();
        Fragment fragment = getFragmentManager().findFragmentById(R.id.framelayout1);
        getFragmentManager().beginTransaction().remove(fragment).commit();
        //listView.setAdapter(messageAdapter);
        */
    }

    private void refreshMessages(){

        autoDatabaseHelper = new AutoDatabaseHelper(this);
        autoChannelDB = autoDatabaseHelper.getWritableDatabase();
        autoChannelCursor = autoChannelDB.rawQuery("select FMChannelID,AutoChannel,ChannelName from AutoFMChannelTable", null);
        int rows = autoChannelCursor.getCount();
        autoChannelCursor.moveToFirst();
        autoChannelArrayList.clear();

        while(!autoChannelCursor.isAfterLast() ) {
           // hash.put(autoChannelCursor.getString(autoChannelCursor.getColumnIndex(AutoDatabaseHelper.CHANNEL_ID)),
           //         autoChannelCursor.getString(autoChannelCursor.getColumnIndex(AutoDatabaseHelper.CHANNEL_NAME)));
            fmChanlNo = autoChannelCursor.getString(autoChannelCursor.getColumnIndex(AutoDatabaseHelper.AutoChannel));
            fmChanlName =autoChannelCursor.getString(autoChannelCursor.getColumnIndex(AutoDatabaseHelper.CHANNEL_NAME));

            String item = ""+fmChanlNo +"M " + fmChanlName;
            autoChannelArrayList.add(item);
            autoChannelCursor.moveToNext();
        }
        autoFMChannelAdapter.notifyDataSetChanged();
    }


    public void removeFrag(){

        Fragment fragment = getFragmentManager().findFragmentById(R.id.autoFMFramLayout);
        if(fragment != null)
            getFragmentManager().beginTransaction().remove(fragment).commit();
    }

    private void setAutoFm(boolean autoLightSwitchStatus){
        CharSequence text;
        int duration;

        autoFMMuteSwitch.setChecked(autoLightSwitchStatus);

        Toast toast = Toast.makeText(AutoFMActivity.this, "Switch is "+ (autoLightSwitchStatus?"On":"Off"), Toast.LENGTH_LONG);
        toast.show();
    }


    private class AutoFMChannelAdapter extends ArrayAdapter<String> {

        public AutoFMChannelAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {return autoChannelArrayList.size();}

        public long getItemId(int position)
        {
            autoChannelCursor.moveToPosition(position);

            return autoChannelCursor.getLong( autoChannelCursor.getColumnIndex(AutoDatabaseHelper.CHANNEL_ID));
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
