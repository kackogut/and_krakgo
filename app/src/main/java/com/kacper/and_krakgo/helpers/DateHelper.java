package com.kacper.and_krakgo.helpers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;

import com.kacper.and_krakgo.KrakGoApp;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.*;
import static java.util.Calendar.MONTH;

/**
 * Created by kacper on 19/01/2018.
 */

public class DateHelper {
    public static DatePickerDialog getDOBDialog(Context context, DatePickerDialog.OnDateSetListener listener) {
        Calendar calendar = getInstance();
        calendar.set(YEAR, calendar.get(YEAR) - 18);
        DatePickerDialog dialog = new DatePickerDialog(context, listener, calendar.get(YEAR),
                calendar.get(MONTH), calendar.get(DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        return dialog;
    }

    public static String formatDate(Date dateToFormat) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = KrakGoApp.getApplicationCtx().getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = KrakGoApp.getApplicationCtx().getResources().getConfiguration().locale;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd - MMMM - yyyy", locale);
        return sdf.format(dateToFormat);
    }

    public static int getYearDifference(Date dateToCompare){
        Calendar currentDate = getInstance();
        Calendar calendarToCompare = getInstance();
        calendarToCompare.setTime(dateToCompare);
        int difference = currentDate.get(YEAR) - calendarToCompare.get(YEAR);
        if(calendarToCompare.get(MONTH) > currentDate.get(MONTH) ||
                (calendarToCompare.get(MONTH) == currentDate.get(MONTH)
                        && calendarToCompare.get(DATE) > currentDate.get(DATE))){
            difference--;
        }
        return difference;
    }
}
