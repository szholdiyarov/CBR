package com.zholdiyarov.rubrates;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.zholdiyarov.rubrates.utils.CalendarUtil;

/**
 * author: szholdiyarov
 * date:17/02/2016
 * Purpose: This is main activity which start first and display list of dates to choose.
 */
public class MainActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    /* Declaration variables */
    private int numberPickerValue;
    private Button button_rate;
    private CalendarUtil calendarUtil;
    private NumberPicker numberPicker;
    private final long START_DAY = 1388534400000l; // 01/01/2014

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        button_rate = (Button) findViewById(R.id.button_rate);
        numberPicker = (NumberPicker) findViewById(R.id.numberPicker1);
        calendarUtil = new CalendarUtil(START_DAY);
        initializePicker();

        button_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRates(numberPickerValue);
            }
        });

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        numberPickerValue = newVal;
    }

    /* Initialize number picker here */
    private void initializePicker() {
        int numberOfDays = calendarUtil.getArrayOfDatesToPrint().length;

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(numberOfDays - 1); // max value is number of days.
        numberPicker.setWrapSelectorWheel(false);

        numberPicker.setDisplayedValues(calendarUtil.getArrayOfDatesToPrint());
        numberPicker.setOnValueChangedListener(this);
        numberPicker.setValue(numberOfDays);
        numberPickerValue = numberPicker.getValue();
    }

    /* Start new activity to show rates for a given date */
    private void showRates(int numberPickerValue) {
        Intent intent = new Intent(MainActivity.this, RatesActivity.class);
        intent.putExtra("key", calendarUtil.getHashMapOfDates().get(numberPickerValue).getDates());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }
}
