package org.telematica.constants;

public interface AppConstants {
    String API_TOKEN = ""; // process.env.API_TOKEN
    String TELEGRAM_API_URL = "https://api.telegram.org/bot"; //
    String TELEGRAM_CHANNEL_OR_GROUP = ""; // process.env.TELEGRAM_CHANNEL_OR_GROUP
    String TELEGRAM_CHANNEL_TIKTOK = ""; // process.env.TELEGRAM_TIKTOK_CHANNEL

    /*
    public static enum RESPONSE_MESSAGES {
        CHANNEL_ERROR: "¡Hubo un Error en la Petición al Canal!",
    }
    public static enum CONSOLE {
        ALREADY_NOTIFIED: "ALREADY_NOTIFIED",
        NOTIFIED: "NOTIFIED",
        TELEGRAM_MESSAGE: "TELEGRAM_MESSAGE",
        NOT_LIVE: "NOT_LIVE",
        SERVER_ERROR: "SERVER_ERROR"
    }
    */
}

