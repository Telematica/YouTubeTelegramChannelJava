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
            String vid = data[0].toString();
            String channelId = userOrChannel[0].toString();
            String channelName = userOrChannel[1].toString();
            message = switch (type) {
                case ALREADY_NOTIFIED -> "Esta transmisión ya fue notificada: " + vid + " - " + channelName + " " + channelId;
                case NOTIFIED -> "¡Transmisión Notificada! : " + vid + " - " + channelName + " " + channelId;
                case NOT_LIVE -> "El Canal no está en vivo: " + channelName + ": " + channelId + "Programado para: ${youtubeData.scheduledStartTime}";
                case SERVER_ERROR -> "¡Hubo un Error en la Petición al Canal! " + liveRequestError;
                case TELEGRAM_MESSAGE -> "🔴 " + channelName + "está transmitiendo En Vivo! \n\n 🔗 Entra a: http://youtu.be/" + vid +"\n\n 🕒 ${ youtubeData.liveSince } \n\n 👥 Espectadores: ${new Intl.NumberFormat('es-MX', { maximumSignificantDigits: 3,}).format(youtubeData.viewCount || 0)";
            };
        } else if (Objects.equals(PLATFORMS.TIKTOK, platform)) {
            String uniqueId = data[0].toString();
            String userId = userOrChannel[0].toString();
            String roomId = userOrChannel[1].toString();
            String nickname = userOrChannel[2].toString();
            message = switch (type) {
                case ALREADY_NOTIFIED -> "Esta transmisión ya fue notificada: " + roomId + " - " + uniqueId + " - " + userId;
                case NOTIFIED -> "¡Transmisión Notificada! : " + roomId + " - " + uniqueId + " - " + userId;
                case NOT_LIVE -> "El Canal no está en vivo: " + uniqueId + " - " + userId;
                case SERVER_ERROR -> "¡Hubo un Error en la Petición al Canal! " + liveRequestError;
                case TELEGRAM_MESSAGE -> "🔴 " + nickname + "está transmitiendo En Vivo! \n\n 🔗 Entra a:  https://tiktok.com/@" + uniqueId +"\n\n 🕒 Comenzó a transmitir: () \n\n 👥 Espectadores: ${new Intl.NumberFormat('es-MX', { maximumSignificantDigits: 3,}).format(youtubeData.viewCount || 0)";
            };
        }
        return message;
    }
}
