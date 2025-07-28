package org.telematica;

import org.telematica.utils.Dates;
import org.telematica.utils.Log;

import java.util.logging.Level;

public class Main {

    public static void main(String[] args) throws RuntimeException {
        try {
            Log.setUpCustomLogger();
            System.out.println(Dates.getYearMonthDayString());
            BotNotifier.execute();
        } catch (Exception e) {
            Log.LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
}