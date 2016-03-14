package com.michael.kidquest.server;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.michael.kidquest.Constants;

import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by m_por on 15/02/2016.
 */
public class ServerRestClient {
    private static final String TAG = "ServerRestClient";
    //milliseconds
    private static final int GET_TIMEOUT = 3000;

    private static AsyncHttpClient client = new AsyncHttpClient();

    public ServerRestClient(String email, String password) {
        client.setBasicAuth(email, password);
    }

    public ServerRestClient(String token) {
        client.setBasicAuth(token, "nopassword");
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.setTimeout(GET_TIMEOUT);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(Context context, String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
        client.post(context, getAbsoluteUrl(url), entity, contentType, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        Log.i(TAG, Constants.SERVER_URL + relativeUrl);
        return Constants.SERVER_URL + relativeUrl;
    }
}
