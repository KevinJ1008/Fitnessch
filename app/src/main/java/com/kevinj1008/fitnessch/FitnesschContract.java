package com.kevinj1008.fitnessch;

import com.kevinj1008.fitnessch.objects.Article;

public interface FitnesschContract {

    interface View extends BaseView<Presenter> {

        void showMainUi();

        void showScheduleUi();

        void showCalendarUi();

        void showProfileUi();

        void refreshLikedUi();

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void transToMain();

        void transToSchedule();

        void transToProfile();

        void transToDetail(Article article);

        void refreshLiked();

        void transToCalendar();

//        void transToChatRoom(Message message, User user);

//        void refreshChatFragmentUiPresenter();

    }

}
