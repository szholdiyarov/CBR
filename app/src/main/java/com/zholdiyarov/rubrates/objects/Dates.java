package com.zholdiyarov.rubrates.objects;

/**
 * Created by szholdiyarov on 2/17/16.
 */
public class Dates {


    public Dates(String dayOfMonth, int month, String year) {
        this.dayOfMonth = dayOfMonth;
        this.month = month;
        this.year = year;
    }


    public String getDayOfMonth() {
        if(dayOfMonth.length() == 1){
            return "0"+dayOfMonth;
        }else{
            return dayOfMonth;
        }
    }


    public int getMonth() {
        return month + 1;
    }

    public String getYear() {
        return year;
    }


    public String getDates() {
        if (getMonth() < 10) {
            return getDayOfMonth() + "/0" + getMonth() + "/" + getYear();
        }else{
            return getDayOfMonth() + "/" + getMonth() + "/" + getYear();
        }
    }


    private String dayOfMonth;
    private int month;
    private String year;


}

