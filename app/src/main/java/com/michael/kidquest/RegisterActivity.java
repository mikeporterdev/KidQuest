package com.michael.kidquest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.michael.kidquest.server.ServerRestClient;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by m_por on 08/05/2016.
 */
public class RegisterActivity extends AppCompatActivity {
    private String TAG = "RegisterActivity";

    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mCharacterName;
    private EditText mParentPin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        Button btnSubmit = (Button) findViewById(R.id.register_button);

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mCharacterName = (EditText) findViewById(R.id.character_name);
        mParentPin = (EditText) findViewById(R.id.parent_pin);

        assert btnSubmit != null;
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reset errors
                mEmailView.setError(null);
                mPasswordView.setError(null);
                mParentPin.setError(null);
                mCharacterName.setError(null);

                boolean valid = true;
                if (mEmailView == null || mEmailView.getText().toString().equals("")){
                    valid = false;
                    mEmailView.setError("You must enter an email address");
                }

                String email = mEmailView.getText().toString();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    valid = false;
                    mEmailView.setError("You must enter a valid email address");
                }

                if (mPasswordView == null || mPasswordView.getText().toString().equals("")){
                    valid = false;
                    mPasswordView.setError("You must enter a password");
                }

                String password = mPasswordView.getText().toString();

                if (mParentPin == null || mParentPin.getText().toString().equals("")){
                    valid = false;
                    mParentPin.setError("You must enter a pin code");
                }

                if (mParentPin.length() != 4){
                    valid = false;
                    mParentPin.setError("Pin code must be 4 numerical digits");
                }

                String pin = mParentPin.getText().toString();

                if (!pin.matches("^[0-9]{4}$")){
                    valid = false;
                    mParentPin.setError("Pin code must be 4 numerical digits");
                }

                if (mCharacterName == null || mCharacterName.getText().toString().equals("")){
                    valid = false;
                    mCharacterName.setError("You must enter a character name");
                }

                String characterName = mCharacterName.getText().toString();

                if (valid){
                    Map<String, String> details = new HashMap<>();
                    details.put("email", email);
                    details.put("password", password);
                    details.put("character_name", characterName);
                    details.put("parent_pin", pin);

                    String json = new GsonBuilder().create().toJson(details, Map.class);
                    try {
                        StringEntity entity = new StringEntity(json.toString());
                        ServerRestClient serverRestClient = new ServerRestClient();

                        serverRestClient.post(getApplicationContext(), "users/", entity, "application/json", new AsyncHttpResponseHandler(){

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                Log.i(TAG, "onSuccess: Registered");
                                Toast.makeText(getApplicationContext(), "Account created.", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Log.e(TAG, "onFailure: Could not register");
                                Toast.makeText(getApplicationContext(), "Account could not be created.", Toast.LENGTH_SHORT).show();
                                error.printStackTrace();
                            }
                        });
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }
}
