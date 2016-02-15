package com.michael.kidquest.quest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.michael.kidquest.R;
import com.michael.kidquest.greendao.model.Quest;

import java.util.List;

/**
 * Created by m_por on 15/02/2016.
 */
public class PresetQuestAdapter extends RecyclerView.Adapter<PresetQuestAdapter.ViewHolder> {
    private List<Quest> mQuests;
    private Context mContext;

    public PresetQuestAdapter(List<Quest> mQuests, Context mContext) {
        this.mQuests = mQuests;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.small_quest_card, null);
        return new PresetQuestAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Quest q = mQuests.get(position);
        holder.textViewQuestName.setText(q.getTitle());
        holder.textViewQuestDifficulty.setText(q.getDifficultyLevel().toString());

        holder.btnAction.setText("Add Quest");
        holder.btnAction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("quest", mQuests.get(position));
                GetPresetQuestRequest activity = (GetPresetQuestRequest) mContext;
                activity.setResult(1, returnIntent);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mQuests.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textViewQuestName;
        private final TextView textViewQuestDifficulty;
        private final TextView btnAction;

        public ViewHolder(View itemLayoutView){
            super(itemLayoutView);
            textViewQuestName = (TextView) itemLayoutView.findViewById(R.id.small_card_quest_name);
            textViewQuestDifficulty = (TextView) itemLayoutView.findViewById(R.id.small_card_difficulty);
            btnAction = (Button) itemLayoutView.findViewById(R.id.small_card_btn_action);
        }
    }
}
