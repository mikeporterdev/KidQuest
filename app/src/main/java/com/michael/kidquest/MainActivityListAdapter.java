package com.michael.kidquest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michael.kidquest.model.Quest;

import java.util.List;

/**
 * Created by Michael Porter on 04/02/16.
 *
 * Builds the content of the quest cards
 */
public class MainActivityListAdapter extends RecyclerView.Adapter<MainActivityListAdapter.ViewHolder>{
    private final List<Quest> mData;

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public MainActivityListAdapter(List<Quest> data) {
        this.mData = data;
    }

    @Override
    public MainActivityListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //Create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item, null);

        //Create ViewHolder
        return new MainActivityListAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position){
        viewHolder.textViewQuestName.setText(mData.get(position).getTitle());
        viewHolder.textViewQuestDescription.setText(mData.get(position).getDescription());
        viewHolder.textViewGoldReward.setText("10gp");
        viewHolder.textViewXpReward.setText("100xp");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView textViewQuestName;
        public final TextView textViewQuestDescription;
        public final TextView textViewGoldReward;
        public final TextView textViewXpReward;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            textViewQuestName = (TextView) itemLayoutView.findViewById(R.id.activity_main_item_quest_name);
            textViewQuestDescription = (TextView) itemLayoutView.findViewById(R.id.activity_main_item_quest_desc);
            textViewGoldReward = (TextView) itemLayoutView.findViewById(R.id.activity_main_gold);
            textViewXpReward = (TextView) itemLayoutView.findViewById(R.id.activity_main_xp);
        }

    }


}
