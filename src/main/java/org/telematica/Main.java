package org.telematica;

import java.util.concurrent.ExecutionException;
import org.telematica.scrappers.platforms.YouTubeLiveChannelScrapper;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Object[] vals = YouTubeLiveChannelScrapper.scrap("@telematicas");
        for (Object value : vals) {
            System.out.println(value);
        }
    }
}