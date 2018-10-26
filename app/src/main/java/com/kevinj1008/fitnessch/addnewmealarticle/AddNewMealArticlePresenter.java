package com.kevinj1008.fitnessch.addnewmealarticle;

import static com.google.common.base.Preconditions.checkNotNull;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kevinj1008.fitnessch.Fitnessch;
import com.kevinj1008.fitnessch.addnew.AddNewContract;
import com.kevinj1008.fitnessch.addnewarticle.AddNewArticleContract;
import com.kevinj1008.fitnessch.addnewmealchild.AddNewMealChildContract;
import com.kevinj1008.fitnessch.objects.Meal;
import com.kevinj1008.fitnessch.util.Constants;
import com.kevinj1008.fitnessch.util.SharedPreferencesManager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class AddNewMealArticlePresenter implements AddNewMealArticleContract.Presenter {

    private AddNewMealArticleContract.View mAddNewMealArticleView;
    private AddNewMealChildContract.View mAddNewMealChildView;
    private AddNewArticleContract.View mAddNewArticleView;
    private AddNewContract.View mAddNewView;
    private List<Meal> mMeals;
    private SharedPreferencesManager mSharedPreferencesManager;
    private int mlastVisibleItemPosition;
    private int mfirstVisibleItemPosition;

    public AddNewMealArticlePresenter(AddNewMealArticleContract.View addNewMealArticleView,
                                      AddNewContract.View addNewView, AddNewArticleContract.View addNewArticleView,
                                      List<Meal> meals) {
        mAddNewMealArticleView = checkNotNull(addNewMealArticleView, "addNewMealArticleView cannot be null!");
        mAddNewView = checkNotNull(addNewView, "addNewView cannot be null!");
        if (addNewArticleView != null) {
            mAddNewArticleView = checkNotNull(addNewArticleView, "addNewArticleView cannot be null!");
        }
        mAddNewMealArticleView.setPresenter(this);
        mMeals = meals;
        mSharedPreferencesManager = new SharedPreferencesManager(Fitnessch.getAppContext());
    }


    @Override
    public void start() {
        showMeals(mMeals);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void sendMeal(String title, String content) {
        String author = mSharedPreferencesManager.getUserName();
        String authorId = mSharedPreferencesManager.getUserDbUid();
        String authorPhoto = mSharedPreferencesManager.getUserPhoto();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        Map<String, Object> article = new HashMap<>();
        article.put("article_tag", "菜單");
        article.put("author", author);
        article.put("user_id", authorId);
        article.put("author_photo", authorPhoto);
        article.put("title", title);
        article.put("content", content);
        article.put("create_time", FieldValue.serverTimestamp());
        article.put("create_year", year);
        article.put("create_month", month);
        article.put("create_day", day);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("articles").add(article).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(Constants.TAG, "Successfully written! " + documentReference.toString());
                String articleId = documentReference.getId();
                documentReference.update("article_id", articleId);
                Map<String, Object> meal = new HashMap<>();
                for (int i = 0; i < mMeals.size(); i++) {
                    meal.put("meal_title", mMeals.get(i).getMealTitle());
                    meal.put("meal_type", mMeals.get(i).getMealType());
                    meal.put("meal_ingredient", mMeals.get(i).getMealIngredient());
                    meal.put("meal_cal", mMeals.get(i).getMealCal());

                    String documentKey;
                    if (i < 10) {
                        documentKey = "0" + String.valueOf(i);
                    } else {
                        documentKey = String.valueOf(i);
                    }

                    documentReference.collection("meal").document(documentKey).set(meal);
                }

                //TODO: Available clean data
                refresh();
                Toast.makeText(Fitnessch.getAppContext(), "建立菜單成功。", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(Constants.TAG, "Error writing document " + e.toString());
                Toast.makeText(Fitnessch.getAppContext(), "新增菜單失敗，請檢查網路連線。", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showMeals(List<Meal> meals) {
        mAddNewMealArticleView.showMeal(meals);
    }

    @Override
    public void onScrollStateChanged(int visibleItemCount, int totalItemCount, int newState) {

    }

    @Override
    public void onScrolled(RecyclerView.LayoutManager layoutManager) {

    }

    @Override
    public void refresh() {
        mAddNewView.refreshUi();
        mAddNewMealArticleView.refreshUi();
        if (mAddNewArticleView != null) {
            mAddNewArticleView.refreshUi();
        }
    }

}
