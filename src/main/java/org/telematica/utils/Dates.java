package org.telematica.utils;

import java.util.Calendar;

public class Dates {
    public static String getYearMonthDayString() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        return (year + String.format("-%02d", month) + String.format("-%02d", day));
    }
}
