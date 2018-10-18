package com.kevinj1008.fitnessch.addnewarticle;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kevinj1008.fitnessch.Fitnessch;
import com.kevinj1008.fitnessch.addnew.AddNewContract;
import com.kevinj1008.fitnessch.addnewmealarticle.AddNewMealArticleContract;
import com.kevinj1008.fitnessch.addnewschedulechild.AddNewScheduleChildContract;
import com.kevinj1008.fitnessch.objects.Schedule;
import com.kevinj1008.fitnessch.util.Constants;
import com.kevinj1008.fitnessch.util.SharedPreferencesManager;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddNewArticlePresenter implements AddNewArticleContract.Presenter {

    private AddNewArticleContract.View mAddNewArticleView;
    private AddNewMealArticleContract.View mAddNewMealArticleView;
    private AddNewScheduleChildContract.View mAddNewScheduleChildView;
    private AddNewContract.View mAddNewView;
    private List<Schedule> mSchedules;
    private SharedPreferencesManager mSharedPreferencesManager;
    private int mlastVisibleItemPosition;
    private int mfirstVisibleItemPosition;

    public AddNewArticlePresenter(AddNewArticleContract.View addNewArticleView, AddNewContract.View addNewView, AddNewMealArticleContract.View addNewMealArticleView, List<Schedule> schedules) {
        mAddNewArticleView = checkNotNull(addNewArticleView, "addNewArticleView cannot be null!");
        mAddNewView = checkNotNull(addNewView, "addNewView cannot be null!");
        if (addNewMealArticleView != null) {
            mAddNewMealArticleView = checkNotNull(addNewMealArticleView, "addNewMealArticleView cannot be null!");
        }
        mAddNewArticleView.setPresenter(this);
        mSchedules = schedules;
        mSharedPreferencesManager = new SharedPreferencesManager(Fitnessch.getAppContext());
    }


    @Override
    public void start() {
        showSchedules(mSchedules);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void sendSchedule(String title, String content) {
        //TODO: Change author name hard code to user
        String author = mSharedPreferencesManager.getUserName();
        String authorId = mSharedPreferencesManager.getUserDbUid();
        String authorPhoto = mSharedPreferencesManager.getUserPhoto();
        //
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        Map<String, Object> article = new HashMap<>();
        article.put("article_tag", "課表");
        article.put("author", author);
        article.put("user_id", authorId);
        article.put("author_photo", authorPhoto);
        article.put("title", title);
        article.put("content", content);
        article.put("create_time", FieldValue.serverTimestamp());
        article.put("create_year", year);
        article.put("create_month", month);
        article.put("create_day", day);

        //TODO: Change "article" to "articles"

        db.collection("articles").add(article).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(Constants.TAG, "Successfully written! " + documentReference.toString());
                String articleId = documentReference.getId();
                documentReference.update("article_id", articleId);
                Map<String, Object> schedule = new HashMap<>();
                for (int i = 0; i < mSchedules.size(); i++) {
                    schedule.put("schedule_title", mSchedules.get(i).getScheduleTitle());
                    schedule.put("schedule_type", mSchedules.get(i).getType());
                    schedule.put("schedule_weight", mSchedules.get(i).getScheduleWeight());
                    schedule.put("schedule_reps", mSchedules.get(i).getScheduleReps());

                    String documentKey = String.valueOf(i);

                    documentReference.collection("schedule").document(documentKey).set(schedule);
                }

                //TODO: Available clean data
                refresh();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(Constants.TAG, "Error writing document " + e.toString());
                Toast.makeText(Fitnessch.getAppContext(), "新增課表失敗，請檢查網路連線。", Toast.LENGTH_SHORT).show();
            }
        });

        CollectionReference collectionReference = db.collection("schedule_title_list");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        String title = documentSnapshot.getData().get("title").toString();
                        
                    }
                }
            }
        });
    }

    @Override
    public void showSchedules(List<Schedule> schedules) {
        mAddNewArticleView.showSchedule(schedules);
    }

    @Override
    public void onScrollStateChanged(int visibleItemCount, int totalItemCount, int newState) {

    }

    @Override
    public void onScrolled(RecyclerView.LayoutManager layoutManager) {
//        if (layoutManager instanceof LinearLayoutManager) {
//
//            mlastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
//                    .findLastVisibleItemPosition();
//            mfirstVisibleItemPosition = ((LinearLayoutManager) layoutManager)
//                    .findFirstVisibleItemPosition();
//
//        } else if (layoutManager instanceof GridLayoutManager) {
//
//            mlastVisibleItemPosition = ((GridLayoutManager) layoutManager)
//                    .findLastVisibleItemPosition();
//            mfirstVisibleItemPosition = ((GridLayoutManager) layoutManager)
//                    .findFirstVisibleItemPosition();
//        }
    }

    @Override
    public void refresh() {
        mAddNewView.refreshUi();
        mAddNewArticleView.refreshUi();
        if (mAddNewMealArticleView != null) {
            mAddNewMealArticleView.refreshUi();
        }
    }


}
