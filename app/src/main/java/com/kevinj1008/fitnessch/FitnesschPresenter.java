package com.kevinj1008.fitnessch;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.StringDef;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.kevinj1008.fitnessch.addnew.AddNewFragment;
import com.kevinj1008.fitnessch.addnew.AddNewPresenter;
import com.kevinj1008.fitnessch.addnewarticle.AddNewArticleFragment;
import com.kevinj1008.fitnessch.addnewarticle.AddNewArticlePresenter;
import com.kevinj1008.fitnessch.addnewschedulechild.AddNewScheduleChildFragment;
import com.kevinj1008.fitnessch.calendar.CalendarFragment;
import com.kevinj1008.fitnessch.calendar.CalendarPresenter;
import com.kevinj1008.fitnessch.datearticle.DateArticleFragment;
import com.kevinj1008.fitnessch.datearticle.DateArticlePresenter;
import com.kevinj1008.fitnessch.detail.DetailFragment;
import com.kevinj1008.fitnessch.detail.DetailPresenter;
import com.kevinj1008.fitnessch.main.MainFragment;
import com.kevinj1008.fitnessch.main.MainPresenter;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.objects.Schedule;
import com.kevinj1008.fitnessch.profile.ProfileFragment;
import com.kevinj1008.fitnessch.profile.ProfilePresenter;
import com.kevinj1008.fitnessch.util.SharedPreferencesManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class FitnesschPresenter implements FitnesschContract.Presenter {

    private final FitnesschContract.View mFitnesschView;
    private FragmentManager mFragmentManager;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            MAIN, ADDNEW, PROFILE, DETAIL, CALENDAR
    })
    public @interface FragmentType {}
    public static final String MAIN     = "MAIN";
    public static final String ADDNEW = "ADDNEW";
    public static final String PROFILE  = "PROFILE";
    public static final String DETAIL  = "DETAIL";
    public static final String CALENDAR = "CALENDAR";
    public static final String ADDNEW_ARTICLE = "ADDNEWARTICLE";
    public static final String DATEARTICLE = "DATEARTICLE";

    private MainFragment mMainFragment;
    private ProfileFragment mProfileFragment;
    private CalendarFragment mCalendarFragment;
    private AddNewFragment mAddNewFragment;
    private AddNewArticleFragment mAddNewArticleFragment;
    private AddNewScheduleChildFragment mAddNewScheduleChildFragment;
    private DateArticleFragment mDateArticleFragment;

    private MainPresenter mMainPresenter;
    private ProfilePresenter mProfilePresenter;
    private CalendarPresenter mCalendarPresenter;
    private AddNewPresenter mAddNewPresenter;
    private AddNewArticlePresenter mAddNewArticlePresenter;
    private DetailPresenter mDetailPresenter;
    private DateArticlePresenter mDateArticlePresenter;

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
//        if (mFragmentManager.findFragmentByTag(ADDNEW) != null) mFragmentManager.popBackStack();

        if (mFragmentManager.findFragmentByTag(DETAIL) != null) {
            mFragmentManager.popBackStack();
            mDetailPresenter.refreshDetailUi();
        }
