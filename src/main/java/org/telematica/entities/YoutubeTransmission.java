package org.telematica.entities;

/**
 * YouTube Transmission Data Object
 * Please see the {@link com.baeldung.javadoc.Person} class for true identity
 * @author @telematica
 */
public class YoutubeTransmission {
    public YoutubeTransmission() {}
    private String id = "";
    private String startTimestamp = "";
    private String channelId = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(String startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
