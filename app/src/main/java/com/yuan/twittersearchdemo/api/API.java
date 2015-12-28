package com.yuan.twittersearchdemo.api;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.yuan.twittersearchdemo.model.SearchEntity;
import com.yuan.twittersearchdemo.utils.Log;
import com.yuan.twittersearchdemo.model.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * Created by Yuan on 15/12/22.
 */
public class API {

    public static final String API = "https://api.twitter.com";

    /**
     * Twitter创建一个应用生成的key
     */
    public static final String OAUTH_CONSUMER_KEY = "OGEcFxjmAI1RTsnzjf5mYMky0";

    public static final String OAUTH_CONSUMER_SECRET = "14UNt7btzWOH0dB3UtpeGDGDExF01W5axKdzNtui5WqAk8gwvD";

    /**
     * 使用okHTTP
     * @param token
     * @param webargs
     * @return
     * @throws IOException
     */
    public static List<Status> search(String token, String... webargs) throws IOException {
        StringBuilder url = new StringBuilder(API + "/1.1/search/tweets.json?");
        if (!TextUtils.isEmpty(webargs[0])) {
            url.append("q=" + Uri.encode(webargs[0]));
        }
        if (!TextUtils.isEmpty(webargs[1])) {
            url.append("&geo=" + Uri.encode(webargs[1]));
        }
        if (!TextUtils.isEmpty(webargs[2])) {
            url.append("&lang=" + Uri.encode(webargs[2]));
        }
        if (TextUtils.isEmpty(webargs[3])) {
            webargs[3] = "10";
        }
        url.append("&count=" + Uri.encode(webargs[3]));


        Log.i("url = " + url.toString());

        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(5, TimeUnit.SECONDS);

        String auth = "Bearer " + token;
        Request request = new Request.Builder()
                .url(url.toString())
                .header("Authorization", auth)
                .build();

        Response response = client.newCall(request).execute();
        ResponseBody body = response.body();
        Log.i("res = " + "code " + response.code() + "/ header " + response.headers());
        List<Status> list = null;
        if (response.isSuccessful()) {
            String bodystring = body.string();
            Log.i("res body = " + "\n" + bodystring);
            Gson gson = new Gson();
            try {
                SearchEntity entity = gson.fromJson(bodystring, SearchEntity.class);
//            SearchEntity entity = null;
//            try {
//                entity = new SearchEntity(new JSONObject(body.string()));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
              Log.i("entity = " + entity);
              list = entity.statuses;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        body.close();
        return list;
    }

    /**
     * UrlConnection
     * @param token
     * @param webargs
     * @return
     * @throws IOException
     */
    public static List<Status> search2(String token, String... webargs) throws IOException {
        StringBuilder url = new StringBuilder(API + "/1.1/search/tweets.json?");
        if (!TextUtils.isEmpty(webargs[0])) {
            url.append("q=" + Uri.encode(webargs[0]));
        }
        if (!TextUtils.isEmpty(webargs[1])) {
            url.append("&geo=" + Uri.encode(webargs[1]));
        }
        if (!TextUtils.isEmpty(webargs[2])) {
            url.append("&lang=" + Uri.encode(webargs[2]));
        }
        if (TextUtils.isEmpty(webargs[3])) {
            webargs[3] = "10";
        }
        url.append("&count=" + Uri.encode(webargs[3]));


        Log.i("url = " + url.toString());

        URL urlcon = new URL(url.toString());
        HttpURLConnection connection = (HttpURLConnection) urlcon.openConnection();
        connection.setConnectTimeout(7000);
        connection.setReadTimeout(7000);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + token);

        List<Status> list = null;
        if (connection.getResponseCode() == 200) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
            byte[] buffer = new byte[1024 * 8];
            int len = 0;
            while((len = bis.read(buffer,0,buffer.length)) != -1){
                baos.write(buffer,0,len);
            }
            baos.flush();
            String body = new String(baos.toByteArray(),"UTF-8");
            baos.close();
            Log.i("body " + body);
            SearchEntity entity = null;

//            try {
//                entity = new SearchEntity(new JSONObject(body));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            //GSON
            Gson gson = new Gson();
            entity = gson.fromJson(body,SearchEntity.class);
            Log.i("entity = " + entity);
            list = entity.statuses;
        }
        return list;
    }

    public static final String oauth2token() throws IOException {
        String url = API + "/oauth2/token";
        String localtoken = getBase64Token();
        String access_token = null;

        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(5, TimeUnit.SECONDS);

        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), "grant_type=client_credentials");

        Log.i("oauth2token = " + localtoken);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Basic " + localtoken)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        ResponseBody body = response.body();
        if (response.isSuccessful()) {
            try {
                JSONObject json = new JSONObject(body.string());
                if (json.has("access_token")) {
                    access_token = json.getString("access_token");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.i("oauth2token = " + response.code() + "/" + body.string());
        Log.i("oauth2token = " + access_token);
        body.close();
        return access_token;
    }

    /*
    public static final String oauth2token2(){
        try {
            URL url = new URL(API + "/oauth2/token");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Basic " + getBase64Token());
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();
            byte[] bytes = "grant_type=client_credentials".getBytes("UTF-8");
            OutputStream os = connection.getOutputStream();
            os.write(bytes);
            os.flush();
            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 8];
            int len = 0;
            while((len = bis.read(buffer,0,buffer.length)) != -1){
                baos.write(buffer,0,len);
            }
            baos.flush();
            String body = new String(baos.toByteArray(),"UTF-8");
            Log.i("oauth2token2 = " + body);
            baos.close();
            bis.close();
            os.close();
            return body;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    */

    private static String getBase64Token() {
        String localtoken = OAUTH_CONSUMER_KEY + ":" + OAUTH_CONSUMER_SECRET;
        localtoken = Base64.encodeToString(localtoken.getBytes(), Base64.NO_WRAP);
        return localtoken;
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
