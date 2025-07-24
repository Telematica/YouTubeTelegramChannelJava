package org.telematica;

import org.telematica.constants.AppConstants;
import org.telematica.scrappers.platforms.tiktok.UserChannelScrapper;
import org.telematica.scrappers.platforms.youtube.LiveStreamPageScrapper;
import org.telematica.utils.ConsoleMessages;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class BotNotifier {
    public static void execute() throws SQLException {
        try {
            Database.connect();
            Database.connection.setAutoCommit(false);
            BotNotifier.youtubeBatch();
            BotNotifier.tiktokBatch();
            Database.connection.commit();
        } catch (RuntimeException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            Database.connection.rollback();
            throw new RuntimeException(e);
        }
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
                var ytliveData = LiveStreamPageScrapper.scrap(id);
                if (ytliveData.length == 3) {
                    System.out.print(
                            ConsoleMessages.getMessage(
                                AppConstants.PLATFORMS.YOUTUBE,
                                AppConstants.CONSOLE.NOT_LIVE,
                                ytliveData,
                                new Object[]{id, channelName},
                                    java.util.Optional.empty()
                            ) + "\n"
                    );
                } else {
                    String vid = ytliveData[4].toString();
                    String video = Database.getYouTubeLiveById(vid);
                    if (video != null) {
                        System.out.println(video);
                    }
                }
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
