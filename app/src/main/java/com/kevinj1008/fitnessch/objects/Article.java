package com.kevinj1008.fitnessch.objects;

import java.util.ArrayList;

public class Article {

    private String mId;
    private Author mAuthor;
    private String mTitle;
    private String mContent;
    private int mCreatedTime;
    private String mTag;
    private String mName;
//    private int mInterests;
//    private boolean mInterestedIn;


    public Article(String id, String author, String title, String content, int createdTime, String tag) {
        mId = id;
        mName = author;
        mTitle = title;
        mContent = content;
        mCreatedTime = createdTime;
        mTag = tag;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Author getAuthor() {
        return mAuthor;
    }

    public void setAuthor(Author author) {
        mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public int getCreatedTime() {
        return mCreatedTime;
    }

    public void setCreatedTime(int createdTime) {
        mCreatedTime = createdTime;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
