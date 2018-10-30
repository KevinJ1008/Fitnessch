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
import com.kevinj1008.fitnessch.api.beans.ScheduleArticles;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.userschedulechild.UserScheduleChildContract;
import com.kevinj1008.fitnessch.util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserScheduleChildAdapter extends RecyclerView.Adapter {

    private UserScheduleChildContract.Presenter mPresenter;
    private ArrayList<Article> mArticles;
    private Context mContext;
    private int mNextPaging;

    public UserScheduleChildAdapter(ScheduleArticles bean, UserScheduleChildContract.Presenter presenter) {
        mPresenter = presenter;
        this.mArticles = bean.getArticles();
        this.mNextPaging = bean.getPaging();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if (viewType == Constants.VIEWTYPE_USER_PROFILE_SCHEDULE_DEFAULT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_schedule_child_default, parent, false);
            return new UserScheduleDefaultItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_schedule_child, parent, false);
            return new UserScheduleItemViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserScheduleDefaultItemViewHolder) {
            ((UserScheduleDefaultItemViewHolder) holder).mUserDefaultScheduleChildTitle.setText(mArticles.get(position).getTitle());
            ((UserScheduleDefaultItemViewHolder) holder).mUserDefaultScheduleChildContent.setText(mArticles.get(position).getContent());
            String date = new SimpleDateFormat(mContext.getResources().getString(R.string.profile_article_date))
                    .format(new Date(mArticles.get(position).getCreatedTime() * 1000L));
            ((UserScheduleDefaultItemViewHolder) holder).mUserDefaultScheduleChildCreateTime.setText(date);
        } else {
            ((UserScheduleItemViewHolder) holder).mUserScheduleChildTitle.setText(mArticles.get(position).getTitle());
            ((UserScheduleItemViewHolder) holder).mUserScheduleChildContent.setText(mArticles.get(position).getContent());
            String date = new SimpleDateFormat(mContext.getResources().getString(R.string.profile_article_date))
                    .format(new Date(mArticles.get(position).getCreatedTime() * 1000L));
            ((UserScheduleItemViewHolder) holder).mUserScheduleChildCreateTime.setText(date);
        }

    }

    @Override
    public int getItemCount() {
        return (mArticles.isEmpty()) ? 0 : mArticles.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? Constants.VIEWTYPE_USER_PROFILE_SCHEDULE_DEFAULT : Constants.VIEWTYPE_USER_PROFILE_SCHEDULE_CHILD;
    }

    private class UserScheduleDefaultItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mUserDefaultScheduleChildTitle;
        private TextView mUserDefaultScheduleChildCreateTime;
        private TextView mUserDefaultScheduleChildContent;

        public UserScheduleDefaultItemViewHolder(View itemView) {
            super(itemView);

            mUserDefaultScheduleChildTitle = itemView.findViewById(R.id.user_schedule_child_default_title);
            mUserDefaultScheduleChildCreateTime = itemView.findViewById(R.id.user_schedule_child_default_create_time);
            mUserDefaultScheduleChildContent = itemView.findViewById(R.id.user_schedule_child_default_content);

            ((ConstraintLayout) itemView.findViewById(R.id.user_schedule_child_default_article_container)).setOnClickListener(clickListener);
        }
        private View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.openDetail(mArticles.get(getAdapterPosition()));
            }
        };

    }

    private class UserScheduleItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mUserScheduleChildTitle;
        private TextView mUserScheduleChildCreateTime;
        private TextView mUserScheduleChildContent;

        public UserScheduleItemViewHolder(View itemView) {
            super(itemView);
            mUserScheduleChildTitle = itemView.findViewById(R.id.user_schedule_child_title);
            mUserScheduleChildCreateTime = itemView.findViewById(R.id.user_schedule_child_create_time);
            mUserScheduleChildContent = itemView.findViewById(R.id.user_schedule_child_content);

            ((ConstraintLayout) itemView.findViewById(R.id.user_schedule_child_article_container)).setOnClickListener(clickListener);

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
