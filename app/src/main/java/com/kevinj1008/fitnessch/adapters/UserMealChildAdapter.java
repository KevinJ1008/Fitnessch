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
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.usermealchild.UserMealChildContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserMealChildAdapter extends RecyclerView.Adapter {

    private UserMealChildContract.Presenter mPresenter;
    private ArrayList<Article> mArticles;
    private Context mContext;
    private int mNextPaging;

    public UserMealChildAdapter(MealArticles bean, UserMealChildContract.Presenter presenter) {
        mPresenter = presenter;
        this.mArticles = bean.getArticles();
        this.mNextPaging = bean.getPaging();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_child, parent, false);
        return new UserMealItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((UserMealItemViewHolder) holder).mUserMealChildTitle.setText(mArticles.get(position).getTitle());
        ((UserMealItemViewHolder) holder).mUserMealChildContent.setText(mArticles.get(position).getContent());
        String date = new SimpleDateFormat("MM 月 dd 日")
                .format(new Date(mArticles.get(position).getCreatedTime() * 1000L));
        ((UserMealItemViewHolder) holder).mUserMealChildCreateTime.setText(date);
    }

    @Override
    public int getItemCount() {
        return (mArticles.isEmpty()) ? 0 : mArticles.size();
    }

    private class UserMealItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mUserMealChildTitle;
        private TextView mUserMealChildCreateTime;
        private TextView mUserMealChildContent;

        public UserMealItemViewHolder(View itemView) {
            super(itemView);

            mUserMealChildTitle = itemView.findViewById(R.id.meal_child_title);
            mUserMealChildCreateTime = itemView.findViewById(R.id.meal_child_create_time);
            mUserMealChildContent = itemView.findViewById(R.id.meal_child_content);

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
