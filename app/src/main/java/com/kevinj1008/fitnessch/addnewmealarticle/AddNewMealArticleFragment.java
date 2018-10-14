package com.kevinj1008.fitnessch.addnewmealarticle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kevinj1008.fitnessch.Fitnessch;
import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.activities.FitnesschActivity;
import com.kevinj1008.fitnessch.adapters.AddNewArticleAdapter;
import com.kevinj1008.fitnessch.adapters.AddNewMealArticleAdapter;
import com.kevinj1008.fitnessch.addnewarticle.AddNewArticleContract;
import com.kevinj1008.fitnessch.objects.Meal;
import com.kevinj1008.fitnessch.objects.Schedule;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddNewMealArticleFragment extends Fragment implements AddNewMealArticleContract.View {

    private AddNewMealArticleContract.Presenter mPresenter;
    private AddNewMealArticleAdapter mAddNewMealArticleAdapter;
    private List<Meal> mMeals;
    private EditText mTitleEditText;
    private EditText mContentEditText;

    public AddNewMealArticleFragment() {
        // Requires empty public constructor
    }

    public static AddNewMealArticleFragment newInstance() {
        return new AddNewMealArticleFragment();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(AddNewMealArticleContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAddNewMealArticleAdapter = new AddNewMealArticleAdapter(mPresenter);
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addnew_mealarticle, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_add_new_meal_article);
//        Button sendBtn = root.findViewById(R.id.add_new_meal_article_btn);
        ImageView sendBtn = root.findViewById(R.id.add_new_meal_article_btn);
        mTitleEditText = root.findViewById(R.id.add_new_meal_article_title_edittext);
        mContentEditText = root.findViewById(R.id.add_new_meal_article_content_edittext);
        ConstraintLayout constraintLayout = root.findViewById(R.id.fragment_addnew_meal_article);

        sendBtn.setOnClickListener(clickListener);
        constraintLayout.setOnClickListener(clickListener);

        mTitleEditText.setOnFocusChangeListener(focusChangeListener);
        mContentEditText.setOnFocusChangeListener(focusChangeListener);

        recyclerView.setLayoutManager(new LinearLayoutManager(Fitnessch.getAppContext()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                mPresenter.onScrollStateChanged(
                        recyclerView.getLayoutManager().getChildCount(),
                        recyclerView.getLayoutManager().getItemCount(),
                        newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                mPresenter.onScrolled(recyclerView.getLayoutManager());
            }
        });
        recyclerView.setAdapter(mAddNewMealArticleAdapter);

        return root;
    }

    @Override
    public void showMeal(List<Meal> meals) {
        mAddNewMealArticleAdapter.addMeals(meals);
    }

    @Override
    public void showAddNewMealArticleUi() {

    }

    @Override
    public void refreshUi() {
        mAddNewMealArticleAdapter.clearData();
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.add_new_meal_article_btn) {

                //TODO: Add typing input limitation

                String title = mTitleEditText.getText().toString();
                String content = mContentEditText.getText().toString();

                InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(view.getWindowToken(), 0);

                if (!title.equals("") && !content.equals("")) {
                    mPresenter.sendMeal(title, content);
                    mTitleEditText.getText().clear();
                    mContentEditText.getText().clear();
                    mTitleEditText.clearFocus();
                    mContentEditText.clearFocus();
                    ((FitnesschActivity)getActivity()).transToMain();
                } else {
                    Toast.makeText(getContext(), "請輸入標題和內容。", Toast.LENGTH_SHORT).show();
                    mTitleEditText.clearFocus();
                    mContentEditText.clearFocus();
                }
            } else if (view.getId() == R.id.fragment_addnewarticle) {
                InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            mTitleEditText.clearFocus();
            mContentEditText.clearFocus();
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
