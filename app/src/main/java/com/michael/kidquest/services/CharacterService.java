package com.michael.kidquest.services;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.michael.kidquest.KidQuestApplication;
import com.michael.kidquest.greendao.model.Character;
import com.michael.kidquest.greendao.model.CharacterDao;
import com.michael.kidquest.greendao.model.DaoSession;

import java.util.List;

/**
 * Created by Michael Porter on 11/02/16.
 */
public class CharacterService {
    private Context context;

    public void addCharacter(Character character) {
        if (validateCharacter(character)) {
            //All new characters start with level 1
            character.setLevel(1);

            getCharacterDao().insertOrReplace(character);
        }
    }

    public void levelUpCharacter() {
        Character c = getCharacter();
        c.setLevel(c.getLevel() + 1);
        getCharacterDao().update(c);
    }

    public Character getCharacter() {
        List<Character> characters = getCharacterDao().queryBuilder().limit(1).list();

        if (characters.size() == 0) {
            //TODO: Handle this
            return null;
        } else if (characters.size() == 1) {
            return characters.get(0);
        } else {
            //TODO: Throw exception
            return null;
        }
    }

    public void parentPrompt(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        final EditText editText = new EditText(v.getContext());

        editText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        //cap input at four numbers
        editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
        builder.setView(editText);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (matchesPin(editText.getText().toString())){
                    Toast.makeText(context.getApplicationContext(), "Pin matches", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context.getApplicationContext(), "Pin Does Not Match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.show();
    }

    public boolean matchesPin(String pin){
        //TODO: Probably make this more secure
        String pin2 = getCharacter().getParentPin();
        return pin.equals(pin2);
    }

    private CharacterDao getCharacterDao() {
        DaoSession daoSession = ((KidQuestApplication) context).getDaoSession();
        return daoSession.getCharacterDao();

    }

    public boolean validateCharacter(Character character) {
        //TODO: Actually validate a character
        return true;
    }

    public CharacterService(Context context) {
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
