package com.kevinj1008.fitnessch.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.kevinj1008.fitnessch.Fitnessch;
import com.kevinj1008.fitnessch.FitnesschContract;
import com.kevinj1008.fitnessch.FitnesschPresenter;
import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.objects.Article;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.BlurTransformation;

import static com.google.common.base.Preconditions.checkNotNull;

public class FitnesschActivity extends BaseActivity implements FitnesschContract.View, NavigationView.OnNavigationItemSelectedListener {

    private FitnesschContract.Presenter mPresenter;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
//    private ImageView mBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new FitnesschPresenter(this, getFragmentManager());
        init();

    }

    private void init() {

        setContentView(R.layout.activity_main);

        // Blur background image
//        ImageView imageView = findViewById(R.id.parent_background);
//        Picasso.get()
//                .load(R.drawable.background_image_03)
//                .transform(new BlurTransformation(getBaseContext(), 65))
//                .into(imageView);

        setToolbar();
        setDrawerLayout();

        mPresenter.start();

    }

    private void setToolbar() {
        // Retrieve the AppCompact Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // Set the padding to match the Status Bar height
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        mToolbarTitle = (TextView) mToolbar.findViewById(R.id.textview_toolbar_title);
        mToolbarTitle.setText(getResources().getString(R.string.all_fitnessch));
    }

    /**
     * Set the title of toolbar.
     *
     * @param title
     */
    private void setToolbarTitle(String title) {
        mToolbarTitle.setText(title);
    }

    /**
     * Set Drawer
     */
    private void setDrawerLayout() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawrlayout_main);
        mDrawerLayout.setFitsSystemWindows(true);
        mDrawerLayout.setClipToPadding(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navview_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        // navview header
//        ImageView userImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageview_drawer_userimage);
//
//        userImage.setOutlineProvider(new AuthorOutlineProvider());
//
//        userImage.setTag(UserManager.getInstance().getUserImage());
//        new ImageFromLruCache().set(userImage, UserManager.getInstance().getUserImage());
//
//        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.imageview_drawer_useremail))
//                .setText(UserManager.getInstance().getUserEmail());

        // logout button
//        ((LinearLayout) findViewById(R.id.linearlayout_drawer_logoutbutton))
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.d(Constants.TAG, "Drawer, Logout.onClick");

//                        UserManager.getInstance().logoutVoyage(new LogoutCallback() {
//                            @Override
//                            public void onCompleted() {
//                                Log.d(Constants.TAG, "logoutVoyage.onCompleted");
//
//                                popLogin();
//                            }
//                        });
//                    }
//                });

    }

    /**
     * @return height of status bar
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }



    @Override
    public void showMainUi() {

    }

    @Override
    public void showScheduleUi() {

    }

    @Override
    public void showCalendarUi() {

    }

    @Override
    public void showProfileUi() {

    }

    @Override
    public void refreshLikedUi() {

    }

    @Override
    public void setPresenter(FitnesschContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    public void transToDetail(Article article) {
        mPresenter.transToDetail(article);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_main:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                mPresenter.transToMain();
                break;

            case R.id.nav_calendar:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                mPresenter.transToCalendar();
                break;

            case R.id.nav_profile:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                mPresenter.transToProfile();
                break;

            default:
        }
        return true;
    }
}
