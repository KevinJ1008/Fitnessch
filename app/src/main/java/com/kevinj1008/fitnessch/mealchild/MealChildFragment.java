package com.kevinj1008.fitnessch.mealchild;

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
import com.kevinj1008.fitnessch.activities.FitnesschActivity;
import com.kevinj1008.fitnessch.adapters.MealChildAdapter;
import com.kevinj1008.fitnessch.api.beans.MealArticles;
import com.kevinj1008.fitnessch.objects.Article;

import static com.google.common.base.Preconditions.checkNotNull;

public class MealChildFragment extends Fragment implements MealChildContract.View {

    private MealChildContract.Presenter mPresenter;
    private MealChildAdapter mMealChildAdapter;

    public MealChildFragment() {
        // Requires empty public constructor
    }

    public static MealChildFragment newInstance() {
        return new MealChildFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMealChildAdapter = new MealChildAdapter(new MealArticles(), mPresenter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(MealChildContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.childfragment_meal, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_child_meal);
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
        recyclerView.setAdapter(mMealChildAdapter);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.start();
    }

    @Override
    public void showArticles(Article bean) {
        mMealChildAdapter.updateData(bean);
    }

    @Override
    public void showDetailUi(Article article) {
        ((FitnesschActivity) getActivity()).transToDetail(article);
    }


}
