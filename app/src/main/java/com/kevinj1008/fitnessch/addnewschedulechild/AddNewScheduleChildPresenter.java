package com.kevinj1008.fitnessch.addnewschedulechild;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kevinj1008.fitnessch.objects.Schedule;
import com.kevinj1008.fitnessch.objects.Title;
import com.kevinj1008.fitnessch.util.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddNewScheduleChildPresenter implements AddNewScheduleChildContract.Presenter {

    private AddNewScheduleChildContract.View mAddNewScheduleChildView;
    private List<Title> mTitles;

    public AddNewScheduleChildPresenter(AddNewScheduleChildContract.View addNewScheduleChildView) {
        mAddNewScheduleChildView = checkNotNull(addNewScheduleChildView, "addNewScheduleChildView cannot be null!");
        mAddNewScheduleChildView.setPresenter(this);
        mTitles = new ArrayList<>();
    }

    @Override
    public void start() {
        searchTitle();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void searchTitle() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //TODO:change "title_list" to "schedule_title_list"
        CollectionReference reference = db.collection("title_list");
        reference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                String title = documentSnapshot.getData().get("title").toString();
                                Title titles = new Title();
                                titles.setTitle(title);
                                mTitles.add(titles);
                            }
                            mAddNewScheduleChildView.showScheduleSearchTitle(mTitles);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(Constants.TAG, "Fail to get title data " + e.getMessage());
            }
        });
    }

    @Override
    public void openAddNewArticle(List<Schedule> schedules) {

    }


}
