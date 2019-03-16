package com.kevinj1008.fitnessch.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.kevinj1008.fitnessch.Fitnessch;
import com.kevinj1008.fitnessch.R;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String connectivityChange = "android.net.conn.CONNECTIVITY_CHANGE";
        int networkStatus = NetworkUtils.getConnectivityStatusString(context);
        if (connectivityChange.equals(intent.getAction())) {
            if (networkStatus == NetworkUtils.NETWORK_STATUS_NOT_CONNECTED) {
                networkCheckDialog(context);
            }
        }
    }

    private void networkCheckDialog(Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        alertDialog.setTitle("請檢查網路連線。");
        alertDialog.setPositiveButton("OK", null);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkUtils networkUtils = new NetworkUtils(Fitnessch.getAppContext());
                if (networkUtils.isNetworkAvailable()) {
                    dialog.cancel();
                }
            }
        });
    }

}
