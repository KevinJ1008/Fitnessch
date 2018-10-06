package com.kevinj1008.fitnessch.main;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kevinj1008.fitnessch.api.beans.GetArticles;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.objects.Schedule;
import com.kevinj1008.fitnessch.util.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mMainView;

    private int mlastVisibleItemPosition;
    private int mfirstVisibleItemPosition;
    private boolean mLoading = false;

    public MainPresenter(MainContract.View mainView) {
        mMainView = checkNotNull(mainView, "mainView cannot be null!");
        mMainView.setPresenter(this);
    }


    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void start() {
        loadArticles();
    }

    @Override
    public void loadArticles() {
        if (!isLoading()) {
            setLoading(true);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("articles")
                    .orderBy("create_time", Query.Direction.DESCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.d(Constants.TAG, "Articles listen failed.", e);
                        return;
                    }
                    for (DocumentChange documentChange  : snapshot.getDocumentChanges()) {
                        Log.d(Constants.TAG, "Get Articles " + documentChange.toString());
                        String id = documentChange.getDocument().getId();
                        String author = documentChange.getDocument().getData().get("author").toString();

                        //TODO: Add author ID

                        String title = documentChange.getDocument().getData().get("title").toString();
                        String content = documentChange.getDocument().getData().get("content").toString();
                        String time = String.valueOf(documentChange.getDocument().getTimestamp("create_time").getSeconds());
                        int createTime = Integer.parseInt(time);
                        String tag = documentChange.getDocument().getData().get("article_tag").toString();
                        Article articles = new Article(id, author, title, content, createTime, tag);

//                        documentChange.getDocument().getReference().collection("schedule").addSnapshotListener(new EventListener<QuerySnapshot>() {
//                            @Override
//                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                                for (DocumentChange scheduleChange : queryDocumentSnapshots.getDocumentChanges()) {
//                                    String scheduleTitle = scheduleChange.getDocument().getData().get("schedule_title").toString();
//                                    String scheduleWeight = scheduleChange.getDocument().getData().get("schedule_weight").toString();
//                                    String scheduleReps = scheduleChange.getDocument().getData().get("schedule_reps").toString();
//                                    String scheduleType = scheduleChange.getDocument().getData().get("schedule_type").toString();
//
//                                    Schedule schedules = new Schedule(scheduleTitle, scheduleWeight, scheduleReps);
//                                    schedules.setType(scheduleType);
//
//                                }
//                            }
//                        });

                        mMainView.showArticles(articles);
                    }
                    //TODO: GET Schedule
                }
            });
        }
    }

    @Override
    public void showArticles(Article bean) {
        mMainView.showArticles(bean);
    }

    @Override
    public void onScrollStateChanged(int visibleItemCount, int totalItemCount, int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE && visibleItemCount > 0) {
            if (mlastVisibleItemPosition == totalItemCount - 1) {
                loadArticles();
            } else if (mfirstVisibleItemPosition == 0) {
                // Scroll to top
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {

            mlastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                    .findLastVisibleItemPosition();
            mfirstVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                    .findFirstVisibleItemPosition();

        } else if (layoutManager instanceof GridLayoutManager) {

            mlastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                    .findLastVisibleItemPosition();
            mfirstVisibleItemPosition = ((GridLayoutManager) layoutManager)
                    .findFirstVisibleItemPosition();
        }
    }

    public boolean isLoading() {
        return mLoading;
    }

    public void setLoading(boolean loading) {
        mLoading = loading;
    }

    @Override
    public void openDetail(Article article) {

    }

    @Override
    public void refresh() {
        mMainView.refreshUi();
    }


}
