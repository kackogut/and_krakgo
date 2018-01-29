package com.kacper.and_krakgo.helpers;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;

import com.kacper.and_krakgo.KrakGoApp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.*;
import static java.util.Calendar.MONTH;

/**
 * Created by kacper on 19/01/2018.
 */

public class DateHelper {
    private static Locale sLocale;

    public static DatePickerDialog getDOBDialog(Context context, DatePickerDialog.OnDateSetListener listener) {
        Calendar calendar = getInstance();
        calendar.set(YEAR, calendar.get(YEAR) - 18);
        DatePickerDialog dialog = new DatePickerDialog(context, listener, calendar.get(YEAR),
                calendar.get(MONTH), calendar.get(DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        return dialog;
    }

    public static String formatDate(Date dateToFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd - MMMM - yyyy", DateHelper.getLoacale());
        return sdf.format(dateToFormat);
    }

    public static int getYearDifference(Date dateToCompare) {
        Calendar currentDate = getInstance();
        Calendar calendarToCompare = getInstance();
        calendarToCompare.setTime(dateToCompare);
        int difference = currentDate.get(YEAR) - calendarToCompare.get(YEAR);
        if (calendarToCompare.get(MONTH) > currentDate.get(MONTH) ||
                (calendarToCompare.get(MONTH) == currentDate.get(MONTH)
                        && calendarToCompare.get(DATE) > currentDate.get(DATE))) {
            difference--;
        }
        return difference;
    }

    public static String getFomattedMessageDate(long dateToFormatt) {
        Calendar currentDate = getInstance();
        Calendar dateToCompare = getInstance();
        SimpleDateFormat sdf;

        dateToCompare.setTime(new Date(dateToFormatt));

        if (currentDate.get(YEAR) == dateToCompare.get(YEAR)) {
            if (currentDate.get(DAY_OF_MONTH) == dateToCompare.get(DAY_OF_MONTH)) {
                sdf = new SimpleDateFormat("kk:mm", DateHelper.getLoacale());
            } else if (currentDate.get(WEEK_OF_MONTH) == dateToCompare.get(WEEK_OF_MONTH)) {
                sdf = new SimpleDateFormat("EEE kk:mm", DateHelper.getLoacale());
            } else {
                sdf = new SimpleDateFormat("dd MMM kk:mm", DateHelper.getLoacale());
            }
        } else {
            sdf = new SimpleDateFormat("dd MMM YYYY", DateHelper.getLoacale());
        }
        return sdf.format(new Date(dateToFormatt));
    }

    private static Locale getLoacale() {
        if (sLocale == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                sLocale = KrakGoApp.getApplicationCtx().getResources().getConfiguration().getLocales().get(0);
            } else {
                sLocale = KrakGoApp.getApplicationCtx().getResources().getConfiguration().locale;
            }
        }
        return sLocale;
    }
}
