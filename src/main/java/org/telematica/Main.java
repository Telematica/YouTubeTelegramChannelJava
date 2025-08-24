package org.telematica;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import org.telematica.bot.Notifier;
import org.telematica.bot.NotifierEnhanced;
import org.telematica.utils.Log;
import org.telematica.utils.SystemSettings;

public class Main {
    public static boolean quiet = false;
    public static boolean printAsTable = false;
    public static boolean multiThread = false;
    public static void main(String[] args) throws RuntimeException {
        try {
            SystemSettings.initSettings();
            Log.setUpCustomLogger();
            Set<String> arguments = new HashSet<>(Arrays.asList(args));
            Main.quiet = arguments.contains("--quiet");
            Main.printAsTable = arguments.contains("--table");
            Main.multiThread = arguments.contains("--multi-thread");
            if (!Main.quiet) {
                System.out.println("Starting at: " + new Date());
                System.out.println("Processing...");
            } else {
                System.out.println("Quiet mode enable, no console output available.");
            }
            if (Main.multiThread) {
                NotifierEnhanced.execute();
            } else {
                Notifier.execute();
            }
            if (!Main.quiet) {
                System.out.println("Finished at: " + new Date());
                System.out.println("All done.");
            }
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

