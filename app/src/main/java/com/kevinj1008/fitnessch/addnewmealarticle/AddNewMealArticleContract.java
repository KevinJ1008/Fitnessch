package com.kevinj1008.fitnessch.addnewmealarticle;

import android.support.v7.widget.RecyclerView;

import com.kevinj1008.fitnessch.BasePresenter;
import com.kevinj1008.fitnessch.BaseView;
import com.kevinj1008.fitnessch.objects.Meal;

import java.util.List;

public interface AddNewMealArticleContract {

    interface View extends BaseView<Presenter> {

        void showMeal(List<Meal> meals);

        void showAddNewMealArticleUi();

        void refreshUi();

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void sendMeal(String title, String content);

        void showMeals(List<Meal> meals);

        void onScrollStateChanged(int visibleItemCount, int totalItemCount, int newState);

        void onScrolled(RecyclerView.LayoutManager layoutManager);

//        void updateInterestedIn(Article article, boolean isInterestedIn);

        void refresh();
    }
}
