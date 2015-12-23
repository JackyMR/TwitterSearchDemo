package com.yuan.twittersearchdemo.api;

import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.yuan.twittersearchdemo.Utils.Log;
import com.yuan.twittersearchdemo.model.SearchEntity;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * Created by Yuan on 15/12/22.
 */
public class API {

    public static final String API = "https://api.twitter.com/1.1/search/tweets.json?";

    public static List<SearchEntity> search(String q, String geo, String lang) throws IOException {
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
        client.setConnectTimeout(5, TimeUnit.SECONDS);

        String timeStamp = "\"" + String.valueOf(System.currentTimeMillis() / 1000) + "\"";

        //在https://dev.twitter.com/rest/tools/console授权自己账号获得header
        String auth = "OAuth oauth_consumer_key=OGEcFxjmAI1RTsnzjf5mYMky0" +
                ",oauth_signature_method=\"HMAC-SHA1\"" +
                ",oauth_timestamp=" + timeStamp +
                ",oauth_nonce=" + "\"532cd49d62041685c1bfc206b2895718\"" +
                ",oauth_version=\"1.0\"" +
                ",oauth_token=4619675898-68kG6u7QWeLyKhFFUtWTGhAaSb9Y26F6lNQKuzr" +
                ",oauth_signature=\"h6hLfB9WxY4D8pEXHSJHug0Fm68%3D\"";
        Request request = new Request.Builder()
                .url(url.toString())
                .header("Authorization", auth)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            Log.i("res = " + "code " + response.code() + "/" + body.string());
            TypeToken<List<SearchEntity>> ENTITY = new TypeToken<List<SearchEntity>>() {
            };
            Gson gson = new Gson();
            List<SearchEntity> list = gson.fromJson(body.charStream(), ENTITY.getType());
            body.close();
            return list;
        }else{
            ResponseBody body = response.body();
            Log.i("res = " + "code " + response.code() + "/" + body.string());
        }
        return null;
    }


    private static String getOathNonce() {
        Timer timer = new Timer();
        return "\"" + String.valueOf(timer.getTimeStamp() + timer.getRandomInteger()) + "\"";
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
