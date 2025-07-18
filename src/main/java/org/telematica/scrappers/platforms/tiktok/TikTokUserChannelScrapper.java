package org.telematica.scrappers.platforms.tiktok;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.telematica.requests.platforms.tiktok.UserChannelRequest;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class TikTokUserChannelScrapper {
    public static Object[] scrap(String uniqueId) throws ExecutionException, InterruptedException {
        try {
            String response = UserChannelRequest.request(uniqueId);
            Document doc = Jsoup.parse(response);
            Element script = doc.getElementById("__UNIVERSAL_DATA_FOR_REHYDRATION__");
            JSONObject tiktokData = new JSONObject(Objects.requireNonNull(script).html());

            boolean live = !tiktokData
                    .getJSONObject("__DEFAULT_SCOPE__")
                    .getJSONObject("webapp.user-detail")
                    .getJSONObject("userInfo")
                    .getJSONObject("user")
                    .get("roomId")
                    .toString()
                    .isEmpty();

            if (!live) {
                return new Object[]{uniqueId,false};
            }

            String roomId = tiktokData
                    .getJSONObject("__DEFAULT_SCOPE__")
                    .getJSONObject("webapp.user-detail")
                    .getJSONObject("userInfo")
                    .getJSONObject("user")
                    .get("roomId")
                    .toString();

            String liveSince = "N/A";
            String title = "(NO TITLE)";
            int viewCount = 0;

            return new Object[]{uniqueId, roomId, true, liveSince, title, viewCount};
        } catch(ExecutionException | InterruptedException e) {
            throw new RuntimeException();
        }
    }
}
