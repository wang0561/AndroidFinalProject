package com.algonquin.cst2335final;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import java.net.MalformedURLException;
import java.net.URL;
/**code are from the lab
 *Created by marvi on 3/6/2017.
 * @author Chen
 * @version 1.0
 *
 * */
public class HouseWeather extends AppCompatActivity {
    /**varibles for information*/
    protected String current, min, max, iconName;
    /**store the image*/
    Bitmap image;

    /**Override the onCreate method to start the application
     * @param savedInstanceState*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_weather_layout);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.houseProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        ForecastQuery thread = new ForecastQuery();
        thread.execute();
    }

    /**inner class for the Asynctask*/
    public class ForecastQuery extends AsyncTask<String, Integer, String> {

        /**download the online information
         * @param args
         * @return nothing*/
        @Override
        protected String doInBackground(String ... args){
            Log.i("Weather","inBackGround");
            try{
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inStream = connection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);

                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( inStream  , "UTF-8");

                int type;
                while((type = xpp.getEventType()) != XmlPullParser.END_DOCUMENT)
                {
                    if(xpp.getEventType() == XmlPullParser.START_TAG)
                    {
                        if(xpp.getName().equals("temperature") ){
                            max =xpp.getAttributeValue(null, "max");
                            publishProgress(25);
                            Thread.sleep(50);
                            min = xpp.getAttributeValue(null, "min");
                            publishProgress(50);
                            Thread.sleep(50);
                            current = xpp.getAttributeValue(null, "value");
                            publishProgress(75);
                            Thread.sleep(50);                        }
                        else if (xpp.getName().equals("weather")){
                            iconName = xpp.getAttributeValue(null, "icon");
                            publishProgress(100);
                            Thread.sleep(50);
                            Log.i("XML iconName:" , iconName );

                            if (!isFileExist( iconName + ".png")) {
                                URL imageURL = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                                Bitmap img = getImage(imageURL);
                                FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                                img.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                outputStream.flush();
                                outputStream.close();
                                FileInputStream fis = null;
                                try {
                                    fis = openFileInput(iconName + ".png");
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                image = BitmapFactory.decodeStream(fis);
                            }else {
                                FileInputStream fis = null;
                                try {
                                    fis = openFileInput(iconName + ".png");
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                image = BitmapFactory.decodeStream(fis);
                                publishProgress(100);
                            }
                        }

                    }
                    xpp.next();
                }
            }catch(Exception me){
                Log.e("AsyncTask", "Malformed URL:" + me.getMessage());
            }

            return " ";
        }
        /**
         * Check the file if it exist
         * @param fname
         * @return exists or not
         * */
        public boolean isFileExist(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();   }

        /**download the Image from website
         * @param url*/
        public Bitmap getImage(URL url) {

            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        /**the progress Bar
         * @param values*/
        public void onProgressUpdate(Integer ... values){

            ProgressBar progressBar = (ProgressBar) findViewById(R.id.houseProgressBar);
            progressBar.setProgress(values[0]);
            progressBar.setVisibility(View.VISIBLE);
        }

        /** set the Textview
         * @param result*/
        public void onPostExecute(String result){

            TextView currentTempView = (TextView)findViewById(R.id.currentTemp);
            currentTempView.setText("Current Temperature:" + current+"°C");

            TextView minTempView = (TextView)findViewById(R.id.minTemp);
            minTempView.setText("Min Temperature: " + min+"°C");

            TextView maxTempView = (TextView)findViewById(R.id.maxTemp);
            maxTempView.setText("Max Temperature: " + max+"°C");

            ImageView imageView = (ImageView)findViewById(R.id.imageWeather);
            imageView.setImageBitmap(image);

            ProgressBar progressBar = (ProgressBar) findViewById(R.id.houseProgressBar);
            progressBar.setVisibility(View.INVISIBLE);
        }

    }
}