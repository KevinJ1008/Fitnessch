package com.kevinj1008.fitnessch.calendar;

import static com.google.common.base.Preconditions.checkNotNull;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kevinj1008.fitnessch.Fitnessch;
import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.activities.FitnesschActivity;
import com.kevinj1008.fitnessch.objects.Article;
import com.kevinj1008.fitnessch.util.DaySelectorDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarFragment extends Fragment implements CalendarContract.View {

    private CalendarContract.Presenter mPresenter;
    private CalendarDay mCalendarDay;
    private List<Article> mArticles;
    private MaterialCalendarView mMaterialCalendarView;
    private List<CalendarDay> mCalendarDays;

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

        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMaterialCalendarView = view.findViewById(R.id.calendar);
        mMaterialCalendarView.addDecorator(new DaySelectorDecorator(getActivity()));
        mMaterialCalendarView.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getStringArray(R.array.calendar_title_month)));

        mMaterialCalendarView.setOnDateChangedListener(dateSelectedListener);
        mMaterialCalendarView.setOnMonthChangedListener(monthChangeListener);
    }

    private OnDateSelectedListener dateSelectedListener = new OnDateSelectedListener() {
        @Override
        public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean isSelected) {
            if (mCalendarDays.contains(calendarDay)) {
                int year = calendarDay.getYear();
                int month = calendarDay.getMonth();
                int day = calendarDay.getDay();

                Article article = new Article();
                article.setCreateYear(year);
                article.setCreateMonth(month);
                article.setCreateDay(day);

                ((FitnesschActivity) getActivity()).transToDate(article);
            } else {
                materialCalendarView.setDateSelected(calendarDay, false);
                showFocusDate();
                Toast.makeText(Fitnessch.getAppContext(), "未建立任何課表與菜單。", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private OnMonthChangedListener monthChangeListener = new OnMonthChangedListener() {
        @Override
        public void onMonthChanged(MaterialCalendarView materialCalendarView, CalendarDay calendarDay) {
            mPresenter.refresh();
            int year = calendarDay.getYear();
            int month = calendarDay.getMonth();
            Article article = new Article();
            article.setCreateYear(year);
            article.setCreateMonth(month);
            mPresenter.monthChangeLoader(article);
        }
    };

    @Override
    public void showArticles(List<Article> articles) {
        mCalendarDays = new ArrayList<>();
        CalendarDay calendarDay = CalendarDay.today();
        mMaterialCalendarView.setDateSelected(calendarDay, true);
        mArticles = articles;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        for (int i = 0; i < mArticles.size(); i++) {
            int day = mArticles.get(i).getCreateDay();
            mCalendarDay = CalendarDay.from(year, month, day);
            mCalendarDays.add(mCalendarDay);
            mMaterialCalendarView.setDateSelected(mCalendarDay, true);
        }
    }

    @Override
    public void showDetailUi(Article article) {

    }

    @Override
    public void showFocusDate() {
        if (!mCalendarDays.isEmpty()) {
            CalendarDay today = CalendarDay.today();
            mMaterialCalendarView.setDateSelected(today, true);
            for (int i = 0; i < mCalendarDays.size(); i++) {
                int year = mCalendarDays.get(i).getYear();
                int month = mCalendarDays.get(i).getMonth();
                int day = mCalendarDays.get(i).getDay();
                CalendarDay calendarDay = CalendarDay.from(year, month, day);
                mMaterialCalendarView.setDateSelected(calendarDay, true);
            }
        } else {
            CalendarDay today = CalendarDay.today();
            mMaterialCalendarView.setCurrentDate(today, true);
            mPresenter.loadArticles();
        }
    }

    @Override
    public void monthChangeArticle(List<Article> articles) {
        mMaterialCalendarView.clearSelection();
        CalendarDay today = CalendarDay.today();
        mMaterialCalendarView.setDateSelected(today, true);
        for (int i = 0; i < articles.size(); i++) {
            int year = articles.get(i).getCreateYear();
            int month = articles.get(i).getCreateMonth();
            int day = articles.get(i).getCreateDay();
            CalendarDay calendarDay = CalendarDay.from(year, month, day);
            mCalendarDays.add(calendarDay);
            mMaterialCalendarView.setDateSelected(calendarDay, true);
        }
    }

    @Override
    public void refreshUi() {
        mArticles.clear();
        mCalendarDays.clear();
    }


}
