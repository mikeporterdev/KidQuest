package com.michael.kidquest.services;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.michael.kidquest.greendao.model.Quest;
import com.michael.kidquest.server.ServerRestClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


/**
 * Created by Michael Porter on 11/02/16.
 */
public class QuestService {
    private String TAG = "QuestService";
    private Context context;

    List<Quest> mQuests = null;

    public void addQuest(Quest quest) {
        sendQuestToServer(quest);
    }

    private void sendQuestToServer(Quest quest) {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("title", quest.getTitle());
            jsonParams.put("difficulty_level", quest.getDifficultyLevel());
            jsonParams.put("description", quest.getDescription());
            StringEntity entity = new StringEntity(jsonParams.toString());

            CharacterService characterService = new CharacterService(context);
            ServerRestClient client = new ServerRestClient(characterService.getToken());
            String url = "users/" + characterService.getServerId() + "/quests/";
            client.post(context, url, entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i(TAG, "Quest saved on server");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e(TAG, "Quest failed to save on server");
                }
            });

        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void completeQuest(Quest q){
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("completed", true);
            StringEntity entity = new StringEntity(jsonParams.toString());

            CharacterService characterService = new CharacterService(context);
            ServerRestClient serverRestClient = new ServerRestClient(characterService.getToken());

            String url = "users/" + characterService.getServerId() + "/quests/" + q.getId() + "/";

            serverRestClient.put(context, url, entity, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i(TAG, "Quest marked as complete on server");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i(TAG, "Unable to mark as complete on server");
                    error.printStackTrace();
                }
            });

        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void confirmQuest(Quest q){
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("confirmed", true);
            StringEntity entity = new StringEntity(jsonParams.toString());

            CharacterService characterService = new CharacterService(context);
            ServerRestClient serverRestClient = new ServerRestClient(characterService.getToken());

            String url = "users/" + characterService.getServerId() + "/quests/" + q.getId() + "/";

            serverRestClient.put(context, url, entity, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i(TAG, "Quest marked as confirmed on server");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i(TAG, "Unable to mark as confirmed on server");
                    error.printStackTrace();
                }
            });

        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private boolean validateQuest() {
        //TODO: Actually validate a quest
        return true;
    }

    public QuestService(Context context) {
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
