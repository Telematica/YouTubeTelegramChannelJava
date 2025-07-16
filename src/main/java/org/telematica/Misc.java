package org.telematica;

import org.json.JSONObject;
import org.telematica.constants.AppConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.telematica.constants.YouTubeConstants.YOUTUBE_REQUEST_HEADERS;

public class Misc {
    public static Integer fib(Integer steps) {
        if (steps <= 2)
            return 1;

        return fib(steps - 2) + fib(steps - 1);
    }

    public static void misc() {
        CreateFile.create();

        org.json.JSONObject jo = new JSONObject();
        jo.put("name", "jon doe");
        jo.put("age", "22");
        jo.put("city", 1e10);

        System.out.println(jo);

        for (int i = 1; i <= 5; i++) {
            // TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have
            // set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut
            // actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }

        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            // System.out.format("%s=%s%n",envName,env.get(envName));
        }

        // String a = LiveStreamPageRequest.request("@telematicas").join().toString();

        int[] numbers = new int[5];
        // int[] numbers2 = { 1, 2, 3, 4, 5 };
        numbers[0] = 0;

        System.out.println(Arrays.toString(numbers));
        System.out.println(AppConstants.API_TOKEN);
        System.out.println(AppConstants.CONSOLE.ALREADY_NOTIFIED.toString());
        System.out.println(AppConstants.Response.CHANNEL_ERROR.getDescription());
        System.out.println(YOUTUBE_REQUEST_HEADERS.keySet().stream()
                .map(key -> key + "=" + YOUTUBE_REQUEST_HEADERS.get(key))
                .collect(Collectors.joining(", ", "{", "}")));
        System.out.println(fib(8).toString());

        ArrayList<Integer> chars = new ArrayList<>();
        chars.add(1);
        chars.add(2);
        System.out.println(chars.size());
        try (Scanner scan = new Scanner(System.in)) {
            System.out.println("Input your name");
            String name = scan.nextLine().trim();
            System.out.println(name);
        }
    }
}
