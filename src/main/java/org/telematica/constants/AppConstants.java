package org.telematica.constants;

public interface AppConstants {
    final static String API_TOKEN = System.getenv().getOrDefault("JAVA_TELEGRAM_API_KEY", "");
    final static String TELEGRAM_API_URL = "https://api.telegram.org/bot"; //
    final static String TELEGRAM_CHANNEL_OR_GROUP = System.getenv().getOrDefault("JAVA_TELEGRAM_CHANNEL_OR_GROUP", "");
    final static String TELEGRAM_CHANNEL_TIKTOK = System.getenv().getOrDefault("JAVA_TELEGRAM_TIKTOK_CHANNEL", "");
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

