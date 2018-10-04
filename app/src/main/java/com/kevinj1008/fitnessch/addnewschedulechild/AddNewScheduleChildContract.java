package com.kevinj1008.fitnessch.addnewschedulechild;

import com.kevinj1008.fitnessch.BasePresenter;
import com.kevinj1008.fitnessch.BaseView;
import com.kevinj1008.fitnessch.objects.Schedule;

import java.util.List;

public interface AddNewScheduleChildContract {

    interface View extends BaseView<Presenter> {

        void showScheduleItem(int position);

        void refreshUi();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void setSchedule(String title, String weight, String reps, int position);

        void openAddNewArticle(List<Schedule> schedules);
    }
}
