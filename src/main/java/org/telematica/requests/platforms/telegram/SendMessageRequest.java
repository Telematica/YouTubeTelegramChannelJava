package org.telematica.requests.platforms.telegram;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.telematica.constants.AppConstants;

public class SendMessageRequest {
    private static final int CONNECTION_TIMEOUT = 5000; // 5 seconds
    private static final int READ_TIMEOUT = 10000; // 10 seconds

    public static void send(String chatId, String text, Boolean disableNotification) {
        HttpURLConnection connection = null;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("chat_id", chatId);
            params.put("text", text);
            params.put("disable_notification", disableNotification);
            String queryParams = org.telematica.utils.Uri.toQueryParam(params);
            URL url = new URL(
                    AppConstants.TELEGRAM_API_URL + AppConstants.API_TOKEN + "/sendMessage" + "?" + queryParams
            );
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setReadTimeout(CONNECTION_TIMEOUT);
            connection.setConnectTimeout(READ_TIMEOUT);
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
