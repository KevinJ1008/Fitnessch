package com.kevinj1008.fitnessch.userprofile;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.kevinj1008.fitnessch.Fitnessch;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.objects.User;
import com.kevinj1008.fitnessch.util.Constants;

import static com.google.common.base.Preconditions.checkNotNull;

public class UserProfilePresenter implements UserProfileContract.Presenter {

    private UserProfileContract.View mUserProfileView;
    private FragmentManager mChildFragmentManager;
    private Article mArticle;


    public UserProfilePresenter(UserProfileContract.View userProfileView, FragmentManager fragmentManager, Article article) {
        mUserProfileView = checkNotNull(userProfileView, "userProfileView cannot be null!");
        mUserProfileView.setPresenter(this);

        mChildFragmentManager = fragmentManager;
        mArticle = article;
    }

    @Override
    public void start() {
        loadUserProfileInfo(mArticle);
        transToUserScheduleChild();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void transToUserScheduleChild() {
        mUserProfileView.showUserScheduleChildUi(mArticle);
    }

    @Override
    public void transToUserMealChild() {

    }

    @Override
    public void refreshLikedArticles() {

    }

    @Override
    public void loadUserProfileInfo(Article article) {
        String uid = article.getAuthorId();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("db_uid", uid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.d(Constants.TAG, "User profile listen failed.", e);
                            return;
                        }
                        String source = snapshot != null && snapshot.getMetadata().hasPendingWrites() ? "Local" : "Server";
                        if (source.equals("Server")) {
                        for (DocumentChange documentChange  : snapshot.getDocumentChanges()) {
                            String name = "未知";
                            try {
                                name = documentChange.getDocument().getData().get("name").toString();
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }

                            String userId = "123";
                            try {
                                userId = documentChange.getDocument().getData().get("db_uid").toString();
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }

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

                            String photo = "...";
                            try {
                                photo = documentChange.getDocument().getData().get("photo").toString();
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }

                            User user = new User();
                            user.setId(userId);
                            user.setName(name);
                            user.setHeight(height);
                            user.setWeight(weight);
                            user.setInfo(info);
                            user.setPhoto(photo);

                            mUserProfileView.showUserProfileInfo(user);
                            }
                        }
                    }
                });
    }

    @Override
    public void refresh() {
        mUserProfileView.refreshUserUi();
    }


}
