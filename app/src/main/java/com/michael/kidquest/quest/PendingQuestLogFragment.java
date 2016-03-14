package com.michael.kidquest.quest;

import android.app.Fragment;
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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**c
 * Created by m_por on 08/02/2016.
 */
public class PendingQuestLogFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private Context context;

    private static final String TAG = "PendingQuestLogFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_quests, container, false);

        mRecyclerView = (RecyclerView) view;
        context = view.getContext();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        CharacterService characterService = new CharacterService(view.getContext().getApplicationContext());

        ServerRestClient client = new ServerRestClient(characterService.getToken());
        String url = "users/" + characterService.getServerId() + "/quests/";
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new GsonBuilder().create();
                try {
                    List<Quest> quests = new LinkedList<Quest>(Arrays.asList(gson.fromJson(response.get("quests").toString(), Quest[].class)));

                    for (Quest q : quests) {
                        if (!q.getCompleted() || q.getConfirmed()) {
                            quests.remove(q);
                        }
                    }

                    RecyclerView.Adapter adapter = new QuestLogAdapter(quests, false);
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

        return view;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Quest quest);
    }
}
