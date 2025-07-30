package org.telematica.requests.platforms.telegram;

import org.telematica.constants.AppConstants;

import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class SendMessageRequest {
    public static void send(String chatId, String text, Boolean disableNotification) {
        HttpURLConnection connection = null;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("chat_id", chatId);
            params.put("text", text);
            params.put("disableNotification", disableNotification);
            String queryParams = org.telematica.utils.Uri.toQueryParam(params);
            URL url = new URL(
                    AppConstants.TELEGRAM_API_URL + AppConstants.API_TOKEN + "/sendMessage" + "?" + queryParams
            );
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.getResponseCode();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
