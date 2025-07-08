package org.telematica.requests.platforms.youtube;

import static org.telematica.constants.YouTubeConstants.YOUTUBE_REQUEST_HEADERS;
import static org.telematica.constants.YouTubeConstants.YOUTUBE_URL;
import static org.telematica.constants.YouTubeConstants.YOUTUBE_CHANNEL_URL;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LiveStreamPageRequest {
    public static String request(String channelId) throws ExecutionException, InterruptedException {
        CompletableFuture<HttpResponse<String>> result;
        String channelUrl = channelId.startsWith("@")
                ? YOUTUBE_URL
                : YOUTUBE_CHANNEL_URL;
        channelUrl += "/" + channelId + "/live";
        try (HttpClient client = HttpClient.newHttpClient()) {
            List<String> headerList = new ArrayList<>();

            for (Map.Entry<String, String> entry : YOUTUBE_REQUEST_HEADERS.entrySet()) {
                headerList.add(entry.getKey());
                headerList.add(entry.getValue());
            }

            // Convert List<String> to String[]
            String[] headerArray = headerList.toArray(new String[0]);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(channelUrl))
                    .version(HttpClient.Version.HTTP_2)
                    .GET()
                    .headers(
                            headerArray
                    )
                    .build();

            result = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result.get().body();
    }
}

