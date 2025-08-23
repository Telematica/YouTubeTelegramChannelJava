package org.telematica.bot;

import org.telematica.Main;
import org.telematica.constants.AppConstants;
import org.telematica.requests.platforms.telegram.SendMessageRequest;
import org.telematica.scrappers.platforms.youtube.LiveStreamPageScrapper;
import org.telematica.utils.ConsoleMessages;
import org.telematica.utils.ConsoleTable;
import org.telematica.utils.Database;
import org.telematica.utils.Log;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class NotifierEnhanced {
    private static final int cores = Runtime.getRuntime().availableProcessors();
    private static final AtomicInteger sentNotifications = new AtomicInteger(0);
    private static final List<Object[]> queue = Collections.synchronizedList(new ArrayList<>());
    private static Map<String, String[]> ytChannels = null;

    private static class RunnableScrapperThread implements Runnable {
        String id;
        RunnableScrapperThread(String channelId) { id = channelId; }

        @Override
        public void run() {
            String channelName = ytChannels.get(id)[0];
            boolean disableNotification = Objects.equals(ytChannels.get(id)[1], "0") ? Boolean.FALSE: Boolean.TRUE;
            String liveRequestError = "";
            Object[] liveData = new Object[0];
            try {
                liveData = LiveStreamPageScrapper.scrap(id);
            } catch (Exception e) {
                // Unexpected error happened
                liveRequestError = e.getMessage();
                System.out.println(e.getMessage());
                Log.LOGGER.log(Level.FINE, "YouTube Scrapper error: " + e.getMessage(), e);
            }
            NotifierEnhanced.queue.add(new Object[]{
                    AppConstants.PLATFORMS.YOUTUBE.toString(),
                    new Object[]{id, channelName},
                    liveData,
                    liveData[4] != null ? "https://youtu.be/" + liveData[4] : "Not available",
                    disableNotification,
                    liveRequestError,
                    true, // notified?
            });
            System.gc();
        }
    }

    private static class RunnableNotificationThread implements Runnable {
        Object[] params;
        RunnableNotificationThread(Object[] p) { params = p; }

        @Override
        public void run() {
            try {
                Object[] channelInfo = (Object[]) params[1];
                Object[] liveData = (Object[]) params[2];
                SendMessageRequest.send(
                        AppConstants.TELEGRAM_CHANNEL_OR_GROUP,
                        ConsoleMessages.getMessage(
                                AppConstants.PLATFORMS.YOUTUBE,
                                AppConstants.CONSOLE.TELEGRAM_MESSAGE,
                                liveData,
                                channelInfo,
                                Optional.ofNullable((String) params[5])
                        ),
                        (boolean) params[4]
                );
                sentNotifications.incrementAndGet();
            } catch (Exception e) {
                // Unexpected error happened
                System.out.println(e.getMessage());
                Log.LOGGER.log(Level.FINE, "Telegram Notification error: " + e.getMessage(), e);
            }
            System.gc();
        }
    }

    public static synchronized void execute() throws SQLException, InterruptedException {
        NotifierEnhanced.youtubeScrapBatch();
        NotifierEnhanced.youtubeDbLogsBatch();
        NotifierEnhanced.youtubeNotificationBatch();
        System.out.println("Notifications sent: " + sentNotifications);
        queue.clear();
    }

    private static synchronized void youtubeDbLogsBatch() throws SQLException {
        Database.transactional(() -> {
            Object[] liveData = null;
            String vid = null;
            Object[] channelInfo = null;
            int index = 0;
            for (Object[] scrapResult : queue) {
                try {
                    liveData = (Object[]) scrapResult[2];
                    // Channel Live
                    if (Objects.equals(liveData[1], true)) {
                        vid = liveData[4].toString();
                        String video = null;
                        channelInfo = (Object[]) scrapResult[1];
                        String id = channelInfo[0].toString();
                        video = Database.getYouTubeLiveById(vid);
                        // Live is not yet notified
                        if (video == null) {
                            String liveSince = liveData[2].toString();
                            String title = liveData[3].toString();
                            String viewCount = liveData[5].toString();
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
                            scrapResult[6] = false;
                            // queue.set(index, scrapResult); // Apparently we can modify the reference directly
                        } else {
                            // Live is already notified
                            Database.createYouTubeLogEntry("1", id);
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                index++;
            }
        });
    }

    private static synchronized void youtubeNotificationBatch() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(cores);
        for (Object[] scrapResult : queue.stream().filter(item -> {
            Object[] liveData = (Object[]) item[2];
            return (boolean) liveData[1] && Objects.equals(item[6], false);
        }).collect(Collectors.toList())) {
            es.execute(new RunnableNotificationThread(scrapResult));
        }
        es.shutdown();
        boolean b = es.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
    }

    private static synchronized void youtubeScrapBatch() throws InterruptedException {
        try {
            Database.transactional(() -> {
                try {
                    NotifierEnhanced.ytChannels = Database.getAllYouTubeChannels();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

        ExecutorService es = Executors.newFixedThreadPool(cores);
        for (Map.Entry<String, String[]> channel : NotifierEnhanced.ytChannels.entrySet()) {
            es.execute(new RunnableScrapperThread(channel.getKey()));
        }
        es.shutdown();
        boolean b = es.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        if (!Main.quiet) {
            if (Main.printAsTable) {
                ConsoleTable.renderTable(NotifierEnhanced.queue);
            }
        }
    }
}
