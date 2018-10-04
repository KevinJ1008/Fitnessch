package com.kevinj1008.fitnessch.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.github.clans.fab.FloatingActionButton;
import com.kevinj1008.fitnessch.FitnesschContract;
import com.kevinj1008.fitnessch.FitnesschPresenter;
import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.objects.Schedule;


import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class FitnesschActivity extends BaseActivity implements FitnesschContract.View, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private FitnesschContract.Presenter mPresenter;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private FloatingActionButton mFloatingActionButton;
//    private ImageView mBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new FitnesschPresenter(this, getSupportFragmentManager());
        init();

    }

    private void init() {

        setContentView(R.layout.activity_main);

        mFloatingActionButton = findViewById(R.id.floating_add_btn);
        mFloatingActionButton.setOnClickListener(this);

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
        mFloatingActionButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAddNewUi() {
        mFloatingActionButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showCalendarUi() {
        mFloatingActionButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProfileUi() {
        mFloatingActionButton.setVisibility(View.VISIBLE);
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

    public void transToAddNewArticle(List<Schedule> schedules) {
        mPresenter.transToAddNewArticle(schedules);
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

    @Override
    public void onClick(View view) {
        mPresenter.transToAddNew();
    }

    @Override
    public void onBackPressed() {
        backButtonHandler();
        return;
    }

    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FitnesschActivity.this);
        alertDialog.setTitle("確定離開 Fitnessch？");
        alertDialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alertDialog.show();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(FitnesschActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
