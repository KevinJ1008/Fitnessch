package com.kevinj1008.fitnessch.profile;

import static com.google.common.base.Preconditions.checkNotNull;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.kevinj1008.fitnessch.Fitnessch;
import com.kevinj1008.fitnessch.mealchild.MealChildFragment;
import com.kevinj1008.fitnessch.mealchild.MealChildPresenter;
import com.kevinj1008.fitnessch.objects.User;
import com.kevinj1008.fitnessch.schedulechild.ScheduleChildFragment;
import com.kevinj1008.fitnessch.schedulechild.ScheduleChildPresenter;
import com.kevinj1008.fitnessch.util.Constants;
import com.kevinj1008.fitnessch.util.SharedPreferencesManager;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;


public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View mProfileView;
    private FragmentManager mChildFragmentManager;
    private SharedPreferencesManager mSharedPreferencesManager;

    private ScheduleChildFragment mScheduleChildFragment;
    private ScheduleChildPresenter mScheduleChildPresenter;

    private MealChildFragment mMealChildFragment;
    private MealChildPresenter mMealChildPresenter;

    public ProfilePresenter(ProfileContract.View profileView, FragmentManager fragmentManager) {
        mProfileView = checkNotNull(profileView, "profileView cannot be null!");
        mProfileView.setPresenter(this);

        mChildFragmentManager = fragmentManager;
        mSharedPreferencesManager = new SharedPreferencesManager(Fitnessch.getAppContext());

    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void setupPresenter(ScheduleChildFragment fragment, ScheduleChildPresenter presenter) {
        mScheduleChildFragment = fragment;
        mScheduleChildPresenter = presenter;
    }

    @Override
    public void start() {
        loadProfileInfo();
    }

    @Override
    public void transToScheduleChild() {

    }

    @Override
    public void transToMealChild() {

    }

    @Override
    public void refreshLikedArticles() {

    }

    @Override
    public void sendProfileInfo(User user) {
        Map<String, Object> info = new HashMap<>();

        info.put("height", user.getHeight());
        info.put("weight", user.getWeight());
        info.put("info", user.getInfo());

        String self = mSharedPreferencesManager.getUserDbUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(self).update(info).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Fitnessch.getAppContext(), "更新完成。", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Fitnessch.getAppContext(), "更新個人資訊失敗。", Toast.LENGTH_SHORT).show();
                Log.d(Constants.TAG, "Fail to update personal info " + e.getMessage());
            }
        });
    }

    @Override
    public void loadProfileInfo() {
        String self = mSharedPreferencesManager.getUserDbUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("db_uid", self)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d(Constants.TAG, "Articles listen failed.", e);
                            return;
                        }
//                        String source = snapshot != null && snapshot.getMetadata().hasPendingWrites() ? "Local" : "Server";
//                        if (source.equals("Server")) {
                            for (DocumentChange documentChange  : snapshot.getDocumentChanges()) {
                                String weight = "0";
                                try {
                                    weight = documentChange.getDocument().getData().get("weight").toString();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                String height = "0";
                                try {
                                    height = documentChange.getDocument().getData().get("height").toString();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                String info = "...";
                                try {
                                    info = documentChange.getDocument().getData().get("info").toString();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                User user = new User();
                                user.setHeight(height);
                                user.setWeight(weight);
                                user.setInfo(info);

                                mProfileView.showProfileInfo(user);
//                            }
                        }
                    }
                });
    }


}
