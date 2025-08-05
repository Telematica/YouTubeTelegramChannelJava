package org.telematica.utils;

import java.util.ArrayList;

public class ConsoleTable {
    public static void renderTable(ArrayList<Object[]> entries) {
        System.out.print("\033\143");
        System.out.flush();
        // Runtime.getRuntime().exec("clear");
        System.out.format("┌──────────┬──────────────────────────────────────────────┬───────┬──────────────────────────────┐%n");
        System.out.format("│ Platform │                 Channel/User                 │ Live? │             Link             │%n");
        System.out.format("├──────────┼──────────────────────────────────────────────┼───────┼──────────────────────────────┤%n");
        String leftAlignment = "│ %-8s │ %-44s │ %-4s │ %-28s │%n";
        for(Object[] entry : entries) {
            String platform = (String) entry[0];
            String channelName = (String) entry[1];
            boolean isLive = (boolean) entry[2];
            String link = (String) entry[3];
            // Truncate channel name and link if they are too long
            // channelName = truncate(channelName, 38);
            // link = truncate(link, 22);
            System.out.format(leftAlignment, platform, channelName, isLive ? "✅" : "❌", link);
        }
        System.out.format("└──────────┴──────────────────────────────────────────────┴───────┴──────────────────────────────┘%n");
    }

    private static String truncate(String text, int maxWidth) {
        if (text.length() <= maxWidth) {
            return text;
        }
        return text.substring(0, maxWidth - 3) + "...";
    }
}