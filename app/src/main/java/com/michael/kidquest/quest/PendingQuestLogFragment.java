package com.michael.kidquest.quest;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michael.kidquest.R;
import com.michael.kidquest.greendao.model.Quest;
import com.michael.kidquest.services.QuestService;

import java.util.List;

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

        QuestService qService = new QuestService(view.getContext().getApplicationContext());
        List<Quest> quests = qService.getQuestListByCompleted(true);

        recyclerView.setAdapter(new PendingQuestLogAdapter(quests));
        return view;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Quest quest);
    }
}
