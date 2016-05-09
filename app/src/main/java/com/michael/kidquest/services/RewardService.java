package com.michael.kidquest.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.michael.kidquest.greendao.model.Reward;
import com.michael.kidquest.reward.RewardViewHolder;
import com.michael.kidquest.server.ServerRestClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by m_por on 25/04/2016.
 */
public class RewardService {
    private final String TAG = "RewardService";
    private final Context context;

    public RewardService(Context context) {
        this.context = context;
    }

    public void addReward(Reward reward){
        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("name", reward.getName());
            jsonParams.put("cost", reward.getCost());

            StringEntity entity = new StringEntity(jsonParams.toString());

            CharacterService characterService = new CharacterService(context);
            ServerRestClient client = new ServerRestClient(characterService.getToken());
            String url = "users/" + characterService.getServerId() + "/rewards/";

            client.post(context, url, entity, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i(TAG, "onSuccess: Reward Saved on server");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i(TAG, "onFailure: Reward failed to save");
                }
            });
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void completeReward(Reward reward, final RewardViewHolder viewHolder){
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("completed", true);
            StringEntity entity = new StringEntity(jsonParams.toString());

            CharacterService characterService = new CharacterService(context);
            ServerRestClient serverRestClient = new ServerRestClient(characterService.getToken());

            String url = "users/" + characterService.getServerId() + "/rewards/" + reward.getId() + "/";

            serverRestClient.put(context, url, entity, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i(TAG, "Quest marked as complete on server");
                    viewHolder.btnAction.setText("Already Purchased");
                    viewHolder.btnAction.setOnClickListener(null);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i(TAG, "Unable to mark as complete on server");
                    if (statusCode==400){
                        Toast.makeText(context, "Not enough gold", Toast.LENGTH_SHORT).show();
                    }
                    error.printStackTrace();
                }
            });

        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
