package com.yuan.twittersearchdemo.api;

import android.net.Uri;
import android.text.TextUtils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.yuan.twittersearchdemo.Utils.Log;

import java.io.IOException;
import java.util.Random;


/**
 * Created by Yuan on 15/12/21.
 */
public class API {

    public static final String API = "https://api.twitter.com/1.1/search/tweets.json?";

    public static String search(String q, String geo, String lang) throws IOException {
        StringBuilder url = new StringBuilder(API);
        if (!TextUtils.isEmpty(q)) {
            url.append("q=" + Uri.encode(q));
        }
        if (!TextUtils.isEmpty(geo)) {
            url.append("&geo=" + Uri.encode(geo));
        }
        if (!TextUtils.isEmpty(geo)) {
            url.append("&lang=" + Uri.encode(lang));
        }

        Log.i("url = " + url.toString());

        OkHttpClient client = new OkHttpClient();

        String timeStamp = "\"1450773797\"";
                //"\"" + String.valueOf(System.currentTimeMillis() / 1000) + "\"";

        //在https://dev.twitter.com/rest/tools/console授权自己账号获得header
        String auth = "OAuth oauth_consumer_key=\"DC0sePOBbQ8bYdC8r4Smg\"" +
                ",oauth_signature_method=\"HMAC-SHA1\"" +
                ",oauth_timestamp=" + timeStamp +
                ",oauth_nonce=" + "\"2655158393\"" +
                ",oauth_version=\"1.0\"" +
                ",oauth_token=\"4619675898-a7gyTVh67uMcPbTI8n1oVSObE4jPqBESSSwXdA7\"" +
                ",oauth_signature=\"BoitFygPw%2FtdDuG8%2FaWwxAa6MwQ%3D\"";
        Request request = new Request.Builder()
                .url(url.toString())
                .header("Authorization", auth)
                .build();

        Response response = client.newCall(request).execute();

        ResponseBody body = response.body();
        String bodyString = body.string();
        Log.i("httpcode = " + response.code() + "/" + auth);
        return bodyString;
    }


    private static String getOathNonce() {
       Timer timer =  new Timer();
        return "\"" + String.valueOf(timer.getTimeStamp() +  timer.getRandomInteger()) + "\"";
    }

    static class Timer {

        private final Random rand = new Random();

        long getTimeStamp() {
            return System.currentTimeMillis() / 1000;
        }

        int getRandomInteger() {
            return rand.nextInt();
        }
    }

}
