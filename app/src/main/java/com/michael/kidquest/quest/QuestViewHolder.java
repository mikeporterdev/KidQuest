package com.michael.kidquest.quest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.michael.kidquest.R;

/**
 * Created by m_por on 15/02/2016.
 */
public class QuestViewHolder extends RecyclerView.ViewHolder {
    public final TextView txtQuestName;
    public final TextView txtQuestDescription;
    public final TextView txtGoldReward;
    public final TextView txtXpReward;
    public final TextView txtExpiryDate;
    public final View viewDivider;

    public final Button btnAction;

    public QuestViewHolder(View itemLayoutView) {
        super(itemLayoutView);

        txtQuestName = (TextView) itemLayoutView.findViewById(R.id.activity_main_item_quest_name);
        txtQuestDescription = (TextView) itemLayoutView.findViewById(R.id.activity_main_item_quest_desc);
        txtGoldReward = (TextView) itemLayoutView.findViewById(R.id.activity_main_gold);
        txtXpReward = (TextView) itemLayoutView.findViewById(R.id.activity_main_xp);
        txtExpiryDate = (TextView) itemLayoutView.findViewById(R.id.questExpiry);
        viewDivider = (View) itemLayoutView.findViewById(R.id.divider);

        btnAction = (Button) itemLayoutView.findViewById(R.id.btnAction);
    }

}
