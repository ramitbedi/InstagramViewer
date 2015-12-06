package com.example.rabedi.instagramviewer.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

/**
 * Created by rabedi on 12/2/15.
 */
public class InstagramView {

    public static final String BASE_URL = "https://api.instagram.com/v1/";
    public static final String POPULAR_PATH = "media/popular";
    public static final String TYPE_IMAGE = "image";


    public String type;
    public ArrayList<Comment> comments;
    public int commentCount;
    public Caption caption;
    public int likes;
    public String link;
    public Media image;
    public String created;

    public static String getPopularUrl() {
        return BASE_URL + POPULAR_PATH + "?client_id=" + "e05c462ebd86446ea48a5af73769b602";
    }

    public static ArrayList<InstagramView> parseInstagramResp(JSONObject response) throws JSONException {
        JSONArray instagramsJSON = response.getJSONArray("data");
        ArrayList<InstagramView> instagrams = new ArrayList<>();
        for (int i = 0; i < instagramsJSON.length(); i++) {
            JSONObject mediaJSON = instagramsJSON.getJSONObject(i);

            String type = mediaJSON.getString("type");
            InstagramView instagram = new InstagramView();
            instagram.type = type;
            if (mediaJSON.optJSONObject("comments") != JSONObject.NULL) {
                JSONObject commentsJSON = mediaJSON.getJSONObject("comments");
                instagram.commentCount = commentsJSON.getInt("count");
                instagram.comments = Comment.parseInstagramResp(commentsJSON);
            } else {
                instagram.commentCount = 0;
            }
            if (mediaJSON.optJSONObject("caption") != null) {
                instagram.caption = Caption.parseInstagramResp(mediaJSON.getJSONObject("caption"));
            }
            instagram.likes = mediaJSON.getJSONObject("likes").getInt("count");
            instagram.link = mediaJSON.getString("link");

            instagram.image = Media.parseInstagramResp(mediaJSON.getJSONObject("images").getJSONObject("standard_resolution"));
            instagram.created = mediaJSON.getString("created_time");
            instagrams.add(instagram);
        }
        return instagrams;
    }

    @Override
    public String toString() {
        return String.format("%s:  Caption: %s.  URL: %s", type, caption.caption, link);
    }
}


