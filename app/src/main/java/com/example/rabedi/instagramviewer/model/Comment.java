package com.example.rabedi.instagramviewer.model;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rabedi on 12/2/15.
 */
public class Comment {
    public String created;
    public String comment;
    public User user;

    public static ArrayList<Comment> parseJSON() {
        return null;
    }

    @Override
    public String toString() {
        return String.format("<strong>%s</strong> (%s): %s",
                user.username,
                DateUtils.getRelativeTimeSpanString(Long.parseLong(created) * 1000),
                comment);
    }

    public static ArrayList<Comment> parseInstagramResp(JSONObject commentsJSONObject) throws JSONException {
        ArrayList<Comment> comments = new ArrayList<>();
        JSONArray commentsJSON = commentsJSONObject.getJSONArray("data");
        for (int i = 0; i < commentsJSON.length(); i++) {
            JSONObject commentJSON = commentsJSON.getJSONObject(i);
            Comment comment = new Comment();
            comment.comment = commentJSON.getString("text");
            comment.user = User.parseInstagramResp(commentJSON.getJSONObject("from"));
            comment.created = commentJSON.getString("created_time");
            comments.add(comment);
        }
        return comments;
    }
}
