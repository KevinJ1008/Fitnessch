package com.kevinj1008.fitnessch;

import static com.google.common.base.Preconditions.checkNotNull;

import android.support.annotation.StringDef;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.kevinj1008.fitnessch.addnew.AddNewFragment;
import com.kevinj1008.fitnessch.addnew.AddNewPresenter;
import com.kevinj1008.fitnessch.addnewarticle.AddNewArticleFragment;
import com.kevinj1008.fitnessch.addnewarticle.AddNewArticlePresenter;
import com.kevinj1008.fitnessch.addnewmealarticle.AddNewMealArticleFragment;
import com.kevinj1008.fitnessch.addnewmealarticle.AddNewMealArticlePresenter;
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
import com.kevinj1008.fitnessch.objects.Meal;
import com.kevinj1008.fitnessch.objects.Schedule;
import com.kevinj1008.fitnessch.profile.ProfileFragment;
import com.kevinj1008.fitnessch.profile.ProfilePresenter;
import com.kevinj1008.fitnessch.rmcalculator.RmCalculatorFragment;
import com.kevinj1008.fitnessch.userprofile.UserProfileFragment;
import com.kevinj1008.fitnessch.userprofile.UserProfilePresenter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;


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
    public static final String ADDNEW_MEAL_ARTICLE = "ADDNEWMEALARTICLE";
    public static final String DATEARTICLE = "DATEARTICLE";
    public static final String RMCALCULATOR = "RMCALCULATOR";
    public static final String USERPROFILE = "USERPROFILE";

    private MainFragment mMainFragment;
    private ProfileFragment mProfileFragment;
    private CalendarFragment mCalendarFragment;
    private AddNewFragment mAddNewFragment;
    private AddNewArticleFragment mAddNewArticleFragment;
    private AddNewMealArticleFragment mAddNewMealArticleFragment;
    private AddNewScheduleChildFragment mAddNewScheduleChildFragment;
    private DateArticleFragment mDateArticleFragment;
    private RmCalculatorFragment mRmCalculatorFragment;
    private UserProfileFragment mUserProfileFragment;
    private DetailFragment mDetailFragment;

    private MainPresenter mMainPresenter;
    private ProfilePresenter mProfilePresenter;
    private CalendarPresenter mCalendarPresenter;
    private AddNewPresenter mAddNewPresenter;
    private AddNewArticlePresenter mAddNewArticlePresenter;
    private AddNewMealArticlePresenter mAddNewMealArticlePresenter;
    private DetailPresenter mDetailPresenter;
    private DateArticlePresenter mDateArticlePresenter;
    private UserProfilePresenter mUserProfilePresenter;

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

        if (mFragmentManager.findFragmentByTag(DETAIL) != null) {
            mFragmentManager.popBackStack();
            mDetailPresenter.refreshDetailUi();
        }

        if (mFragmentManager.findFragmentByTag(USERPROFILE) != null) {
            mFragmentManager.popBackStack();
            mUserProfilePresenter.refresh();
        }
        if (mMainFragment == null) mMainFragment = MainFragment.newInstance();
        if (mCalendarFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mCalendarFragment);
        }
        if (mProfileFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mProfileFragment);
        }
        if (mAddNewFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mAddNewFragment);
        }
        if (mAddNewArticleFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mAddNewArticleFragment);
        }
        if (mAddNewMealArticleFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mAddNewMealArticleFragment);
        }
        if (mDateArticleFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mDateArticleFragment);
        }
        if (mRmCalculatorFragment != null) transaction.hide(mRmCalculatorFragment);
        if (!mMainFragment.isAdded()) {
            transaction.setCustomAnimations(
                    R.anim.slide_bottom_in,
                    R.anim.slide_left_out,
                    R.anim.slide_bottom_in,
                    R.anim.slide_right_out)
                    .add(R.id.linearlayout_main_container, mMainFragment, MAIN);
        } else {

            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_bottom_in,
                    R.anim.slide_right_out)
                    .show(mMainFragment);
        }


        if (mMainPresenter == null) {
            mMainPresenter = new MainPresenter(mMainFragment);
        }
        transaction.commit();

        mFitnesschView.showMainUi();
    }

    @Override
    public void transToAddNew() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mFragmentManager.findFragmentByTag(DETAIL) != null) {
            mFragmentManager.popBackStack();
            mDetailPresenter.refreshDetailUi();
        }

        if (mFragmentManager.findFragmentByTag(USERPROFILE) != null) {
            mFragmentManager.popBackStack();
            mUserProfilePresenter.refresh();
        }
        if (mAddNewFragment == null) mAddNewFragment = AddNewFragment.newInstance();
        if (mCalendarFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mCalendarFragment);
        }
        if (mMainFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mMainFragment);
        }
        if (mProfileFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mProfileFragment);
        }
        if (mAddNewArticleFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_right_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mAddNewArticleFragment);
        }
        if (mAddNewMealArticleFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_right_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mAddNewMealArticleFragment);
        }
        if (mDateArticleFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mDateArticleFragment);
        }
        if (mRmCalculatorFragment != null) transaction.hide(mRmCalculatorFragment);
        if (!mAddNewFragment.isAdded()) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .add(R.id.linearlayout_main_container, mAddNewFragment, ADDNEW);
        } else {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .show(mAddNewFragment);
        }


        if (mAddNewPresenter == null) {
            mAddNewPresenter = new AddNewPresenter(mAddNewFragment, mAddNewFragment.getFragmentManager());
        }
        transaction.commit();

        mFitnesschView.showAddNewUi();
    }

    @Override
    public void transToRmCalculator() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (mFragmentManager.findFragmentByTag(DETAIL) != null) {
            mFragmentManager.popBackStack();
            mDetailPresenter.refreshDetailUi();
        }

        if (mFragmentManager.findFragmentByTag(USERPROFILE) != null) {
            mFragmentManager.popBackStack();
            mUserProfilePresenter.refresh();
        }

        if (mRmCalculatorFragment == null) mRmCalculatorFragment = RmCalculatorFragment.newInstance();
        if (mCalendarFragment != null) transaction.hide(mCalendarFragment);
        if (mMainFragment != null) transaction.hide(mMainFragment);
        if (mAddNewFragment != null) transaction.hide(mAddNewFragment);
        if (mAddNewArticleFragment != null) transaction.hide(mAddNewArticleFragment);
        if (mAddNewMealArticleFragment != null) transaction.hide(mAddNewMealArticleFragment);
        if (mDateArticleFragment != null) transaction.hide(mDateArticleFragment);
        if (!mRmCalculatorFragment.isAdded()) {
            transaction.add(R.id.linearlayout_main_container, mRmCalculatorFragment, RMCALCULATOR);
        } else {
            if (mAddNewArticleFragment != null) mAddNewArticlePresenter.refresh();
            if (mAddNewMealArticleFragment != null) mAddNewMealArticlePresenter.refresh();
            if (mAddNewFragment != null) mAddNewPresenter.refreshUi();
            transaction.show(mRmCalculatorFragment);
        }

        transaction.commit();

    }

    @Override
    public void transToProfile() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (mFragmentManager.findFragmentByTag(DETAIL) != null) {
            mFragmentManager.popBackStack();
            mDetailPresenter.refreshDetailUi();
        }

        if (mFragmentManager.findFragmentByTag(USERPROFILE) != null) {
            mFragmentManager.popBackStack();
            mUserProfilePresenter.refresh();
        }
        if (mProfileFragment == null) {
            mProfileFragment = ProfileFragment.newInstance();
            if (mAddNewArticleFragment != null) mAddNewArticlePresenter.refresh();
            if (mAddNewMealArticleFragment != null) mAddNewMealArticlePresenter.refresh();
            if (mAddNewFragment != null) mAddNewPresenter.refreshUi();
        }
        if (mCalendarFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mCalendarFragment);
        }
        if (mMainFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mMainFragment);
        }
        if (mAddNewFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mAddNewFragment);
        }
        if (mAddNewArticleFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mAddNewArticleFragment);
        }
        if (mAddNewMealArticleFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mAddNewMealArticleFragment);
        }
        if (mDateArticleFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mDateArticleFragment);
        }
        if (mRmCalculatorFragment != null) transaction.hide(mRmCalculatorFragment);
        if (!mProfileFragment.isAdded()) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .add(R.id.linearlayout_main_container, mProfileFragment, PROFILE);
        } else {
            if (mAddNewArticleFragment != null) mAddNewArticlePresenter.refresh();
            if (mAddNewMealArticleFragment != null) mAddNewMealArticlePresenter.refresh();
            if (mAddNewFragment != null) mAddNewPresenter.refreshUi();
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .show(mProfileFragment);
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

        if (mFragmentManager.findFragmentByTag(USERPROFILE) != null) {
            mFragmentManager.popBackStack();
            mUserProfilePresenter.refresh();
        }

        if (mMainFragment != null && !mMainFragment.isHidden()) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_bottom_out,
                    R.anim.slide_bottom_in,
                    R.anim.slide_right_out)
                    .hide(mMainFragment);
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_bottom_out,
                    R.anim.slide_bottom_in,
                    R.anim.slide_right_out)
                    .addToBackStack(MAIN);
        }
        if (mCalendarFragment != null && !mCalendarFragment.isHidden()) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mCalendarFragment);
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .addToBackStack(CALENDAR);
        }
        if (mProfileFragment != null && !mProfileFragment.isHidden()) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_bottom_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mProfileFragment);
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_bottom_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .addToBackStack(PROFILE);
        }
        if (mDateArticleFragment != null && !mDateArticleFragment.isHidden()) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_bottom_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mDateArticleFragment);
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_bottom_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .addToBackStack(DATEARTICLE);
        }
        mDetailFragment = DetailFragment.newInstance();
        transaction.setCustomAnimations(
                R.anim.slide_top_in,
                R.anim.slide_left_out,
                R.anim.slide_bottom_in,
                R.anim.slide_top_out)
                .add(R.id.linearlayout_main_container, mDetailFragment, DETAIL);
        transaction.commit();

        mDetailPresenter = new DetailPresenter(mDetailFragment, article);

