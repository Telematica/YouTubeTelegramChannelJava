package org.telematica;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.telematica.requests.platforms.telegram.TelegramSendMessageRequest;
import org.telematica.scrappers.platforms.YouTubeLiveChannelScrapper;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Object[] vals = YouTubeLiveChannelScrapper.scrap("@telematicas");
        for (Object value : vals) {
            System.out.println(value);
        }

        Map<String, String> env = System.getenv();
        System.out.println(env.get("API_TOKEN"));
        int a = TelegramSendMessageRequest.send("1111", "dssddsds", true);
        System.out.println(a);
    }
}