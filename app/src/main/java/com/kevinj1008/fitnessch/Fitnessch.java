package com.kevinj1008.fitnessch;

import android.app.Application;
import android.content.Context;

public class Fitnessch extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getAppContext() {
        return mContext;
    }
}
