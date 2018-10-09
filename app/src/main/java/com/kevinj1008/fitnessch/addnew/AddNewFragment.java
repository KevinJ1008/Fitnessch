package com.kevinj1008.fitnessch.addnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.adapters.AddNewViewPagerAdapter;
import com.kevinj1008.fitnessch.addnewmealchild.AddNewMealChildFragment;
import com.kevinj1008.fitnessch.addnewmealchild.AddNewMealChildPresenter;
import com.kevinj1008.fitnessch.addnewschedulechild.AddNewScheduleChildFragment;
import com.kevinj1008.fitnessch.addnewschedulechild.AddNewScheduleChildPresenter;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddNewFragment extends Fragment implements AddNewContract.View {
    private AddNewContract.Presenter mPresenter;
    private AddNewViewPagerAdapter mViewPagerAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private AddNewScheduleChildFragment mAddNewScheduleChildFragment;
    private AddNewScheduleChildPresenter mAddNewScheduleChildPresenter;

    private AddNewMealChildFragment mAddNewMealChildFragment;
    private AddNewMealChildPresenter mAddNewMealChildPresenter;

    public AddNewFragment() {
        // Requires empty public constructor
    }

    public static AddNewFragment newInstance() {
        return new AddNewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mAddNewScheduleChildFragment == null) mAddNewScheduleChildFragment = AddNewScheduleChildFragment.newInstance();
        if (mAddNewScheduleChildPresenter == null) {
            mAddNewScheduleChildPresenter = new AddNewScheduleChildPresenter(mAddNewScheduleChildFragment);
        }

//        if (mAddNewMealChildFragment == null) mAddNewMealChildFragment = AddNewMealChildFragment.newInstance();
//        if (mAddNewMealChildPresenter == null) {
//            mAddNewMealChildPresenter = new AddNewMealChildPresenter(mAddNewMealChildFragment);
//        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(AddNewContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addnew, container, false);

        mViewPager = root.findViewById(R.id.addnew_viewpager);
        setupViewPager(mViewPager);

        mTabLayout = root.findViewById(R.id.addnew_tab_container);
        mTabLayout.setupWithViewPager(mViewPager);

        return root;
    }

    private void setupViewPager(ViewPager viewPager) {
        mViewPagerAdapter = new AddNewViewPagerAdapter(getFragmentManager());
        mViewPagerAdapter.addFragment(mAddNewScheduleChildFragment, getString(R.string.all_schedule_title));
        mViewPagerAdapter.addFragment(new AddNewMealChildFragment(), getString(R.string.all_meal_title));
        viewPager.setAdapter(mViewPagerAdapter);
    }

    @Override
    public void showAddNewScheduleChildUi() {

    }

    @Override
    public void showAddNewMealChildUi() {

    }

    @Override
    public void refreshSchedule() {
        mAddNewScheduleChildFragment.refreshUi();
    }


}
