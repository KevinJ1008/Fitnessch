package com.kevinj1008.fitnessch.addnewarticle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kevinj1008.fitnessch.Fitnessch;
import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.adapters.AddNewArticleAdapter;
import com.kevinj1008.fitnessch.objects.Schedule;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddNewArticleFragment extends Fragment implements AddNewArticleContract.View {

    private AddNewArticleContract.Presenter mPresenter;
    private AddNewArticleAdapter mAddNewArticleAdapter;
    private List<Schedule> mSchedules;
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

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_add_new_article);
        Button sendBtn = root.findViewById(R.id.add_new_article_btn);
        mTitleEditText = root.findViewById(R.id.add_new_article_title_edittext);
        mContentEditText = root.findViewById(R.id.add_new_article_content_edittext);

        sendBtn.setOnClickListener(clickListener);

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
            mPresenter.sendSchedule();
            refreshUi();
        }
    };

}
