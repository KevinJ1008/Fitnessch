package com.kevinj1008.fitnessch.addnew;

import android.support.v4.app.FragmentManager;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kevinj1008.fitnessch.addnewmealchild.AddNewMealChildFragment;
import com.kevinj1008.fitnessch.addnewmealchild.AddNewMealChildPresenter;
import com.kevinj1008.fitnessch.addnewschedulechild.AddNewScheduleChildFragment;
import com.kevinj1008.fitnessch.addnewschedulechild.AddNewScheduleChildPresenter;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddNewPresenter implements AddNewContract.Presenter {

    private AddNewContract.View mAddNewView;
    private FragmentManager mChildFragmentManager;

    private AddNewScheduleChildFragment mAddNewScheduleChildFragment;
    private AddNewScheduleChildPresenter mAddNewScheduleChildPresenter;

    private AddNewMealChildFragment mAddNewMealChildFragment;
    private AddNewMealChildPresenter mAddNewMealChildPresenter;

    public AddNewPresenter(AddNewContract.View addNewView, FragmentManager fragmentManager) {
        mAddNewView = checkNotNull(addNewView, "addNewView cannot be null!");
        mAddNewView.setPresenter(this);

        mChildFragmentManager = fragmentManager;


    }

    @Override
    public void start() {
        transToAddNewScheduleChild();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void transToAddNewScheduleChild() {

    }

    @Override
    public void transToAddNewMealChild() {

    }

    @Override
    public void refreshUi() {
        mAddNewView.refreshUi();
    }

}
