package org.telematica.constants;

import java.util.*;


public interface TikTokConstants {
    String TIKTOK_URL = "https://www.tiktok.com";
    Map<String, String> TIKTOK_LIVE_HEADERS = TikTokConstants.createHeadersMap();
    static Map<String, String> createHeadersMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("authority", "www.tiktok.com");
        map.put("method", "GET");
        map.put("scheme", "https");
        map.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        // map.entry("accept-encoding", "gzip, deflate, br, zstd");
        map.put("accept-language", "en-US,en;q=0.9");
        map.put("cache-control", "max-age=0");
        map.put("cookie", System.getenv().getOrDefault("JAVA_TIKTOK_COOKIE", ""));
        map.put("priority", "u=0, i");
        map.put("sec-ch-ua", "Google Chrome\";v=\"137\", \"Chromium\";v=\"137\", \"Not/A)Brand\";v=\"24");
        map.put("sec-ch-ua-mobile", "?0");
        map.put("sec-ch-ua-platform", "\"macOS\"");
        map.put("sec-fetch-dest", "document");
        map.put("sec-fetch-mode", "navigate");
        map.put("sec-fetch-site", "same-origin");
        map.put("sec-fetch-user", "?1");
        map.put("upgrade-insecure-requests", "1");
        map.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 Safari/537.36");
        return map;
    }
}
