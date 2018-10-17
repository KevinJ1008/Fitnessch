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
import com.kevinj1008.fitnessch.objects.Meal;
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
    private List<Meal> mMeals = new ArrayList<>();
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

        } else if (viewType == Constants.VIEWTYPE_DETAIL_MEAL_TITLE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_meal_title, parent, false);
            return new DetailMealTitleViewHolder(view);

        } else if (viewType == Constants.VIEWTYPE_DETAIL_SCHEDULE_CONTENT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_schedule_content, parent, false);
            return new DetailScheduleContentViewHolder(view);

        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_meal_content, parent, false);
            return new DetailMealContentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == 1) {
            if (mSchedules.size() > 0) {
                ((DetailScheduleTitleViewHolder) holder).mScheduleSeparator.setVisibility(View.INVISIBLE);
            } else {
                ((DetailMealTitleViewHolder) holder).mMealeSeparator.setVisibility(View.INVISIBLE);
            }
        }
        if (holder instanceof DetailMainItemViewHolder) {
            Picasso.get()
                    .load(mArticle.getAuthorImage())
                    .transform(new CropCircleTransformation())
                    .fit()
                    .into(((DetailMainItemViewHolder) holder).mAuthorImage);

            ((DetailMainItemViewHolder) holder).mAuthor.setText(mArticle.getName());

            ((DetailMainItemViewHolder) holder).mArticleTitle.setText(mArticle.getTitle());

            String date = new SimpleDateFormat("yyyy 年 MM 月 dd 日")
                    .format(new Date(mArticle.getCreatedTime() * 1000L));
            ((DetailMainItemViewHolder) holder).mArticleCreateTime.setText(date);

            ((DetailMainItemViewHolder) holder).mArticleContent.setText(mArticle.getContent());

        } else if (holder instanceof DetailScheduleTitleViewHolder) {
            ((DetailScheduleTitleViewHolder) holder).mScheduleTitle.setText(mSchedules.get(position - 1).getScheduleTitle());

        } else if (holder instanceof DetailMealTitleViewHolder) {
            ((DetailMealTitleViewHolder) holder).mMealTitle.setText(mMeals.get(position - 1).getMealTitle());

        } else if (holder instanceof DetailScheduleContentViewHolder) {
            ((DetailScheduleContentViewHolder) holder).mScheduleWeight.setText(mSchedules.get(position - 1).getScheduleWeight());
            ((DetailScheduleContentViewHolder) holder).mScheduleReps.setText(mSchedules.get(position - 1).getScheduleReps());

        } else if (holder instanceof DetailMealContentViewHolder) {
            ((DetailMealContentViewHolder) holder).mMealIngredient.setText(mMeals.get(position - 1).getMealIngredient());
            ((DetailMealContentViewHolder) holder).mMealCal.setText(mMeals.get(position - 1).getMealCal());

        }

    }

    @Override
    public int getItemViewType(int position) {
        String type = "";

        if (position == 0) {
            return Constants.VIEWTYPE_DETAIL_MAIN;
        }
        if (mSchedules.size() > 0) {
            try {
                type = mSchedules.get(position - 1).getType();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (mMeals.size() > 0) {
            try {
                type = mMeals.get(position - 1).getMealType();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        switch (type) {
            case "CONTENT" :
                if (mSchedules.size() > 0) {
                    return Constants.VIEWTYPE_DETAIL_SCHEDULE_CONTENT;
                } else if (mMeals.size() > 0) {
                    return Constants.VIEWTYPE_DETAIL_MEAL_CONTENT;
                }

            case "TITLE" :
                if (mSchedules.size() > 0) {
                    return Constants.VIEWTYPE_DETAIL_SCHEDULE_TITLE;
                } else if (mMeals.size() > 0) {
                    return Constants.VIEWTYPE_DETAIL_MEAL_TITLE;
                }

            default:
                Log.d(Constants.TAG, "Detail Page View Type ");
                return Constants.VIEWTYPE_DETAIL_MAIN;
        }

    }

    @Override
    public int getItemCount() {
        if (mMeals.size() > 0) {
            return mMeals.size() + 1;
        } else if (mSchedules.size() > 0) {
            return mSchedules.size() + 1;
        } else {
            return 1;
        }
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

    private class DetailMealTitleViewHolder extends RecyclerView.ViewHolder {

        private TextView mMealTitle;
        private View mMealeSeparator;

        public DetailMealTitleViewHolder(View itemView) {
            super(itemView);

            mMealTitle = itemView.findViewById(R.id.item_detail_meal_title);
            mMealeSeparator = itemView.findViewById(R.id.item_detail_meal_separator);

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

    private class DetailMealContentViewHolder extends RecyclerView.ViewHolder {

        private TextView mMealIngredient;
        private TextView mMealCal;

        public DetailMealContentViewHolder(View itemView) {
            super(itemView);

            mMealIngredient = itemView.findViewById(R.id.item_detail_ingredient_content);
            mMealCal = itemView.findViewById(R.id.item_detail_cal_content);
        }
    }

    public void updateArticle(Article article) {
        mArticle = article;
        notifyDataSetChanged();
    }

    public void updateSchedule(List<Schedule> schedules) {
        mSchedules = schedules;
//        notifyDataSetChanged();
        notifyDataSetChanged();
    }

    public void updateMeal(List<Meal> meals) {
        mMeals = meals;
        notifyDataSetChanged();
    }

    public void initData() {
        mSchedules.clear();
        mMeals.clear();
        notifyDataSetChanged();
    }
}
