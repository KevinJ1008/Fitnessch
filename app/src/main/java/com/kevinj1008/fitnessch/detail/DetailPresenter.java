package com.kevinj1008.fitnessch.detail;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.kevinj1008.fitnessch.Fitnessch;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.objects.Schedule;
import com.kevinj1008.fitnessch.util.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

public class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.View mDetailView;
    private Article mArticle;
    private List<Schedule> mSchedules;

    public DetailPresenter(DetailContract.View detailView, Article article) {
        mDetailView = checkNotNull(detailView, "detailView cannot be null!");
        mDetailView.setPresenter(this);

        mArticle = article;
        mSchedules = new ArrayList<>();
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
        final String articleId = mArticle.getId();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("articles")
                .whereEqualTo("article_id", articleId)
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
                                documentChange.getDocument().getReference().collection("schedule").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot querySnapshot) {
                                        Log.d(Constants.TAG, "Successfully get schedule " + querySnapshot.toString());
                                        for (DocumentChange scheduleChange : querySnapshot.getDocumentChanges()) {
                                            String scheduleType = scheduleChange.getDocument().getData().get("schedule_type").toString();
                                            String scheduleTitle = scheduleChange.getDocument().getData().get("schedule_title").toString();
                                            String scheduleWeight = scheduleChange.getDocument().getData().get("schedule_weight").toString();
                                            String scheduleReps = scheduleChange.getDocument().getData().get("schedule_reps").toString();

                                            Schedule schedule = new Schedule();
                                            schedule.setType(scheduleType);
                                            schedule.setScheduleTitle(scheduleTitle);
                                            schedule.setScheduleWeight(scheduleWeight);
                                            schedule.setScheduleReps(scheduleReps);

                                            mSchedules.add(schedule);
                                        }

                                        mDetailView.showSchedule(mSchedules);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(Constants.TAG, "Fail to get schedule " + e.getMessage());
                                        Toast.makeText(Fitnessch.getAppContext(), "請檢查網路連線。", Toast.LENGTH_SHORT).show();
                                    }
                                });
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
    public void refreshDetailUi() {
        mDetailView.refreshUi();
    }


}
