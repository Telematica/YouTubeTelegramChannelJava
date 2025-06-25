package org.telematica;

import org.json.JSONObject;
import org.telematica.constants.AppConstants;
import org.telematica.entities.YoutubeTransmission;
import org.telematica.requests.YouTubeChannelLiveStreamRequest;

import java.util.Map;

public class Main {
    public static void main(String[] args) {

        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf(AppConstants.API_TOKEN);
        YoutubeTransmission live = new YoutubeTransmission();
        // CreateFile file;
        //file = new CreateFile();
        //file.create();

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
            System.out.format("%s=%s%n",
                    envName,
                    env.get(envName));
        }

        YouTubeChannelLiveStreamRequest.request("@telematicas");
    }
}