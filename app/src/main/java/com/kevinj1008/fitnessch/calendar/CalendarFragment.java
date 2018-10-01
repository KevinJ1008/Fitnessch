package com.kevinj1008.fitnessch.calendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kevinj1008.fitnessch.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

public class CalendarFragment extends Fragment {

//    private CalendarContract.Presenter mPresenter;

    public CalendarFragment() {
        // Requires empty public constructor
    }

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        MaterialCalendarView calendarView = root.findViewById(R.id.calendar);
        CalendarDay calendar = CalendarDay.today();
        calendarView.setDateSelected(calendar, true);
        return root;
    }
}
