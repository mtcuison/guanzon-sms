package org.guanzonsms.Object;

import java.util.Calendar;
import java.util.TimeZone;

public class Timestamp {
    private static final Calendar CALENDAR_MNL = Calendar.getInstance(TimeZone.getTimeZone("Asia/Manila"));

    public static String get() {
        String lsHourxxx = String.valueOf(CALENDAR_MNL.get(Calendar.HOUR) < 10 ? "0" + CALENDAR_MNL.get(Calendar.HOUR) : CALENDAR_MNL.get(Calendar.HOUR));
        String lsMinutes = String.valueOf(CALENDAR_MNL.get(Calendar.MINUTE) < 10 ? "0" + CALENDAR_MNL.get(Calendar.MINUTE) : CALENDAR_MNL.get(Calendar.MINUTE));
        String lsSeconds = String.valueOf(CALENDAR_MNL.get(Calendar.SECOND) < 10 ? "0" + CALENDAR_MNL.get(Calendar.SECOND) : CALENDAR_MNL.get(Calendar.SECOND));
        String lsMonthxx = String.valueOf((CALENDAR_MNL.get(Calendar.MONTH) + 1) < 10 ? "0" + CALENDAR_MNL.get(Calendar.MONTH) + 1 : CALENDAR_MNL.get(Calendar.MONTH) + 1);
        String lsMontDay = String.valueOf(CALENDAR_MNL.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + CALENDAR_MNL.get(Calendar.DAY_OF_MONTH) : CALENDAR_MNL.get(Calendar.DAY_OF_MONTH));
        String lsYearxxx = String.valueOf(CALENDAR_MNL.get(Calendar.YEAR));

        return lsHourxxx + ":" + lsMinutes + ":" + lsSeconds  + " " + lsMonthxx  + "/" + lsMontDay + "/" + lsYearxxx;
    }

}
