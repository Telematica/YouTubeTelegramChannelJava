package org.telematica.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Uri {
    public static String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, String.valueOf(StandardCharsets.UTF_8));
    }

    public static String toQueryParam(java.util.Map<String, Object> mapKV) throws UnsupportedEncodingException {
        List<String> list = new ArrayList<>();

        for (java.util.Map.Entry<String, Object> entry : mapKV.entrySet()) {
            list.add(entry.getKey() + "=" + Uri.encodeValue(entry.getValue().toString()));
        }

        String[] params = list.toArray(new String[0]);

        return String.join("&", params);
    }
}
