package com.michael.kidquest.character;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.michael.kidquest.R;
import com.michael.kidquest.server.ServerRestClient;
import com.michael.kidquest.services.CharacterService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class ParentSetup extends AppCompatActivity {
    private static final String TAG = "ParentSetup";
    private EditText mEmail;
    private EditText mPassword;
    private Button mSubmit;
    private ServerRestClient serverRestClient;
    private CharacterService characterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        characterService = new CharacterService(getApplicationContext());
        serverRestClient = new ServerRestClient(characterService.getToken());

        mEmail = (EditText) findViewById(R.id.parent_email);
        mPassword = (EditText) findViewById(R.id.parent_password);

        mSubmit = (Button) findViewById(R.id.parent_email_sign_in_button);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                registerParentDetails(email, password);
            }
        });


    }

    private void registerParentDetails(final String email, final String password){
        ServerRestClient serverRestClient = new ServerRestClient();
        JSONObject params = new JSONObject();

        try {
            params.put("email", email);
            params.put("password", password);
            StringEntity entity = new StringEntity(params.toString());

            serverRestClient.post(this, "users/", entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i(TAG, "Successfully registered parent");
                    getParentDetails(email, password);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i(TAG, "Failed to register parent");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void getParentDetails(String email, String password){
        ServerRestClient serverRestClient = new ServerRestClient(email, password);

        serverRestClient.get("token/", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new GsonBuilder().create();

                JsonObject jsonobj = gson.fromJson(response.toString(), JsonObject.class);

                int id = jsonobj.get("id").getAsInt();

                characterService.setParent(id);

                Toast.makeText(getApplicationContext(), "Added parent account", Toast.LENGTH_SHORT).show();
                finishActivity(3);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.i("ParentSetup", responseString);
            }
        });
    }

}
