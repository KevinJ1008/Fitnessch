package com.kevinj1008.fitnessch.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 0;
    public static final int NETWORK_STATUS_NOT_CONNECTED = 3;
    public static final int NETWORK_STATUS_WIFI = 4;
    public static final int NETWORK_STATUS_MOBILE = 5;

    private Context mContext;

    /**
     * Public constructor that takes mContext for later use
     */
    public NetworkUtils(Context context) {
        mContext = context;
    }

    /**
     * Encode user email to use it as a Firebase key (Firebase does not allow "." in the key name)
     * Encoded email is also used as "userEmail", list and item "owner" value
     */
    public static String encodeEmail(String userEmail) {
        return userEmail.replace(".", ",");
    }

    //This is a method to Check if the device internet connection is currently on
    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager

                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

    private static int getConnectivityStatus(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            }
        }
        return TYPE_NOT_CONNECTED;
    }

    public static int getConnectivityStatusString(Context context) {
        int connectivityStatus = NetworkUtils.getConnectivityStatus(context);
        int statusCode = 0;
        if (connectivityStatus == NetworkUtils.TYPE_WIFI) {
            statusCode = NETWORK_STATUS_WIFI;
        } else if (connectivityStatus == NetworkUtils.TYPE_MOBILE) {
            statusCode = NETWORK_STATUS_MOBILE;
        } else if (connectivityStatus == NetworkUtils.TYPE_NOT_CONNECTED) {
            statusCode = NETWORK_STATUS_NOT_CONNECTED;
        }
        return statusCode;
    }
}
