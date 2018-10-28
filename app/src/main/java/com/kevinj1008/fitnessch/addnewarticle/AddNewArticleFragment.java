package com.kevinj1008.fitnessch.addnewarticle;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
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
import com.kevinj1008.fitnessch.objects.Schedule;

import java.util.ArrayList;
import java.util.List;


public class AddNewArticleFragment extends Fragment implements AddNewArticleContract.View {

    private AddNewArticleContract.Presenter mPresenter;
    private AddNewArticleAdapter mAddNewArticleAdapter;
    private EditText mTitleEditText;
    private EditText mContentEditText;

    public AddNewArticleFragment() {
        // Requires empty public constructor
    }

    public static AddNewArticleFragment newInstance() {
        return new AddNewArticleFragment();
    }

    @Override
    public void setPresenter(AddNewArticleContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAddNewArticleAdapter = new AddNewArticleAdapter(mPresenter);
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addnewarticle, container, false);

        mTitleEditText = root.findViewById(R.id.add_new_article_title_edittext);
        mContentEditText = root.findViewById(R.id.add_new_article_content_edittext);
        ConstraintLayout constraintLayout = root.findViewById(R.id.fragment_addnewarticle);
        constraintLayout.setOnClickListener(clickListener);

        mTitleEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        mTitleEditText.addTextChangedListener(textWatcher);

        ConstraintLayout sendArticleBtn = root.findViewById(R.id.add_new_article_btn_background);
        sendArticleBtn.setOnClickListener(clickListener);

        mTitleEditText.setOnFocusChangeListener(focusChangeListener);
        mContentEditText.setOnFocusChangeListener(focusChangeListener);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_add_new_article);
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
        recyclerView.setAdapter(mAddNewArticleAdapter);

        return root;
    }

    @Override
    public void showSchedule(List<Schedule> schedules) {
        mAddNewArticleAdapter.addSchedules(schedules);
    }

    @Override
    public void showAddNewArticleUi() {

    }

    @Override
    public void refreshUi() {
        mAddNewArticleAdapter.clearData();
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.add_new_article_btn_background) {

                //TODO: Add typing input limitation

                String title = mTitleEditText.getText().toString();
                String content = mContentEditText.getText().toString();

                InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(view.getWindowToken(), 0);

                if (!"".equals(title) && !title.startsWith(" ") && !"".equals(content) && !content.startsWith(" ")) {
                    mPresenter.sendSchedule(title, content);
                    mTitleEditText.getText().clear();
                    mContentEditText.getText().clear();
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

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String editText = mTitleEditText.getText().toString();
            if (editText.length() >= 15) {
                Toast.makeText(Fitnessch.getAppContext(), "標題不可多於 15 個字元", Toast.LENGTH_SHORT).show();
            }
        }
    };

}
