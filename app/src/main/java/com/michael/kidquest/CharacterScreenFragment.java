package com.michael.kidquest;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michael.kidquest.greendao.model.*;
import com.michael.kidquest.greendao.model.Character;

/**
 * Created by Michael Porter on 09/02/16.
 */
public class CharacterScreenFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character_screen, container, false);

        DaoSession daoSession = ((KidQuestApplication) view.getContext().getApplicationContext()).getDaoSession();
        Character character = daoSession.getCharacterDao().loadAll().get(0);

        TextView txtName = (TextView) view.findViewById(R.id.character_name);
        txtName.setText(character.getName());

        TextView txtLevel = (TextView) view.findViewById(R.id.character_level);
        txtLevel.setText("Level: " + String.valueOf(character.getLevel()));

        return view;
    }
}
