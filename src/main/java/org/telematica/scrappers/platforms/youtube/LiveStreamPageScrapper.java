package org.telematica.scrappers.platforms.youtube;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.telematica.requests.platforms.youtube.LiveStreamPageRequest;

public class LiveStreamPageScrapper {
    public static Object[] scrap(String channelId) throws RuntimeException {
        try {
            String response = LiveStreamPageRequest.request(channelId);
            Document doc = Jsoup.parse(response);
            String url = doc.select("link[rel=canonical]").attr("href");
            Elements scripts = doc.select("script");
            String ytInitialDataRawScript = "{}";
            String ytInitialPlayerResponseRawScript = "{}";
            String ytInitialData = "var ytInitialData = ";
            String ytInitialPlayerResponse = "var ytInitialPlayerResponse = ";

            for (Element script : scripts) {
                if (script.html().contains(ytInitialData)) {
                    ytInitialDataRawScript = script.html().replace(ytInitialData, "");
                }

                if (script.html().contains(ytInitialPlayerResponse)) {
                    ytInitialPlayerResponseRawScript = script.html()
                            .replace(ytInitialPlayerResponse, "")
                            .replaceAll("; ?var meta.*", "");
                }
            }

            JSONObject youtubeData = new JSONObject(ytInitialDataRawScript.replace(ytInitialData, ""));
            JSONObject playerResponse = new JSONObject(ytInitialPlayerResponseRawScript);
            String title = doc.select("meta[name=title]").attr("content");
            Pattern watchIdPattern = Pattern.compile("watch\\?v=(.*)");
            Matcher matcher = watchIdPattern.matcher(url);
            String vid = matcher.find() ? matcher.group(1) : null;

            boolean live = watchIdPattern.matcher(url).find()
                    && playerResponse.has("playabilityStatus")
                    && Objects.equals(playerResponse.getJSONObject("playabilityStatus").get("status"), "OK");

            if (!live) {
                String scheduledStartTime = playerResponse.has("playabilityStatus") &&
                        playerResponse.getJSONObject("playabilityStatus").get("status") == "LIVE_STREAM_OFFLINE"
                                ? playerResponse
                                        .getJSONObject("playabilityStatus")
                                        .getJSONObject("liveStreamability")
                                        .getJSONObject("liveStreamabilityRenderer")
                                        .getJSONObject("offlineSlate")
                                        .getJSONObject("liveStreamOfflineSlateRenderer")
                                        .get("scheduledStartTime")
                                        .toString()
                                : "0";
                return new Object[] { channelId, false, null, null, vid, null, scheduledStartTime };
            }

            String liveSince = youtubeData
                    .getJSONObject("contents")
                    .getJSONObject("twoColumnWatchNextResults")
                    .getJSONObject("results")
                    .getJSONObject("results")
                    .getJSONArray("contents")
                    .getJSONObject(0)
                    .getJSONObject("videoPrimaryInfoRenderer")
                    .getJSONObject("dateText")
                    .get("simpleText")
                    .toString();

            String viewCount = youtubeData
                    .getJSONObject("contents")
                    .getJSONObject("twoColumnWatchNextResults")
                    .getJSONObject("results")
                    .getJSONObject("results")
                    .getJSONArray("contents")
                    .getJSONObject(0)
                    .getJSONObject("videoPrimaryInfoRenderer")
                    .getJSONObject("viewCount")
                    .getJSONObject("videoViewCountRenderer")
                    .getJSONObject("viewCount")
                    .getJSONArray("runs")
                    .getJSONObject(0)
                    .get("text")
                    .toString();

            return new Object[] { channelId, true, liveSince, title, vid, viewCount, null };
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}