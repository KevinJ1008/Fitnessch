package com.kevinj1008.fitnessch.adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.datearticle.DateArticleContract;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DateArticleAdapter extends RecyclerView.Adapter {

    private DateArticleContract.Presenter mPresenter;
    private List<Article> mArticles;

    public DateArticleAdapter(DateArticleContract.Presenter presenter) {
        mPresenter = presenter;
        mArticles = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constants.VIEWTYPE_DATE_ARTICLE_DEFAULT) {
            View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_article_default, parent, false);
            return new DateArticleDefaultItemViewHolder(view);
        } else {
            View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_article, parent, false);
            return new DateArticleItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DateArticleDefaultItemViewHolder) {
            if (mArticles.get(position).getTag().equals("課表")) {
                ((DateArticleDefaultItemViewHolder) holder).mDefaultTagImage.setImageResource(R.drawable.icon_dumbbell);
            } else {
                ((DateArticleDefaultItemViewHolder) holder).mDefaultTagImage.setImageResource(R.drawable.icon_meal);
            }
            ((DateArticleDefaultItemViewHolder) holder).mDefaultTitle.setText(mArticles.get(position).getTitle());
            String date = new SimpleDateFormat("HH：mm")
                    .format(new Date(mArticles.get(position).getCreatedTime() * 1000L));
            ((DateArticleDefaultItemViewHolder) holder).mDefaultCreateTime.setText(date);
            ((DateArticleDefaultItemViewHolder) holder).mDefaultContent.setText(mArticles.get(position).getContent());
            ((DateArticleDefaultItemViewHolder) holder).mDefaultContent.post(new Runnable() {
                @Override
                public void run() {
                    int lineCount = ((DateArticleDefaultItemViewHolder) holder).mDefaultContent.getLineCount();
                    if (lineCount == 1) {
                        ((DateArticleDefaultItemViewHolder) holder).mDefaultContent.setGravity(Gravity.CENTER);
                    } else {
                        ((DateArticleDefaultItemViewHolder) holder).mDefaultContent.setGravity(Gravity.NO_GRAVITY);
                    }
                }
            });
        } else {
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
            ((DateArticleItemViewHolder) holder).mContent.post(new Runnable() {
                @Override
                public void run() {
                    int lineCount = ((DateArticleItemViewHolder) holder).mContent.getLineCount();
                    Log.d(Constants.TAG, "Get line Count " + lineCount);
                    if (lineCount == 1) {
                        ((DateArticleItemViewHolder) holder).mContent.setGravity(Gravity.CENTER);
                    } else {
                        ((DateArticleItemViewHolder) holder).mContent.setGravity(Gravity.NO_GRAVITY);
                    }
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return (mArticles.isEmpty() ? 0 : mArticles.size());
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? Constants.VIEWTYPE_DATE_ARTICLE_DEFAULT : Constants.VIEWTYPE_DATE_ARTICLE;
    }

    private class DateArticleDefaultItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mDefaultTitle;
        private TextView mDefaultCreateTime;
        private TextView mDefaultContent;
        private ImageView mDefaultTagImage;

        public DateArticleDefaultItemViewHolder(View itemView) {
            super(itemView);

            mDefaultTitle = itemView.findViewById(R.id.date_article_title_default);
            mDefaultCreateTime = itemView.findViewById(R.id.date_article_time_default);
            mDefaultContent = itemView.findViewById(R.id.date_article_content_default);
            mDefaultTagImage = itemView.findViewById(R.id.tag_image_default);

            ((ConstraintLayout) itemView.findViewById(R.id.date_article_default_container)).setOnClickListener(clickListener);
        }

        private View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.openDetail(mArticles.get(getAdapterPosition()));
            }
        };
    }

    private class DateArticleItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mCreateTime;
        private TextView mContent;
        private ImageView mTagImage;

        public DateArticleItemViewHolder(View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.date_article_title);
            mCreateTime = itemView.findViewById(R.id.date_article_time);
            mContent = itemView.findViewById(R.id.date_article_content);
            mTagImage = itemView.findViewById(R.id.tag_image);

            ((ConstraintLayout) itemView.findViewById(R.id.date_article_container)).setOnClickListener(clickListener);

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
