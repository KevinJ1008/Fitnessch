package com.kevinj1008.fitnessch.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.detail.DetailContract;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.objects.Schedule;
import com.kevinj1008.fitnessch.util.Constants;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

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
        if (position == 1) {
            ((DetailScheduleTitleViewHolder) holder).mScheduleSeparator.setVisibility(View.INVISIBLE);
        }
        if (holder instanceof DetailMainItemViewHolder) {
            Picasso.get()
                    .load(mArticle.getAuthorImage())
                    .transform(new CropCircleTransformation())
                    .fit()
                    .into(((DetailMainItemViewHolder) holder).mAuthorImage);

            ((DetailMainItemViewHolder) holder).mAuthor.setText(mArticle.getName());

            ((DetailMainItemViewHolder) holder).mArticleTitle.setText(mArticle.getTitle());

            String date = new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH：mm")
                    .format(new Date(mArticle.getCreatedTime() * 1000L));
            ((DetailMainItemViewHolder) holder).mArticleCreateTime.setText(date);

            ((DetailMainItemViewHolder) holder).mArticleContent.setText(mArticle.getContent());

        } else if (holder instanceof DetailScheduleTitleViewHolder) {
            ((DetailScheduleTitleViewHolder) holder).mScheduleTitle.setText(mSchedules.get(position - 1).getScheduleTitle());

        } else if (holder instanceof DetailScheduleContentViewHolder) {
            ((DetailScheduleContentViewHolder) holder).mScheduleWeight.setText(mSchedules.get(position - 1).getScheduleWeight());
            ((DetailScheduleContentViewHolder) holder).mScheduleReps.setText(mSchedules.get(position - 1).getScheduleReps());

        }

    }

    @Override
    public int getItemViewType(int position) {
        String type = "";

        if (position == 0) {
            return Constants.VIEWTYPE_DETAIL_MAIN;
        }

        try {
            type = mSchedules.get(position - 1).getType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (type) {
            case "CONTENT" :
                return Constants.VIEWTYPE_DETAIL_SCHEDULE_CONTENT;

            case "TITLE" :
                return Constants.VIEWTYPE_DETAIL_SCHEDULE_TITLE;

            default:
                Log.d(Constants.TAG, "Detail Page View Type ");
                return Constants.VIEWTYPE_DETAIL_MAIN;
        }

    }

    @Override
    public int getItemCount() {
        return (mSchedules.isEmpty() ? 1 : mSchedules.size() + 1);
    }

    private class DetailMainItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mAuthorImage;
        private TextView mAuthor;
        private TextView mArticleTitle;
        private TextView mArticleCreateTime;
        private TextView mArticleContent;

        public DetailMainItemViewHolder(View itemView) {
            super(itemView);

            mAuthorImage = itemView.findViewById(R.id.detail_article_author_image);
            mAuthor = itemView.findViewById(R.id.detail_article_author);
            mArticleTitle = itemView.findViewById(R.id.detail_article_title);
            mArticleCreateTime = itemView.findViewById(R.id.detail_article_create_time);
            mArticleContent = itemView.findViewById(R.id.detail_article_content);

        }
    }

    private class DetailScheduleTitleViewHolder extends RecyclerView.ViewHolder {

        private TextView mScheduleTitle;
        private View mScheduleSeparator;

        public DetailScheduleTitleViewHolder(View itemView) {
            super(itemView);

            mScheduleTitle = itemView.findViewById(R.id.item_detail_schedule_title);
            mScheduleSeparator = itemView.findViewById(R.id.item_detail_schedule_separator);

        }
    }

    private class DetailScheduleContentViewHolder extends RecyclerView.ViewHolder {

        private TextView mScheduleWeight;
        private TextView mScheduleReps;

        public DetailScheduleContentViewHolder(View itemView) {
            super(itemView);

            mScheduleWeight = itemView.findViewById(R.id.item_detail_weight_content);
            mScheduleReps = itemView.findViewById(R.id.item_detail_reps_content);

        }
    }

    public void updateArticle(Article article) {
        mArticle = article;
        notifyItemChanged(0);
    }

    public void updateSchedule(List<Schedule> schedules) {
        mSchedules = schedules;
//        notifyDataSetChanged();
        notifyItemInserted(1);
    }

    public void initData() {
        mSchedules.clear();
        notifyDataSetChanged();
    }
}
