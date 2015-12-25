package com.yuan.twittersearchdemo.utils;

import com.yuan.twittersearchdemo.BuildConfig;

/**
 * Created by Yuan on 15/12/21.
 */
public class Log {

    public static final int i (String log){
        if(BuildConfig.DEBUG) {
            return android.util.Log.i("twittersearchdemo", "yuan : " + log);
        }else {
            return -1;
        }
    }
}
