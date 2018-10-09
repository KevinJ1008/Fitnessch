package com.kevinj1008.fitnessch.addnewschedulechild;

import com.kevinj1008.fitnessch.objects.Schedule;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddNewScheduleChildPresenter implements AddNewScheduleChildContract.Presenter {

    private AddNewScheduleChildContract.View mAddNewScheduleChildView;

    public AddNewScheduleChildPresenter(AddNewScheduleChildContract.View addNewScheduleChildView) {
        mAddNewScheduleChildView = checkNotNull(addNewScheduleChildView, "addNewScheduleChildView cannot be null!");
        mAddNewScheduleChildView.setPresenter(this);
    }


    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void setSchedule(String title, String weight, String reps, int position) {

    }

    @Override
    public void openAddNewArticle(List<Schedule> schedules) {

    }

    @Override
    public void start() {

    }
}
