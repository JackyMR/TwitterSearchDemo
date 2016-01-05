package com.yuan.twittersearchdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String getUrlStrInString(String origin) {
        if (origin == null) {
            throw new NullPointerException("the param origin is null");
        }
        Pattern pattern = Pattern.compile("(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?");
        Matcher matcher = pattern.matcher(origin);
        StringBuffer buffer = new StringBuffer();
        //while
        if (matcher.find()) {
            buffer.append(matcher.group());
        }
        return buffer.toString();
    }

}
