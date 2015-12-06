package com.example.rabedi.instagramviewer.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rabedi on 12/3/15.
 */
public class Media {
    public String url;
    public int width;
    public int height;

    public static Media parseJSON() {
        return null;
    }

    public static Media parseInstagramResp(JSONObject mediaJSON) throws JSONException {
        Media media = new Media();
        media.url = mediaJSON.getString("url");
        media.height = mediaJSON.getInt("height");
        media.width = mediaJSON.getInt("width");
        return media;
    }
}
