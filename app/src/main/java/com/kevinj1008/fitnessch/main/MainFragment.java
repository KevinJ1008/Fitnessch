package com.kevinj1008.fitnessch.main;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kevinj1008.fitnessch.Fitnessch;
import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.activities.FitnesschActivity;
import com.kevinj1008.fitnessch.adapters.MainAdapter;
import com.kevinj1008.fitnessch.api.beans.GetArticles;
import com.kevinj1008.fitnessch.objects.Article;



public class MainFragment extends Fragment implements MainContract.View {

    private MainContract.Presenter mPresenter;
    private MainAdapter mMainAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public MainFragment() {
        // Requires empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainAdapter = new MainAdapter(new GetArticles(), mPresenter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_main);
        mSwipeRefreshLayout = root.findViewById(R.id.main_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(refreshListener);
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
        recyclerView.setAdapter(mMainAdapter);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
    }

    @Override
    public void showArticles(Article bean) {
        mMainAdapter.updateData(bean);
    }

    @Override
    public void showDetailUi(Article article) {
        ((FitnesschActivity) getActivity()).transToDetail(article);
    }

    @Override
    public void showUserProfile(Article article) {
        ((FitnesschActivity) getActivity()).transToUserProfile(article);
    }

    @Override
    public void refreshUi() {
        mMainAdapter.initData();
    }

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mSwipeRefreshLayout.setRefreshing(true);
            mMainAdapter.initData();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPresenter.loadArticles();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 1000);
        }
    };


}
