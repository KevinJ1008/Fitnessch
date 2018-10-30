package com.kevinj1008.fitnessch.activities;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.kevinj1008.fitnessch.FitnesschContract;
import com.kevinj1008.fitnessch.FitnesschPresenter;
import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.objects.Meal;
import com.kevinj1008.fitnessch.objects.Schedule;
import com.kevinj1008.fitnessch.util.Constants;
import com.kevinj1008.fitnessch.util.SharedPreferencesManager;
import com.squareup.picasso.Picasso;
import io.fabric.sdk.android.Fabric;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class FitnesschActivity extends BaseActivity implements FitnesschContract.View,
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private FitnesschContract.Presenter mPresenter;
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private FloatingActionButton mFloatingActionButton;
    private SharedPreferencesManager mSharedPreferencesManager;
    private boolean isToolBarNavListenerReg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        mPresenter = new FitnesschPresenter(this, getSupportFragmentManager());
        init();

//        Button crashButton = findViewById(R.id.test_crash_btn);
//        crashButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Crashlytics.getInstance().crash();
//            }
//        });

    }

    private void init() {

        setContentView(R.layout.activity_main);

        mFloatingActionButton = findViewById(R.id.floating_add_btn);
        mFloatingActionButton.setOnClickListener(this);

        mSharedPreferencesManager = new SharedPreferencesManager(mContext);

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
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawrlayout_main);
        mDrawerLayout.setFitsSystemWindows(true);
        mDrawerLayout.setClipToPadding(false);

        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navview_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        // navview header
        ImageView userImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageview_drawer_userimage);
        String userImageUri = mSharedPreferencesManager.getUserPhoto();
        Uri photoUri = Uri.parse(userImageUri);

        Picasso.get()
                .load(photoUri)
                .transform(new CropCircleTransformation())
                .fit()
                .into(userImage);

        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.imageview_drawer_useremail))
                .setText(mSharedPreferencesManager.getUserEmail());

        // logout button
        ((LinearLayout) findViewById(R.id.linearlayout_drawer_logoutbutton))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(Constants.TAG, "Drawer, Logout.onClick");

                        signOut();
                    }
                });

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

    private void enableToggle(boolean enable) {
        if (enable) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_backbutton);
            if (!isToolBarNavListenerReg) {
                mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
                isToolBarNavListenerReg = true;
            }
        } else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            mDrawerToggle.setToolbarNavigationClickListener(null);
            mDrawerLayout.addDrawerListener(mDrawerToggle);
            mDrawerToggle.syncState();
            isToolBarNavListenerReg = false;
        }
    }

    @Override
    public void showMainUi() {
        mFloatingActionButton.setVisibility(View.VISIBLE);
        mToolbarTitle.setText(getResources().getString(R.string.all_fitnessch));
    }

    @Override
    public void showAddNewUi() {
        mFloatingActionButton.setVisibility(View.INVISIBLE);
        mToolbarTitle.setText(R.string.all_add_new_schedule_meal);
    }

    @Override
    public void showCalendarUi() {
        mFloatingActionButton.setVisibility(View.VISIBLE);
        mToolbarTitle.setText(getResources().getString(R.string.all_calendar));
    }

    @Override
    public void showProfileUi() {
        mFloatingActionButton.setVisibility(View.VISIBLE);
        mToolbarTitle.setText(getResources().getString(R.string.all_profile));
    }

    @Override
    public void showDateUi() {
        mFloatingActionButton.setVisibility(View.VISIBLE);
        mToolbarTitle.setText(getResources().getString(R.string.all_date_schedule_meal));
    }

    @Override
    public void showDetailUi() {
        mFloatingActionButton.setVisibility(View.INVISIBLE);
//        enableToggle(true);
        mToolbarTitle.setText(getResources().getString(R.string.all_fitnessch));
    }

    @Override
    public void showUserProfileUi() {
        mFloatingActionButton.setVisibility(View.INVISIBLE);
        enableToggle(true);
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

    public void transToAddNewMealArticle(List<Meal> meals) {
        mPresenter.transToAddNewMealArticle(meals);
    }

    public void transToDate(Article article) {
        mPresenter.transToDate(article);
    }

    public void transToMain() {
        mPresenter.transToMain();
    }

    public void transToUserProfile(Article article) {
        mPresenter.transToUserProfile(article);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_main:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                mPresenter.refreshAllUi();
                mPresenter.transToMain();
                break;

            case R.id.nav_calendar:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                mPresenter.refreshAllUi();
                mPresenter.transToCalendar();
                break;

//            case R.id.nav_rm_calculator:
//                mDrawerLayout.closeDrawer(GravityCompat.START);
//                Toast.makeText(Fitnessch.getAppContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
//            mPresenter.refreshAllUi();
////                mPresenter.transToRMCalculator();
//                break;

            case R.id.nav_profile:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                mPresenter.refreshAllUi();
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
        ConstraintLayout calendarPage = findViewById(R.id.calendar_page);
        ConstraintLayout profilePage = findViewById(R.id.profile_page);
        ConstraintLayout addNewArticlePage = findViewById(R.id.fragment_addnewarticle);
        ConstraintLayout addNewMealArticlePage = findViewById(R.id.fragment_addnew_meal_article);
        ConstraintLayout addNewPage = findViewById(R.id.addnew_page);
        ConstraintLayout dateArticlePage = findViewById(R.id.date_article_page);
        ConstraintLayout rmCalculatorPage = findViewById(R.id.rm_calculator_page);
        ConstraintLayout detailPage = findViewById(R.id.detail_page);
        ConstraintLayout userProfile = findViewById(R.id.user_profile_page);
        FrameLayout mainPage = findViewById(R.id.main_page);

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if ((addNewPage != null && addNewPage.getVisibility() == View.VISIBLE)
                || (mainPage != null && mainPage.getVisibility() == View.VISIBLE)) {
            backButtonHandler();
        } else if (profilePage != null && profilePage.getVisibility() == View.VISIBLE) {
            mPresenter.transToMain();
        } else if (calendarPage != null && calendarPage.getVisibility() == View.VISIBLE) {
            mPresenter.transToMain();
            mPresenter.refreshCalendarFocus();
        } else if (addNewArticlePage != null && addNewArticlePage.getVisibility() == View.VISIBLE) {
            mPresenter.transToAddNew();
        } else if (addNewMealArticlePage != null && addNewMealArticlePage.getVisibility() == View.VISIBLE) {
            mPresenter.transToAddNew();
        } else if (dateArticlePage != null && dateArticlePage.getVisibility() == View.VISIBLE) {
            mPresenter.transToCalendar();
            mPresenter.refreshDateArticleUi();
        } else if (rmCalculatorPage != null && rmCalculatorPage.getVisibility() == View.VISIBLE) {
            mPresenter.transToMain();
        } else if (userProfile != null && userProfile.getVisibility() == View.VISIBLE) {
            enableToggle(false);
            mPresenter.transToMain();
            mPresenter.refreshUserUi();
        } else {
            if (detailPage != null && detailPage.getVisibility() == View.VISIBLE) {
                mPresenter.refreshDetailUi();
            }
            super.onBackPressed();
        }
    }

    public void backButtonHandler() {
        ConstraintLayout addNewPage = findViewById(R.id.addnew_page);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FitnesschActivity.this);
        if (addNewPage != null && addNewPage.getVisibility() == View.VISIBLE) {
            alertDialog.setTitle("確定要離開此頁面？");
            alertDialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //TODO: clear data
                    mPresenter.transToMain();
                    mPresenter.refreshAddNewUi();
                }
            });
            alertDialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
        } else {
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
        }
        alertDialog.show();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(FitnesschActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void signOut() {
        mSharedPreferencesManager.clearSharedPreferences();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(FitnesschActivity.this, FitnesschLoginActivity.class);
        startActivity(intent);
        finish();
    }

}
