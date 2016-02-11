package com.michael.kidquest.services;

import android.content.Context;

import com.michael.kidquest.KidQuestApplication;
import com.michael.kidquest.greendao.model.DaoSession;
import com.michael.kidquest.greendao.model.Quest;
import com.michael.kidquest.greendao.model.QuestDao;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Michael Porter on 11/02/16.
 */
public class QuestService {
    private Context context;

    public List<Quest> getQuestListByCompleted(boolean completed){
        DaoSession daoSession = ((KidQuestApplication) context).getDaoSession();
        QuestDao qDao = daoSession.getQuestDao();

        QueryBuilder<Quest> query = qDao.queryBuilder()
                .where(QuestDao.Properties.Completed.eq(completed));

        return query.list();
    }

    public QuestService(Context context) {
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
