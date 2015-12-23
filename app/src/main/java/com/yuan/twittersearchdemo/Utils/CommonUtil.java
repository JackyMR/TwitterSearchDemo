package com.yuan.twittersearchdemo.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import java.util.Locale;

/**
 * Created by Yuan on 15/12/22.
 */
public class CommonUtil {

    public static String getLocalLang() {
        return Locale.getDefault().getLanguage();
    }

    public static void hideInput(Activity context) {
        InputMethodManager mInputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mInputMethodManager.isActive())
            mInputMethodManager.hideSoftInputFromWindow(context
                    .getCurrentFocus().getWindowToken(), 0);
    }

}
