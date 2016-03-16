package com.michael.kidquest.services;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.michael.kidquest.DialogSingleButtonListener;
import com.michael.kidquest.greendao.KidQuestApplication;
import com.michael.kidquest.greendao.custommodel.DifficultyLevel;
import com.michael.kidquest.greendao.model.Character;
import com.michael.kidquest.greendao.model.CharacterDao;
import com.michael.kidquest.greendao.model.DaoSession;
import com.michael.kidquest.server.ServerRestClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

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

    public void isCorrectPin(final View v, final DialogSingleButtonListener dialogSingleButtonListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Enter parent pin");

        final EditText editText = new EditText(context);
        editText.setInputType(InputType.TYPE_CLASS_PHONE | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editText.setTextColor(Color.BLACK);
        builder.setView(editText);

        //Credit to Handrata Samsul for this code. http://stackoverflow.com/questions/35353350/alertdialog-return-boolean-value
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (matchesPin(editText.getText().toString())){
                    dialogSingleButtonListener.onButtonClicked(dialog);
                } else {
                    Toast.makeText(v.getContext(), "Pin does not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void questReward(DifficultyLevel difficultyLevel){
        int xp = 0;
        int gold = 0;

        switch (difficultyLevel){
            case VERY_EASY:
                xp = 100;
                gold = 100;
                break;
            case EASY:
                xp = 300;
                gold = 300;
                break;
            case MEDIUM:
                xp = 600;
                gold = 600;
                break;
            case HARD:
                xp = 1000;
                gold = 1000;
                break;
            case VERY_HARD:
                xp = 1500;
                gold = 1500;
                break;
        }

        Character c = getCharacter();
        c.setXp(c.getXp() + xp);
        c.setGold(c.getGold() + gold);
        getCharacterDao().insertOrReplace(c);
    }

    public boolean matchesPin(String pin) {
        //TODO: Probably make this more secure
        String pin2 = getCharacter().getParentPin();
        return pin.equals(pin2);
    }

    public String getToken(){
        String token = getCharacter().getToken();
        return token;
    }

    public void setToken(String token){
        Character c = getCharacter();
        c.setToken(token);
        getCharacterDao().insertOrReplace(c);
    }

    public int getServerId(){
        return getCharacter().getServerId();
    }

    public void setServerId(int id){
        Character c = getCharacter();
        c.setServerId(id);
        getCharacterDao().insertOrReplace(c);
    }

    public void setParent(int id){
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("parent_id", id);
            StringEntity stringEntity = new StringEntity(jsonParams.toString());

            String url = "users/" + getServerId() + "/";
            ServerRestClient serverRestClient = new ServerRestClient(getToken());

            serverRestClient.put(context, url, stringEntity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
