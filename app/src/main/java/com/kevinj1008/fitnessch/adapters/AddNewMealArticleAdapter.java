package com.kevinj1008.fitnessch.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.addnewarticle.AddNewArticleContract;
import com.kevinj1008.fitnessch.addnewmealarticle.AddNewMealArticleContract;
import com.kevinj1008.fitnessch.objects.Meal;
import com.kevinj1008.fitnessch.objects.Schedule;
import com.kevinj1008.fitnessch.util.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddNewMealArticleAdapter extends RecyclerView.Adapter {

    private List<Meal> mMeals;
    private Map<String, List<Meal>> mStringListMap;
    private AddNewMealArticleContract.Presenter mPresenter;

    public AddNewMealArticleAdapter(AddNewMealArticleContract.Presenter presenter) {
//        this.mSchedules = schedule;
        mStringListMap = new HashMap<>();
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constants.VIEWTYPE_ADDNEW_MEAL_ARTICLE_TITLE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addnew_mealarticle_title, parent, false);
            return new MealArticleTitleItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addnew_mealarticle_content, parent, false);
            return new MealArticleContentItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MealArticleTitleItemViewHolder) {
            if (position == 0) {
                ((MealArticleTitleItemViewHolder) holder).mArticleSeparator.setVisibility(View.GONE);
            } else {
                ((MealArticleTitleItemViewHolder) holder).mArticleSeparator.setVisibility(View.VISIBLE);
            }
            ((MealArticleTitleItemViewHolder) holder).mMealTitle.setText(mMeals.get(position).getMealTitle());
        } else if (holder instanceof MealArticleContentItemViewHolder) {
            ((MealArticleContentItemViewHolder) holder).mIngredient.setText(mMeals.get(position).getMealIngredient());
            ((MealArticleContentItemViewHolder) holder).mCal.setText(mMeals.get(position).getMealCal());
        }
    }

    @Override
    public int getItemViewType(int position) {
        String type = mMeals.get(position).getMealType();
        if (type.equals("CONTENT")) {
            return Constants.VIEWTYPE_ADDNEW_MEAL_ARTICLE_CONTENT;
        } else {
            return Constants.VIEWTYPE_ADDNEW_MEAL_ARTICLE_TITLE;
        }
    }

    @Override
    public int getItemCount() {
        return (mMeals.isEmpty()) ? 0 : mMeals.size();
    }

    private class MealArticleTitleItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mMealTitle;
        private View mArticleSeparator;

        public MealArticleTitleItemViewHolder(View itemView) {
            super(itemView);

            mMealTitle = itemView.findViewById(R.id.addnew_article_meal_item_title);
            mArticleSeparator = itemView.findViewById(R.id.article_meal_separator);
        }
    }

    private class MealArticleContentItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mIngredient;
        private TextView mCal;

        public MealArticleContentItemViewHolder(View itemView) {
            super(itemView);

            mIngredient = itemView.findViewById(R.id.addnew_article_ingredient_content);
            mCal = itemView.findViewById(R.id.addnew_article_cal_content);
        }
    }

    public void addMeals(List<Meal> meals) {
        mMeals = meals;
    }

    public void clearData() {
        mMeals.clear();
        notifyDataSetChanged();
    }
}
