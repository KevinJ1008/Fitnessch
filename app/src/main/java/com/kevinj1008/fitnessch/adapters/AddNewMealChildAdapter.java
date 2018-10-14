package com.kevinj1008.fitnessch.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.objects.Meal;
import com.kevinj1008.fitnessch.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddNewMealChildAdapter extends RecyclerView.Adapter {

    private List<Meal> mMeals;
    private Map<String, List<Meal>> mStringListMap;

    public AddNewMealChildAdapter(List<Meal> meals) {
        this.mMeals = meals;
        mStringListMap = new HashMap<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constants.VIEWTYPE_ADDNEW_MEAL_TITLE) {
            View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.addnewmeal_title, parent, false);
            return new MealTitleItemViewHolder(view);
        } else {
            View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.addnewmeal_content, parent, false);
            return new MealContentItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            ((MealTitleItemViewHolder) holder).mSeparator.setVisibility(View.INVISIBLE);
        }
        if (holder instanceof MealTitleItemViewHolder) {
            ((MealTitleItemViewHolder) holder).mMealTitle.setText(mMeals.get(position).getMealTitle());
        } else if (holder instanceof MealContentItemViewHolder){
            ((MealContentItemViewHolder) holder).mIngredient.setText(mMeals.get(position).getMealIngredient());
            ((MealContentItemViewHolder) holder).mCal.setText(mMeals.get(position).getMealCal());
        }
    }

    @Override
    public int getItemCount() {
        return (mMeals.isEmpty()) ? 0 : mMeals.size();
    }

    @Override
    public int getItemViewType(int position) {
        String type = mMeals.get(position).getMealType();
        if (type.equals("CONTENT")) {
            return Constants.VIEWTYPE_ADDNEW_MEAL_CONTENT;
        } else {
            return Constants.VIEWTYPE_ADDNEW_MEAL_TITLE;
        }
    }

    private class MealTitleItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mMealTitle;
        private View mSeparator;

        public MealTitleItemViewHolder(View itemView) {
            super(itemView);

            mMealTitle = itemView.findViewById(R.id.addnew_meal_item_title);
            mSeparator = itemView.findViewById(R.id.meal_separator);
        }
    }

    private class MealContentItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mIngredient;
        private TextView mCal;

        public MealContentItemViewHolder(View itemView) {
            super(itemView);

            mIngredient = itemView.findViewById(R.id.addnew_ingredient_content);
            mCal = itemView.findViewById(R.id.addnew_cal_content);
        }
    }

    public void updateData(Meal meal) {
        mMeals.clear();

        String title = meal.getMealTitle();
        if (mStringListMap.containsKey(title)) {
            List<Meal> content = mStringListMap.get(title);
            content.add(meal);
            mStringListMap.put(title, content);
        } else {
            List<Meal> titleList = new ArrayList<>();
            Meal titleItem = new Meal();
            titleItem.setMealTitle(meal.getMealTitle());
            titleItem.setMealType("TITLE");

            titleList.add(titleItem);
            titleList.add(meal);

            mStringListMap.put(title, titleList);
        }
        Iterator iterator = mStringListMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            for (int i = 0 ; i < mStringListMap.get(key).size() ; ++i) {
                mMeals.add(mStringListMap.get(key).get(i));
            }
        }
        Log.d(Constants.TAG, "Check Schedule size " + mMeals.size());
        notifyDataSetChanged();
    }

    public List<Meal> getMeals() {
        return mMeals;
    }

    public Map<String, List<Meal>> getMealMap() {
        return mStringListMap;
    }

    public List<Meal> getNewMealList() {
        Iterator iterator = mStringListMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String)iterator.next();
            for (int i = 0 ; i < mStringListMap.get(key).size() ; ++i) {
                mMeals.add(mStringListMap.get(key).get(i));
            }
        }
        return mMeals;
    }

    public void clearData() {
        mMeals.clear();
        mStringListMap.clear();
    }
}