//        mFitnesschView.showDetailUi();
    }

    @Override
    public void refreshLiked() {

    }

    @Override
    public void transToCalendar() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (mFragmentManager.findFragmentByTag(DETAIL) != null) {
            mFragmentManager.popBackStack();
            mDetailPresenter.refreshDetailUi();
        }

        if (mFragmentManager.findFragmentByTag(USERPROFILE) != null) {
            mFragmentManager.popBackStack();
            mUserProfilePresenter.refresh();
        }

        if (mCalendarFragment == null) {
            mCalendarFragment = CalendarFragment.newInstance();
            if (mAddNewArticleFragment != null) mAddNewArticlePresenter.refresh();
            if (mAddNewMealArticleFragment != null) mAddNewMealArticlePresenter.refresh();
            if (mAddNewFragment != null) mAddNewPresenter.refreshUi();
        }
        if (mProfileFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mProfileFragment);
        }
        if (mMainFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mMainFragment);
        }
        if (mAddNewFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mAddNewFragment);
        }
        if (mAddNewArticleFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mAddNewArticleFragment);
        }
        if (mAddNewMealArticleFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mAddNewMealArticleFragment);
        }
        if (mDateArticleFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mDateArticleFragment);
        }
        if (mRmCalculatorFragment != null) transaction.hide(mRmCalculatorFragment);
        if (!mCalendarFragment.isAdded()) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_top_out,
                    R.anim.slide_bottom_in)
                    .add(R.id.linearlayout_main_container, mCalendarFragment, CALENDAR);
        } else {
            if (mAddNewArticleFragment != null) mAddNewArticlePresenter.refresh();
            if (mAddNewMealArticleFragment != null) mAddNewMealArticlePresenter.refresh();
            if (mAddNewFragment != null) mAddNewPresenter.refreshUi();
            transaction.setCustomAnimations(
                            R.anim.slide_right_in,
                            R.anim.slide_left_out,
                            R.anim.slide_top_out,
                            R.anim.slide_bottom_in)
                    .show(mCalendarFragment);
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

        if (mFragmentManager.findFragmentByTag(USERPROFILE) != null) {
            mFragmentManager.popBackStack();
            mUserProfilePresenter.refresh();
        }

        if (mAddNewArticleFragment == null) mAddNewArticleFragment = AddNewArticleFragment.newInstance();
        if (mProfileFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mProfileFragment);
        }
        if (mMainFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mMainFragment);
        }
        if (mAddNewFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mAddNewFragment);
        }
        if (mCalendarFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mCalendarFragment);
        }
        if (mDateArticleFragment != null) transaction.hide(mDateArticleFragment);
        if (mAddNewMealArticleFragment != null) transaction.hide(mAddNewMealArticleFragment);
        if (mRmCalculatorFragment != null) transaction.hide(mRmCalculatorFragment);

        if (!mAddNewArticleFragment.isAdded()) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .add(R.id.linearlayout_main_container, mAddNewArticleFragment, ADDNEW_ARTICLE);
        } else {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .show(mAddNewArticleFragment);
        }


        if (mAddNewArticlePresenter == null) {
            mAddNewArticlePresenter = new AddNewArticlePresenter(mAddNewArticleFragment,
                    mAddNewFragment, mAddNewMealArticleFragment, schedules);
        }
        transaction.commit();
    }

    @Override
    public void transToAddNewMealArticle(List<Meal> meals) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mFragmentManager.findFragmentByTag(DETAIL) != null) {
            mFragmentManager.popBackStack();
            mDetailPresenter.refreshDetailUi();
        }

        if (mFragmentManager.findFragmentByTag(USERPROFILE) != null) {
            mFragmentManager.popBackStack();
            mUserProfilePresenter.refresh();
        }

        if (mAddNewMealArticleFragment == null) mAddNewMealArticleFragment = AddNewMealArticleFragment.newInstance();
        if (mProfileFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mProfileFragment);
        }
        if (mMainFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mMainFragment);
        }
        if (mAddNewFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mAddNewFragment);
        }
        if (mCalendarFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mCalendarFragment);
        }
        if (mDateArticleFragment != null) transaction.hide(mDateArticleFragment);
        if (mAddNewArticleFragment != null) transaction.hide(mAddNewArticleFragment);
        if (mRmCalculatorFragment != null) transaction.hide(mRmCalculatorFragment);

        if (!mAddNewMealArticleFragment.isAdded()) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .add(R.id.linearlayout_main_container, mAddNewMealArticleFragment, ADDNEW_MEAL_ARTICLE);
        } else {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .show(mAddNewMealArticleFragment);
        }


        if (mAddNewMealArticlePresenter == null) {
            mAddNewMealArticlePresenter = new AddNewMealArticlePresenter(mAddNewMealArticleFragment,
                    mAddNewFragment, mAddNewArticleFragment, meals);
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

        if (mFragmentManager.findFragmentByTag(USERPROFILE) != null) {
            mFragmentManager.popBackStack();
            mUserProfilePresenter.refresh();
        }

        if (mDateArticleFragment == null) mDateArticleFragment = DateArticleFragment.newInstance();
        if (mProfileFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mProfileFragment);
        }
        if (mMainFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mMainFragment);
        }
        if (mAddNewFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mAddNewFragment);
        }
        if (mAddNewMealArticleFragment != null) transaction.hide(mAddNewMealArticleFragment);
        if (mAddNewArticleFragment != null) transaction.hide(mAddNewArticleFragment);
        if (mCalendarFragment != null) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mCalendarFragment);
        }
        if (mRmCalculatorFragment != null) transaction.hide(mRmCalculatorFragment);

        if (!mDateArticleFragment.isAdded()) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .add(R.id.linearlayout_main_container, mDateArticleFragment, DATEARTICLE);
        } else {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .show(mDateArticleFragment);
            mDateArticlePresenter.changeDateUi(article);
        }

        if (mDateArticlePresenter == null) {
            mDateArticlePresenter = new DateArticlePresenter(mDateArticleFragment, article);
        }
        transaction.commit();

        mFitnesschView.showDateUi();

    }

    @Override
    public void transToUserProfile(Article article) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (mFragmentManager.findFragmentByTag(DETAIL) != null) {
            mFragmentManager.popBackStack();
            mDetailPresenter.refreshDetailUi();
        }

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
        if (mDetailFragment != null && !mDetailFragment.isHidden()) {
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .hide(mDetailFragment);
            transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)
                    .addToBackStack(DETAIL);
        }
        UserProfileFragment userProfileFragment = UserProfileFragment.newInstance();
        transaction.setCustomAnimations(
                R.anim.slide_right_in,
                R.anim.slide_left_out,
                R.anim.slide_left_in,
                R.anim.slide_right_out)
                .add(R.id.linearlayout_main_container, userProfileFragment, USERPROFILE);
        transaction.commit();

        if (mUserProfilePresenter == null) {
            mUserProfilePresenter = new UserProfilePresenter(userProfileFragment, userProfileFragment.getFragmentManager(), article);
        }
    }

    @Override
    public void refreshAddNewUi() {
//        if (mAddNewFragment != null) mAddNewFragment.refreshUi();
//        if (mAddNewMealArticleFragment != null) mAddNewMealArticleFragment.refreshUi();
//        if (mAddNewArticleFragment != null) mAddNewArticleFragment.refreshUi();
        if (mAddNewPresenter != null) mAddNewPresenter.refreshUi();
        if (mAddNewMealArticlePresenter != null) mAddNewMealArticlePresenter.refresh();
        if (mAddNewArticlePresenter != null) mAddNewArticlePresenter.refresh();
    }

    @Override
    public void refreshDateArticleUi() {
//        if (mDateArticleFragment != null) mDateArticleFragment.refreshUi();
        if (mDateArticlePresenter != null) mDateArticlePresenter.refreshDateUi();
    }

    @Override
    public void refreshCalendarFocus() {
        if (mCalendarPresenter != null) mCalendarPresenter.refresh();
    }

    @Override
    public void refreshDetailUi() {
        if (mDetailPresenter != null) mDetailPresenter.refreshDetailUi();
    }

    @Override
    public void refreshAllUi() {
        if (mAddNewPresenter != null) mAddNewPresenter.refreshUi();
        if (mAddNewMealArticlePresenter != null) mAddNewMealArticlePresenter.refresh();
        if (mAddNewArticlePresenter != null) mAddNewArticlePresenter.refresh();
        if (mDateArticlePresenter != null) mDateArticlePresenter.refreshDateUi();
        if (mCalendarPresenter != null) mCalendarPresenter.refresh();
        if (mDetailPresenter != null) mDetailPresenter.refreshDetailUi();
    }

}
