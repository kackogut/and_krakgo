package com.kacper.krakgo.helpers

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build

import com.kacper.krakgo.KrakGoApp

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

import java.util.Calendar.*
import java.util.Calendar.MONTH

/**
 * Created by kacper on 19/01/2018.
 */

object DateHelper {
    private var sLocale: Locale? = null

    fun getDOBDialog(context: Context, listener: DatePickerDialog.OnDateSetListener): DatePickerDialog {
        val calendar = getInstance()
        calendar.set(YEAR, calendar.get(YEAR) - 18)
        val dialog = DatePickerDialog(context, listener, calendar.get(YEAR),
                calendar.get(MONTH), calendar.get(DAY_OF_MONTH))
        dialog.datePicker.maxDate = calendar.timeInMillis
        return dialog
    }

    fun formatDate(context: Context, dateToFormat: Date): String {
        val sdf = SimpleDateFormat("dd - MMMM - yyyy", DateHelper.getLoacale(context))
        return sdf.format(dateToFormat)
    }

    fun getYearDifference(dateToCompare: Date): Int {
        val currentDate = getInstance()
        val calendarToCompare = getInstance()
        calendarToCompare.time = dateToCompare
        var difference = currentDate.get(YEAR) - calendarToCompare.get(YEAR)
        if (calendarToCompare.get(MONTH) > currentDate.get(MONTH) || calendarToCompare.get(MONTH) == currentDate.get(MONTH) && calendarToCompare.get(DATE) > currentDate.get(DATE)) {
            difference--
        }
        return difference
    }

    fun getFomattedMessageDate(context: Context, dateToFormatt: Long): String {
        val currentDate = getInstance()
        val dateToCompare = getInstance()
        val sdf: SimpleDateFormat

        dateToCompare.time = Date(dateToFormatt)

        if (currentDate.get(YEAR) == dateToCompare.get(YEAR)) {
            if (currentDate.get(DAY_OF_MONTH) == dateToCompare.get(DAY_OF_MONTH)) {
                sdf = SimpleDateFormat("kk:mm", DateHelper.getLoacale(context))
            } else if (currentDate.get(WEEK_OF_MONTH) == dateToCompare.get(WEEK_OF_MONTH)) {
                sdf = SimpleDateFormat("EEE kk:mm", DateHelper.getLoacale(context))
            } else {
                sdf = SimpleDateFormat("dd MMM kk:mm", DateHelper.getLoacale(context))
            }
        } else {
            sdf = SimpleDateFormat("dd MMM YYYY", DateHelper.getLoacale(context))
        }
        return sdf.format(Date(dateToFormatt))
    }

    private fun getLoacale(context: Context): Locale {
        if (sLocale == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                sLocale = context.resources.configuration.locales.get(0)
            } else {
                sLocale = context.resources.configuration.locale
            }
        }
        return sLocale!!
    }
}
