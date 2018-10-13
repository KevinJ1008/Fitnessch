package com.kevinj1008.fitnessch.objects;

public class Meal {
    private String mMealTitle;
    private String mMealIngredient;
    private String mMealCal;
    private String mMealType;

    public Meal() {
        mMealType = "CONTENT";
        mMealTitle = "";
        mMealIngredient = "";
        mMealCal = "";
    }

    public String getMealTitle() {
        return mMealTitle;
    }

    public void setMealTitle(String mealTitle) {
        mMealTitle = mealTitle;
    }

    public String getMealIngredient() {
        return mMealIngredient;
    }

    public void setMealIngredient(String mealIngredient) {
        mMealIngredient = mealIngredient;
    }

    public String getMealCal() {
        return mMealCal;
    }

    public void setMealCal(String mealCal) {
        mMealCal = mealCal;
    }

    public String getMealType() {
        return mMealType;
    }

    public void setMealType(String mealType) {
        mMealType = mealType;
    }
}
