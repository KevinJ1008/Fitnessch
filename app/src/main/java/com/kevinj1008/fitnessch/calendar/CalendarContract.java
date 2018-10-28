package com.kevinj1008.fitnessch.calendar;

import com.kevinj1008.fitnessch.BasePresenter;
import com.kevinj1008.fitnessch.BaseView;
import com.kevinj1008.fitnessch.objects.Article;

import java.util.List;

public interface CalendarContract {

    interface View extends BaseView<Presenter> {

        void showArticles(List<Article> articles);

        void showFocusDate();

        void monthChangeArticle(List<Article> articles);

        void refreshUi();

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadArticles();

        void reloadArticle();

        void monthChangeLoader(Article article);

//        void updateInterestedIn(Article article, boolean isInterestedIn);

        void refresh();
    }
}
