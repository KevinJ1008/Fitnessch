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
    private String mAuthorId;
    private String mAuthorImage;
//    private int mInterests;
//    private boolean mInterestedIn;


    public Article() {
        mId = "";
        mName = "";
        mTitle = "";
        mContent = "";
        mCreatedTime = -1;
        mTag = "";
        mAuthorId = "";
        mAuthorImage = "";
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

    public String getAuthorId() {
        return mAuthorId;
    }

    public void setAuthorId(String authorId) {
        mAuthorId = authorId;
    }

    public String getAuthorImage() {
        return mAuthorImage;
    }

    public void setAuthorImage(String authorImage) {
        mAuthorImage = authorImage;
    }
}
