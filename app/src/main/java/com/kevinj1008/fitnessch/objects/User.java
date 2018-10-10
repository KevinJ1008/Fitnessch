package com.kevinj1008.fitnessch.objects;

public class User {

    private String mName;
    private String mHeight;
    private String mWeight;
    private String mInfo;
    private String mPhoto;
    private String mId;

    public User() {
        mName = "";
        mHeight = "";
        mWeight = "";
        mInfo = "";
        mPhoto = "";
        mId = "";
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getHeight() {
        return mHeight;
    }

    public void setHeight(String height) {
        mHeight = height;
    }

    public String getWeight() {
        return mWeight;
    }

    public void setWeight(String weight) {
        mWeight = weight;
    }

    public String getInfo() {
        return mInfo;
    }

    public void setInfo(String info) {
        mInfo = info;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }
}
