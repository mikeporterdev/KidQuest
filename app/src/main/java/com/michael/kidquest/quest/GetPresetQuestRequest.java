package com.michael.kidquest.quest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.michael.kidquest.R;
import com.michael.kidquest.greendao.model.Quest;
import com.michael.kidquest.server.ServerRestClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by m_por on 15/02/2016.
 */

public class GetPresetQuestRequest extends AppCompatActivity {
    private String TAG = "GetPresetQuestRequest";
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preset_quest);

        recyclerView = (RecyclerView) findViewById(R.id.preset_quest_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ServerRestClient.get("quest/get_staff_pick", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new GsonBuilder().create();
                try {
                    List<Quest> quests = Arrays.asList(gson.fromJson(response.get("quests").toString(), Quest[].class));
                    RecyclerView.Adapter adapter = new PresetQuestAdapter(quests, GetPresetQuestRequest.this);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing list of staff pick quests", e);
                }
            }


        });

    }

}
