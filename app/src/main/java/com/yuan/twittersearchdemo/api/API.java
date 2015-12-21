package com.yuan.twittersearchdemo.api;

import android.net.Uri;
import android.text.TextUtils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.yuan.twittersearchdemo.Utils.Log;

import java.io.IOException;

/**
 * Created by Yuan on 15/12/21.
 */
public class API {

    public static final String API = "https://api.twitter.com/1.1/search/tweets.json?";

    public static String search(String q, String geo, String lang) throws IOException {
        String url = API;
        if (!TextUtils.isEmpty(q)) {
            url += "q=" + Uri.encode(q);
        }
        if (!TextUtils.isEmpty(geo)) {
            url += "&geo=" + Uri.encode(geo);
        }
        if (!TextUtils.isEmpty(geo)) {
            url += "&lang=" + Uri.encode(lang);
        }

        Log.i("url = " + url);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "OAuth oauth_consumer_key=\"DC0sePOBbQ8bYdC8r4Smg\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1450693219\",oauth_nonce=\"2000500146\",oauth_version=\"1.0\",oauth_token=\"4619675898-a7gyTVh67uMcPbTI8n1oVSObE4jPqBESSSwXdA7\",oauth_signature=\"MqIK8LM2uMdKqvSokyKGqN2bbEU%3D\"")
                .header("Host","api.twitter.com")
                .header("X-Target-URI","https://api.twitter.com")
                .header("Connection","Keep-Alive")
                .build();

        Response response = client.newCall(request).execute();

        ResponseBody body = response.body();
        Log.i(body.string());
        return body.string();
    }

}
