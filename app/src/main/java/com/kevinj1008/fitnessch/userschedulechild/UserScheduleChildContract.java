package com.kevinj1008.fitnessch.userschedulechild;

import android.support.v7.widget.RecyclerView;

import com.kevinj1008.fitnessch.BasePresenter;
import com.kevinj1008.fitnessch.BaseView;
import com.kevinj1008.fitnessch.objects.Article;

public interface UserScheduleChildContract {

    interface View extends BaseView<Presenter> {

        void showArticles(Article bean);

        void showDetailUi(Article article);

        void refreshUserUi();

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadArticles(Article article);

        void showArticles(Article bean);

        void onScrollStateChanged(int visibleItemCount, int totalItemCount, int newState);

        void onScrolled(RecyclerView.LayoutManager layoutManager);

        void openDetail(Article article);

    }
}
