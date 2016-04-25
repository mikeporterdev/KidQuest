package com.michael.kidquest.reward;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.michael.kidquest.R;

/**
 * Created by m_por on 25/04/2016.
 */
public class RewardViewHolder extends RecyclerView.ViewHolder {
    public final TextView txtRewardName;
    public final TextView txtRewardCost;
    public final Button btnAction;

    public RewardViewHolder(View itemView) {
        super(itemView);

        txtRewardName = (TextView) itemView.findViewById(R.id.small_card_reward_name);
        txtRewardCost = (TextView) itemView.findViewById(R.id.small_reward_cost);
        btnAction = (Button) itemView.findViewById(R.id.small_reward_card_btn_action);
    }
}
