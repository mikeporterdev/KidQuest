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

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.michael.kidquest.DialogSingleButtonListener;
import com.michael.kidquest.greendao.model.Character;
import com.michael.kidquest.server.ServerRestClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

public class CharacterService {
    private Context context;
    private Character character;

    public void updateCharacter(String character_name, String parent_pin) {

        JSONObject params = new JSONObject();

        try {
            if (character_name != null){
                params.put("character_name", character_name);
            }
            if (parent_pin != null){
                params.put("parent_pin", parent_pin);
            }

            HttpEntity entity = new StringEntity(params.toString());

            ServerRestClient serverRestClient = new ServerRestClient(getToken());
            serverRestClient.put(context, "users/" + getServerId(), entity, new AsyncHttpResponseHandler() {
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

    public void getCharacter(JsonHttpResponseHandler handler) {
        ServerRestClient serverRestClient = new ServerRestClient(getToken());

        serverRestClient.get("users/" + getServerId() + "/", handler);
    }

    public void isCorrectPin(final View v, final DialogSingleButtonListener dialogSingleButtonListener) {
        if (isParent()){
            dialogSingleButtonListener.onButtonClicked(null);
        } else {
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
    }

    private boolean matchesPin(String pin) {
        //TODO: Probably make this more secure
        String pin2 = getParentPin();
        return pin.equals(pin2);
    }

    public String getToken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("kidquest", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", null);
    }

    public void setToken(String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("kidquest", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.commit();
    }

    public String getGcmId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("kidquest", Context.MODE_PRIVATE);
        return sharedPreferences.getString("gcm_id", null);
    }

    public void setGcmId(String gcmId) {
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

            serverRestClient.put(context, url, stringEntity, new AsyncHttpResponseHandler() {
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

    public int getServerId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("kidquest", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("server_id", 0);
    }

    public void setServerId(int id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("kidquest", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("server_id", id);
        editor.commit();
    }

    private String getParentPin() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("kidquest", Context.MODE_PRIVATE);
        return sharedPreferences.getString("parent_pin", null);
    }

    public void setParentPin(String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("kidquest", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("parent_pin", id);
        editor.commit();
    }

    public boolean isParent(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("kidquest", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("is_parent", false);
    }

    public void setParent(boolean isParent){
        SharedPreferences sharedPreferences = context.getSharedPreferences("kidquest", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_parent", isParent);
        editor.commit();
    }

    public void signOut(){
        context.getSharedPreferences("kidquest", Context.MODE_PRIVATE).edit().clear().commit();
        Toast.makeText(context, "You have been signed out", Toast.LENGTH_SHORT).show();
    }

    public void addNewParent(int id) {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("parent_id", id);
            StringEntity stringEntity = new StringEntity(jsonParams.toString());

            String url = "users/" + getServerId() + "/";
            ServerRestClient serverRestClient = new ServerRestClient(getToken());

            serverRestClient.put(context, url, stringEntity, new AsyncHttpResponseHandler() {
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

    public CharacterService(Context context) {
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


}
