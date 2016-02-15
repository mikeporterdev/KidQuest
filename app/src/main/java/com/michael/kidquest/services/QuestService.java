package com.michael.kidquest.services;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.michael.kidquest.KidQuestApplication;
import com.michael.kidquest.greendao.model.DaoSession;
import com.michael.kidquest.greendao.model.Quest;
import com.michael.kidquest.greendao.model.QuestDao;
import com.michael.kidquest.server.ServerRestClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import de.greenrobot.dao.query.QueryBuilder;


/**
 * Created by Michael Porter on 11/02/16.
 */
public class QuestService {
    private String TAG = "QuestService";
    private Context context;

    List<Quest> mQuests = null;

    private String SERVER_ADDRESS = "http://192.168.0.160:5000";

    public void addQuest(Quest quest) {
        QuestDao qDao = getQuestDao();

        //Adding the quest now.
        quest.setDateAdded(new Date());
        //All quests start as incomplete
        quest.setCompleted(false);

        qDao.insert(quest);
        sendQuestToServer(quest);
    }

    public List<Quest> getQuestListByCompleted(boolean completed) {
        QuestDao qDao = getQuestDao();

        QueryBuilder<Quest> query = qDao.queryBuilder()
                .where(QuestDao.Properties.Completed.eq(completed));

        return query.list();
    }

    private void sendQuestToServer(Quest quest) {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("title", quest.getTitle());
            StringEntity entity = new StringEntity(jsonParams.toString());

            ServerRestClient.post(context, String.format("%s/quest", SERVER_ADDRESS), entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i(TAG, "Quest saved on server");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e(TAG, "Quest failed to save on server");
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private boolean validateQuest() {
        //TODO: Actually validate a quest
        return true;
    }

    private QuestDao getQuestDao() {
        DaoSession daoSession = ((KidQuestApplication) context).getDaoSession();
        return daoSession.getQuestDao();
    }

    public QuestService(Context context) {
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
