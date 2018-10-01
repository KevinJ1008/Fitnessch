package com.kevinj1008.fitnessch.calendar;

import com.kevinj1008.fitnessch.BasePresenter;
import com.kevinj1008.fitnessch.BaseView;
import com.kevinj1008.fitnessch.objects.Article;

public interface CalendarContract {

    interface View extends BaseView<Presenter> {

        void showArticles(Article bean);

        void showDetailUi(Article article);

        void refreshUi();

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadArticles();

        void showArticles(Article bean);

        void openDetail(Article article);

//        void updateInterestedIn(Article article, boolean isInterestedIn);

        void refresh();
    }
}
