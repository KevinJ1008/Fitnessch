package com.kevinj1008.fitnessch;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.StringDef;

import com.kevinj1008.fitnessch.main.MainFragment;
import com.kevinj1008.fitnessch.main.MainPresenter;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.profile.ProfileFragment;
import com.kevinj1008.fitnessch.profile.ProfilePresenter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.google.common.base.Preconditions.checkNotNull;

public class FitnesschPresenter implements FitnesschContract.Presenter {

    private final FitnesschContract.View mFitnesschView;
    private FragmentManager mFragmentManager;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            MAIN, SCHEDULE, PROFILE, DETAIL, CALENDAR
    })
    public @interface FragmentType {}
    public static final String MAIN     = "MAIN";
    public static final String SCHEDULE = "SCHEDULE";
    public static final String PROFILE  = "PROFILE";
    public static final String DETAIL  = "DETAIL";
    public static final String CALENDAR = "CALENDAR";

    private MainFragment mMainFragment;
    private ProfileFragment mProfileFragment;

    private MainPresenter mMainPresenter;
    private ProfilePresenter mProfilePresenter;

    public FitnesschPresenter(FitnesschContract.View fitnesschView, FragmentManager fragmentManager) {
        mFitnesschView = checkNotNull(fitnesschView, "fitnesschView cannot be null!");
        mFitnesschView.setPresenter(this);

        mFragmentManager = fragmentManager;
    }

    @Override
    public void start() {
        transToMain();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void transToMain() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
//        if (mFragmentManager.findFragmentByTag(FRIEND) != null) mFragmentManager.popBackStack();

//        if (mFragmentManager.findFragmentByTag(DETAIL) != null) mFragmentManager.popBackStack();
//        if (mFragmentManager.findFragmentByTag(SCHEDULE) != null) mFragmentManager.popBackStack();
        if (mMainFragment == null) mMainFragment = MainFragment.newInstance();
//        if (mDiscoverFragment != null) transaction.hide(mDiscoverFragment);
        if (mProfileFragment != null) transaction.hide(mProfileFragment);
//        if (mChatFragment != null) transaction.hide(mChatFragment);
        if (!mMainFragment.isAdded()) {
            transaction.add(R.id.linearlayout_main_container, mMainFragment, MAIN);
        } else {
            transaction.show(mMainFragment);
        }


        if (mMainPresenter == null) {
            mMainPresenter = new MainPresenter(mMainFragment);
        }
        transaction.commit();

        mFitnesschView.showMainUi();
    }

    @Override
    public void transToSchedule() {

    }

    @Override
    public void transToProfile() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
//        if (mFragmentManager.findFragmentByTag(FRIEND) != null) mFragmentManager.popBackStack();

//        if (mFragmentManager.findFragmentByTag(DETAIL) != null) mFragmentManager.popBackStack();
//        if (mFragmentManager.findFragmentByTag(SCHEDULE) != null) mFragmentManager.popBackStack();
        if (mProfileFragment == null) mProfileFragment = ProfileFragment.newInstance();
//        if (mDiscoverFragment != null) transaction.hide(mDiscoverFragment);
        if (mMainFragment != null) transaction.hide(mMainFragment);
//        if (mChatFragment != null) transaction.hide(mChatFragment);
        if (!mProfileFragment.isAdded()) {
            transaction.add(R.id.linearlayout_main_container, mProfileFragment, PROFILE);
        } else {
            transaction.show(mProfileFragment);
        }


        if (mProfilePresenter == null) {
            mProfilePresenter = new ProfilePresenter(mProfileFragment, mProfileFragment.getFragmentManager());
        }
        transaction.commit();

        mFitnesschView.showProfileUi();
    }

    @Override
    public void transToDetail(Article article) {

    }

    @Override
    public void refreshLiked() {

    }

    @Override
    public void transToCalendar() {

    }


}