//        if (mFragmentManager.findFragmentByTag(SCHEDULE) != null) mFragmentManager.popBackStack();
        if (mMainFragment == null) mMainFragment = MainFragment.newInstance();
        if (mCalendarFragment != null) transaction.hide(mCalendarFragment);
        if (mProfileFragment != null) transaction.hide(mProfileFragment);
        if (mAddNewFragment != null) transaction.hide(mAddNewFragment);
        if (mAddNewArticleFragment != null) transaction.hide(mAddNewArticleFragment);
        if (mDateArticleFragment != null) transaction.hide(mDateArticleFragment);
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
    public void transToAddNew() {
//        if (mFragmentManager.findFragmentByTag(ADDNEW) != null) mFragmentManager.popBackStack();
//        FragmentTransaction transaction = mFragmentManager.beginTransaction();
//
//        if (mMainFragment != null && !mMainFragment.isHidden()) {
//            transaction.hide(mMainFragment);
//            transaction.addToBackStack(MAIN);
//        }
//        if (mProfileFragment != null && !mProfileFragment.isHidden()) {
//            transaction.hide(mProfileFragment);
//            transaction.addToBackStack(PROFILE);
//        }
//        if (mCalendarFragment != null && !mCalendarFragment.isHidden()) {
//            transaction.hide(mCalendarFragment);
//            transaction.addToBackStack(CALENDAR);
//        }
//        AddNewFragment addNewFragment = AddNewFragment.newInstance();
//        transaction.add(R.id.linearlayout_main_container, addNewFragment, ADDNEW);
//
//        mAddNewPresenter = new AddNewPresenter(addNewFragment, addNewFragment.getFragmentManager());
//
//        transaction.commit();

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mFragmentManager.findFragmentByTag(DETAIL) != null) {
            mFragmentManager.popBackStack();
            mDetailPresenter.refreshDetailUi();
        }
        if (mAddNewFragment == null) mAddNewFragment = AddNewFragment.newInstance();
        if (mCalendarFragment != null) transaction.hide(mCalendarFragment);
        if (mMainFragment != null) transaction.hide(mMainFragment);
        if (mProfileFragment != null) transaction.hide(mProfileFragment);
        if (mAddNewArticleFragment != null) transaction.hide(mAddNewArticleFragment);
        if (mDateArticleFragment != null) transaction.hide(mDateArticleFragment);
        if (!mAddNewFragment.isAdded()) {
            transaction.add(R.id.linearlayout_main_container, mAddNewFragment, ADDNEW);
        } else {
            transaction.show(mAddNewFragment);
        }


        if (mAddNewPresenter == null) {
            mAddNewPresenter = new AddNewPresenter(mAddNewFragment, mAddNewFragment.getFragmentManager());
        }
        transaction.commit();

        mFitnesschView.showAddNewUi();
    }

    @Override
    public void transToProfile() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
//        if (mFragmentManager.findFragmentByTag(ADDNEW) != null) mFragmentManager.popBackStack();

        if (mFragmentManager.findFragmentByTag(DETAIL) != null) {
            mFragmentManager.popBackStack();
            mDetailPresenter.refreshDetailUi();
        }
//        if (mFragmentManager.findFragmentByTag(SCHEDULE) != null) mFragmentManager.popBackStack();
        if (mProfileFragment == null) mProfileFragment = ProfileFragment.newInstance();
        if (mCalendarFragment != null) transaction.hide(mCalendarFragment);
        if (mMainFragment != null) transaction.hide(mMainFragment);
        if (mAddNewFragment != null) transaction.hide(mAddNewFragment);
        if (mAddNewArticleFragment != null) transaction.hide(mAddNewArticleFragment);
        if (mDateArticleFragment != null) transaction.hide(mDateArticleFragment);
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
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (mMainFragment != null && !mMainFragment.isHidden()) {
            transaction.hide(mMainFragment);
            transaction.addToBackStack(MAIN);
        }
        if (mCalendarFragment != null && !mCalendarFragment.isHidden()) {
            transaction.hide(mCalendarFragment);
            transaction.addToBackStack(CALENDAR);
        }
        if (mProfileFragment != null && !mProfileFragment.isHidden()) {
            transaction.hide(mProfileFragment);
            transaction.addToBackStack(PROFILE);
        }
        if (mDateArticleFragment != null && !mDateArticleFragment.isHidden()) {
            transaction.hide(mDateArticleFragment);
            transaction.addToBackStack(DATEARTICLE);
        }
        DetailFragment detailFragment = DetailFragment.newInstance();
        transaction.add(R.id.linearlayout_main_container, detailFragment, DETAIL);
        transaction.commit();

        mDetailPresenter = new DetailPresenter(detailFragment, article);
    }

    @Override
    public void refreshLiked() {

    }

    @Override
    public void transToCalendar() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
