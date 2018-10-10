package com.kevinj1008.fitnessch.detail;

import com.google.firebase.firestore.FirebaseFirestore;
import com.kevinj1008.fitnessch.objects.Article;

import static com.google.common.base.Preconditions.checkNotNull;

public class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.View mDetailView;
    private Article mArticle;

    public DetailPresenter(DetailContract.View detailView, Article article) {
        mDetailView = checkNotNull(detailView, "detailView cannot be null!");
        mDetailView.setPresenter(this);

        mArticle = article;
    }

    @Override
    public void start() {
        mDetailView.showArticle(mArticle);
        loadSchedule();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadSchedule() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

    }

    @Override
    public void showToolbar() {

    }

    @Override
    public void hideToolbar() {

    }


}
