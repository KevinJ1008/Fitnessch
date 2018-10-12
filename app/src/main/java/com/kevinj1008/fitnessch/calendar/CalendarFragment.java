package com.kevinj1008.fitnessch.calendar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.objects.Article;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.google.common.base.Preconditions.checkNotNull;

public class CalendarFragment extends Fragment implements CalendarContract.View {

    private CalendarContract.Presenter mPresenter;
    private CalendarDay mCalendarDay;

    public CalendarFragment() {
        // Requires empty public constructor
    }

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public void setPresenter(CalendarContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
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
        calendarView.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getStringArray(R.array.calendar_title_month)));
        CalendarDay calendar = CalendarDay.today();
        calendarView.setDateSelected(calendar, true);
        calendarView.setOnDateChangedListener(dateSelectedListener);
        return root;
    }

    private OnDateSelectedListener dateSelectedListener = new OnDateSelectedListener() {
        @Override
        public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
            mCalendarDay = calendarDay;

            int year = mCalendarDay.getYear();
            int month = mCalendarDay.getMonth();
            int day = mCalendarDay.getDay();
        }
    };

    @Override
    public void showArticles(Article bean) {

    }

    @Override
    public void showDetailUi(Article article) {

    }

    @Override
    public void refreshUi() {

    }


}
