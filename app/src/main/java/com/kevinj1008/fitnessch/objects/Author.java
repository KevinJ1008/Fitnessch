package com.kevinj1008.fitnessch.objects;

public class Author {

    private String mId;
    private String mImage;
    private String mName;

    public Author(String id, String image, String name) {
        mId = id;
        mImage = image;
        mName = name;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
