package com.yuan.twittersearchdemo.Utils;

import android.content.Context;

/**
 * Created by Yuan on 15/12/21.
 */
public class CommonUtil {

    public static String getLocalLang(Context context){
        return context.getResources().getConfiguration().locale.getLanguage();
    }
}
