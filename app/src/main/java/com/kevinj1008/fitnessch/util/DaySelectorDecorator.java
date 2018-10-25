package com.kevinj1008.fitnessch.util;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.kevinj1008.fitnessch.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class DaySelectorDecorator implements DayViewDecorator {

    private Drawable mDrawable;

    public DaySelectorDecorator(Activity context) {
        mDrawable = ContextCompat.getDrawable(context, R.drawable.calendar_selector);
    }

    @Override
    public boolean shouldDecorate(CalendarDay calendarDay) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(mDrawable);
    }
}
