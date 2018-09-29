package com.kevinj1008.fitnessch.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.mealchild.MealChildFragment;
import com.kevinj1008.fitnessch.mealchild.MealChildPresenter;
import com.kevinj1008.fitnessch.schedulechild.ScheduleChildFragment;
import com.kevinj1008.fitnessch.schedulechild.ScheduleChildPresenter;
import com.kevinj1008.fitnessch.adapters.ProfileViewPagerAdapter;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProfileFragment extends Fragment implements ProfileContract.View {
    private ProfileContract.Presenter mPresenter;
    private ProfileViewPagerAdapter mViewPagerAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private ScheduleChildFragment mScheduleChildFragment;
    private ScheduleChildPresenter mScheduleChildPresenter;

    private MealChildFragment mMealChildFragment;
    private MealChildPresenter mMealChildPresenter;


    public ProfileFragment() {
        // Requires empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mScheduleChildFragment == null) mScheduleChildFragment = ScheduleChildFragment.newInstance();
        if (mScheduleChildPresenter == null) {
            mScheduleChildPresenter = new ScheduleChildPresenter(mScheduleChildFragment);
        }

        if (mMealChildFragment == null) mMealChildFragment = MealChildFragment.newInstance();
        if (mMealChildPresenter == null) {
            mMealChildPresenter = new MealChildPresenter(mMealChildFragment);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        mViewPager = root.findViewById(R.id.profile_view_pager);
        setupViewPager(mViewPager);

        mTabLayout = root.findViewById(R.id.profile_tab_container);
        mTabLayout.setupWithViewPager(mViewPager);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mPresenter.setupPresenter(, mScheduleChildPresenter);
//        mPresenter.start();
    }

    private void setupViewPager(ViewPager viewPager) {
        mViewPagerAdapter = new ProfileViewPagerAdapter(getFragmentManager());
        mViewPagerAdapter.addFragment(mScheduleChildFragment, getString(R.string.all_schedule_title));
        mViewPagerAdapter.addFragment(mMealChildFragment, getString(R.string.all_meal_title));
        viewPager.setAdapter(mViewPagerAdapter);
    }

    @Override
    public void showScheduleChildUi() {

    }

    @Override
    public void showMealChildUi() {

    }


}
