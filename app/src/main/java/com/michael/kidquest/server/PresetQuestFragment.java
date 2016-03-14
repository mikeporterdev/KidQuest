package com.michael.kidquest.server;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.michael.kidquest.R;
import com.michael.kidquest.greendao.model.Quest;
import com.michael.kidquest.quest.PresetQuestAdapter;
import com.michael.kidquest.services.CharacterService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by m_por on 16/02/2016.
 */
public class PresetQuestFragment extends Fragment {

    private String TAG = "PresetQuestFragment";
    private RecyclerView mRecyclerView;
    private Context context;
    private ProgressBar mProgressBar;
    private TextView mErrorMessage;
    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.preset_quest_fragment, container, false);
        context = view.getContext();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.preset_quest_list);
        mProgressBar = (ProgressBar) view.findViewById(R.id.preset_quest_progress);
        mErrorMessage = (TextView) view.findViewById(R.id.preset_quest_error_message);

        mRecyclerView.setVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        Bundle bundle = this.getArguments();
        String url = bundle.getString("URL");

        CharacterService characterService = new CharacterService(this.getContext());
        ServerRestClient client = new ServerRestClient(characterService.getToken());

        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new GsonBuilder().create();
                try {
                    List<Quest> quests = Arrays.asList(gson.fromJson(response.get("quests").toString(), Quest[].class));
                    RecyclerView.Adapter adapter = new PresetQuestAdapter(quests, context);
                    mRecyclerView.setAdapter(adapter);

                    mRecyclerView.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing list of trending quests", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                mProgressBar.setVisibility(View.GONE);
                mErrorMessage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                mProgressBar.setVisibility(View.GONE);
                mErrorMessage.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

}
