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
import com.kevinj1008.fitnessch.schedulechild.ScheduleChildContract;
import com.kevinj1008.fitnessch.api.beans.ScheduleArticles;
import com.kevinj1008.fitnessch.objects.Article;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ScheduleChildAdapter extends RecyclerView.Adapter {

    private ScheduleChildContract.Presenter mPresenter;
    private ArrayList<Article> mArticles;
    private Context mContext;
    private int mNextPaging;

    public ScheduleChildAdapter(ScheduleArticles bean, ScheduleChildContract.Presenter presenter) {
        mPresenter = presenter;
        this.mArticles = bean.getArticles();
        this.mNextPaging = bean.getPaging();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_child, parent, false);
        return new ScheduleItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ScheduleItemViewHolder) holder).mScheduleChildTitle.setText(mArticles.get(position).getTitle());
        ((ScheduleItemViewHolder) holder).mScheduleChildContent.setText(mArticles.get(position).getContent());
        String date = new SimpleDateFormat("MM 月 dd 日")
                .format(new Date(mArticles.get(position).getCreatedTime() * 1000L));
        ((ScheduleItemViewHolder) holder).mScheduleChildCreateTime.setText(date);
    }

    @Override
    public int getItemCount() {
        return (mArticles.isEmpty()) ? 0 : mArticles.size();
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

//        for (Article article : bean.getArticles()) {

            mArticles.add(0, bean);
//        }
        notifyDataSetChanged();
    }

    public void initData() {
        mArticles.clear();
        notifyDataSetChanged();
    }
}
