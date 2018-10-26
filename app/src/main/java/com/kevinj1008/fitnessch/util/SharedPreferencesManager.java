package com.kevinj1008.fitnessch.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private SharedPreferences mSharedPreferences;
    private Context mContext;
    private static final int  MODE_PRIVATE = 0;
    private static final String PREF_NAME = "User_Profile";
    private SharedPreferences.Editor mEditor;

    public SharedPreferencesManager(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void saveUserIdToken(String token) {
        mEditor.putString("access_token", token);
        mEditor.apply();
    }

    public String getUserIdToken() {
        return mSharedPreferences.getString("access_token", "");
    }

    public void saveUserId(String id) {
        mEditor.putString("id", id);
        mEditor.apply();
    }

    public String getUserId() {
        return mSharedPreferences.getString("id", "");
    }

    public void saveUserEmail(String email) {
        mEditor.putString("mail", email);
        mEditor.apply();
    }

    public String getUserEmail() {
        return mSharedPreferences.getString("mail", "");
    }

    public void saveUserName(String name) {
        mEditor.putString("name", name);
        mEditor.apply();
    }

    public String getUserName() {
        return mSharedPreferences.getString("name", "");
    }

    public void saveUserPhoto(String photo) {
        mEditor.putString("photo", photo);
        mEditor.apply();
    }

    public String getUserPhoto() {
        return mSharedPreferences.getString("photo", "");
    }

    public void saveUserDbUid(String dbUid) {
        mEditor.putString("db_uid", dbUid);
        mEditor.apply();
    }

    public String getUserDbUid() {
        return mSharedPreferences.getString("db_uid", "");
    }

    public void clearSharedPreferences() {
        mEditor.clear();
        mEditor.apply();
    }

}
