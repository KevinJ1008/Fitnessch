package com.kevinj1008.fitnessch.addnewmealchild;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kevinj1008.fitnessch.Fitnessch;
import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.activities.FitnesschActivity;
import com.kevinj1008.fitnessch.adapters.AddNewMealChildAdapter;
import com.kevinj1008.fitnessch.objects.Meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddNewMealChildFragment extends Fragment implements AddNewMealChildContract.View {

    private AddNewMealChildAdapter mAddNewMealChildAdapter;
    private List<Meal> mMeals;
    private Button mAddNewBtn;
    private EditText mMealTitle;
    private EditText mMealIngredient;
    private EditText mMealCal;
    private Button mMealCompleteBtn;
    private ImageView mAddBtn;
    private ImageView mNextBtn;

    public static AddNewMealChildFragment newInstance() {
        return new AddNewMealChildFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMeals = new ArrayList<>();
        mAddNewMealChildAdapter = new AddNewMealChildAdapter(mMeals);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.childfragment_addnewmeal, container, false);

        mAddNewBtn = root.findViewById(R.id.addnew_meal_btn);
        mMealTitle = root.findViewById(R.id.addnew_meal_title_edittext);
        mMealIngredient = root.findViewById(R.id.addnew_meal_ingredient_edittext);
        mMealCal = root.findViewById(R.id.addnew_meal_cal_edittext);
        mMealCompleteBtn = root.findViewById(R.id.addnew_meal_complete_btn);
        mAddBtn = root.findViewById(R.id.addnew_meal_btn_iamge);
        mNextBtn = root.findViewById(R.id.addnew_meal_next_btn);
        ConstraintLayout constraintLayout = root.findViewById(R.id.childfragment_addnewmeal);

        InputFilter typeFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int i, int i1, Spanned spanned, int i2, int i3) {
                Pattern pattern = Pattern.compile("[a-zA-Z|\u4e00-\u9fa5]+");
                Matcher matcher = pattern.matcher(source.toString());
                if (!matcher.matches()) return "";
                return null;
            }
        };

        mMealTitle.setFilters(new InputFilter[]{typeFilter});
        mMealIngredient.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5), typeFilter});
        mMealCal.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

        mMealTitle.setOnFocusChangeListener(focusChangeListener);
        mMealIngredient.setOnFocusChangeListener(focusChangeListener);
        mMealCal.setOnFocusChangeListener(focusChangeListener);

        constraintLayout.setOnClickListener(clickListener);
        mAddNewBtn.setOnClickListener(clickListener);
        mMealCompleteBtn.setOnClickListener(clickListener);
        mAddBtn.setOnClickListener(clickListener);
        mNextBtn.setOnClickListener(clickListener);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_addnew_meal);
        recyclerView.setLayoutManager(new LinearLayoutManager(Fitnessch.getAppContext()));
        recyclerView.setAdapter(mAddNewMealChildAdapter);

        return root;
    }

    @Override
    public void setPresenter(AddNewMealChildContract.Presenter presenter) {

    }

    @Override
    public void showScheduleItem(int position) {

    }

    @Override
    public void refreshUi() {
        mAddNewMealChildAdapter.clearData();
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.addnew_meal_btn_iamge) {
                String title = mMealTitle.getText().toString();
                String ingredient = mMealIngredient.getText().toString();
                String cal = mMealCal.getText().toString() + " cal";

                InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(view.getWindowToken(), 0);

                if (!title.equals("") && !ingredient.equals("") && !cal.equals("") && !cal.startsWith("0")) {
                    Meal meal = new Meal();
                    meal.setMealTitle(title);
                    meal.setMealIngredient(ingredient);
                    meal.setMealCal(cal);

                    mAddNewMealChildAdapter.updateData(meal);

                    mMealTitle.getText().clear();
                    mMealIngredient.getText().clear();
                    mMealCal.getText().clear();
                } else {
                    Toast.makeText(getContext(), "請輸入菜單、食材及熱量。", Toast.LENGTH_SHORT).show();
                    mMealTitle.clearFocus();
                    mMealIngredient.clearFocus();
                    mMealCal.clearFocus();
                }
                mMealTitle.clearFocus();
                mMealIngredient.clearFocus();
                mMealCal.clearFocus();

            } else if (view.getId() == R.id.addnew_meal_next_btn) {
                if (mAddNewMealChildAdapter.getMeals().size() > 0) {
                    mMealTitle.getText().clear();
                    mMealIngredient.getText().clear();
                    mMealCal.getText().clear();

                    mMealTitle.clearFocus();
                    mMealIngredient.clearFocus();
                    mMealCal.clearFocus();

                    ((FitnesschActivity) getActivity()).transToAddNewMealArticle(mAddNewMealChildAdapter.getMeals());
                } else if (mAddNewMealChildAdapter.getMealMap().size() > 0) {
                    mMealTitle.getText().clear();
                    mMealIngredient.getText().clear();
                    mMealCal.getText().clear();

                    mMealTitle.clearFocus();
                    mMealIngredient.clearFocus();
                    mMealCal.clearFocus();

                    ((FitnesschActivity) getActivity()).transToAddNewMealArticle(mAddNewMealChildAdapter.getNewMealList());
                } else {
                    Toast.makeText(getContext(), "請輸入菜單。", Toast.LENGTH_SHORT).show();
                }

            } else if (view.getId() == R.id.childfragment_addnewmeal) {
                InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(view.getWindowToken(), 0);
                mMealTitle.clearFocus();
                mMealIngredient.clearFocus();
                mMealCal.clearFocus();
            }
        }
    };

    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (!hasFocus) {
                ((FitnesschActivity) getActivity()).hideKeyboard(view);
            }
        }
    };


}
