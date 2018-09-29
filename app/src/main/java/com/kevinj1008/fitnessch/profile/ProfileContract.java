package com.kevinj1008.fitnessch.profile;

import com.kevinj1008.fitnessch.BasePresenter;
import com.kevinj1008.fitnessch.BaseView;
import com.kevinj1008.fitnessch.schedulechild.ScheduleChildFragment;
import com.kevinj1008.fitnessch.schedulechild.ScheduleChildPresenter;

public interface ProfileContract {

    interface View extends BaseView<Presenter> {

        void showScheduleChildUi();

        void showMealChildUi();

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void transToScheduleChild();

        void transToMealChild();

        void refreshLikedArticles();

        void setupPresenter(ScheduleChildFragment fragment, ScheduleChildPresenter presenter);
    }
}
