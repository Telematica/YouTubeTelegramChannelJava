package org.telematica.constants;

import java.util.Map;
import static java.util.Map.entry;

public interface YouTubeConstants {
    final static String YOUTUBE_URL = "https://www.youtube.com";
    final static String YOUTUBE_CHANNEL_URL = "https://www.youtube.com/channel";
    final static Map<String, String> YOUTUBE_REQUEST_HEADERS = Map.ofEntries(
            entry("authority", "www.youtube.com"),
            entry("cache-control", "max-age=0"),
            entry("upgrade-insecure-requests", "1"),
            entry("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36"),
            entry("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"),
            entry("x-client-data", "CI+2yQEIorbJAQjBtskBCKmdygEI0K/KAQi8sMoBCO21ygEIjrrKARi9usoBGJq+ygE="),
            entry("sec-fetch-site", "none"),
            entry("sec-fetch-mode", "navigate"),
            entry("sec-fetch-user", "?1"),
            entry("sec-fetch-dest", "document"),
            entry("accept-language", "es-MX,es;q=0.9"),
            entry("cookie", "doc")
    );
    final static Map<String, String> YOUTUBE_LIVE_HEADERS = Map.ofEntries(
            entry("authority","www.youtube.com"),
            entry("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"),
            entry("accept-language", "es-MX,es;q=0.9"),
            entry("cookie", "VISITOR_INFO1_LIVE=T1SF4zda-Sw; LOGIN_INFO=AFmmF2swRAIgQXORz8nJ9cguaG2qu6py36elqFw-sTzAoW_5CfBVf64CIBri5r71aIMFYjs7DpFv7d_et04aYqKbzuNSmUZBRikX:QUQ3MjNmekVyMkhGZHVnUWVkWWtlcVlrLVVWdzNpQVBXSGdQdHFieU4zZlVlRHZpczl3d0thbk1pSDYzQUJQRThsNUt5c1Y0eUJQdkFiU3FyR2pvYzA0YkZEd0NWNHdiOHJRYlJVU2Y2YV9NbDNiTEZyX3hHaDBhR2N5LXlYM2p1c29nTE1WRzR3YUNZRDZYYlpITXVUVzNFR0c4aUV2MzRn; NID=511=e8eoRdiqDztPAcrEVNABQBna3mh-2RgKnbBTCwJilHeDfwYt39HKngs91EH2TdLnIIhLvhykrr9NfjKyWI_6fi2Wt_SIhwMC6Nt12Tjb5gL6qIRC-AzUuCEFJa7yoz-wRHr0adMS_F7Dnhe_JIOJvqwKutbjKL30qzfOCb03gd0; HSID=ACbp2x0R8Y-FlW6aX; SSID=AjmVi9pWIqNRc9eNf; APISID=zzpwkQNNrHxxQOO2/A8mXX1qRq7PiPGsx9; SAPISID=gcPg8jl06aSZgo6j/AR-CXtf3OtkFyGNcC; __Secure-1PAPISID=gcPg8jl06aSZgo6j/AR-CXtf3OtkFyGNcC; __Secure-3PAPISID=gcPg8jl06aSZgo6j/AR-CXtf3OtkFyGNcC; DEVICE_INFO=ChxOekU0TnpZek9UZ3lOemd6TURrMk16STFNZz09ENfA/p0GGNfA/p0G; SID=TggmYnJakhgrrN8wSHcWxCoDu2onwa8LUT7CV__Eg3HaZ0YUFH0RIwFLGxao7o4B-JEmhw.; __Secure-1PSID=TggmYnJakhgrrN8wSHcWxCoDu2onwa8LUT7CV__Eg3HaZ0YUzZlIAx_Im4HRGri2hRjD7g.; __Secure-3PSID=TggmYnJakhgrrN8wSHcWxCoDu2onwa8LUT7CV__Eg3HaZ0YU7lFU4EqsosyiO4Dxa2kNMQ.; PREF=f6=40000080&tz=America.Mexico_City&f5=30000&volume=35&f7=150&f4=4000000; YSC=zvsk7oPlMx4; SIDCC=AFvIBn-vWNIt1lMoLt_bDFJPRxDHVpXxHkYqIyG0dSZOa1wvcS_A5YnTyy73N7BfakPHAWwtXnic; __Secure-1PSIDCC=AFvIBn88ADoyc91qT6IiG3vmISae8wVBz9tSRIueyc_tofXYcsagEsavvP7e2CjsU4tIKJuuIr9_; __Secure-3PSIDCC=AFvIBn9IwUKWyXbJAwH0C8Otoe2h7URrsHhCr8xeIkb6gZpmrPSxfEza1hf2L5_EDXB5pAprXUED"),
            entry("sec-ch-ua", "\"Chromium\";v=\"110\", \"Not A(Brand\";v=\"24\", \"Google Chrome\";v=\"110\""),
            entry("sec-ch-ua-arch", "x86"),
            entry("sec-ch-ua-bitness","64"),
            entry("sec-ch-ua-full-version","110.0.5481.100"),
            entry("sec-ch-ua-full-version-list", "\"Chromium\";v=\"110.0.5481.100\", \"Not A(Brand\";v=\"24.0.0.0\", \"Google Chrome\";v=\"110.0.5481.100\""),
            entry("sec-ch-ua-mobile", "?0"),
            entry("sec-ch-ua-model", ""),
            entry("sec-ch-ua-platform", "macOS"),
            entry("sec-ch-ua-platform-version", "13.1.0"),
            entry("sec-ch-ua-wow64", "?0"),
            entry("sec-fetch-dest", "document"),
            entry("sec-fetch-mode", "navigate"),
            entry("sec-fetch-site", "none"),
            entry("sec-fetch-user", "?1"),
            entry("service-worker-navigation-preload", "true"),
            entry("upgrade-insecure-requests", "1"),
            entry("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36"),
            entry("x-client-data","CIW2yQEIorbJAQjBtskBCKmdygEIgf7KAQiWocsBCPj1zAEI9IvNAQiIjM0BCJyNzQEI0o3NAQi5kc0BCM+RzQEIjJPNAQjS4awC")
    );
}

