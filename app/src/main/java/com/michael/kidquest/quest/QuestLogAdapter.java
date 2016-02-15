package com.michael.kidquest.quest;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.michael.kidquest.DialogSingleButtonListener;
import com.michael.kidquest.KidQuestApplication;
import com.michael.kidquest.R;
import com.michael.kidquest.greendao.model.DaoSession;
import com.michael.kidquest.greendao.model.Quest;
import com.michael.kidquest.services.CharacterService;
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
        viewHolder.txtQuestDescription.setText(q.getDescription());
        viewHolder.txtGoldReward.setText("10gp");
        viewHolder.txtXpReward.setText("100xp");

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

        viewHolder.btnAction.setText("Mark as Complete");
        viewHolder.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get clicked quest and mark as completed
                Quest q = mQuests.get(position);
                q.setCompleted(true);

                //save quest
                DaoSession daoSession = ((KidQuestApplication) v.getContext().getApplicationContext()).getDaoSession();
                daoSession.getQuestDao().insertOrReplace(q);

                //hide quest from the list of open quests
                mQuests.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    private void closeMethod(QuestViewHolder viewHolder, int position) {
        Quest q = mQuests.get(position);

        viewHolder.btnAction.setText("Confirm Quest");
        viewHolder.btnAction.setOnClickListener(new View.OnClickListener(){
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
}
