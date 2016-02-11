package com.michael.kidquest.services;

import android.content.Context;

import com.michael.kidquest.KidQuestApplication;
import com.michael.kidquest.greendao.model.DaoSession;
import com.michael.kidquest.greendao.model.Quest;
import com.michael.kidquest.greendao.model.QuestDao;

import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Michael Porter on 11/02/16.
 */
public class QuestService {
    private Context context;

    public void addQuest(Quest quest){
        QuestDao qDao = getQuestDao();

        //Adding the quest now.
        quest.setDateAdded(new Date());
        //All quests start as incomplete
        quest.setCompleted(false);

        qDao.insert(quest);
    }

    public List<Quest> getQuestListByCompleted(boolean completed){
        QuestDao qDao = getQuestDao();

        QueryBuilder<Quest> query = qDao.queryBuilder()
                .where(QuestDao.Properties.Completed.eq(completed));

        return query.list();
    }

    private boolean validateQuest(){
        return true;
    }

    private QuestDao getQuestDao(){
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
