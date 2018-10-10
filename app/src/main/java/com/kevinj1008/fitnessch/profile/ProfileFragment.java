package com.kevinj1008.fitnessch.profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.activities.FitnesschActivity;
import com.kevinj1008.fitnessch.mealchild.MealChildFragment;
import com.kevinj1008.fitnessch.mealchild.MealChildPresenter;
import com.kevinj1008.fitnessch.objects.User;
import com.kevinj1008.fitnessch.schedulechild.ScheduleChildFragment;
import com.kevinj1008.fitnessch.schedulechild.ScheduleChildPresenter;
import com.kevinj1008.fitnessch.adapters.ProfileViewPagerAdapter;
import com.kevinj1008.fitnessch.util.SharedPreferencesManager;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProfileFragment extends Fragment implements ProfileContract.View {
    private ProfileContract.Presenter mPresenter;
    private ProfileViewPagerAdapter mViewPagerAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private EditText mProfileEditHeight;
    private EditText mProfileEditWeight;
    private EditText mProfileEditInfo;
    private ImageView mProfileEditBtn;
    private ImageView mProfileConfirmBtn;
    private TextView mProfileHeight;
    private TextView mProfileWeight;
    private TextView mProfileInfo;
    private SharedPreferencesManager mSharedPreferencesManager;

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

        mPresenter.start();

        mSharedPreferencesManager = new SharedPreferencesManager(getContext());

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

        mProfileEditHeight = root.findViewById(R.id.profile_height_edittext);
        mProfileEditWeight = root.findViewById(R.id.profile_weight_edittext);
        mProfileEditInfo = root.findViewById(R.id.profile_info_edittext);
        mProfileEditBtn = root.findViewById(R.id.profile_edit_btn);
        mProfileConfirmBtn = root.findViewById(R.id.profile_confirm_btn);
        mProfileHeight = root.findViewById(R.id.profile_height);
        mProfileWeight = root.findViewById(R.id.profile_weight);
        mProfileInfo = root.findViewById(R.id.profile_info);

        mProfileEditBtn.setOnClickListener(clickListener);
        mProfileConfirmBtn.setOnClickListener(clickListener);

        mProfileEditInfo.setOnFocusChangeListener(focusChangeListener);
        mProfileEditWeight.setOnFocusChangeListener(focusChangeListener);
        mProfileEditHeight.setOnFocusChangeListener(focusChangeListener);

        ImageView userImage = root.findViewById(R.id.profile_image);
        String userPhoto = mSharedPreferencesManager.getUserPhoto();
        Picasso.get()
                .load(userPhoto)
                .placeholder(R.drawable.all_placeholder_avatar)
                .transform(new CropCircleTransformation())
                .into(userImage);

        TextView userName = root.findViewById(R.id.profile_name);
        userName.setText(mSharedPreferencesManager.getUserName());

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

    @Override
    public void showProfileInfo(User user) {
        mProfileWeight.setText(user.getWeight());
        mProfileHeight.setText(user.getHeight());
        mProfileInfo.setText(user.getInfo());
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.profile_edit_btn) {
                mProfileHeight.setVisibility(View.INVISIBLE);
                mProfileWeight.setVisibility(View.INVISIBLE);
                mProfileInfo.setVisibility(View.INVISIBLE);
                mProfileEditBtn.setVisibility(View.INVISIBLE);
                mProfileConfirmBtn.setVisibility(View.VISIBLE);

                mProfileEditHeight.setVisibility(View.VISIBLE);
                mProfileEditWeight.setVisibility(View.VISIBLE);
                mProfileEditInfo.setVisibility(View.VISIBLE);

                mProfileEditHeight.setText(mProfileHeight.getText());
                mProfileEditWeight.setText(mProfileWeight.getText());
                mProfileEditInfo.setText(mProfileInfo.getText());

            } else if (view.getId() == R.id.profile_confirm_btn) {
                String height = mProfileEditHeight.getText().toString();
                String weight = mProfileEditWeight.getText().toString();
                String info = mProfileEditInfo.getText().toString();

                InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(view.getWindowToken(), 0);

                if (!height.equals("") && !weight.equals("") && !info.equals("")) {
                    User user = new User();
                    user.setHeight(height);
                    user.setWeight(weight);
                    user.setInfo(info);

                    mPresenter.sendProfileInfo(user);

                    mProfileEditInfo.getText().clear();
                    mProfileEditWeight.getText().clear();
                    mProfileEditHeight.getText().clear();

                    mProfileEditHeight.setVisibility(View.INVISIBLE);
                    mProfileEditWeight.setVisibility(View.INVISIBLE);
                    mProfileEditInfo.setVisibility(View.INVISIBLE);

                    mProfileHeight.setVisibility(View.VISIBLE);
                    mProfileWeight.setVisibility(View.VISIBLE);
                    mProfileInfo.setVisibility(View.VISIBLE);
                    mProfileEditBtn.setVisibility(View.VISIBLE);
                    mProfileConfirmBtn.setVisibility(View.INVISIBLE);

                } else {
                    Toast.makeText(getContext(), "請輸入個人資訊。", Toast.LENGTH_SHORT).show();
                }
            } else if (view.getId() == R.id.profile_page) {
                InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            mProfileEditHeight.clearFocus();
            mProfileEditWeight.clearFocus();
            mProfileEditInfo.clearFocus();
        }
    };

    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (!hasFocus) {
                ((FitnesschActivity) getActivity()).hideKeyboard(view);

                mProfileEditInfo.getText().clear();
                mProfileEditWeight.getText().clear();
                mProfileEditHeight.getText().clear();

                mProfileEditHeight.setVisibility(View.INVISIBLE);
                mProfileEditWeight.setVisibility(View.INVISIBLE);
                mProfileEditInfo.setVisibility(View.INVISIBLE);

                mProfileHeight.setVisibility(View.VISIBLE);
                mProfileWeight.setVisibility(View.VISIBLE);
                mProfileInfo.setVisibility(View.VISIBLE);
                mProfileEditBtn.setVisibility(View.VISIBLE);
                mProfileConfirmBtn.setVisibility(View.INVISIBLE);

                mProfileEditHeight.clearFocus();
                mProfileEditWeight.clearFocus();
                mProfileEditInfo.clearFocus();
            }
        }
    };

}
