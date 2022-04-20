package com.example.PickBeforeGo.components;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.PickBeforeGo.activities.AdminFormActivity;

import java.util.Calendar;

public class CalendarPicker {
    public static DatePickerDialog datePickerDialog;

    public static void initDatePicker(AdminFormActivity mainActivity, Button dateButton) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                month = month + 1;
                String date = makeDateString(year, month, day);
                System.out.println("i was called" + day + month + year);
                dateButton.setText(date);
                AdminFormActivity.restockDay = String.valueOf(year);
                AdminFormActivity.restockMonth = String.valueOf(month);
                AdminFormActivity.restockYear = String.valueOf(day);
            }
        };

        Calendar calendarIns = Calendar.getInstance();
        int year = calendarIns.get(Calendar.YEAR);
        int month = calendarIns.get(Calendar.MONTH);
        int day = calendarIns.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;
        datePickerDialog = new DatePickerDialog(mainActivity, style, dateSetListener, year, month, day);
    }

    private static String makeDateString(int day, int month, int year) {
        return day + " " + getMonthFormat(month) + " " + year;
    }

    private static String getMonthFormat(int month)   {
        switch(month) {
            case 1: return "JAN";
            case 2: return "FEB";
            case 3: return "MAR";
            case 4: return "APR";
            case 5: return "MAY";
            case 6: return "JUN";
            case 7: return "JUL";
            case 8: return "AUG";
            case 9: return "SEP";
            case 10: return "OCT";
            case 11: return "NOV";
            case 12: return "DEC";
            default: throw new IllegalStateException("Unexpected value: " + month);
        }
    }

    public static String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int year = cal.get(Calendar.YEAR);
        return makeDateString(day, month, year);
    }

    public static String[] getTodayInit() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int year = cal.get(Calendar.YEAR);
        return new String[]{String.valueOf(day), getMonthFormat(month), String.valueOf(year)};
    }
}