package com.kevinj1008.fitnessch.mealchild;

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
import com.kevinj1008.fitnessch.Fitnessch;
import com.kevinj1008.fitnessch.activities.FitnesschActivity;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.util.Constants;
import com.kevinj1008.fitnessch.util.SharedPreferencesManager;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

public class MealChildPresenter implements MealChildContract.Presenter {

    private MealChildContract.View mMealChildView;
    private int mLastVisibleItemPosition;
    private int mFirstVisibleItemPosition;
    private SharedPreferencesManager mSharedPreferencesManager;
    //    private int mPaging = Constants.FIRST_PAGING;
    private boolean mLoading = false;

    public MealChildPresenter(MealChildContract.View mealChildView) {
        mMealChildView = checkNotNull(mealChildView, "mealChildView cannot be null!");
        mMealChildView.setPresenter(this);
        mSharedPreferencesManager = new SharedPreferencesManager(Fitnessch.getAppContext());
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
            String author = mSharedPreferencesManager.getUserDbUid();
            //

            setLoading(true);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("articles")
                    .orderBy("create_time", Query.Direction.DESCENDING)
                    .whereEqualTo("user_id", author)
                    .whereEqualTo("article_tag", "菜單")
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
                                String authorId = documentChange.getDocument().getData().get("user_id").toString();

                                //TODO: Add author photo
                                String authorPhoto = documentChange.getDocument().getData().get("author_photo").toString();

                                String title = documentChange.getDocument().getData().get("title").toString();
                                String content = documentChange.getDocument().getData().get("content").toString();
                                String time = String.valueOf(documentChange.getDocument().getTimestamp("create_time").getSeconds());
                                int createTime = Integer.parseInt(time);
                                String tag = documentChange.getDocument().getData().get("article_tag").toString();

                                Article articles = new Article(id, author, title, content, createTime, tag);

                                //TODO: Add author ID and photo to object
                                articles.setAuthorId(authorId);
                                articles.setAuthorImage(authorPhoto);

                                mMealChildView.showArticles(articles);
                            }

                        }
                    });
        }
    }

    @Override
    public void showArticles(Article bean) {
        mMealChildView.showArticles(bean);
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

    @Override
    public void openDetail(Article article) {

    }


}
