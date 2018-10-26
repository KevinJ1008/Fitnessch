package com.kevinj1008.fitnessch;

import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.objects.Meal;
import com.kevinj1008.fitnessch.objects.Schedule;

import java.util.List;

public interface FitnesschContract {

    interface View extends BaseView<Presenter> {

        void showMainUi();

        void showAddNewUi();

        void showCalendarUi();

        void showProfileUi();

        void showDateUi();

        void showDetailUi();

        void refreshLikedUi();

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void transToMain();

        void transToAddNew();

        void transToRmCalculator();

        void transToProfile();

        void transToDetail(Article article);

        void refreshLiked();

        void transToCalendar();

        void transToAddNewArticle(List<Schedule> schedules);

        void transToAddNewMealArticle(List<Meal> meals);

        void transToDate(Article article);

        void transToUserProfile(Article article);

        void refreshAddNewUi();

        void refreshDateArticleUi();

        void refreshCalendarFocus();

        void refreshDetailUi();

        void refreshAllUi();

//        void transToChatRoom(Message message, User user);

//        void refreshChatFragmentUiPresenter();

    }

}
