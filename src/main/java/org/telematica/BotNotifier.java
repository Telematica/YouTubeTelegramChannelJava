package org.telematica;

import org.telematica.scrappers.platforms.tiktok.UserChannelScrapper;
import org.telematica.scrappers.platforms.youtube.LiveStreamPageScrapper;

import java.sql.SQLException;
import java.util.Map;


public class BotNotifier {
    public static void execute() throws SQLException {
        Database.connect();
        BotNotifier.youtubeBatch();
        BotNotifier.tiktokBatch();
        Database.connection.close();
    }

    public static void youtubeBatch() {
        Map<String, String> ytChannels = null;
        try {
            ytChannels = Database.getAllYouTubeChannels();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Map.Entry<String, String> channel : ytChannels.entrySet()) {
            String id = channel.getKey();
            String channelName = channel.getValue();
            try {
                var values = LiveStreamPageScrapper.scrap(id);
                System.out.print(channelName + " : ");
                for (Object value : values) {
                    System.out.print(value + " --- ");
                }
                System.out.println(" ");
            } catch(Exception e) {
                System.out.println(e.getMessage());
                // @todo: log e.getMessage()
                // throw new RuntimeException(e);
            }

            /*
            if (false) {
                int result = SendMessageRequest.send(
                        AppConstants.TELEGRAM_CHANNEL_OR_GROUP,
                        "Prueba Java Backend.",
                        true
                );
            }
            */

            // System.gc();
        }
    }

    // @todo: mimic this.youtubeBatch functionality
    public static void tiktokBatch() {
        Map<String, String> tiktokChannels = null;

        try {
            tiktokChannels = Database.getAllTiktokUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Map.Entry<String, String> channel : tiktokChannels.entrySet()) {
            String id = channel.getKey();
            String channelName = channel.getValue();
            try {
                var values = UserChannelScrapper.scrap("@"+id);
                System.out.print(channelName + " : ");
                for (Object value : values) {
                    System.out.print(value + " --- ");
                }
                System.out.println(" ");
            } catch(Exception e) {
                System.out.println(e.getMessage());
                // throw new RuntimeException(e);
            }
        }

    }
}
