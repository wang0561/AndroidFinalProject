
/**
 * @version 1.0
 * @(#)ActivityDate.java 1.0 2017/04/19
 * this is a part of project for CST2335_010 Android final Project;
 * */
package com.algonquin.cst2335final;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * This class is autmobile temperature dispaly management class
 * @version 1.0
 * @author BO
 */

public class AutoTempDisplay extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "AutoTempDisplay";

    Context ctx = this;
    ListView autoTempListView;
    ArrayList<String> autoTempArrayList;
    ImageView autoTempWeatherImage;
    ProgressBar autoTempWeatherProgressBar;
    Bitmap autoTempImageBitmap;
    AutoChatAdapter autoMessageAdapter;
    String tempLow, tempHigh;


    /**
     * method onCreate creates activity
     *  @param savedInstanceState is bundle
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_temp_display);

        autoTempWeatherImage = (ImageView) findViewById(R.id.autotempimageView1);
        autoTempWeatherProgressBar = (ProgressBar) findViewById(R.id.autotempprogressBar1);

        autoTempWeatherProgressBar.setVisibility(View.VISIBLE);
        autoTempWeatherProgressBar.setMax(100);

        Bundle bun = getIntent().getExtras();
        tempLow = bun.getString("tempLow");
        tempHigh =bun.getString("tempHigh");

        ForecastQuery thread = new ForecastQuery();
        thread.execute("a", "b", "c");

    }

    /**
     * the inner  class  ForecastQuery is subclass of AsyncTask to  run activity in the back ground
     *
     * @version 1.0
     * @author BO
     */
    private class ForecastQuery extends AsyncTask<String, Integer, String[]> {

        String minTemp, maxTemp, currentTemp, iconName;
        String[] result = new String[4];

        /**
         * method doInBackground run activity in the backgroud
         *  @param args is string variable
         * */
        @Override
        protected String[] doInBackground(String... args) {

            String urlString = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";

            HttpURLConnection conn;
            InputStream inputStream;
            try {

                URL url = new URL(urlString);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                inputStream = conn.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);

                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(inputStream, "UTF-8");

                Log.i("XML parsing:", "" + xpp.getEventType());
                int type;
                while ((type = xpp.getEventType()) != XmlPullParser.END_DOCUMENT) {
                    if (xpp.getEventType() == XmlPullParser.START_TAG) {
                        if (xpp.getName().equals("temperature")) {
                            currentTemp = xpp.getAttributeValue(null, "value");
                            publishProgress(25);
                            minTemp = xpp.getAttributeValue(null, "min");
                            publishProgress(50);
                            maxTemp = xpp.getAttributeValue(null, "max");
                            publishProgress(75);

                            Log.i("XML minTemp:", minTemp);
                            Log.i("XML maxTemp:", maxTemp);

                        } else if (xpp.getName().equalsIgnoreCase("weather")) {

                            iconName = "01n";//xpp.getAttributeValue(null, "icon");
                            Log.i("XML iconName:", iconName);

                            String fileName = iconName + ".png";
                            File file = getBaseContext().getFileStreamPath(fileName);

                            boolean fileExists = fileExistance(file);
                            if (!fileExists) {

                                Log.i("ASYNCTASK", "Image File not found , loading it from the web site");
                                String ImageURL = "http://openweathermap.org/img/w/" + iconName + ".png";
                                autoTempImageBitmap = HttpUtils.getImage(ImageURL);

                                try {
                                    FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                                    autoTempImageBitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    outputStream.flush();
                                    outputStream.close();

                                } catch (Exception e) {

                                    Log.i("ASYNCTASK", "");

                                }
                            } else {

                                Log.i("ASYNCTASK ", "Image File found locally");
                                FileInputStream fis = null;
                                try {
                                    fis = new FileInputStream(file);
                                    autoTempImageBitmap = BitmapFactory.decodeStream(fis);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }


                            }
                            publishProgress(100);

                        }
                    }
                    xpp.next();
                }

            } catch (Exception e) {

            }

            result[0] = "Current oustside temperature: " + currentTemp;
            result[1] = "Today's lowest temperature: " +minTemp;
            result[2] = "Today's highest temperature: " +maxTemp;
            result[3] = iconName;

           // Log.i(ACTIVITY_NAME,currentTemp);

            return result;
        }

        /**
         * method onProgressUpdate updae the  activity progress
         *  @param progress is string variable
         * */
        public void onProgressUpdate(Integer... progress) {
            //  outputText.setText("Progress:" + progress[0]);


            autoTempWeatherProgressBar.setProgress(progress[0]);


            Log.i("ASYNCTASK Onprogress", "" + progress[0]);
        }

        /**
         * method onPostExecute updae the  activity progress
         *  @param value is string variable
         * */
        public void onPostExecute(String... value) {

            autoTempListView = (ListView) findViewById(R.id.autotemplistview1);
            autoMessageAdapter = new AutoChatAdapter(ctx);
            //autoTempListView.setAdapter(autoMessageAdapter);

            autoTempArrayList = new ArrayList<>();
            for (int i=0; i < value.length-1;i++) autoTempArrayList.add(value[i]);
            autoTempArrayList.add("Lowest temperature you set: " +tempLow);

            autoTempArrayList.add("Highest temperature you set: "+ tempHigh);

            autoTempListView.setAdapter(autoMessageAdapter);



            // outputText.setText("Work finished");

            //tvCurrentTemperature.setText("Current Temperature " + value[0]);
            //tvMinTemperature.setText("Min Temperature " +value[1] );
            //tvMaxTemperature.setText("Max Temperature " +value[2]);

            autoTempWeatherImage.setImageBitmap(autoTempImageBitmap);
           // autoTempWeatherProgressBar.setVisibility(View.INVISIBLE);



            Log.i("ASYNCTASK PostExecute:", "Completed");
        }

        /**
         * method fileExistance verify if  the file exists
         *  @param file is file variable
         * */
        private boolean fileExistance(File file) {

            return file.exists();
        }
    }

    /**
     * the inner  class  AutoChatAdapter is to instantiate the adapter for the list view
     * fragment
     * @version 1.0
     * @author BO
     */
    private class AutoChatAdapter extends ArrayAdapter<String> {

        public AutoChatAdapter(Context ctx) {
            super(ctx, 0);
        }
        public int getCount() {return autoTempArrayList.size();}

        @Override
        public String getItem(int position) {
            return autoTempArrayList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            // View view= new View();
            LayoutInflater inflater = AutoTempDisplay.this.getLayoutInflater();

            View   tempList = inflater.inflate(R.layout.temp_list_auto, null);

            TextView tv1 = (TextView) tempList.findViewById(R.id.autotemptextview2);
            tv1.setText(getItem(position));


            return tempList;
        }


        }

}
