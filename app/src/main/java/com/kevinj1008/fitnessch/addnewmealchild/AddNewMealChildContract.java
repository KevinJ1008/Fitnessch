package com.kevinj1008.fitnessch.addnewmealchild;

import com.kevinj1008.fitnessch.BasePresenter;
import com.kevinj1008.fitnessch.BaseView;
import com.kevinj1008.fitnessch.objects.Meal;

import java.util.List;

public interface AddNewMealChildContract {

    interface View extends BaseView<Presenter> {

        void showScheduleItem(int position);

        void refreshUi();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void setMeal(String title, String ingredient, String cal, int position);

        void openAddNewArticle(List<Meal> meals);
    }
}
