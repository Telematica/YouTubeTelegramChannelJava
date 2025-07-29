package org.telematica.utils;

import java.util.Calendar;

public class Dates {
    public static String getYearMonthDayString() {
        var calendar = Calendar.getInstance();
        var year = calendar.get(Calendar.YEAR);
        var month = calendar.get(Calendar.MONTH);
        var day = calendar.get(Calendar.DATE);
        return (year + String.format("-%02d", month) + String.format("-%02d", day));
    }
}
