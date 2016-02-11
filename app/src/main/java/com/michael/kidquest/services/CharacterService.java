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

    public Character getCharacter(){
        DaoSession daoSession = ((KidQuestApplication) context).getDaoSession();
        CharacterDao cDao = daoSession.getCharacterDao();

        List<Character> characters = cDao.queryBuilder().limit(1).list();

        if (characters.size() == 0){
            //TODO: Handle this
            return null;
        } else if (characters.size() == 1){
            return characters.get(0);
        } else {
            //TODO: Throw exception
            return null;
        }
    }

    public CharacterService(Context context) {
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
