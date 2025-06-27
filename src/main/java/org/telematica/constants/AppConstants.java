package org.telematica.constants;

public interface AppConstants {
    String API_TOKEN = ""; // process.env.API_TOKEN
    String TELEGRAM_API_URL = "https://api.telegram.org/bot"; //
    String TELEGRAM_CHANNEL_OR_GROUP = ""; // process.env.TELEGRAM_CHANNEL_OR_GROUP
    String TELEGRAM_CHANNEL_TIKTOK = ""; // process.env.TELEGRAM_TIKTOK_CHANNEL
    public static enum CONSOLE {
        ALREADY_NOTIFIED,
        NOTIFIED,
        TELEGRAM_MESSAGE,
        NOT_LIVE,
        SERVER_ERROR
    }
    public static enum Response {
        CHANNEL_ERROR("¡Hubo un Error en la Petición al Canal!");

        private final String description;

        private Response(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}

