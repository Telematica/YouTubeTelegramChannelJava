package org.telematica.constants;

import java.util.Map;

import static java.util.Map.entry;

public interface TikTokConstants {
    public final static String TIKTOK_URL = "https://www.tiktok.com";
    public final static Map<String, String> TIKTOK_LIVE_HEADERS = Map.ofEntries(
            entry("authority", "www.tiktok.com"),
            entry("method", "GET"),
            entry("scheme", "https"),
            entry("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"),
            // entry("accept-encoding", "gzip, deflate, br, zstd"),
            entry("accept-language", "en-US,en;q=0.9"),
            entry("cache-control", "max-age=0"),
            entry("cookie", System.getenv().getOrDefault("JAVA_TIKTOK_COOKIE", "")),
            entry("priority", "u=0, i"),
            entry("sec-ch-ua", "Google Chrome\";v=\"137\", \"Chromium\";v=\"137\", \"Not/A)Brand\";v=\"24"),
            entry("sec-ch-ua-mobile", "?0"),
            entry("sec-ch-ua-platform", "\"macOS\""),
            entry("sec-fetch-dest", "document"),
            entry("sec-fetch-mode", "navigate"),
            entry("sec-fetch-site", "same-origin"),
            entry("sec-fetch-user", "?1"),
            entry("upgrade-insecure-requests", "1"),
            entry("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 Safari/537.36")
    );
}
