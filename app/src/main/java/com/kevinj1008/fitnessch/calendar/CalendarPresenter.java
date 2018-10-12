package com.kevinj1008.fitnessch.calendar;

import android.support.v4.app.FragmentManager;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

public class CalendarPresenter implements CalendarContract.Presenter {

    private final CalendarContract.View mCalendarView;
    private SharedPreferencesManager mSharedPreferencesManager;
    private List<Article> mArticles;

    public CalendarPresenter(CalendarContract.View calendarView) {
        mCalendarView = checkNotNull(calendarView, "calendarView cannot be null!");
        mCalendarView.setPresenter(this);

        mSharedPreferencesManager = new SharedPreferencesManager(Fitnessch.getAppContext());
        mArticles = new ArrayList<>();
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
        String uid = mSharedPreferencesManager.getUserDbUid();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("articles")
                .whereEqualTo("user_id", uid)
                .whereEqualTo("create_year", year)
                .whereEqualTo("create_month", month)
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
                                String createDay = documentChange.getDocument().getData().get("create_day").toString();
                                int day = Integer.parseInt(createDay);

                                Article article = new Article();
                                article.setCreateDay(day);

                                mArticles.add(article);
                            }
                            mCalendarView.showArticles(mArticles);
                        }
                    }
                });
    }

    @Override
    public void showArticles(Article bean) {

    }

    @Override
    public void openDetail(Article article) {

    }

    @Override
    public void reloadArticle() {
        mCalendarView.showFocusDate();
    }

    @Override
    public void monthChangeLoader(final Article article) {
        final int year = article.getCreateYear();
        final int month = article.getCreateMonth();
        String uid = mSharedPreferencesManager.getUserDbUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("articles")
                .whereEqualTo("user_id", uid)
                .whereEqualTo("create_year", year)
                .whereEqualTo("create_month", month)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d(Constants.TAG, "Articles listen failed.", e);
                            return;
                        }
                        String source = snapshot != null && snapshot.getMetadata().hasPendingWrites() ? "Local" : "Server";
                        if (source.equals("Server")) {
                            for (DocumentChange documentChange : snapshot.getDocumentChanges()) {
                                String createDay = documentChange.getDocument().getData().get("create_day").toString();
                                int day = Integer.parseInt(createDay);

                                article.setCreateYear(year);
                                article.setCreateMonth(month);
                                article.setCreateDay(day);

                                mArticles.add(article);
                            }
                            mCalendarView.monthChangeArticle(mArticles);
                        }
                    }
                });
    }

    @Override
    public void refresh() {
        mCalendarView.refreshUi();
    }


}
