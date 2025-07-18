package org.telematica;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

import org.telematica.constants.AppConstants;
import org.telematica.requests.platforms.telegram.TelegramSendMessageRequest;
import org.telematica.scrappers.platforms.tiktok.TikTokUserChannelScrapper;
import org.telematica.scrappers.platforms.youtube.YouTubeLiveChannelScrapper;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, UnsupportedEncodingException {
        var values = YouTubeLiveChannelScrapper.scrap("@telematicas");
        for (Object value : values) {
            System.out.println(value);
        }
        // System.gc();
        values = TikTokUserChannelScrapper.scrap("@constancewashington_");
        for (Object value : values) {
            System.out.println(value);
        }

        if (false) {
            int result = TelegramSendMessageRequest.send(
                    AppConstants.TELEGRAM_CHANNEL_OR_GROUP,
                    "Prueba Java Backend.",
                    true
            );
        }

    }
}