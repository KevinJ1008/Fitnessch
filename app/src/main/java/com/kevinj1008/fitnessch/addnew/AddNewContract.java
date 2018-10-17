package com.kevinj1008.fitnessch.addnew;

import com.kevinj1008.fitnessch.BasePresenter;
import com.kevinj1008.fitnessch.BaseView;

public interface AddNewContract {


    interface View extends BaseView<Presenter> {

        void showAddNewScheduleChildUi();

        void showAddNewMealChildUi();

        void refreshUi();

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void transToAddNewScheduleChild();

        void transToAddNewMealChild();

        void refreshUi();

//        void setupPresenter(ScheduleChildFragment fragment, ScheduleChildPresenter presenter);
    }
}
