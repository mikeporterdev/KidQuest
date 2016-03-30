package com.michael.kidquest.services;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.michael.kidquest.DialogSingleButtonListener;
import com.michael.kidquest.greendao.KidQuestApplication;
import com.michael.kidquest.greendao.model.Character;
import com.michael.kidquest.greendao.model.CharacterDao;
import com.michael.kidquest.greendao.model.DaoSession;
import com.michael.kidquest.server.ServerRestClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Michael Porter on 11/02/16.
 */
public class CharacterService {
    private Context context;
    private Character character;

    public void addCharacter(Character character) {
        if (validateCharacter(character)) {
            //All new characters start with level 1
            character.setCharacter_level(1);

            getCharacterDao().insertOrReplace(character);
        }
    }

    public Character getCharacter() {
        ServerRestClient serverRestClient = new ServerRestClient(getToken());

        serverRestClient.get("users/" + getServerId() + "/", null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new GsonBuilder().create();
                character = gson.fromJson(response.toString(), Character.class);
            }
        });
        return character;
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
                if (matchesPin(editText.getText().toString())) {
                    dialogSingleButtonListener.onButtonClicked(dialog);
                } else {
                    Toast.makeText(v.getContext(), "Pin does not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean matchesPin(String pin) {
        //TODO: Probably make this more secure
        String pin2 = getCharacter().getParentPin();
        return pin.equals(pin2);
    }

    public String getToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("kidquest", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        return token;
    }

    public void setToken(String token){
        SharedPreferences sharedPreferences = context.getSharedPreferences("kidquest", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.commit();
    }

    public String getGcmId(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("kidquest", Context.MODE_PRIVATE);
        String gcm_id = sharedPreferences.getString("gcm_id", null);
        return gcm_id;
    }

    public void setGcmId(String gcmId){
        SharedPreferences sharedPreferences = context.getSharedPreferences("kidquest", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("gcm_id", gcmId);
        editor.commit();

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("gcm_id", gcmId);
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
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getServerId(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("kidquest", Context.MODE_PRIVATE);
        int serverId = sharedPreferences.getInt("server_id", 0);
        return serverId;
    }

    public void setServerId(int id){
        SharedPreferences sharedPreferences = context.getSharedPreferences("kidquest", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("server_id", id);
        editor.commit();
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
