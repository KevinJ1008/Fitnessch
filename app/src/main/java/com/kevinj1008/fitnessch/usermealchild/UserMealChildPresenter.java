package com.kevinj1008.fitnessch.usermealchild;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.kevinj1008.fitnessch.Fitnessch;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.objects.User;
import com.kevinj1008.fitnessch.util.Constants;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

public class UserMealChildPresenter implements UserMealChildContract.Presenter  {

    private UserMealChildContract.View mUserMealChildView;
    private int mLastVisibleItemPosition;
    private int mFirstVisibleItemPosition;
    private boolean mLoading = false;
    private Article mArticle;

    public UserMealChildPresenter(UserMealChildContract.View userMealChildView, Article article) {
        mUserMealChildView = checkNotNull(userMealChildView, "userMealChildView cannot be null!");
        mUserMealChildView.setPresenter(this);
        mArticle = article;
    }

    @Override
    public void start() {
        loadArticles();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadArticles() {
        String uid = mArticle.getAuthorId();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("articles")
                .orderBy("create_time", Query.Direction.ASCENDING)
                .whereEqualTo("user_id", uid)
                .whereEqualTo("article_tag", "菜單")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d(Constants.TAG, "Articles listen failed.", e);
                            return;
                        }
                        String source = snapshot != null && snapshot.getMetadata().hasPendingWrites() ? "Local" : "Server";
                        if (source.equals("Server")) {
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

                                Article articles = new Article();

                                //TODO: Add author ID and photo to object
                                articles.setId(id);
                                articles.setName(author);
                                articles.setTitle(title);
                                articles.setContent(content);
                                articles.setCreatedTime(createTime);
                                articles.setTag(tag);
                                articles.setAuthorId(authorId);
                                articles.setAuthorImage(authorPhoto);

                                mUserMealChildView.showArticles(articles);
                            }
                        }
                    }
                });

    }

    @Override
    public void showArticles(Article bean) {
        mUserMealChildView.showArticles(bean);
    }

    @Override
    public void onScrollStateChanged(int visibleItemCount, int totalItemCount, int newState) {

    }

    @Override
    public void onScrolled(RecyclerView.LayoutManager layoutManager) {

    }

    @Override
    public void openDetail(Article article) {
        mUserMealChildView.showDetailUi(article);
    }


}
