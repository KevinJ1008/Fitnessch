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

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

public class CalendarPresenter implements CalendarContract.Presenter {

    private final CalendarContract.View mCalendarView;
    private SharedPreferencesManager mSharedPreferencesManager;

    public CalendarPresenter(CalendarContract.View calendarView) {
        mCalendarView = checkNotNull(calendarView, "calendarView cannot be null!");
        mCalendarView.setPresenter(this);

        mSharedPreferencesManager = new SharedPreferencesManager(Fitnessch.getAppContext());
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadArticles() {
        String uid = mSharedPreferencesManager.getUserDbUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("articles")
                .whereEqualTo("user_id", uid)
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
//                                documentChange
                            }
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
    public void refresh() {

    }

    @Override
    public void start() {

    }
}
