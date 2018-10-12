package com.kevinj1008.fitnessch.datearticle;

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
import android.widget.TextView;

import com.kevinj1008.fitnessch.Fitnessch;
import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.activities.FitnesschActivity;
import com.kevinj1008.fitnessch.adapters.DateArticleAdapter;
import com.kevinj1008.fitnessch.objects.Article;

import static com.google.common.base.Preconditions.checkNotNull;

public class DateArticleFragment extends Fragment implements DateArticleContract.View {

    private DateArticleContract.Presenter mPresenter;
    private DateArticleAdapter mDateArticleAdapter;
    private Article mArticle;
    private TextView mDateText;

    public DateArticleFragment() {
        // Requires empty public constructor
    }

    public static DateArticleFragment newInstance() {
        return new DateArticleFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDateArticleAdapter = new DateArticleAdapter(mPresenter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void setPresenter(DateArticleContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_date_article, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_date_article);

        recyclerView.setLayoutManager(new LinearLayoutManager(Fitnessch.getAppContext()));
        recyclerView.setAdapter(mDateArticleAdapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.start();

        mDateText = view.findViewById(R.id.date_article_date);
        int year = mArticle.getCreateYear();
        int month = mArticle.getCreateMonth();
        int day = mArticle.getCreateDay();
        String date = year + " 年 " + month + " 月 " + day + " 日";
        mDateText.setText(date);
    }

    @Override
    public void setToolbarVisibility(boolean visible) {

    }

    @Override
    public void showArticle(Article article) {
        mDateArticleAdapter.updateArticle(article);
    }

    @Override
    public void refreshUi() {
        mDateArticleAdapter.clearData();
    }

    @Override
    public void showDateUi(Article article) {
        mArticle = article;
    }

    @Override
    public void showDetailUi(Article article) {
        ((FitnesschActivity) getActivity()).transToDetail(article);
    }

    @Override
    public void renewDateUi(Article article) {
        mArticle = article;
        int year = mArticle.getCreateYear();
        int month = mArticle.getCreateMonth();
        int day = mArticle.getCreateDay();
        String date = year + " 年 " + month + " 月 " + day + " 日";
        mDateText.setText(date);
        mPresenter.loadArticle(article);
    }


}
