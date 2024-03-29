package com.michael.kidquest.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.michael.kidquest.R;
import com.michael.kidquest.greendao.model.Character;
import com.michael.kidquest.server.ServerRestClient;
import com.michael.kidquest.services.CharacterService;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Michael Porter on 07/02/16.
 */
public class NavBarListAdapter extends RecyclerView.Adapter<NavBarListAdapter.ViewHolder>{

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private Character character;

    private final String[] navLocations;
    private final String characterName;
    private final int characterLevel;
    private Context context;


    private OnItemClickListener mItemClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final int holderId;

        TextView characterName;
        TextView navDrawerItemName;
        TextView characterLevel;
        ProgressBar progressBar;
        TextView gold;
        TextView progressDesc;

        public ViewHolder(View itemView, int viewType){
            super(itemView);

            if (viewType == TYPE_ITEM){
                navDrawerItemName = (TextView) itemView.findViewById(R.id.nav_drawer_item_name);
                holderId = 1;
            } else {
                characterName = (TextView) itemView.findViewById(R.id.character_name);
                characterLevel = (TextView) itemView.findViewById(R.id.character_level);
                progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
                gold = (TextView) itemView.findViewById(R.id.header_gold);
                progressDesc = (TextView) itemView.findViewById(R.id.xpdesc);

                holderId = 0;
            }

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null){
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    public NavBarListAdapter(String[] navLocations, Character character) {
        this.navLocations = navLocations;
        this.character = character;
        this.characterName = character.getCharacter_name();
        this.characterLevel = character.getCharacter_level();
    }

    @Override
    public NavBarListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_list_item, parent, false);
            return new ViewHolder(v, viewType);
        } else if (viewType == TYPE_HEADER){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_header, parent, false);
            return new ViewHolder(v, viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(NavBarListAdapter.ViewHolder holder, int position) {

        if(holder.holderId == 1){
            context = holder.navDrawerItemName.getContext();
            holder.navDrawerItemName.setText(navLocations[position - 1]);
        } else {
            context = holder.gold.getContext();
            holder.characterName.setText(characterName);
            holder.characterLevel.setText("Level: " + String.valueOf(character.getCharacter_level()));
            holder.progressBar.setMax(character.getXpRequired());
            holder.progressBar.setProgress(character.getXp());
            holder.gold.setText("Gold: " + String.valueOf(character.getGold()));
            holder.progressDesc.setText("XP: " + String.valueOf(character.getXp() + "/" + String.valueOf(character.getXpRequired())));
        }
    }

    @Override
    public int getItemCount() {
        return navLocations.length + 1;
    }

    @Override
    public int getItemViewType(int position){
        return (isPositionHeader(position) ? TYPE_HEADER : TYPE_ITEM);
    }

    public void update(){
        CharacterService cService = new CharacterService(context);
        ServerRestClient serverRestClient = new ServerRestClient(cService.getToken());

        serverRestClient.get("users/" + cService.getServerId() + "/", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new GsonBuilder().create();
                character = gson.fromJson(response.toString(), Character.class);
                notifyItemChanged(0);
            }
        });


    }

    private boolean isPositionHeader(int position){
        return position == 0;
    }
}
