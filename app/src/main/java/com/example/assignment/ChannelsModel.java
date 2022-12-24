package com.example.assignment;

public class ChannelsModel {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public ChannelsModel(String name, String logo, String channelID) {
        this.name = name;
        this.logo = logo;
        this.channelID = channelID;
    }

    public ChannelsModel() {
    }

    String name,logo,channelID;
}
