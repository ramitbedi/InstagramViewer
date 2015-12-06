package com.example.rabedi.instagramviewer;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.rabedi.instagramviewer.model.InstagramView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotoMainActivity extends AppCompatActivity {
    private ArrayList<InstagramView> stream;
    private CustomArrayAdaptor customAdapter;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Swipe Refresh
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchInstagramPhotos();
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        stream = new ArrayList<>();
        customAdapter = new CustomArrayAdaptor(this, stream);
        ListView lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(customAdapter);
        fetchInstagramPhotos();

    }

    public void fetchInstagramPhotos() {

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(InstagramView.getPopularUrl(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.d("INFO:","Success");
                    stream = InstagramView.parseInstagramResp(response);
                } catch (Exception ex) {
                    Log.d("Instagram JSONException", "JSON Exception: " + ex.getMessage());
                    ex.printStackTrace();
                }
                customAdapter.clear();
                customAdapter.addAll(stream);
                customAdapter.notifyDataSetChanged();
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("Instagram Network", "Failed  Status: " + statusCode);
                 super.onFailure(statusCode, headers, t,e);

            }


        });
    }
}
