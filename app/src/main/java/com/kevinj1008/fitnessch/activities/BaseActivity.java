package com.kevinj1008.fitnessch.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.constraint.ConstraintLayout;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.util.DelayedProgressDialog;
import com.kevinj1008.fitnessch.receiver.NetworkChangeReceiver;


public class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    private DelayedProgressDialog mProgressBar;
    private NetworkChangeReceiver mNetworkChangeReceiver;
    private final String IS_RELAUNCH = "is_relaunch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mContext = this;

        if (savedInstanceState != null && savedInstanceState.containsKey(IS_RELAUNCH)) {
            if (savedInstanceState.getBoolean(IS_RELAUNCH, false)) restartApplication();
        }

        setStatusBar();

        IntentFilter intentFilter = new IntentFilter();
        mNetworkChangeReceiver = new NetworkChangeReceiver();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(mNetworkChangeReceiver, intentFilter);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(IS_RELAUNCH, true);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetworkChangeReceiver);
    }

    /**
    * Restart application when activity recycle by system.
     */
    private void restartApplication() {
        // Intent to start launcher activity and closing all previous ones
        Intent restartIntent = new Intent(getApplicationContext(), FitnesschLoginActivity.class);
        restartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(restartIntent);

        // Kill Current Process
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    /**
     * To change status bar to transparent.
     * @notice this method have to be used before setContentView.
     */
    private void setStatusBar() {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);//calculateStatusColor(Color.WHITE, (int) alphaValue)
    }

    /**
     * To change status bar to transparent and make layout no limit to login page.
     * @notice this method have to be used before setContentView.
     */
    public void setLoginStatusBar() {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            window.setNavigationBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(Color.TRANSPARENT);//calculateStatusColor(Color.WHITE, (int) alphaValue)
    }


    public void showProgressBar() {
        ConstraintLayout layout = findViewById(R.id.login_page);
        if (mProgressBar == null) {
            mProgressBar = new DelayedProgressDialog();
            mProgressBar.show(getSupportFragmentManager(), "tag");
//            mProgressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200, 200);
//            params.addRule(RelativeLayout.CENTER_IN_PARENT);
//            layout.addView(mProgressBar, params);
//            mProgressBar.setIndeterminate(true);
//            mProgressBar.setVisibility(View.VISIBLE);
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    public void hideProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.cancel();
        }
//        if (mProgressBar != null && mProgressBar.isIndeterminate()) {
//            mProgressBar.setIndeterminate(false);
//            mProgressBar.setVisibility(View.GONE);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        }
    }

}
