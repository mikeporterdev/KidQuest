package com.michael.kidquest.quest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.michael.kidquest.KidQuestApplication;
import com.michael.kidquest.R;
import com.michael.kidquest.greendao.model.DaoSession;
import com.michael.kidquest.greendao.model.Quest;
import com.ocpsoft.pretty.time.PrettyTime;

import java.util.List;

/**
 * Created by Michael Porter on 04/02/16.
 * <p/>
 * Builds the content of the quest cards
 */
public class OpenQuestLogAdapter extends RecyclerView.Adapter<OpenQuestLogAdapter.ViewHolder> {
    private final List<Quest> mQuests;

    @Override
    public int getItemCount() {
        return mQuests.size();
    }

    public OpenQuestLogAdapter(List<Quest> quests) {
        this.mQuests = quests;
    }

    @Override
    public OpenQuestLogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quest_card, null);

        //Create ViewHolder
        return new OpenQuestLogAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Quest q = mQuests.get(position);

        viewHolder.textViewQuestName.setText(q.getTitle());
        viewHolder.textViewQuestDescription.setText(q.getDescription());
        viewHolder.textViewGoldReward.setText("10gp");
        viewHolder.textViewXpReward.setText("100xp");

        if (q.getExpiryDate() != null) {
            PrettyTime p = new PrettyTime();
            viewHolder.textViewExpiryDate.setText("Expires: " + p.format(q.getExpiryDate()));
        }

        viewHolder.btnMarkAsComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get clicked quest and mark as completed
                Quest q = mQuests.get(position);
                q.setCompleted(true);

                //save quest
                DaoSession daoSession = ((KidQuestApplication) v.getContext().getApplicationContext()).getDaoSession();
                daoSession.getQuestDao().insertOrReplace(q);

                mQuests.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textViewQuestName;
        public final TextView textViewQuestDescription;
        public final TextView textViewGoldReward;
        public final TextView textViewXpReward;
        public final TextView textViewExpiryDate;

        public final Button btnMarkAsComplete;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            textViewQuestName = (TextView) itemLayoutView.findViewById(R.id.activity_main_item_quest_name);
            textViewQuestDescription = (TextView) itemLayoutView.findViewById(R.id.activity_main_item_quest_desc);
            textViewGoldReward = (TextView) itemLayoutView.findViewById(R.id.activity_main_gold);
            textViewXpReward = (TextView) itemLayoutView.findViewById(R.id.activity_main_xp);
            textViewExpiryDate = (TextView) itemLayoutView.findViewById(R.id.questExpiry);

            btnMarkAsComplete = (Button) itemLayoutView.findViewById(R.id.btnMarkAsComplete);
        }

    }


}
