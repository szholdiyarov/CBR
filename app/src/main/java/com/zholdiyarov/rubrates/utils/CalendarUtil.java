package com.zholdiyarov.rubrates.utils;

import com.zholdiyarov.rubrates.objects.Dates;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * author: szholdiyarov
 * date:17/02/2016
 * Purpose: This is helper class to work with Calendar library.
 */
public class CalendarUtil {

    /* Variable declaration */
    private Calendar calendarBase;
    private Calendar calendarToday;
    private HashMap<Integer, Dates> hashMapOfDates = new HashMap<>(); // to retrieve date by given position
    private ArrayList<String> listOfDates = new ArrayList<>();        // to be used to print dates

    public CalendarUtil(long startDay) {
        calendarBase = Calendar.getInstance();
        calendarToday = Calendar.getInstance();

        calendarBase.setTime(new Date(startDay));
        init();
    }

    private void init() {
        int len = daysBetween(calendarBase.getTime(), calendarToday.getTime());
        for (int dayNumber = 0; dayNumber < len; dayNumber++) {
            listOfDates.add(getDateInPrintableFormat());
            hashMapOfDates.put(dayNumber, getDate());

            calendarBase.add(Calendar.DAY_OF_MONTH, 1); // add new day
        }
    }

    public String[] getArrayOfDatesToPrint() {
        return listOfDates.toArray(new String[0]);
    }

    public HashMap<Integer, Dates> getHashMapOfDates() {
        return hashMapOfDates;
    }

    private Dates getDate() {
        return new Dates(getDayInMonth(), getMonthNumberInYear(), getYear());
    }

    private String getDayNameInWeek() {
        return new SimpleDateFormat("E", Locale.ENGLISH).format(calendarBase.getTime());
    }

    private String getDayInMonth() {
        return new SimpleDateFormat("d").format(calendarBase.getTime());
    }

    private String getMonthNameInYear() {
        return new SimpleDateFormat("MMM").format(calendarBase.getTime());
    }

    private String getYear() {
        return new SimpleDateFormat("y").format(calendarBase.getTime());
    }

    private int getMonthNumberInYear() {
        return calendarBase.get(Calendar.MONTH);
    }

    private String getDateInPrintableFormat() {
        return getYear() + " " + getMonthNameInYear() + " " + getDayInMonth() + " " + getDayNameInWeek();
    }

    private int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

}