package com.michael.kidquest.quest;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.michael.kidquest.DialogSingleButtonListener;
import com.michael.kidquest.R;
import com.michael.kidquest.greendao.model.Quest;
import com.michael.kidquest.services.CharacterService;
import com.michael.kidquest.services.QuestService;
import com.ocpsoft.pretty.time.PrettyTime;

import java.util.List;

/**
 * Created by Michael Porter on 04/02/16.
 * <p/>
 * Builds the content of the quest cards
 */
public class QuestLogAdapter extends RecyclerView.Adapter<QuestViewHolder> {
    private final List<Quest> mQuests;
    private boolean isOpen;
    private CharacterService characterService;

    public QuestLogAdapter(List<Quest> quests, boolean isOpen) {
        this.mQuests = quests;
        this.isOpen = isOpen;
    }

    @Override
    public QuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quest_card, null);
        return new QuestViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(QuestViewHolder viewHolder, int position) {
        Quest q = mQuests.get(position);

        viewHolder.txtQuestName.setText(q.getTitle());

        characterService = new CharacterService(viewHolder.txtQuestName.getContext());

        if (q.getDescription() != null && !q.getDescription().equalsIgnoreCase("")) {
            viewHolder.txtQuestDescription.setVisibility(View.VISIBLE);
            viewHolder.txtQuestDescription.setText(q.getDescription());

        } else {
            viewHolder.txtQuestDescription.setVisibility(View.GONE);
            viewHolder.viewDivider.setVisibility(View.GONE);
        }

        viewHolder.txtGoldReward.setText(String.format("%dgp", q.getCurrentReward()));
        viewHolder.txtXpReward.setText(String.format("%dxp", q.getXpReward()));

        if (isOpen){
            openMethod(viewHolder, position);
        } else {
            closeMethod(viewHolder, position);
        }
    }

    private void openMethod(QuestViewHolder viewHolder, final int position){
        Quest q = mQuests.get(position);

        if (q.getExpiryDate() != null) {
            //print date in the format of (x hours/days/weeks from now)
            PrettyTime p = new PrettyTime();
            viewHolder.txtExpiryDate.setText("Expires: " + p.format(q.getExpiryDate()));
        } else {
            viewHolder.txtExpiryDate.setVisibility(View.GONE);
        }

        if (!characterService.isParent()) {
            viewHolder.btnAction.setText("Mark as Complete");
            viewHolder.btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get clicked quest and mark as completed
                    Quest q = mQuests.get(position);
                    QuestService qService = new QuestService(v.getContext().getApplicationContext());
                    qService.completeQuest(q);

                    mQuests.remove(position);
                    notifyDataSetChanged();
                }
            });
        } else {
            viewHolder.btnAction.setVisibility(View.GONE);
        }

    }

    private void closeMethod(QuestViewHolder viewHolder, final int position) {
        viewHolder.btnAction.setText("Confirm Quest");
        viewHolder.btnAction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                CharacterService characterService = new CharacterService(v.getContext().getApplicationContext());

                characterService.isCorrectPin(v, new DialogSingleButtonListener() {
                    @Override
                    public void onButtonClicked(DialogInterface dialog) {
                        QuestService questService = new QuestService(v.getContext().getApplicationContext());
                        Quest q = mQuests.get(position);
                        questService.confirmQuest(q);

                        mQuests.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(v.getContext().getApplicationContext(), "Rewards Given.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mQuests.size();
    }
}
