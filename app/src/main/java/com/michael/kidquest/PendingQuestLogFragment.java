package com.michael.kidquest;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michael.kidquest.greendao.model.DaoSession;
import com.michael.kidquest.greendao.model.Quest;
import com.michael.kidquest.greendao.model.QuestDao;
import com.michael.kidquest.quest.PendingQuestLogAdapter;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by m_por on 08/02/2016.
 */
public class PendingQuestLogFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_quests, container, false);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        List<Quest> pendingQuests = getQuests(context);

        recyclerView.setAdapter(new PendingQuestLogAdapter(pendingQuests));
        return view;
    }

    private List<Quest> getQuests(Context context) {
        //get list of pending quests
        DaoSession daoSession = ((KidQuestApplication) context.getApplicationContext()).getDaoSession();
        QuestDao qDao = daoSession.getQuestDao();
        QueryBuilder<Quest> query = qDao.queryBuilder().where(QuestDao.Properties.Completed.eq(true));
        return query.list();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Quest quest);
    }
}
