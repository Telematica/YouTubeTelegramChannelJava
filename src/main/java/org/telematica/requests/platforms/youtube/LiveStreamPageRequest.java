package org.telematica.requests.platforms.youtube;

import org.telematica.constants.YouTubeConstants;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class LiveStreamPageRequest {
    private static String channelId;
    public static CompletableFuture<HttpResponse<String>> request(String channelId) {
        LiveStreamPageRequest.channelId = channelId;
        CompletableFuture<HttpResponse<String>> result;
        String channelUrl = channelId.startsWith("@")
                ? YouTubeConstants.YOUTUBE_URL + "/" + channelId + "/live"
                : YouTubeConstants.YOUTUBE_CHANNEL_URL + "/" + channelId + "/live";
        try (HttpClient client = HttpClient.newHttpClient()) {
            // @todo: Add headers
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(channelUrl))
                    //.headers(HttpHeaders.of())
                    .build();

            result = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

