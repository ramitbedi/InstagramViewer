package com.example.rabedi.instagramviewer;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rabedi.instagramviewer.model.InstagramView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rabedi on 12/2/15.
 */
public class CustomArrayAdaptor extends ArrayAdapter {
    private static class ViewHolder {

        ImageView ivImage;
        TextView tvCaption;
        TextView tvComments;
    }

    public CustomArrayAdaptor(Context context, List<InstagramView> objects) {
        super(context, R.layout.activity_photo_child, objects);
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        InstagramView instagram = (InstagramView) getItem(position);
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_photo_child, parent, false);

            viewHolder.ivImage = (ImageView) view.findViewById(R.id.imageView);
            viewHolder.tvCaption = (TextView) view.findViewById(R.id.tvCaption);

            viewHolder.tvComments = (TextView) view.findViewById(R.id.tvComments);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (instagram.type.equals(InstagramView.TYPE_IMAGE)) {
            Picasso.with(getContext()).load(instagram.image.url).fit().centerCrop()
                    .placeholder(R.drawable.loading1).into(viewHolder.ivImage);
        }

        viewHolder.ivImage.setAdjustViewBounds(true);
        viewHolder.ivImage.setTag(R.string.type, instagram.type);
        if (instagram.type.equals(InstagramView.TYPE_IMAGE)) {
            if (instagram.image != null && instagram.image.url != null) {
                viewHolder.ivImage.setTag(R.string.url, instagram.image.url);
            } else {
                Log.d("InvalidUrl", "Invalid image url");
            }


            if (instagram.caption != null) {
                viewHolder.tvCaption.setText(Html.fromHtml(instagram.caption.toString()));
            }


            String comments = "";
            if (instagram.comments != null && instagram.comments.size() > 0) {

                comments = instagram.comments.get(instagram.comments.size() - 1).toString();
               //Display last two comments from the list
                if (instagram.comments.size() > 1) {
                    comments += "<br />";
                    comments += instagram.comments.get(instagram.comments.size() - 2).toString();
                }
            } else {
                comments += "No Comments.";
            }
            viewHolder.tvComments.setText(Html.fromHtml(comments));
        }
        return view;
    }
}