package com.kevinj1008.fitnessch.addnewmealchild;

import com.kevinj1008.fitnessch.BasePresenter;
import com.kevinj1008.fitnessch.BaseView;
import com.kevinj1008.fitnessch.objects.Meal;
import com.kevinj1008.fitnessch.objects.Title;

import java.util.List;

public interface AddNewMealChildContract {

    interface View extends BaseView<Presenter> {

        void showMealSearchTitle(List<Title> titles);

        void refreshUi();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void searchTitle();

        void openAddNewArticle(List<Meal> meals);

        void refreshMealUi();
    }
}
