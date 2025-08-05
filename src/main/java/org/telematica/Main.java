package org.telematica;

import java.util.Arrays;
import java.util.logging.Level;

import org.telematica.bot.Notifier;
import org.telematica.utils.Log;
import org.telematica.utils.SystemSettings;

public class Main {
    public static void main(String[] args) throws RuntimeException {
        try {
            SystemSettings.initSettings();
            Log.setUpCustomLogger();
            Notifier.execute();
            System.exit(0);
        } catch (Exception e) {
            Log.LOGGER.log(
                    Level.SEVERE,
                    Arrays.toString(e.getStackTrace()),
                    e
            );
        }
    }
}

