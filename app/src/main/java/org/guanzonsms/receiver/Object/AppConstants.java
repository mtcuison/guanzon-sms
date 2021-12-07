package org.guanzonsms.receiver.Object;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AppConstants {

    // Time and Date
    public static String CURRENT_TIMESTAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().getTime());
    public static String CURRENT_DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());
    public static String CURRENT_MONTH_YEAR = new SimpleDateFormat("yyyyMM", Locale.getDefault()).format(Calendar.getInstance().getTime());
    public static String CURRENT_DATE_WORD = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
    public static String CURRENT_TIME = String.valueOf(new Timestamp(new Date().getTime()));

    public static int DataServiceID = 213;

}
