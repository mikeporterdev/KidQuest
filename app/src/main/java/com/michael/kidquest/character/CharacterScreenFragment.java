package com.michael.kidquest.character;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michael.kidquest.R;
import com.michael.kidquest.greendao.model.Character;
import com.michael.kidquest.services.CharacterService;

/**
 * Created by Michael Porter on 09/02/16.
 */
public class CharacterScreenFragment extends Fragment {

    private CharacterService cService;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_character_screen, container, false);

        updateCharacter();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCharacter();
    }

    public void updateCharacter(){
        cService = new CharacterService(view.getContext().getApplicationContext());

        Character character = cService.getCharacter();

        TextView txtName = (TextView) view.findViewById(R.id.character_name);
        txtName.setText(character.getName());

        TextView txtLevel = (TextView) view.findViewById(R.id.character_level);
        txtLevel.setText("Level: " + String.valueOf(character.getLevel()));

        TextView txtGold = (TextView) view.findViewById(R.id.character_gold);
        txtGold.setText(String.valueOf(character.getGold()) + "gp");

        TextView txtXp = (TextView) view.findViewById(R.id.character_xp);
        txtXp.setText("XP: " + String.valueOf(character.getXp()) + "/10000");
    }
}
