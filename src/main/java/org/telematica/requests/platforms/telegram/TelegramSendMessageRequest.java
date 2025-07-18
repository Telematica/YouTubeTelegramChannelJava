package org.telematica.requests.platforms.telegram;

import org.telematica.constants.AppConstants;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class TelegramSendMessageRequest {
    public static int send(String chatId, String text, Boolean disableNotification) throws ExecutionException, InterruptedException, UnsupportedEncodingException {
        CompletableFuture<HttpResponse<String>> result;

        String Url = AppConstants.TELEGRAM_API_URL + AppConstants.API_TOKEN + "/sendMessage" + "?";

        Url += org.telematica.utils.Uri.toQueryParam(Map.ofEntries(
                Map.entry("chat_id", chatId),
                Map.entry("text", text),
                Map.entry("disableNotification", disableNotification))
        );

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(URI.create(Url))
                    .version(HttpClient.Version.HTTP_2)
                    .GET()
                    .build();
            result = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result.get().statusCode();
    }
}
