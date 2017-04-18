package com.algonquin.cst2335final;

import java.util.ArrayList;

/**
 * a custom arraylist which contain the information of the temperature
 * Created by marvi on 4/12/2017.
 * @author Chen
 *
 */


public class HouseTempArray extends ArrayList {
    /**store the value of hour*/
    private int hour;
    /**store the value of min*/
    private int min;
    /**store the value of temperature*/
    private int temp;
    /**store the value of id of the database*/
    private int id;

    /**setter of the id
     * @param id*/
    public void setId(int id){
        this.id = id;
    }
    /**
     * getter of the id
     * @return id
     * */
    public int getId(){
        return id;
    }
    /**
     * setter of the hour
     * @param hour*/
    public void setHour(int hour){
        this.hour = hour;
    }
    /**setter of the minute
     * @param min*/
    public void setMin(int min){
        this.min = min;
    }
    /**setter of the temperature
     * @param temp*/
    public void setTemp(int temp){
        this.temp = temp;
    }

    /**getter of the hour
     * @return hour
     * */
    public int getHour(){
        return hour;
    }
    /**getter of the min
     * @return min*/
    public int getMin(){
        return min;
    }
    /**getter of the tempertaure
     * @return temp*/
    public int getTemp(){
        return temp;
    }
}
