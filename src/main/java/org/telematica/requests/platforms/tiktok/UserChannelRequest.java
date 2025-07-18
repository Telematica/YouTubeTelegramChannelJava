package org.telematica.requests.platforms.tiktok;

import org.telematica.constants.TikTokConstants;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class UserChannelRequest {
    public static String request(String channelId) throws ExecutionException, InterruptedException {
        CompletableFuture<HttpResponse<String>> result;
        String channelUrl = TikTokConstants.TIKTOK_URL + "/" + channelId;
        try (HttpClient client = HttpClient.newHttpClient()) {
            String[] headersArray = org.telematica.utils.Map.toArray(TikTokConstants.TIKTOK_LIVE_HEADERS);
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(URI.create(channelUrl))
                    .version(HttpClient.Version.HTTP_2)
                    .GET()
                    .headers(headersArray)
                    .build();
            result = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result.get().body();
    }
}
