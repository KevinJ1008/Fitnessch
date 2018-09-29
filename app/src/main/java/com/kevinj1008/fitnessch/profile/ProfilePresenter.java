package com.kevinj1008.fitnessch.profile;

import android.support.v4.app.FragmentManager;

import com.kevinj1008.fitnessch.mealchild.MealChildFragment;
import com.kevinj1008.fitnessch.mealchild.MealChildPresenter;
import com.kevinj1008.fitnessch.schedulechild.ScheduleChildFragment;
import com.kevinj1008.fitnessch.schedulechild.ScheduleChildPresenter;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View mProfileView;
    private FragmentManager mChildFragmentManager;

    private ScheduleChildFragment mScheduleChildFragment;
    private ScheduleChildPresenter mScheduleChildPresenter;

    private MealChildFragment mMealChildFragment;
    private MealChildPresenter mMealChildPresenter;

    public ProfilePresenter(ProfileContract.View profileView, FragmentManager fragmentManager) {
        mProfileView = checkNotNull(profileView, "profileView cannot be null!");
        mProfileView.setPresenter(this);

        mChildFragmentManager = fragmentManager;


    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void setupPresenter(ScheduleChildFragment fragment, ScheduleChildPresenter presenter) {
        mScheduleChildFragment = fragment;
        mScheduleChildPresenter = presenter;
    }

    @Override
    public void start() {
        transToScheduleChild();
    }

    @Override
    public void transToScheduleChild() {
//        mProfileView.showScheduleChildUi();

//        FragmentTransaction transaction = mChildFragmentManager.beginTransaction();

//        if (mScheduleChildFragment == null) mScheduleChildFragment = ScheduleChildFragment.newInstance();
////        if (mMealChildFragment != null) transaction.hide(mMealChildFragment);
//        if (!mScheduleChildFragment.isAdded()) {
//            transaction.add(R.id.profile_view_pager, mScheduleChildFragment);
//        } else {
//            transaction.show(mScheduleChildFragment);
//        }
//
//        if (mScheduleChildPresenter == null) {
//            mScheduleChildPresenter = new ScheduleChildPresenter(mScheduleChildFragment);
//        }
//
//        transaction.commit();
    }

    @Override
    public void transToMealChild() {

    }

    @Override
    public void refreshLikedArticles() {

    }


}
