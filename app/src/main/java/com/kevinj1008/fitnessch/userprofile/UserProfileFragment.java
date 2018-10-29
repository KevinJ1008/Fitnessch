package com.kevinj1008.fitnessch.userprofile;

import static com.google.common.base.Preconditions.checkNotNull;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.adapters.UserProfileViewPagerAdapter;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.objects.User;
import com.kevinj1008.fitnessch.usermealchild.UserMealChildFragment;
import com.kevinj1008.fitnessch.usermealchild.UserMealChildPresenter;
import com.kevinj1008.fitnessch.userschedulechild.UserScheduleChildFragment;
import com.kevinj1008.fitnessch.userschedulechild.UserScheduleChildPresenter;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class UserProfileFragment extends Fragment implements UserProfileContract.View {

    private UserProfileContract.Presenter mPresenter;
    private UserProfileViewPagerAdapter mUserProfileViewPagerAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private UserScheduleChildFragment mUserScheduleChildFragment;
    private UserScheduleChildPresenter mUserScheduleChildPresenter;
    private UserMealChildFragment mUserMealChildFragment;
    private UserMealChildPresenter mUserMealChildPresenter;

    private TextView mUserProfileHeight;
    private TextView mUserProfileWeight;
    private TextView mUserProfileInfo;
    private TextView mUserName;
    private ImageView mUserImage;
    private TextView mUserProfileHeightUnit;
    private TextView mUserProfileWeightUnit;
    private Article mArticle;

    public UserProfileFragment() {
        // Requires empty public constructor
    }

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.start();

        if (mUserScheduleChildFragment == null) mUserScheduleChildFragment = UserScheduleChildFragment.newInstance();
        if (mUserScheduleChildPresenter == null) {
            mUserScheduleChildPresenter = new UserScheduleChildPresenter(mUserScheduleChildFragment, mArticle);
        }

        if (mUserMealChildFragment == null) mUserMealChildFragment = UserMealChildFragment.newInstance();
        if (mUserMealChildPresenter == null) {
            mUserMealChildPresenter = new UserMealChildPresenter(mUserMealChildFragment, mArticle);
        }

    }

    @Override
    public void setPresenter(UserProfileContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_profile, container, false);

        mUserProfileHeight = root.findViewById(R.id.user_profile_height);
        mUserProfileWeight = root.findViewById(R.id.user_profile_weight);
        mUserProfileInfo = root.findViewById(R.id.user_profile_info);
        mUserName = root.findViewById(R.id.user_profile_name);
        mUserImage = root.findViewById(R.id.user_profile_image);
        mUserProfileHeightUnit = root.findViewById(R.id.user_profile_height_unit);
        mUserProfileWeightUnit = root.findViewById(R.id.user_profile_weight_unit);

        mViewPager = root.findViewById(R.id.user_profile_view_pager);
        setupViewPager(mViewPager);

        mTabLayout = root.findViewById(R.id.user_profile_tab_container);
        mTabLayout.setupWithViewPager(mViewPager);

        return root;
    }

    private void setupViewPager(ViewPager viewPager) {
        mUserProfileViewPagerAdapter = new UserProfileViewPagerAdapter(getFragmentManager());
        mUserProfileViewPagerAdapter.addFragment(mUserScheduleChildFragment, getString(R.string.all_schedule_title));
        mUserProfileViewPagerAdapter.addFragment(mUserMealChildFragment, getString(R.string.all_meal_title));
        viewPager.setAdapter(mUserProfileViewPagerAdapter);
    }

    @Override
    public void showUserScheduleChildUi(Article article) {
        mArticle = article;
    }

    @Override
    public void showUserChildUi(Article article) {
        mUserMealChildPresenter.loadArticles(article);
        mUserScheduleChildPresenter.loadArticles(article);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (hidden) {
//            mPresenter.refresh();
//        }
    }

    @Override
    public void showUserProfileInfo(User user) {
        mUserProfileHeight.setText(user.getHeight());
        mUserProfileWeight.setText(user.getWeight());
        mUserProfileInfo.setText(user.getInfo());
        mUserName.setText(user.getName());
        Picasso.get()
                .load(user.getPhoto())
                .placeholder(R.drawable.all_placeholder_avatar)
                .transform(new CropCircleTransformation())
                .into(mUserImage);
    }

    @Override
    public void refreshUserUi() {
        if (mUserMealChildFragment != null) mUserMealChildFragment.refreshUserUi();
        if (mUserScheduleChildFragment != null) mUserScheduleChildFragment.refreshUserUi();
    }


}
