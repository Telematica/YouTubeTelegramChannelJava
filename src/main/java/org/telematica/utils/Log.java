package org.telematica.utils;

import org.telematica.Main;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    public static Logger LOGGER = null;

    public static void setUpCustomLogger() {
        if(LOGGER != null) {
            return;
        }

        Logger logger = Logger.getLogger(Main.class.getName());
        FileHandler fh;

        try {
            fh = new FileHandler(
                    "/Users/dealersocket/YouTubeTelegramChannelJava/target/MyLogFile.log",
                    true
            );
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.setLevel(Level.INFO); // Set the logging level
            // @todo: Rotate files
            // FileHandler fh = new FileHandler("mylogfile.%g.log", 1024 * 1024, 10);

            Log.LOGGER = logger;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
