package com.michael.kidquest.server;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.michael.kidquest.Constants;

import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by m_por on 15/02/2016.
 */
public class ServerRestClient {
    private static final String TAG = "ServerRestClient";
    //milliseconds
    private static final int TIMEOUT = 3000;

    private static final AsyncHttpClient client = new AsyncHttpClient();

    public ServerRestClient() {
    }

    public ServerRestClient(String email, String password) {
        client.setBasicAuth(email, password);
        client.setTimeout(TIMEOUT);
    }

    public ServerRestClient(String token) {
        client.setBasicAuth(token, "nopassword");
        client.setTimeout(TIMEOUT);
    }

    public void get(String url, AsyncHttpResponseHandler responseHandler){
        client.get(getAbsoluteUrl(url), null, responseHandler);
    }

    public void post(Context context, String url, HttpEntity entity, AsyncHttpResponseHandler responseHandler) {
        client.post(context, getAbsoluteUrl(url), entity, "application/json", responseHandler);
    }

    public void put(Context context, String url, HttpEntity entity, AsyncHttpResponseHandler responseHandler){
        client.put(context, getAbsoluteUrl(url), entity, "application/json", responseHandler);
    }

    public void setRetries(){
        client.setMaxRetriesAndTimeout(0, TIMEOUT);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        String url = Constants.SERVER_URL + relativeUrl;

        //allows me to stop fucking up the urls
        if (!url.endsWith("/")){
            url = url.concat("/");
        }

        Log.i(TAG, url);
        return url;
    }
}
