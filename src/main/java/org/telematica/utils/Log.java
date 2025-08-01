package org.telematica.utils;

import org.telematica.Main;

import java.io.IOException;
import java.net.URL;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    public static Logger LOGGER = null;

    public static String getTargetDir() {
        URL location = Main.class.getProtectionDomain().getCodeSource().getLocation();
        return location.getFile().replace("classes/", "");
    }

    public static void setUpCustomLogger() {
        if (LOGGER != null) {
            return;
        }

        Logger logger = Logger.getLogger(Main.class.getName());
        FileHandler fh;
        String logPath = System.getenv().getOrDefault("JAVA_LOG_PATH", Log.getTargetDir());
        String fileExtension = ".log";
        String logFilePath = logPath + Dates.getYearMonthDayString() + fileExtension;

        try {
            fh = new FileHandler(logFilePath, true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.setLevel(Level.INFO); // Set the logging level
            // @todo: Rotate files
            // FileHandler fh = new FileHandler("mylogfile.%g.log", 1024 * 1024, 10);
            logger.setUseParentHandlers(false); // this stops logger to write on the console
            Log.LOGGER = logger;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
