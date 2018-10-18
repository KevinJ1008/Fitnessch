package com.kevinj1008.fitnessch.addnewschedulechild;

import com.kevinj1008.fitnessch.BasePresenter;
import com.kevinj1008.fitnessch.BaseView;
import com.kevinj1008.fitnessch.objects.Schedule;
import com.kevinj1008.fitnessch.objects.Title;

import java.util.List;

public interface AddNewScheduleChildContract {

    interface View extends BaseView<Presenter> {

        void showScheduleSearchTitle(List<Title> titles);

        void refreshUi();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void searchTitle();

        void openAddNewArticle(List<Schedule> schedules);
    }
}
