package com.kevinj1008.fitnessch.addnewmealchild;

import com.kevinj1008.fitnessch.objects.Meal;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddNewMealChildPresenter implements AddNewMealChildContract.Presenter {

    private AddNewMealChildContract.View mAddNewMealChildView;

    public AddNewMealChildPresenter(AddNewMealChildContract.View addNewMealChildView) {
        mAddNewMealChildView = checkNotNull(addNewMealChildView, "addNewMealChildView cannot be null!");
        mAddNewMealChildView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void setMeal(String title, String ingredient, String cal, int position) {

    }

    @Override
    public void openAddNewArticle(List<Meal> meals) {

    }

    @Override
    public void start() {

    }
}
