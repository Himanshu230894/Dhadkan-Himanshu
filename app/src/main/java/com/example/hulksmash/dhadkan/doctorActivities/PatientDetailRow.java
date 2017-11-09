package com.example.hulksmash.dhadkan.doctorActivities;

import android.util.Log;

/**
 * Created by hulksmash on 10/9/17.
 */


public class PatientDetailRow {
    public String date_date, date_month, date_year, time_hour, time_min, time_period, weight, heart_rate, systolic, diastolic;


    PatientDetailRow(String date, String time, String weight, String heart_rate, String systolic, String diastolic) {
        this.date_date = get_date_date(date);
        this.date_month = get_date_month(date);
        this.date_year = get_date_year(date);
        this.time_hour = get_time_hour(time);
        this.time_min = get_time_min(time);
        this.time_period = get_time_period(time);
        this.weight = weight;
        this.heart_rate = heart_rate;
        this.systolic = systolic;
        this.diastolic = diastolic;
    }

    private String get_time_period(String time) {
        if (Integer.parseInt(time.split(":")[0]) > 12) {
            return "P.M";
        } else {
            return "A.M";
        }
    }

    private String get_time_min(String time) {
        return "" + time.split(":")[1];
    }

    private String get_time_hour(String time) {
        Log.d("TAG", time.toString());
        if (Integer.parseInt(time.split(":")[0]) > 12) {
            int hr_int = Integer.parseInt(time.split(":")[0]) - 12;
            return "" + hr_int;
        } else {
            return "" + time.split(":")[0];
        }
    }

    private String get_date_year(String date) {
        return date.split("-")[0];
    }

    private String get_date_month(String date) {
        String[] months = {"", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int mon_int = Integer.parseInt(date.split("-")[1]);
        return months[mon_int];
    }

    private String get_date_date(String date) {
        return date.split("-")[2];
    }
}
