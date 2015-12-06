package com.example.rabedi.instagramviewer.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rabedi on 12/2/15.
 */
public class Caption {
    public String created;
    public String caption;
    public User from;

    @Override
    public String toString() {
        return String.format("<strong>%s</strong>: %s", from.username, caption);
    }

    public static Caption parseInstagramResp(JSONObject captionJSON) throws JSONException {
        Caption caption = new Caption();
        caption.created = captionJSON.getString("created_time");
        caption.caption = captionJSON.getString("text");
        caption.from = User.parseInstagramResp(captionJSON.getJSONObject("from"));
        return caption;
    }
}
