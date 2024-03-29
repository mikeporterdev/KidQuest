package com.michael.kidquest.reward;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.michael.kidquest.R;
import com.michael.kidquest.greendao.model.Reward;
import com.michael.kidquest.server.ServerRestClient;
import com.michael.kidquest.services.CharacterService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by m_por on 25/04/2016.
 */
public class RewardFragment extends Fragment {
    private RecyclerView mRecyclerView;

    private static final String TAG = "RewardFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rewards, container, false);

        mRecyclerView = (RecyclerView) view;
        Context context = view.getContext();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        CharacterService characterService = new CharacterService(context);
        ServerRestClient client = new ServerRestClient(characterService.getToken());

        String url = "users/" + characterService.getServerId() + "/rewards/";
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new GsonBuilder().create();
                try {
                    List<Reward> rewards = Arrays.asList(gson.fromJson(response.get("rewards").toString(), Reward[].class));

                    List<Reward> orderedRewards = new ArrayList<>();
                    for (Reward r : rewards){
                        if (!r.isCompleted()){
                            orderedRewards.add(r);
                        }
                    }

                    for (Reward r : rewards) {
                        if (r.isCompleted()){
                            orderedRewards.add(r);
                        }
                    }

                    RecyclerView.Adapter adapter = new RewardAdapter(orderedRewards);
                    mRecyclerView.setAdapter(adapter);
                    mRecyclerView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });

        return view;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Reward reward);
    }
}
