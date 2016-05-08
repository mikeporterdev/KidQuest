package com.michael.kidquest.quest;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.michael.kidquest.R;
import com.michael.kidquest.greendao.model.Quest;
import com.michael.kidquest.server.ServerRestClient;
import com.michael.kidquest.services.CharacterService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**c
 * Created by m_por on 08/02/2016.
 */
public class PendingQuestLogFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private Context context;

    private static final String TAG = "PendingQuestLogFragment";
    private CharacterService characterService;
    private ServerRestClient client;
    private RecyclerView.Adapter mAdapter;

    private List<Quest> mQuests;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_quests, container, false);

        mRecyclerView = (RecyclerView) view;
        context = view.getContext();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        characterService = new CharacterService(view.getContext().getApplicationContext());
        client = new ServerRestClient(characterService.getToken());

        characterService = new CharacterService(context.getApplicationContext());
        client = new ServerRestClient(characterService.getToken());

        getQuests();

        return view;
    }

    private void getQuests() {
        String url = "users/" + characterService.getServerId() + "/quests/";
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create();
                try {
                    List<Quest> quests = Arrays.asList(gson.fromJson(response.get("quests").toString(), Quest[].class));

                    List<Quest> pendingQuests = new ArrayList<>();

                    for (Quest q : quests) {
                        if (!q.getConfirmed() && q.getCompleted()) {
                            pendingQuests.add(q);
                        }
                    }

                    mQuests = pendingQuests;

                    RecyclerView.Adapter adapter = new QuestLogAdapter(pendingQuests, false);
                    mRecyclerView.setAdapter(adapter);

                    mRecyclerView.setVisibility(View.VISIBLE);
                    //mProgressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing list of trending quests", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //mProgressBar.setVisibility(View.GONE);
                //mErrorMessage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                //mProgressBar.setVisibility(View.GONE);
                //mErrorMessage.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void update() {

        mQuests.clear();

        String url = "users/" + characterService.getServerId() + "/quests/";
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create();
                try {
                    List<Quest> quests = Arrays.asList(gson.fromJson(response.get("quests").toString(), Quest[].class));

                    List<Quest> pendingQuests = new ArrayList<>();

                    for (Quest q : quests) {
                        if (!q.getConfirmed() && q.getCompleted()) {
                            pendingQuests.add(q);
                        }
                    }

                    mQuests = pendingQuests;

                    mAdapter.notifyDataSetChanged();

                    mRecyclerView.setVisibility(View.VISIBLE);
                    //mProgressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing list of trending quests", e);
                }
            }
        });

        mAdapter = new QuestLogAdapter(mQuests, false);
        mRecyclerView.setAdapter(mAdapter);
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Quest quest);
    }
}
