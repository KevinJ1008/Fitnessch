package com.kevinj1008.fitnessch.addnewarticle;

import android.support.v7.widget.RecyclerView;

import com.kevinj1008.fitnessch.BasePresenter;
import com.kevinj1008.fitnessch.BaseView;
import com.kevinj1008.fitnessch.objects.Schedule;

import java.util.List;

public interface AddNewArticleContract {

    interface View extends BaseView<Presenter> {

        void showSchedule(List<Schedule> schedules);

        void showAddNewArticleUi();

        void refreshUi();

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void sendSchedule(String title, String content);

        void showSchedules(List<Schedule> schedules);

        void onScrollStateChanged(int visibleItemCount, int totalItemCount, int newState);

        void onScrolled(RecyclerView.LayoutManager layoutManager);

//        void updateInterestedIn(Article article, boolean isInterestedIn);

        void refresh();
    }
}
