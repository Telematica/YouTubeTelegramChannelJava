package org.telematica;

import org.telematica.constants.AppConstants;
import org.telematica.requests.platforms.telegram.SendMessageRequest;
import org.telematica.scrappers.platforms.tiktok.UserChannelScrapper;
import org.telematica.scrappers.platforms.youtube.LiveStreamPageScrapper;
import org.telematica.utils.ConsoleMessages;
import org.telematica.utils.Log;

import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

public class BotNotifier {
    public static void execute() throws SQLException {
        Database.transactional(() -> {
            BotNotifier.youtubeBatch();
            BotNotifier.tiktokBatch();
        });
    }

    private static void youtubeBatch() {
        Map<String, String[]> ytChannels = null;

        try {
            ytChannels = Database.getAllYouTubeChannels();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String message = "";

        for (Map.Entry<String, String[]> channel : ytChannels.entrySet()) {
            String id = channel.getKey();
            String channelName = channel.getValue()[0];
            boolean disableNotification = Boolean.parseBoolean(channel.getValue()[1]);
            String vid = "";

            try {
                var ytliveData = LiveStreamPageScrapper.scrap(id);

                // Channel not live
                if (Objects.equals(ytliveData[1], false)) {
                    message = ConsoleMessages.getMessage(
                            AppConstants.PLATFORMS.YOUTUBE,
                            AppConstants.CONSOLE.NOT_LIVE,
                            ytliveData,
                            new Object[]{id, channelName},
                            java.util.Optional.empty()
                    );
                } else {
                    // Channel Live
                    vid = ytliveData[4].toString();
                    String video = Database.getYouTubeLiveById(vid);

                    // Live is not yet notified
                    if (video == null) {
                        String liveSince = ytliveData[2].toString();
                        String title = ytliveData[3].toString();
                        String viewCount = ytliveData[5].toString();
                        message = ConsoleMessages.getMessage(
                                AppConstants.PLATFORMS.YOUTUBE,
                                AppConstants.CONSOLE.NOTIFIED,
                                ytliveData,
                                new Object[]{id, channelName},
                                java.util.Optional.empty()
                        );
                        Database.createYouTubeLiveEntry(
                                vid,
                                id,
                                "1",
                                title,
                                null,
                                null,
                                null,
                                viewCount.replace(",", ""),
                                liveSince
                        );
                        Database.createYouTubeLogEntry("2", id);
                        SendMessageRequest.send(
                                    AppConstants.TELEGRAM_CHANNEL_OR_GROUP,
                                    ConsoleMessages.getMessage(
                                            AppConstants.PLATFORMS.YOUTUBE,
                                            AppConstants.CONSOLE.TELEGRAM_MESSAGE,
                                            ytliveData,
                                            new Object[]{id, channelName},
                                            java.util.Optional.empty()
                                    ),
                                disableNotification
                        );
                    } else {
                        // Live is already notified
                        message = ConsoleMessages.getMessage(
                                AppConstants.PLATFORMS.YOUTUBE,
                                AppConstants.CONSOLE.ALREADY_NOTIFIED,
                                ytliveData,
                                new Object[]{id, channelName},
                                java.util.Optional.empty()
                        );
                        Database.createYouTubeLogEntry("1", id);
                    }
                }
            } catch (Exception e) {
                // Unexpected error happened
                System.out.println(e.getMessage());
                Log.LOGGER.log(Level.FINE, "YouTube Scrapper error: " + e.getMessage(), e);
                message = ConsoleMessages.getMessage(
                        AppConstants.PLATFORMS.YOUTUBE,
                        AppConstants.CONSOLE.SERVER_ERROR,
                        new Object[]{null,null,null,null,null,null, null},
                        new Object[]{id, channelName},
                        e.getMessage().describeConstable()
                );
            }
            if (!vid.isEmpty()) {
                System.out.print("Link: " + "https://youtu.be/" + vid + " --- ");
            }
            System.out.println(message);
            System.gc();
        }
    }

    // @todo: mimic this.youtubeBatch functionality
    private static void tiktokBatch() {
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
                var values = UserChannelScrapper.scrap("@" + id);
                System.out.print(channelName + " : ");
                for (Object value : values) {
                    System.out.print(value + " --- ");
                }
                System.out.println(" ");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                // throw new RuntimeException(e);
            }
        }

    }
}
