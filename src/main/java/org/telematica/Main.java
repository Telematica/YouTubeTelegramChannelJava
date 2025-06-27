package org.telematica;

import java.util.Map;

import org.json.JSONObject;
import org.telematica.constants.AppConstants;
import org.telematica.entities.YoutubeTransmission;
import org.telematica.requests.platforms.youtube.LiveStreamPageRequest;

public class Main {
    public static void main(String[] args) {
        YoutubeTransmission live = new YoutubeTransmission();

        CreateFile.create();

        org.json.JSONObject jo = new JSONObject();
        jo.put("name", "jon doe");
        jo.put("age", "22");
        jo.put("city", 1e10);

        System.out.println(jo);

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }

        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            // System.out.format("%s=%s%n",envName,env.get(envName));
        }

        String a = LiveStreamPageRequest.request("@telematicas").join().toString();

        int[] numbers = new int[5];
        int[] numbers2 = {1,2,3,4,5};
        numbers[0] = 0;

        System.out.println(numbers);
        System.out.println(AppConstants.API_TOKEN);
        System.out.println(AppConstants.CONSOLE.ALREADY_NOTIFIED.toString());
        System.out.println(AppConstants.Response.CHANNEL_ERROR.getDescription());
        System.out.println(a);
    }
}