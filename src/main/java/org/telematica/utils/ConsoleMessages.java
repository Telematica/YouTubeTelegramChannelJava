package org.telematica.utils;

import java.util.Objects;
import java.util.Optional;

import static org.telematica.constants.AppConstants.*;

public class ConsoleMessages {
    public static String getMessage(
            PLATFORMS platform,
            CONSOLE type,
            Object[] data,
            Object[] userOrChannel,
            Optional<String> liveRequestError
    ) {
        String message = "";
        if (Objects.equals(PLATFORMS.YOUTUBE, platform)) {
            String vid = data[4] != null ? data[4].toString() : "";
            String channelId = userOrChannel[0].toString();
            String channelName = userOrChannel[1].toString();
            String liveSince = data[2] != null ? data[2].toString() : "";
            String viewCount =  data[5] != null ? data[5].toString() : "";
            String scheduledStartTime = data[6] != null ? data[6].toString() : "(N/A)";
            switch (type) {
                case ALREADY_NOTIFIED:
                    message = "Esta transmisión ya fue notificada: " + vid + " - " + channelName + " " + channelId;
                    break;
                case NOTIFIED:
                    message = "¡Transmisión Notificada! : " + vid + " - " + channelName + " " + channelId;
                    break;
                case NOT_LIVE:
                    message = "El Canal no está en vivo: " + channelName + " : " + channelId + " - " + "Programado para: " + scheduledStartTime;
                    break;
                case SERVER_ERROR:
                    message = "¡Hubo un Error en la Petición al Canal! " + (liveRequestError.orElse("Unexpected error."));
                    break;
                case TELEGRAM_MESSAGE:
                    message = "🔴 ¡" + channelName + " está transmitiendo En Vivo! \n\n 🔗 Entra a: http://youtu.be/" + vid +"\n\n 🕒 " + liveSince + "\n\n 👥 Espectadores: " + viewCount;
                    break;
            };
        } else if (Objects.equals(PLATFORMS.TIKTOK, platform)) {
            String uniqueId = data[0].toString();
            String userId = userOrChannel[0].toString();
            String roomId = userOrChannel[1].toString();
            String nickname = userOrChannel[2].toString();
            switch (type) {
                case ALREADY_NOTIFIED:
                    message = "Esta transmisión ya fue notificada: " + roomId + " - " + uniqueId + " - " + userId;
                    break;
                case NOTIFIED:
                    message = "¡Transmisión Notificada! : " + roomId + " - " + uniqueId + " - " + userId;
                    break;
                case NOT_LIVE:
                    message = "El Canal no está en vivo: " + uniqueId + " - " + userId;
                    break;
                case SERVER_ERROR:
                    message = "¡Hubo un Error en la Petición al Canal! " + liveRequestError;
                    break;
                case TELEGRAM_MESSAGE:
                    message = "🔴 ¡" + nickname + "está transmitiendo En Vivo! \n\n 🔗 Entra a: https://tiktok.com/@" + uniqueId +"\n\n 🕒 Comenzó a transmitir: () \n\n 👥 Espectadores: ${new Intl.NumberFormat('es-MX', { maximumSignificantDigits: 3,}).format(youtubeData.viewCount || 0)";
                    break;
            };
        }
        return message;
    }
}
