package com.kevinj1008.fitnessch.adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.datearticle.DateArticleContract;
import com.kevinj1008.fitnessch.datearticle.DateArticlePresenter;
import com.kevinj1008.fitnessch.objects.Article;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateArticleAdapter extends RecyclerView.Adapter {

    private DateArticleContract.Presenter mPresenter;
    private List<Article> mArticles;
    private Map<String, List<Article>> mStringListMap;

    public DateArticleAdapter(DateArticleContract.Presenter presenter) {
        mPresenter = presenter;
        mArticles = new ArrayList<>();
        mStringListMap = new HashMap<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_schedule_article, parent, false);
        return new DateArticleItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            ((DateArticleItemViewHolder) holder).mTagContainer.setVisibility(View.VISIBLE);
        } else {
            ((DateArticleItemViewHolder) holder).mTagContainer.setVisibility(View.GONE);
        }
        if (mArticles.get(position).getTag().equals("課表")) {
            ((DateArticleItemViewHolder) holder).mTagImage.setImageResource(R.drawable.icon_dumbbell);
        } else {
            ((DateArticleItemViewHolder) holder).mTagImage.setImageResource(R.drawable.icon_meal);
        }
        ((DateArticleItemViewHolder) holder).mTitle.setText(mArticles.get(position).getTitle());
        String date = new SimpleDateFormat("HH：mm")
                .format(new Date(mArticles.get(position).getCreatedTime() * 1000L));
        ((DateArticleItemViewHolder) holder).mCreateTime.setText(date);
        ((DateArticleItemViewHolder) holder).mContent.setText(mArticles.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return (mArticles.isEmpty() ? 0 : mArticles.size());
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    private class DateArticleItemViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout mTagContainer;
        private TextView mTitle;
        private TextView mCreateTime;
        private TextView mContent;
        private ImageView mTagImage;

        public DateArticleItemViewHolder(View itemView) {
            super(itemView);

            mTagContainer = itemView.findViewById(R.id.schedule_tag_container);
            mTitle = itemView.findViewById(R.id.date_schedule_title);
            mCreateTime = itemView.findViewById(R.id.date_schedule_time);
            mContent = itemView.findViewById(R.id.date_schedule_content);
            mTagImage = itemView.findViewById(R.id.tag_image);

            ((ConstraintLayout) itemView.findViewById(R.id.date_schedule_article_container)).setOnClickListener(clickListener);

        }

        private View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.openDetail(mArticles.get(getAdapterPosition()));
            }
        };
    }

    public void updateArticle(Article article) {
        mArticles.add(0, article);
        Collections.sort(mArticles, new Comparator<Article>() {
            @Override
            public int compare(Article article, Article t1) {
                return article.getTag().compareToIgnoreCase(t1.getTag());
            }
        });
        notifyDataSetChanged();
    }

    public void clearData() {
        mArticles.clear();
        notifyDataSetChanged();
    }
}
