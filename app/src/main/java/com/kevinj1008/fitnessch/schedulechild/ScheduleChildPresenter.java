package com.kevinj1008.fitnessch.schedulechild;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.util.Constants;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

public class ScheduleChildPresenter implements ScheduleChildContract.Presenter{

    private ScheduleChildContract.View mScheduleChildView;
    private int mLastVisibleItemPosition;
    private int mFirstVisibleItemPosition;
//    private int mPaging = Constants.FIRST_PAGING;
    private boolean mLoading = false;

    public ScheduleChildPresenter(ScheduleChildContract.View scheduleChildView) {
        mScheduleChildView = checkNotNull(scheduleChildView, "scheduleChildView cannot be null!");
        mScheduleChildView.setPresenter(this);
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

            //TODO: Make author to user ID
            String author = "Wun-Bin Jhou";
            //

            setLoading(true);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("articles")
                    .orderBy("create_time", Query.Direction.DESCENDING)
                    .whereEqualTo("author", author)
                    .whereEqualTo("article_tag", "課表")
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
                                String title = documentChange.getDocument().getData().get("title").toString();
                                String content = documentChange.getDocument().getData().get("content").toString();
                                String time = String.valueOf(documentChange.getDocument().getTimestamp("create_time").getSeconds());
                                int createTime = Integer.parseInt(time);
                                String tag = documentChange.getDocument().getData().get("article_tag").toString();
                                Article articles = new Article(id, author, title, content, createTime, tag);
                                mScheduleChildView.showArticles(articles);
                            }

                        }
                    });
        }
    }

    @Override
    public void showArticles(Article bean) {
        mScheduleChildView.showArticles(bean);
    }

    @Override
    public void onScrollStateChanged(int visibleItemCount, int totalItemCount, int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE && visibleItemCount > 0) {
            if (mLastVisibleItemPosition == totalItemCount - 1) {
                loadArticles();
            } else if (mFirstVisibleItemPosition == 0) {
                // Scroll to top
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {

            mLastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                    .findLastVisibleItemPosition();
            mFirstVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                    .findFirstVisibleItemPosition();

        } else if (layoutManager instanceof GridLayoutManager) {

            mLastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                    .findLastVisibleItemPosition();
            mFirstVisibleItemPosition = ((GridLayoutManager) layoutManager)
                    .findFirstVisibleItemPosition();
        }
    }

    public boolean isLoading() {
        return mLoading;
    }

    public void setLoading(boolean loading) {
        mLoading = loading;
    }

//    public void setPaging(int paging) {
//        mPaging = paging;
//    }

    @Override
    public void openDetail(Article article) {

    }


}
