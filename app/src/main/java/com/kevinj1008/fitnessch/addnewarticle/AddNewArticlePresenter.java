package com.kevinj1008.fitnessch.addnewarticle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kevinj1008.fitnessch.objects.Schedule;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddNewArticlePresenter implements AddNewArticleContract.Presenter {

    private AddNewArticleContract.View mAddNewArticleView;
    private List<Schedule> mSchedules;
    private int mlastVisibleItemPosition;
    private int mfirstVisibleItemPosition;

    public AddNewArticlePresenter(AddNewArticleContract.View addNewArticleView, List<Schedule> schedules) {
        mAddNewArticleView = checkNotNull(addNewArticleView, "addNewArticleView cannot be null!");
        mAddNewArticleView.setPresenter(this);
        mSchedules = schedules;
    }


    @Override
    public void start() {
        showSchedules(mSchedules);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void sendSchedule() {

    }

    @Override
    public void showSchedules(List<Schedule> schedules) {
        mAddNewArticleView.showSchedule(schedules);
    }

    @Override
    public void onScrollStateChanged(int visibleItemCount, int totalItemCount, int newState) {

    }

    @Override
    public void onScrolled(RecyclerView.LayoutManager layoutManager) {
//        if (layoutManager instanceof LinearLayoutManager) {
//
//            mlastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
//                    .findLastVisibleItemPosition();
//            mfirstVisibleItemPosition = ((LinearLayoutManager) layoutManager)
//                    .findFirstVisibleItemPosition();
//
//        } else if (layoutManager instanceof GridLayoutManager) {
//
//            mlastVisibleItemPosition = ((GridLayoutManager) layoutManager)
//                    .findLastVisibleItemPosition();
//            mfirstVisibleItemPosition = ((GridLayoutManager) layoutManager)
//                    .findFirstVisibleItemPosition();
//        }
    }

    @Override
    public void refresh() {

    }


}
