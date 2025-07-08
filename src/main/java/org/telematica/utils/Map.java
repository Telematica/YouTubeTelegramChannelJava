package org.telematica.utils;

import java.util.ArrayList;
import java.util.List;

public class Map {
    public static String[] MapToArray(java.util.Map<String, String> mapKV) {
        List<String> list = new ArrayList<>();

        for (java.util.Map.Entry<String, String> entry : mapKV.entrySet()) {
            list.add(entry.getKey());
            list.add(entry.getValue());
        }

        return list.toArray(new String[0]);
    }
}
