package com.yuan.twittersearchdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yuan.twittersearchdemo.Utils.CommonUtil;
import com.yuan.twittersearchdemo.api.API;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    API.search("#123","12", CommonUtil.getLocalLang(MainActivity.this));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /*
    Authorization:
    OAuth oauth_consumer_key="DC0sePOBbQ8bYdC8r4Smg",
    oauth_signature_method="HMAC-SHA1",
    oauth_timestamp="1450684903",oauth_nonce="3361723282",
    oauth_version="1.0",
    oauth_token="4619675898-a7gyTVh67uMcPbTI8n1oVSObE4jPqBESSSwXdA7",
    oauth_signature="xEkQD4k8XS%2F3xorPL7tvxmiEtV4%3D"
    Host:
    api.twitter.com
    X-Target-URI:
    https://api.twitter.com
    Connection:
    Keep-Alive
     */
}
