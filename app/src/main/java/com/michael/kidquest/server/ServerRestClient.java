package com.michael.kidquest.server;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by m_por on 15/02/2016.
 */
public class ServerRestClient {
    private static final String BASE_URL = "http://kitari.ddns.net:5000/";
    private static final String TAG = "ServerRestClient";
    //milliseconds
    private static final int GET_TIMEOUT = 3000;

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.setTimeout(GET_TIMEOUT);
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(Context context, String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
        client.post(context, getAbsoluteUrl(url), entity, contentType, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        Log.i(TAG, BASE_URL + relativeUrl);
        return BASE_URL + relativeUrl;
    }
}
