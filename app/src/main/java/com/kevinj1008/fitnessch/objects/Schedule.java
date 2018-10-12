package com.kevinj1008.fitnessch.objects;

public class Schedule {
    private String mScheduleTitle;
    private String mScheduleWeight;
    private String mScheduleReps;
    private String mType;

    public Schedule() {
        mType = "CONTENT";
        mScheduleTitle = "";
        mScheduleWeight = "";
        mScheduleReps = "";
    }

    public String getScheduleTitle() {
        return mScheduleTitle;
    }

    public void setScheduleTitle(String scheduleTitle) {
        mScheduleTitle = scheduleTitle;
    }

    public String getScheduleWeight() {
        return mScheduleWeight;
    }

    public void setScheduleWeight(String scheduleWeight) {
        mScheduleWeight = scheduleWeight;
    }

    public String getScheduleReps() {
        return mScheduleReps;
    }

    public void setScheduleReps(String scheduleReps) {
        mScheduleReps = scheduleReps;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }
}
