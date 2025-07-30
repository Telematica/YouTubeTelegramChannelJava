package org.telematica.requests.platforms.tiktok;

import  org.telematica.constants.TikTokConstants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class UserChannelRequest {
    public static String request(String channelId) throws ExecutionException, InterruptedException {
        HttpURLConnection connection = null;
        String channelUrl = TikTokConstants.TIKTOK_URL + "/" + channelId;
        StringBuilder response = null;

        try {
            URL url = new URL(channelUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);

            for(Map.Entry<String, String> header : TikTokConstants.TIKTOK_LIVE_HEADERS.entrySet()) {
                connection.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;
                response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                System.out.println("POST request did not work. " + responseCode);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Objects.requireNonNull(response).toString();
    }
}
