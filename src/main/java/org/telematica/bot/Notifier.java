package org.telematica.bot;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;

import org.telematica.Main;
import org.telematica.constants.AppConstants;
import org.telematica.requests.platforms.telegram.SendMessageRequest;
import org.telematica.scrappers.platforms.tiktok.UserChannelScrapper;
import org.telematica.scrappers.platforms.youtube.LiveStreamPageScrapper;
import org.telematica.utils.ConsoleMessages;
import org.telematica.utils.ConsoleTable;
import org.telematica.utils.Database;
import org.telematica.utils.Log;

public class Notifier {
    private static final ArrayList<Object[]> entries = new ArrayList<>();
    
    public static void execute() throws SQLException {
        Database.transactional(() -> {
            Notifier.youtubeBatch();
            Notifier.tiktokBatch();
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
            boolean isLive = false;

            try {
                Object[] liveData = LiveStreamPageScrapper.scrap(id);

                // Channel not live
                if (Objects.equals(liveData[1], false)) {
                    message = ConsoleMessages.getMessage(
                            AppConstants.PLATFORMS.YOUTUBE,
                            AppConstants.CONSOLE.NOT_LIVE,
                            liveData,
                            new Object[]{id, channelName},
                            java.util.Optional.empty()
                    );
                } else {
                    // Channel Live
                    vid = liveData[4].toString();
                    String video = Database.getYouTubeLiveById(vid);
                    isLive = true;

                    // Live is not yet notified
                    if (video == null) {
                        String liveSince = liveData[2].toString();
                        String title = liveData[3].toString();
                        String viewCount = liveData[5].toString();
                        message = ConsoleMessages.getMessage(
                                AppConstants.PLATFORMS.YOUTUBE,
                                AppConstants.CONSOLE.NOTIFIED,
                                liveData,
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
                                            liveData,
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
                                liveData,
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
                        Optional.ofNullable(e.getMessage())
                );
            }
            
            entries.add(new Object[]{
                AppConstants.PLATFORMS.YOUTUBE.toString(),
                channelName,
                isLive,
                !"".equals(vid) ? "https://youtu.be/" + vid : "Not available"
            });

            if (!Main.quiet) {
                if (Main.printAsTable) {
                    ConsoleTable.renderTable(Notifier.entries);
                } else {
                    if (!vid.isEmpty()) {
                        System.out.print("Link: " + "https://youtu.be/" + vid + " --- ");
                    }
                    System.out.println(message);
                }
            }

            System.gc();
        }
    }

    private static void tiktokBatch() {
        Map<String, String[]> tiktokChannels = null;

        try {
            tiktokChannels = Database.getAllTiktokUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String message = "";

        for (Map.Entry<String, String[]> channel : tiktokChannels.entrySet()) {
            String id = channel.getKey();
            String channelName = channel.getValue()[0];
            String uniqueId = channel.getValue()[1];
            String roomId = "";
            boolean isLive = false;

            try {
                Object[] liveData = UserChannelScrapper.scrap("@" + uniqueId);
                isLive = (boolean) liveData[2];

                if (!isLive) {
                    message = ConsoleMessages.getMessage(
                            AppConstants.PLATFORMS.TIKTOK,
                            AppConstants.CONSOLE.NOT_LIVE,
                            liveData,
                            new Object[]{uniqueId, channelName},
                            java.util.Optional.empty()
                    );
                } else {
                    // User Live
                    roomId = liveData[1].toString();
                    String room = Database.getTikTokLiveById(roomId);

                    // Live is not yet notified
                    if (room == null) {
                        String liveSince = liveData[3].toString();
                        String title = liveData[4].toString();
                        // String viewCount = liveData[5].toString();
                        message = ConsoleMessages.getMessage(
                                AppConstants.PLATFORMS.TIKTOK,
                                AppConstants.CONSOLE.NOTIFIED,
                                liveData,
                                new Object[]{uniqueId, channelName},
                                java.util.Optional.empty()
                        );
                        Database.createTikTokLiveEntry(
                                roomId,
                                id,
                                "1",
                                title,
                                null,
                                null,
                                null,
                                "0",
                                liveSince
                        );
                        Database.createTikTokLogEntry("2", id);
                        SendMessageRequest.send(
                                AppConstants.TELEGRAM_CHANNEL_TIKTOK,
                                ConsoleMessages.getMessage(
                                        AppConstants.PLATFORMS.TIKTOK,
                                        AppConstants.CONSOLE.TELEGRAM_MESSAGE,
                                        liveData,
                                        new Object[]{id, channelName},
                                        java.util.Optional.empty()
                                ),
                                false
                        );
                    } else {
                        // Live is already notified
                        message = ConsoleMessages.getMessage(
                                AppConstants.PLATFORMS.TIKTOK,
                                AppConstants.CONSOLE.ALREADY_NOTIFIED,
                                liveData,
                                new Object[]{id, channelName},
                                java.util.Optional.empty()
                        );
                        Database.createTikTokLogEntry("1", id);
                    }
                }

            } catch (Exception e) {
                // Unexpected error happened
                // System.out.println(e.getMessage());
                Log.LOGGER.log(Level.WARNING, "TikTok Scrapper error: " + e.getMessage());
                message = ConsoleMessages.getMessage(
                        AppConstants.PLATFORMS.TIKTOK,
                        AppConstants.CONSOLE.SERVER_ERROR,
                        new Object[]{null,null,null,null,null,null, null},
                        new Object[]{uniqueId, channelName},
                        Optional.ofNullable(e.getMessage())
                );
            }
            entries.add(new Object[]{
                AppConstants.PLATFORMS.TIKTOK.toString(),
                channelName,
                isLive,
                isLive ? "https://tiktok.com/@" + channelName + "/live" : "Not available"
            });

            if (!Main.quiet) {
                if (Main.printAsTable) {
                    ConsoleTable.renderTable(Notifier.entries);
                } else {
                    if (!roomId.isEmpty()) {
                        System.out.print("Link: " + "https://tiktok.com/@" + channelName + "/live");
                    }
                    System.out.println(message);
                }
            }

            System.gc();
        }
    }
}
