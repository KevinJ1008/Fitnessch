package com.kevinj1008.fitnessch.addnewmealchild;

import static com.google.common.base.Preconditions.checkNotNull;

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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kevinj1008.fitnessch.Fitnessch;
import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.activities.FitnesschActivity;
import com.kevinj1008.fitnessch.adapters.AddNewMealChildAdapter;
import com.kevinj1008.fitnessch.objects.Meal;
import com.kevinj1008.fitnessch.objects.Title;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class AddNewMealChildFragment extends Fragment implements AddNewMealChildContract.View {

    private AddNewMealChildAdapter mAddNewMealChildAdapter;
    private List<Meal> mMeals;
    private Button mAddNewBtn;
    private EditText mMealIngredient;
    private EditText mMealCal;
    private Button mMealCompleteBtn;
    private ImageView mAddBtn;
    private ImageView mNextBtn;
    private AutoCompleteTextView mSearchText;
    private AddNewMealChildContract.Presenter mPresenter;

    public static AddNewMealChildFragment newInstance() {
        return new AddNewMealChildFragment();
    }

    @Override
    public void setPresenter(AddNewMealChildContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.start();

        mMeals = new ArrayList<>();
        mAddNewMealChildAdapter = new AddNewMealChildAdapter(mMeals, getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.childfragment_addnewmeal, container, false);

        mAddNewBtn = root.findViewById(R.id.addnew_meal_btn);
        mMealIngredient = root.findViewById(R.id.addnew_meal_ingredient_edittext);
        mMealCal = root.findViewById(R.id.addnew_meal_cal_edittext);
        mMealCompleteBtn = root.findViewById(R.id.addnew_meal_complete_btn);
        mAddBtn = root.findViewById(R.id.addnew_meal_btn_iamge);
        mNextBtn = root.findViewById(R.id.addnew_meal_next_btn);
        mSearchText = root.findViewById(R.id.addnew_meal_auto_search);

        InputFilter typeFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int i, int i1, Spanned spanned, int i2, int i3) {
                Pattern pattern = Pattern.compile("[a-zA-Z|\u4e00-\u9fa5]+");
                Matcher matcher = pattern.matcher(source.toString());
                if (!matcher.matches()) return "";
                return null;
            }
        };

        mSearchText.setFilters(new InputFilter[]{typeFilter});
        mMealIngredient.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5), typeFilter});
        mMealCal.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

        mMealIngredient.setOnFocusChangeListener(focusChangeListener);
        mMealCal.setOnFocusChangeListener(focusChangeListener);
        mSearchText.setOnFocusChangeListener(focusChangeListener);

        ConstraintLayout constraintLayout = root.findViewById(R.id.childfragment_addnewmeal);
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
    public void showMealSearchTitle(List<Title> titles) {
        String[] titleList = new String[titles.size()];
        for (int i = 0; i < titles.size(); i++) {
            titleList[i] = titles.get(i).getTitle();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Fitnessch.getAppContext(), R.layout.search_text_layout, R.id.search_text, titleList);
        mSearchText.setThreshold(1);
        mSearchText.setAdapter(adapter);
    }

    @Override
    public void refreshUi() {
        mAddNewMealChildAdapter.clearData();

        mMealIngredient.getText().clear();
        mMealCal.getText().clear();
        mSearchText.getText().clear();

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.addnew_meal_btn_iamge) {
                String title = mSearchText.getText().toString();
                String ingredient = mMealIngredient.getText().toString();
                String cal = mMealCal.getText().toString();

                InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(view.getWindowToken(), 0);

                if (!"".equals(title) && !"".equals(ingredient) && !"".equals(cal) && !cal.startsWith("0")) {
                    if (!title.contains(" ") && !ingredient.contains(" ") && !cal.contains(" ")) {
                        Meal meal = new Meal();
                        meal.setMealTitle(title);
                        meal.setMealIngredient(ingredient);
                        meal.setMealCal(cal + " cal");

                        mAddNewMealChildAdapter.updateData(meal);

                        mMealIngredient.getText().clear();
                        mMealCal.getText().clear();
                    } else {
                        Toast.makeText(getContext(), "請勿輸入空白。", Toast.LENGTH_SHORT).show();
                        mSearchText.clearFocus();
                        mMealIngredient.clearFocus();
                        mMealCal.clearFocus();
                    }
                } else {
                    Toast.makeText(getContext(), "請輸入菜單、食材及熱量。", Toast.LENGTH_SHORT).show();
                    mSearchText.clearFocus();
                    mMealIngredient.clearFocus();
                    mMealCal.clearFocus();
                }
                mSearchText.clearFocus();
                mMealIngredient.clearFocus();
                mMealCal.clearFocus();

            } else if (view.getId() == R.id.addnew_meal_next_btn) {
                if (mAddNewMealChildAdapter.getMeals().size() > 0) {
                    mSearchText.getText().clear();
                    mMealIngredient.getText().clear();
                    mMealCal.getText().clear();

                    mSearchText.clearFocus();
                    mMealIngredient.clearFocus();
                    mMealCal.clearFocus();

                    ((FitnesschActivity) getActivity()).transToAddNewMealArticle(mAddNewMealChildAdapter.getMeals());
                } else if (mAddNewMealChildAdapter.getMealMap().size() > 0) {
                    mSearchText.getText().clear();
                    mMealIngredient.getText().clear();
                    mMealCal.getText().clear();

                    mSearchText.clearFocus();
                    mMealIngredient.clearFocus();
                    mMealCal.clearFocus();

                    ((FitnesschActivity) getActivity()).transToAddNewMealArticle(mAddNewMealChildAdapter.getNewMealList());
                } else {
                    Toast.makeText(getContext(), "請輸入菜單。", Toast.LENGTH_SHORT).show();
                }

            } else if (view.getId() == R.id.childfragment_addnewmeal) {
                InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(view.getWindowToken(), 0);
                mSearchText.clearFocus();
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
