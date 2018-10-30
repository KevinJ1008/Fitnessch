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
import com.kevinj1008.fitnessch.schedulechild.ScheduleChildContract;
import com.kevinj1008.fitnessch.util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ScheduleChildAdapter extends RecyclerView.Adapter {

    private ScheduleChildContract.Presenter mPresenter;
    private ArrayList<Article> mArticles;
    private Context mContext;

    public ScheduleChildAdapter(ScheduleArticles bean, ScheduleChildContract.Presenter presenter) {
        mPresenter = presenter;
        this.mArticles = bean.getArticles();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        if (viewType == Constants.VIEWTYPE_PROFILE_SCHEDULE_DEFAULT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_child_default, parent, false);
            return new ScheduleDefaultItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_child, parent, false);
            return new ScheduleItemViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ScheduleDefaultItemViewHolder) {
            ((ScheduleDefaultItemViewHolder) holder).mDefaultScheduleChildTitle.setText(mArticles.get(position).getTitle());
            ((ScheduleDefaultItemViewHolder) holder).mDefaultScheduleChildContent.setText(mArticles.get(position).getContent());
            String date = new SimpleDateFormat(mContext.getResources().getString(R.string.profile_article_date))
                    .format(new Date(mArticles.get(position).getCreatedTime() * 1000L));
            ((ScheduleDefaultItemViewHolder) holder).mDefaultScheduleChildCreateTime.setText(date);
        } else {
            ((ScheduleItemViewHolder) holder).mScheduleChildTitle.setText(mArticles.get(position).getTitle());
            ((ScheduleItemViewHolder) holder).mScheduleChildContent.setText(mArticles.get(position).getContent());
            String date = new SimpleDateFormat(mContext.getResources().getString(R.string.profile_article_date))
                    .format(new Date(mArticles.get(position).getCreatedTime() * 1000L));
            ((ScheduleItemViewHolder) holder).mScheduleChildCreateTime.setText(date);
        }

    }

    @Override
    public int getItemCount() {
        return (mArticles.isEmpty()) ? 0 : mArticles.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? Constants.VIEWTYPE_PROFILE_SCHEDULE_DEFAULT : Constants.VIEWTYPE_PROFILE_SCHEDULE_CHILD;
    }

    private class ScheduleDefaultItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mDefaultScheduleChildTitle;
        private TextView mDefaultScheduleChildCreateTime;
        private TextView mDefaultScheduleChildContent;

        public ScheduleDefaultItemViewHolder(View itemView) {
            super(itemView);

            mDefaultScheduleChildTitle = itemView.findViewById(R.id.schedule_child_default_title);
            mDefaultScheduleChildCreateTime = itemView.findViewById(R.id.schedule_child_default_create_time);
            mDefaultScheduleChildContent = itemView.findViewById(R.id.schedule_child_default_content);

            ((ConstraintLayout) itemView.findViewById(R.id.schedule_child_article_default_container)).setOnClickListener(clickListener);
        }

        private View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.openDetail(mArticles.get(getAdapterPosition()));
            }
        };
    }

    private class ScheduleItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mScheduleChildTitle;
        private TextView mScheduleChildCreateTime;
        private TextView mScheduleChildContent;

        public ScheduleItemViewHolder(View itemView) {
            super(itemView);
            mScheduleChildTitle = itemView.findViewById(R.id.schedule_child_title);
            mScheduleChildCreateTime = itemView.findViewById(R.id.schedule_child_create_time);
            mScheduleChildContent = itemView.findViewById(R.id.schedule_child_content);

            ((ConstraintLayout) itemView.findViewById(R.id.schedule_child_article_container)).setOnClickListener(clickListener);

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
