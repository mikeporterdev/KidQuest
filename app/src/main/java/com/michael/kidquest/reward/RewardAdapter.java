package com.michael.kidquest.reward;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michael.kidquest.R;
import com.michael.kidquest.greendao.model.Reward;
import com.michael.kidquest.services.CharacterService;
import com.michael.kidquest.services.RewardService;

import java.util.List;

/**
 * Created by Michael Porter on 04/02/16.
 * <p/>
 * Builds the content of the quest cards
 */
public class RewardAdapter extends RecyclerView.Adapter<RewardViewHolder> {
    private final List<Reward> mRewards;
    private CharacterService characterService;

    public RewardAdapter(List<Reward> rewards, boolean isOpen) {
        this.mRewards = rewards;
    }

    @Override
    public RewardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reward_card, null);
        return new RewardViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(RewardViewHolder viewHolder, final int position) {
        Reward reward = mRewards.get(position);

        viewHolder.txtRewardName.setText(reward.getName());
        viewHolder.txtRewardCost.setText(String.valueOf(reward.getCost()));

        characterService = new CharacterService(viewHolder.txtRewardName.getContext());

        if (!characterService.isParent()){
            viewHolder.btnAction.setText("Purchase");
            viewHolder.btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Reward r = mRewards.get(position);
                    RewardService rewardService = new RewardService(v.getContext().getApplicationContext());
                    rewardService.completeReward(r);
                }
            });
        } else {
            viewHolder.btnAction.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mRewards.size();
    }
}
