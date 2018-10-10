package com.kevinj1008.fitnessch.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.detail.DetailContract;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.objects.Schedule;
import com.kevinj1008.fitnessch.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter {

    private Article mArticle;
    private List<Schedule> mSchedules = new ArrayList<>();
    private DetailContract.Presenter mPresenter;

    public DetailAdapter(Article article, DetailContract.Presenter presenter) {
        this.mArticle = article;
        mPresenter = presenter;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constants.VIEWTYPE_DETAIL_MAIN) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_article, parent, false);
            return new DetailMainItemViewHolder(view);
        } else if (viewType == Constants.VIEWTYPE_DETAIL_SCHEDULE_TITLE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_schedule_title, parent, false);
            return new DetailScheduleTitleViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_schedule_content, parent, false);
            return new DetailScheduleContentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        String type = mSchedules.get(position).getType();
        if (position == 0) {
            return Constants.VIEWTYPE_DETAIL_MAIN;
        } else if (type.equals("CONTENT")) {
            return Constants.VIEWTYPE_DETAIL_SCHEDULE_CONTENT;
        } else {
            return Constants.VIEWTYPE_DETAIL_SCHEDULE_TITLE;
        }
    }

    @Override
    public int getItemCount() {
        return mSchedules.size() + 1;
    }

    private class DetailMainItemViewHolder extends RecyclerView.ViewHolder {

        public DetailMainItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class DetailScheduleTitleViewHolder extends RecyclerView.ViewHolder {

        public DetailScheduleTitleViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class DetailScheduleContentViewHolder extends RecyclerView.ViewHolder {

        public DetailScheduleContentViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void updateArticle(Article article) {
        mArticle = article;
        notifyItemChanged(0);
    }

    public void updateSchedule(List<Schedule> schedules) {
        mSchedules = schedules;
        notifyDataSetChanged();
    }
}
