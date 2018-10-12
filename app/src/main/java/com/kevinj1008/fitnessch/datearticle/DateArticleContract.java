package com.kevinj1008.fitnessch.datearticle;

import com.kevinj1008.fitnessch.BasePresenter;
import com.kevinj1008.fitnessch.BaseView;
import com.kevinj1008.fitnessch.objects.Article;

public interface DateArticleContract {

    interface View extends BaseView<Presenter> {

        void setToolbarVisibility(boolean visible);

        void showArticle(Article article);

        void refreshUi();

        void showDateUi(Article article);

        void showDetailUi(Article article);

        void renewDateUi(Article article);
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadArticle(Article article);

        void showToolbar();

        void hideToolbar();

        void refreshDateUi();

        void setDateUi(Article article);

        void changeDateUi(Article article);

        void openDetail(Article article);

    }
}
