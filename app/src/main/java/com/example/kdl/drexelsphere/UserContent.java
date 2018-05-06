package com.example.kdl.drexelsphere;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserContent {

    private String userName;
    private String userMajor;
    private String userGradYear;


    public UserContent(){
        //this constructor is required
    }

    public UserContent(String userName, String userMajor, String userGradYear) {

        this.userName = userName;
        this.userMajor = userMajor;
        this.userGradYear = userGradYear;

    }

    public String getuserName() {
        return userName;
    }

    public String getuserMajor() {
        return userMajor;
    }

    public String getuserGradYear() {
        return userGradYear;
    }
}


