package com.michael.kidquest.quest;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.michael.kidquest.DialogSingleButtonListener;
import com.michael.kidquest.R;
import com.michael.kidquest.greendao.model.Quest;
import com.michael.kidquest.services.CharacterService;

import java.util.List;

/**
 * Created by m_por on 08/02/2016.
 */
public class PendingQuestLogAdapter extends RecyclerView.Adapter<PendingQuestLogAdapter.ViewHolder> {
    private final List<Quest> mQuests;

    public PendingQuestLogAdapter(List<Quest> quests){
        mQuests = quests;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quest_card, null);
        return new PendingQuestLogAdapter.ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Quest q = mQuests.get(position);

        holder.textViewQuestName.setText(q.getTitle());
        holder.textViewQuestDescription.setText(q.getDescription());
        holder.textViewGoldReward.setText("10gp");
        holder.textViewXpReward.setText("100gp");

        holder.btnMarkAsComplete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                CharacterService characterService = new CharacterService(v.getContext().getApplicationContext());

                characterService.isCorrectPin(v, new DialogSingleButtonListener() {
                    @Override
                    public void onButtonClicked(DialogInterface dialog) {
                        Toast.makeText(v.getContext(), "Match", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mQuests.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
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
