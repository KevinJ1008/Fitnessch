package com.kevinj1008.fitnessch.profile;

import com.kevinj1008.fitnessch.BasePresenter;
import com.kevinj1008.fitnessch.BaseView;
import com.kevinj1008.fitnessch.objects.User;
import com.kevinj1008.fitnessch.schedulechild.ScheduleChildFragment;
import com.kevinj1008.fitnessch.schedulechild.ScheduleChildPresenter;

public interface ProfileContract {

    interface View extends BaseView<Presenter> {

        void showProfileInfo(User user);

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void sendProfileInfo(User user);

        void loadProfileInfo();

        void setupPresenter(ScheduleChildFragment fragment, ScheduleChildPresenter presenter);
    }
}
