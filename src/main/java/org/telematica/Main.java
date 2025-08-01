package org.telematica;

import org.telematica.bot.Notifier;
import org.telematica.utils.Log;
import org.telematica.utils.SystemSettings;

import java.util.Arrays;
import java.util.logging.Level;

public class Main {
    public static void main(String[] args) throws RuntimeException {
        try {
            SystemSettings.initSettings();
            Log.setUpCustomLogger();
            Notifier.execute();
        } catch (Exception e) {
            Log.LOGGER.log(
                    Level.SEVERE,
                    Arrays.toString(e.getStackTrace()),
                    e
            );
        }
    }
}

