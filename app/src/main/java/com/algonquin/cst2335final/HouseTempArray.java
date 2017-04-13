package com.algonquin.cst2335final;

import java.util.ArrayList;

/**
 * Created by marvi on 4/12/2017.
 */

public class HouseTempArray extends ArrayList {
    private int hour;
    private int min;
    private int temp;
    private int id;
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public void setHour(int hour){
        this.hour = hour;
    }
    public void setMin(int min){
        this.min = min;
    }
    public void setTemp(int temp){
        this.temp = temp;
    }
    public int getHour(){
        return hour;
    }
    public int getMin(){
        return min;
    }
    public int getTemp(){
        return temp;
    }
}
