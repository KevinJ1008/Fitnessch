package com.kevinj1008.fitnessch.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private SharedPreferences mSharedPreferences;
    private Context mContext;
    private int MODE_PRIVATE = 0;
    private static final String PREF_NAME = "User_Profile";
    private SharedPreferences.Editor mEditor;

    public SharedPreferencesManager (Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void saveUserIdToken(Context context, String token) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("access_token", token);
        editor.apply();
    }

    public String getUserIdToken() {
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return mSharedPreferences.getString("access_token", "");
    }

    public void saveUserId(Context context, String id) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("id", id);
        editor.apply();
    }

    public String getUserId() {
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return mSharedPreferences.getString("id", "");
    }

    public void saveUserEmail(Context context, String email) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("mail", email);
        editor.apply();
    }

    public String getUserEmail() {
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return mSharedPreferences.getString("mail", "");
    }

    public void saveUserName(Context context, String name) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("name", name);
        editor.apply();
    }

    public String getUserName() {
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return mSharedPreferences.getString("name", "");
    }

    public void saveUserPhoto(Context context, String photo) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("photo", photo);
        editor.apply();
    }

    public String getUserPhoto() {
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return mSharedPreferences.getString("photo", "");
    }

    public void saveUserDbUid(Context context, String dbUid) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("db_uid", dbUid);
        editor.apply();
    }

    public String getUserDbUid() {
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return mSharedPreferences.getString("db_uid", "");
    }

    public void clearSharedPreferences() {
        mEditor.clear();
        mEditor.apply();
    }

}
