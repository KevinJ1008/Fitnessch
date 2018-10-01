package com.kevinj1008.fitnessch.calendar;

import android.support.v4.app.FragmentManager;

import com.kevinj1008.fitnessch.objects.Article;

import static com.google.common.base.Preconditions.checkNotNull;

public class CalendarPresenter implements CalendarContract.Presenter {

    private final CalendarContract.View mCalendarView;

    public CalendarPresenter(CalendarContract.View calendarView) {
        mCalendarView = checkNotNull(calendarView, "calendarView cannot be null!");
        mCalendarView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadArticles() {

    }

    @Override
    public void showArticles(Article bean) {

    }

    @Override
    public void openDetail(Article article) {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void start() {

    }
}
