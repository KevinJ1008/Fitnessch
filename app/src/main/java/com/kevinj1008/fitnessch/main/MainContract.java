package com.kevinj1008.fitnessch.main;

import android.support.v7.widget.RecyclerView;

import com.kevinj1008.fitnessch.BasePresenter;
import com.kevinj1008.fitnessch.BaseView;
import com.kevinj1008.fitnessch.api.beans.GetArticles;
import com.kevinj1008.fitnessch.objects.Article;

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void showArticles(Article bean);

        void showDetailUi(Article article);

        void refreshUi();

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadArticles();

        void showArticles(Article bean);

        void onScrollStateChanged(int visibleItemCount, int totalItemCount, int newState);

        void onScrolled(RecyclerView.LayoutManager layoutManager);

        void openDetail(Article article);

//        void updateInterestedIn(Article article, boolean isInterestedIn);

        void refresh();
    }
}
