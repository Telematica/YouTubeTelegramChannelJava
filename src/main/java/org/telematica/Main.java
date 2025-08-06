package org.telematica;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import org.telematica.bot.Notifier;
import org.telematica.utils.Log;
import org.telematica.utils.SystemSettings;

public class Main {
    public static boolean quiet = false;
    public static boolean printAsTable = false;
    public static void main(String[] args) throws RuntimeException {
        try {
            Set<String> arguments = new HashSet<>(Arrays.asList(args));
            Main.quiet = arguments.contains("--quiet");
            Main.printAsTable = arguments.contains("--table");
            SystemSettings.initSettings();
            Log.setUpCustomLogger();
            Notifier.execute();
            System.out.println("All done.");
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

