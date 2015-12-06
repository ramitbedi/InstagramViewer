package com.example.rabedi.instagramviewer.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rabedi on 12/2/15.
 */
public class User {

    public String username;
    public String full_name;
    public String profile_picture;

    public static User parseInstagramResp(JSONObject userJSON) throws JSONException {
        User user = new User();
        user.full_name = userJSON.getString("full_name");
        user.username = userJSON.getString("username");
        user.profile_picture = userJSON.getString("profile_picture");
        return user;
    }
}
