package com.kevinj1008.fitnessch.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.addnewarticle.AddNewArticleContract;
import com.kevinj1008.fitnessch.objects.Schedule;
import com.kevinj1008.fitnessch.util.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddNewArticleAdapter extends RecyclerView.Adapter {

    private List<Schedule> mSchedules;
    private Map<String, List<Schedule>> mStringListMap;
    private AddNewArticleContract.Presenter mPresenter;

    public AddNewArticleAdapter(AddNewArticleContract.Presenter presenter) {
        mStringListMap = new HashMap<>();
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constants.VIEWTYPE_ADDNEW_SCHEDULE_ARTICLE_TITLE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addnewarticle_title, parent, false);
            return new ScheduleArticleTitleItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addnewarticle_content, parent, false);
            return new ScheduleArticleContentItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ScheduleArticleTitleItemViewHolder) {
            if (position == 0) {
                ((ScheduleArticleTitleItemViewHolder) holder).mArticleSeparator.setVisibility(View.GONE);
            } else {
                ((ScheduleArticleTitleItemViewHolder) holder).mArticleSeparator.setVisibility(View.VISIBLE);
            }
            ((ScheduleArticleTitleItemViewHolder) holder).mScheduleTitle.setText(mSchedules.get(position).getScheduleTitle());
        } else if (holder instanceof ScheduleArticleContentItemViewHolder) {
            ((ScheduleArticleContentItemViewHolder) holder).mScheduleWeight.setText(mSchedules.get(position).getScheduleWeight());
            ((ScheduleArticleContentItemViewHolder) holder).mScheduleReps.setText(mSchedules.get(position).getScheduleReps());
        }
    }

    @Override
    public int getItemViewType(int position) {
        String type = mSchedules.get(position).getType();
        if (type.equals("CONTENT")) {
            return Constants.VIEWTYPE_ADDNEW_SCHEDULE_ARTICLE_CONTENT;
        } else {
            return Constants.VIEWTYPE_ADDNEW_SCHEDULE_ARTICLE_TITLE;
        }
    }

    @Override
    public int getItemCount() {
        return (mSchedules.isEmpty()) ? 0 : mSchedules.size();
    }

    private class ScheduleArticleTitleItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mScheduleTitle;
        private View mArticleSeparator;

        public ScheduleArticleTitleItemViewHolder(View itemView) {
            super(itemView);

            mScheduleTitle = itemView.findViewById(R.id.addnew_article_schedule_item_title);
            mArticleSeparator = itemView.findViewById(R.id.article_schedule_separator);
        }
    }

    private class ScheduleArticleContentItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mScheduleWeight;
        private TextView mScheduleReps;

        public ScheduleArticleContentItemViewHolder(View itemView) {
            super(itemView);

            mScheduleWeight = itemView.findViewById(R.id.addnew_article_weight_content);
            mScheduleReps = itemView.findViewById(R.id.addnew_article_reps_content);
        }
    }

    public void addSchedules(List<Schedule> schedules) {
        mSchedules = schedules;
    }

    public void clearData() {
        mSchedules.clear();
        notifyDataSetChanged();
    }
}
