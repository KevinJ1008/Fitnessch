package com.kevinj1008.fitnessch.main;

import static com.google.common.base.Preconditions.checkNotNull;

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


public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mMainView;

    private int mLastVisibleItemPosition;
    private int mFirstVisibleItemPosition;
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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("articles")
                .orderBy("create_time", Query.Direction.ASCENDING)
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
                                String id = "0123456789";
                                try {
                                    id = documentChange.getDocument().getId();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                String author = "未知作者";
                                try {
                                    author = documentChange.getDocument().getData().get("author").toString();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                String authorId = "9876543210";
                                try {
                                    authorId = documentChange.getDocument().getData().get("user_id").toString();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                //TODO: Add author photo
                                String authorPhoto = null;
                                try {
                                    authorPhoto = documentChange.getDocument().getData().get("author_photo").toString();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                String title = "未知標題";
                                try {
                                    title = documentChange.getDocument().getData().get("title").toString();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                String content = "未知內容";
                                try {
                                    content = documentChange.getDocument().getData().get("content").toString();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                int createTime = 0;
                                try {
                                    String time = String.valueOf(documentChange.getDocument().getTimestamp("create_time").getSeconds());
                                    createTime = Integer.parseInt(time);
                                } catch (NumberFormatException e1) {
                                    e1.printStackTrace();
                                }

                                String tag = "未知標籤";
                                try {
                                    tag = documentChange.getDocument().getData().get("article_tag").toString();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

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

                                mMainView.showArticles(articles);
                            }
                            //TODO: GET Schedule
                        }
                    }
                });
    }

    @Override
    public void showArticles(Article bean) {
        mMainView.showArticles(bean);
    }

    @Override
    public void onScrollStateChanged(int visibleItemCount, int totalItemCount, int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE && visibleItemCount > 0) {
            if (mLastVisibleItemPosition == totalItemCount - 1) {
//                loadArticles();
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
        mMainView.showDetailUi(article);
    }

    @Override
    public void refresh() {
        mMainView.refreshUi();
    }


}
