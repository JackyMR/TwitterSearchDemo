package com.yuan.twittersearchdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by Yuan on 15/12/24.
 */
public class SpUtil {

    public static final String SP_NAME = "twittersearch";

    public static final String ACCESS_TOKEN = "token";

    private SharedPreferences sharePreferences;
    private SharedPreferences.Editor editor;

    private static SpUtil util;

    private SpUtil(Context context) {
        sharePreferences = context.getSharedPreferences("twittersearch",
                Context.MODE_PRIVATE);
        editor = sharePreferences.edit();
    }

    public static SpUtil getInstance(Context context) {
        if (null == util) {
            return (util = new SpUtil(context.getApplicationContext()));
        } else {
            return util;
        }
    }

    public void put(String key, String value) {
        if (null != editor) {
            editor.putString(key, value);
            editor.commit();
        }
    }

    public void put(String key, float value) {
        if (null != editor) {
            editor.putFloat(key, value);
            editor.commit();
        }
    }

    public void put(String key, int value) {
        if (null != editor) {
            editor.putInt(key, value);
            editor.commit();
        }
    }

    public void put(String key, boolean value) {
        if (null != editor) {
            editor.putBoolean(key, value);
            editor.commit();
        }
    }

    public void put(String key, long value) {
        if (null != editor) {
            editor.putLong(key, value);
            editor.commit();
        }
    }

    public String getString(String key) {
        return sharePreferences.getString(key, "");
    }

    public int getInt(String key) {
        return sharePreferences.getInt(key, Integer.MIN_VALUE);
    }

    public float getFloat(String key) {
        return sharePreferences.getFloat(key, Float.MIN_VALUE);
    }

    public boolean getBoolean(String key) {
        return sharePreferences.getBoolean(key, false);
    }

    public long getLong(String key) {
        return sharePreferences.getLong(key, Long.MIN_VALUE);
    }

    public Set<String> getStrings(String key) {
        return sharePreferences.getStringSet(key, null);
    }

    public void put(String key, Set<String> values) {
        if (null != editor) {
            editor.putStringSet(key, values);
            editor.commit();
        }
    }

    public boolean hasKey(String key) {
        return sharePreferences.contains(key);
    }

    public void remove(String key) {
        if (null != editor) {
            editor.remove(key);
            editor.commit();
        }
    }

    public void clear() {
        if (null != editor) {
            editor.clear();
            editor.commit();
        }
    }


}
