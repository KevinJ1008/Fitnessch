package com.kevinj1008.fitnessch.datearticle;

import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.kevinj1008.fitnessch.Fitnessch;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.util.Constants;
import com.kevinj1008.fitnessch.util.SharedPreferencesManager;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

public class DateArticlePresenter implements DateArticleContract.Presenter {

    private DateArticleContract.View mDateArticleView;
    private SharedPreferencesManager mSharedPreferencesManager;
    private Article mArticle;

    public DateArticlePresenter(DateArticleContract.View dateArticleView, Article article) {
        mDateArticleView = checkNotNull(dateArticleView, "dateArticleView cannot be null!");
        mDateArticleView.setPresenter(this);

        mArticle = article;

        mSharedPreferencesManager = new SharedPreferencesManager(Fitnessch.getAppContext());
    }


    @Override
    public void start() {
        loadArticle(mArticle);
        setDateUi(mArticle);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadArticle(Article article) {
        int year = article.getCreateYear();
        int month = article.getCreateMonth();
        int day = article.getCreateDay();
        String uid = mSharedPreferencesManager.getUserDbUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("articles")
                .whereEqualTo("user_id", uid)
                .whereEqualTo("create_year", year)
                .whereEqualTo("create_month", month)
                .whereEqualTo("create_day", day)
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

                                mDateArticleView.showArticle(articles);
                            }
                        }
                    }
                });
    }

    @Override
    public void showToolbar() {

    }

    @Override
    public void hideToolbar() {

    }

    @Override
    public void refreshDateUi() {
        mDateArticleView.refreshUi();
    }

    @Override
    public void setDateUi(Article article) {
        mDateArticleView.showDateUi(article);
    }

    @Override
    public void changeDateUi(Article article) {
        mDateArticleView.renewDateUi(article);
    }

    @Override
    public void openDetail(Article article) {
        mDateArticleView.showDetailUi(article);
    }


}
