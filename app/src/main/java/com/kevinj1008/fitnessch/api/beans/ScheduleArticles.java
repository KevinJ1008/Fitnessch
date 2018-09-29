package com.kevinj1008.fitnessch.api.beans;

import com.kevinj1008.fitnessch.objects.Article;

import java.util.ArrayList;

public class ScheduleArticles {

    private ArrayList<Article> mArticles;
    private int mPaging;

    public ScheduleArticles() {
        mArticles = new ArrayList<>();
        mPaging = -1;
    }

    public ArrayList<Article> getArticles() {
        return mArticles;
    }

    public void setArticles(ArrayList<Article> articles) {
        mArticles = articles;
    }

    public int getPaging() {
        return mPaging;
    }

    public void setPaging(int paging) {
        mPaging = paging;
    }
}