//        if (mFragmentManager.findFragmentByTag(ADDNEW) != null) mFragmentManager.popBackStack();

        if (mFragmentManager.findFragmentByTag(DETAIL) != null) {
            mFragmentManager.popBackStack();
            mDetailPresenter.refreshDetailUi();
        }
//        if (mFragmentManager.findFragmentByTag(SCHEDULE) != null) mFragmentManager.popBackStack();
        if (mCalendarFragment == null) mCalendarFragment = CalendarFragment.newInstance();
        if (mProfileFragment != null) transaction.hide(mProfileFragment);
        if (mMainFragment != null) transaction.hide(mMainFragment);
        if (mAddNewFragment != null) transaction.hide(mAddNewFragment);
        if (mAddNewArticleFragment != null) transaction.hide(mAddNewArticleFragment);
        if (mDateArticleFragment != null) transaction.hide(mDateArticleFragment);
        if (!mCalendarFragment.isAdded()) {
            transaction.add(R.id.linearlayout_main_container, mCalendarFragment, CALENDAR);
        } else {
            transaction.show(mCalendarFragment);
            mCalendarPresenter.reloadArticle();
        }


        if (mCalendarPresenter == null) {
            mCalendarPresenter = new CalendarPresenter(mCalendarFragment);
        }
        transaction.commit();

        mFitnesschView.showCalendarUi();
    }

    @Override
    public void transToAddNewArticle(List<Schedule> schedules) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mFragmentManager.findFragmentByTag(DETAIL) != null) {
            mFragmentManager.popBackStack();
            mDetailPresenter.refreshDetailUi();
        }

        if (mAddNewArticleFragment == null) mAddNewArticleFragment = AddNewArticleFragment.newInstance();
        if (mProfileFragment != null) transaction.hide(mProfileFragment);
        if (mMainFragment != null) transaction.hide(mMainFragment);
        if (mAddNewFragment != null) transaction.hide(mAddNewFragment);
        if (mCalendarFragment != null) transaction.hide(mCalendarFragment);
        if (mDateArticleFragment != null) transaction.hide(mDateArticleFragment);

        if (!mAddNewArticleFragment.isAdded()) {
            transaction.add(R.id.linearlayout_main_container, mAddNewArticleFragment, ADDNEW_ARTICLE);
        } else {
            transaction.show(mAddNewArticleFragment);
        }


        if (mAddNewArticlePresenter == null) {
            mAddNewArticlePresenter = new AddNewArticlePresenter(mAddNewArticleFragment, mAddNewFragment, schedules);
        }
        transaction.commit();
    }

    @Override
    public void transToDate(Article article) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mFragmentManager.findFragmentByTag(DETAIL) != null) {
            mFragmentManager.popBackStack();
            mDetailPresenter.refreshDetailUi();
        }

        if (mDateArticleFragment == null) mDateArticleFragment = DateArticleFragment.newInstance();
        if (mProfileFragment != null) transaction.hide(mProfileFragment);
        if (mMainFragment != null) transaction.hide(mMainFragment);
        if (mAddNewFragment != null) transaction.hide(mAddNewFragment);
        if (mCalendarFragment != null) transaction.hide(mCalendarFragment);

        if (!mDateArticleFragment.isAdded()) {
            transaction.add(R.id.linearlayout_main_container, mDateArticleFragment, DATEARTICLE);
        } else {
            transaction.show(mDateArticleFragment);
            mDateArticlePresenter.changeDateUi(article);
        }

        if (mDateArticlePresenter == null) {
            mDateArticlePresenter = new DateArticlePresenter(mDateArticleFragment, article);
        }
        transaction.commit();

    }

    @Override
    public void refreshAddNewUi() {
        mAddNewFragment.refreshSchedule();
    }

    @Override
    public void refreshAddNewArticleUi() {
        if (mAddNewArticleFragment != null) {
            mAddNewArticleFragment.refreshUi();
        }
    }

    @Override
    public void refreshDateArticleUi() {
        mDateArticlePresenter.refreshDateUi();
    }

    @Override
    public void refreshCalendarFocus() {
        mCalendarPresenter.refresh();
    }

}
