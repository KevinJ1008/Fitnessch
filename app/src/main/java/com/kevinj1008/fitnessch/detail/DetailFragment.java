package com.kevinj1008.fitnessch.detail;

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

import com.kevinj1008.fitnessch.Fitnessch;
import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.adapters.DetailAdapter;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.objects.Meal;
import com.kevinj1008.fitnessch.objects.Schedule;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class DetailFragment extends Fragment implements DetailContract.View {

    private DetailContract.Presenter mPresenter;
    private DetailAdapter mDetailAdapter;

    public DetailFragment() {
        // Requires empty public constructor
    }

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDetailAdapter = new DetailAdapter(new Article(), mPresenter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(Fitnessch.getAppContext()));
        recyclerView.setAdapter(mDetailAdapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
    }

    @Override
    public void setToolbarVisibility(boolean visible) {

    }

    @Override
    public void showArticle(Article article) {
        mDetailAdapter.updateArticle(article);
    }

    @Override
    public void showSchedule(List<Schedule> schedules) {
        if (mDetailAdapter != null) mDetailAdapter.updateSchedule(schedules);
    }

    @Override
    public void showMeal(List<Meal> meals) {
        if (mDetailAdapter != null) mDetailAdapter.updateMeal(meals);
    }

    @Override
    public void refreshUi() {
        mDetailAdapter.initData();
    }


}
