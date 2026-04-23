package com.example.istreamapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "istream_session";
    private static final String KEY_USER_ID = "user_id";

    public static void saveLogin(Context context, int userId) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_USER_ID, userId).apply();
    }

    public static int getLoggedInUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_USER_ID, -1);
    }

    public static boolean isLoggedIn(Context context) {
        return getLoggedInUserId(context) != -1;
    }

    public static void logout(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }
}