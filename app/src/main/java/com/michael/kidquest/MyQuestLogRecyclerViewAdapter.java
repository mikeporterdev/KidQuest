package com.michael.kidquest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.michael.kidquest.model.DaoSession;
import com.michael.kidquest.model.Quest;

import java.util.List;

/**
 * Created by Michael Porter on 04/02/16.
 * <p/>
 * Builds the content of the quest cards
 */
public class MyQuestLogRecyclerViewAdapter extends RecyclerView.Adapter<MyQuestLogRecyclerViewAdapter.ViewHolder> {
    private final List<Quest> mData;

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public MyQuestLogRecyclerViewAdapter(List<Quest> data) {
        this.mData = data;
    }

    @Override
    public MyQuestLogRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_questlog, null);

        //Create ViewHolder
        return new MyQuestLogRecyclerViewAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Quest q = mData.get(position);

        viewHolder.textViewQuestName.setText(q.getTitle());
        viewHolder.textViewQuestDescription.setText(q.getDescription());
        viewHolder.textViewGoldReward.setText("10gp");
        viewHolder.textViewXpReward.setText("100xp");

        viewHolder.btnMarkAsComplete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //get clicked quest and mark as completed
                Quest q = mData.get(position);
                q.setCompleted(true);

                //save quest
                DaoSession daoSession = ((KidQuestApplication) v.getContext().getApplicationContext()).getDaoSession();
                daoSession.getQuestDao().insertOrReplace(q);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textViewQuestName;
        public final TextView textViewQuestDescription;
        public final TextView textViewGoldReward;
        public final TextView textViewXpReward;

        public final Button btnMarkAsComplete;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            textViewQuestName = (TextView) itemLayoutView.findViewById(R.id.activity_main_item_quest_name);
            textViewQuestDescription = (TextView) itemLayoutView.findViewById(R.id.activity_main_item_quest_desc);
            textViewGoldReward = (TextView) itemLayoutView.findViewById(R.id.activity_main_gold);
            textViewXpReward = (TextView) itemLayoutView.findViewById(R.id.activity_main_xp);

            btnMarkAsComplete = (Button) itemLayoutView.findViewById(R.id.btnMarkAsComplete);
        }

    }


}
