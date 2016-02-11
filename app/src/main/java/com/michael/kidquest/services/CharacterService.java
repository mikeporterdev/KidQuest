package com.michael.kidquest.services;

import android.content.Context;

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
