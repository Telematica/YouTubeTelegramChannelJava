package org.telematica.constants;

import java.util.HashMap;
import java.util.Map;

public interface YouTubeConstants {
    String YOUTUBE_URL = "https://www.youtube.com";
    String YOUTUBE_CHANNEL_URL = "https://www.youtube.com/channel";
    Map<String, String> YOUTUBE_REQUEST_HEADERS = createYouTubeRequestHeaders();
    Map<String, String> YOUTUBE_LIVE_HEADERS = createYouTubeLiveHeaders();

    static Map<String, String> createYouTubeLiveHeaders() {
        Map<String, String> map = new HashMap<>();
        map.put("authority", "www.youtube.com");
        map.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        map.put("accept-encoding", "UTF-8");
        map.put("accept-language", "es-MX,es;q=0.9");
        map.put("cookie", System.getenv().getOrDefault("JAVA_YOUTUBE_COOKIE", ""));
        map.put("sec-ch-ua", "\"Chromium\";v=\"110\", \"Not A(Brand\";v=\"24\", \"Google Chrome\";v=\"110\"");
        map.put("sec-ch-ua-arch", "x86");
        map.put("sec-ch-ua-bitness", "64");
        map.put("sec-ch-ua-full-version", "110.0.5481.100");
        map.put("sec-ch-ua-full-version-list", "\"Chromium\";v=\"110.0.5481.100\", \"Not A(Brand\";v=\"24.0.0.0\", \"Google Chrome\";v=\"110.0.5481.100\"");
        map.put("sec-ch-ua-mobile", "?0");
        map.put("sec-ch-ua-model", "");
        map.put("sec-ch-ua-platform", "macOS");
        map.put("sec-ch-ua-platform-version", "13.1.0");
        map.put("sec-ch-ua-wow64", "?0");
        map.put("sec-fetch-dest", "document");
        map.put("sec-fetch-mode", "navigate");
        map.put("sec-fetch-site", "none");
        map.put("sec-fetch-user", "?1");
        map.put("service-worker-navigation-preload", "true");
        map.put("upgrade-insecure-requests", "1");
        map.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36");
        map.put("x-client-data", "CIW2yQEIorbJAQjBtskBCKmdygEIgf7KAQiWocsBCPj1zAEI9IvNAQiIjM0BCJyNzQEI0o3NAQi5kc0BCM+RzQEIjJPNAQjS4awC");
        return map;
    }

    static Map<String, String> createYouTubeRequestHeaders() {
        Map<String, String> map = new HashMap<>();
        map.put("authority", "www.youtube.com");
        map.put("cache-control", "max-age=0");
        map.put("upgrade-insecure-requests", "1");
        map.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36");
        map.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        map.put("accept-encoding", "UTF-8");
        map.put("x-client-data", "CI+2yQEIorbJAQjBtskBCKmdygEI0K/KAQi8sMoBCO21ygEIjrrKARi9usoBGJq+ygE=");
        map.put("sec-fetch-site", "none");
        map.put("sec-fetch-mode", "navigate");
        map.put("sec-fetch-user", "?1");
        map.put("sec-fetch-dest", "document");
        map.put("accept-language", "es-MX,es;q=0.9");
        map.put("cookie", "doc");
        return map;
    }
}

