package com.example.kdl.drexelsphere;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class EventContent {

    private String eventName;
    private String eventTime;
    private String eventDetail;
    private String userID;
    private String userEmail;

    public EventContent(){
        //this constructor is required
    }

    public EventContent(String eventName, String eventTime, String eventDetail, String userID, String userEmail) {
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.eventDetail = eventDetail;
        this.userID = userID;
        this.userEmail = userEmail;
    }

    public String geteventName() {
        return eventName;
    }

    public String geteventTime() {
        return eventTime;
    }

    public String geteventDetail() {
        return eventDetail;
    }

    public String getuserID() {
        return userID;
    }

    public String getuserEmail() {
        return userEmail;
    }
}

