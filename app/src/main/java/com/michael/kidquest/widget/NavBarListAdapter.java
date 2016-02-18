package com.michael.kidquest.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michael.kidquest.R;

/**
 * Created by Michael Porter on 07/02/16.
 */
public class NavBarListAdapter extends RecyclerView.Adapter<NavBarListAdapter.ViewHolder>{

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String[] navLocations;
    private String characterName;
    private int characterLevel;

    OnItemClickListener mItemClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        int holderId;

        TextView characterName;
        TextView navDrawerItemName;
        TextView characterLevel;

        public ViewHolder(View itemView, int viewType){
            super(itemView);

            if (viewType == TYPE_ITEM){
                navDrawerItemName = (TextView) itemView.findViewById(R.id.nav_drawer_item_name);
                holderId = 1;
            } else {
                characterName = (TextView) itemView.findViewById(R.id.character_name);
                characterLevel = (TextView) itemView.findViewById(R.id.character_level);
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

    public NavBarListAdapter(String[] navLocations, String characterName, int characterLevel) {
        this.navLocations = navLocations;
        this.characterName = characterName;
        this.characterLevel = characterLevel;
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
            holder.navDrawerItemName.setText(navLocations[position - 1]);
        } else {
            holder.characterName.setText(characterName);
            holder.characterLevel.setText("Level: " + String.valueOf(characterLevel));
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

    private boolean isPositionHeader(int position){
        return position == 0;
    }
}
