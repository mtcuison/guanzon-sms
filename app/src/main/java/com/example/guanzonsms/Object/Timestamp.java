package com.example.guanzonsms.Object;

import java.util.Calendar;
import java.util.TimeZone;

public class Timestamp {
    private static final Calendar CALENDAR_MNL = Calendar.getInstance(TimeZone.getTimeZone("Asia/Manila"));

    public static String get() {
        String lsHourxxx = String.valueOf(CALENDAR_MNL.get(Calendar.HOUR));
        String lsMinutes = String.valueOf(CALENDAR_MNL.get(Calendar.MINUTE));
        String lsSeconds = String.valueOf(CALENDAR_MNL.get(Calendar.SECOND));
        String lsAM_PMxx = String.valueOf(CALENDAR_MNL.get(Calendar.AM_PM));
        String lsMonthxx = String.valueOf(CALENDAR_MNL.get(Calendar.MONTH));
        String lsMontDay = String.valueOf(CALENDAR_MNL.get(Calendar.DAY_OF_MONTH));
        String lsYearxxx = String.valueOf(CALENDAR_MNL.get(Calendar.YEAR));

        return lsHourxxx + ":" + lsMinutes + ":" + lsSeconds + ":" + lsAM_PMxx  + " " + lsMonthxx  + "/" + lsMontDay + "/" + lsYearxxx;
    }

}
