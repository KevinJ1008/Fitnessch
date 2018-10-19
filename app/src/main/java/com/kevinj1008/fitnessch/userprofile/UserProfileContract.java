package com.kevinj1008.fitnessch.userprofile;

import com.kevinj1008.fitnessch.BasePresenter;
import com.kevinj1008.fitnessch.BaseView;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.objects.User;

public interface UserProfileContract {

    interface View extends BaseView<Presenter> {

        void showUserScheduleChildUi(Article article);

        void showUserMealChildUi();

        void showUserProfileInfo(User user);

        void refreshUserUi();

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void transToUserScheduleChild();

        void transToUserMealChild();

        void refreshLikedArticles();

        void loadUserProfileInfo(Article article);

        void refresh();

    }
}
