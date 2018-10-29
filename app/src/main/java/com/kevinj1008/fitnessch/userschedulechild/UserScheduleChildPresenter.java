package com.kevinj1008.fitnessch.userschedulechild;

import static com.google.common.base.Preconditions.checkNotNull;

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


public class UserScheduleChildPresenter implements UserScheduleChildContract.Presenter {

    private UserScheduleChildContract.View mUserScheduleChildView;
    private int mLastVisibleItemPosition;
    private int mFirstVisibleItemPosition;
    //    private int mPaging = Constants.FIRST_PAGING;
    private boolean mLoading = false;
    private Article mArticle;

    public UserScheduleChildPresenter(UserScheduleChildContract.View userScheduleChildView, Article article) {
        mUserScheduleChildView = checkNotNull(userScheduleChildView, "userScheduleChildView cannot be null!");
        mUserScheduleChildView.setPresenter(this);
        mArticle = article;
    }

    @Override
    public void start() {
        loadArticles(mArticle);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadArticles(Article article) {
        String uid = article.getAuthorId();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("articles")
                .orderBy("create_time", Query.Direction.ASCENDING)
                .whereEqualTo("user_id", uid)
                .whereEqualTo("article_tag", "課表")
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

                                mUserScheduleChildView.showArticles(articles);
                            }
                        }
                    }
                });
    }

    @Override
    public void showArticles(Article bean) {
        mUserScheduleChildView.showArticles(bean);
    }

    @Override
    public void onScrollStateChanged(int visibleItemCount, int totalItemCount, int newState) {

    }

    @Override
    public void onScrolled(RecyclerView.LayoutManager layoutManager) {

    }

    @Override
    public void openDetail(Article article) {
        mUserScheduleChildView.showDetailUi(article);
    }


}
