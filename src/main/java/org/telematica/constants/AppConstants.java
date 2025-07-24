package org.telematica.constants;

public interface AppConstants {
    String API_TOKEN = System.getenv().getOrDefault("JAVA_TELEGRAM_API_KEY", "");
    String TELEGRAM_API_URL = "https://api.telegram.org/bot"; //
    String TELEGRAM_CHANNEL_OR_GROUP = System.getenv().getOrDefault("JAVA_TELEGRAM_CHANNEL_OR_GROUP", "");
    String TELEGRAM_CHANNEL_TIKTOK = System.getenv().getOrDefault("JAVA_TELEGRAM_TIKTOK_CHANNEL", "");
    enum CONSOLE {
        ALREADY_NOTIFIED,
        NOTIFIED,
        TELEGRAM_MESSAGE,
        NOT_LIVE,
        SERVER_ERROR
    }
    enum PLATFORMS {
        TIKTOK,
        YOUTUBE
    }
    enum Response {
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

