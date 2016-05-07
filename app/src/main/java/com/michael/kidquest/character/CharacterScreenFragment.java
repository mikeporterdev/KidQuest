package com.michael.kidquest.character;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.michael.kidquest.R;
import com.michael.kidquest.greendao.model.Character;
import com.michael.kidquest.services.CharacterService;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Michael Porter on 09/02/16.
 */
public class CharacterScreenFragment extends Fragment {

    private View view;
    private Character character;
    private JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            Gson gson = new GsonBuilder().create();
            character = gson.fromJson(response.toString(), Character.class);
            updateCharacter(character);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_character_screen, container, false);

        CharacterService characterService = new CharacterService(view.getContext().getApplicationContext());
        characterService.getCharacter(handler);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        CharacterService characterService = new CharacterService(view.getContext().getApplicationContext());
        characterService.getCharacter(handler);
    }

    public void updateCharacter(Character character){
        TextView txtName = (TextView) view.findViewById(R.id.character_name);
        txtName.setText(character.getCharacter_name());

        TextView txtLevel = (TextView) view.findViewById(R.id.character_level);
        txtLevel.setText(String.format("Level: %s", String.valueOf(character.getCharacter_level())));

        TextView txtGold = (TextView) view.findViewById(R.id.character_gold);
        txtGold.setText(String.format("%sgp", String.valueOf(character.getGold())));

        TextView txtXp = (TextView) view.findViewById(R.id.character_xp);
        String text = "XP: " + character.getXp() + "/" + character.getXpRequired();
        txtXp.setText(text);
    }
}
