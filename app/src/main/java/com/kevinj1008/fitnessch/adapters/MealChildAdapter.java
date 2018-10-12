package com.kevinj1008.fitnessch.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.api.beans.MealArticles;
import com.kevinj1008.fitnessch.mealchild.MealChildContract;
import com.kevinj1008.fitnessch.objects.Article;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MealChildAdapter extends RecyclerView.Adapter {

    private MealChildContract.Presenter mPresenter;
    private ArrayList<Article> mArticles;
    private Context mContext;
    private int mNextPaging;

    public MealChildAdapter(MealArticles bean, MealChildContract.Presenter presenter) {
        mPresenter = presenter;
        this.mArticles = bean.getArticles();
        this.mNextPaging = bean.getPaging();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_child, parent, false);
        return new MealItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MealItemViewHolder) holder).mMealChildTitle.setText(mArticles.get(position).getTitle());
        ((MealItemViewHolder) holder).mMealChildContent.setText(mArticles.get(position).getContent());
        String date = new SimpleDateFormat("MM 月 dd 日")
                .format(new Date(mArticles.get(position).getCreatedTime() * 1000L));
        ((MealItemViewHolder) holder).mMealChildCreateTime.setText(date);
    }

    @Override
    public int getItemCount() {
        return (mArticles.isEmpty()) ? 0 : mArticles.size();
    }

    private class MealItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mMealChildTitle;
        private TextView mMealChildCreateTime;
        private TextView mMealChildContent;

        public MealItemViewHolder(View itemView) {
            super(itemView);

            mMealChildTitle = itemView.findViewById(R.id.meal_child_title);
            mMealChildCreateTime = itemView.findViewById(R.id.meal_child_create_time);
            mMealChildContent = itemView.findViewById(R.id.meal_child_content);

            ((ConstraintLayout) itemView.findViewById(R.id.meal_child_article_container)).setOnClickListener(clickListener);
        }
        private View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.openDetail(mArticles.get(getAdapterPosition()));
            }
        };
    }

    public void updateData(Article bean) {
        mArticles.add(0, bean);
        notifyDataSetChanged();
    }

    public void initData() {
        mArticles.clear();
        notifyDataSetChanged();
    }
}
