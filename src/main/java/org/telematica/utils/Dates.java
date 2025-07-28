package org.telematica.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

public class Dates {
    public static String getYearMonthDayString() {
        var calendar = Calendar.getInstance();
        var year = calendar.get(Calendar.YEAR);
        var month = calendar.get(Calendar.MONTH);
        var day = calendar.get(Calendar.DATE);
        return (
                year + "-" +
                String.format("%-" + 2 + "s", month) +
                "-" + String.format("%-" + 2 + "s", day)
        );
    }
}
