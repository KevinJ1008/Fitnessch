package com.kevinj1008.fitnessch.addnewschedulechild;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kevinj1008.fitnessch.Fitnessch;
import com.kevinj1008.fitnessch.R;
import com.kevinj1008.fitnessch.activities.FitnesschActivity;
import com.kevinj1008.fitnessch.adapters.AddNewScheduleChildAdapter;
import com.kevinj1008.fitnessch.objects.Schedule;
import com.kevinj1008.fitnessch.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddNewScheduleChildFragment extends Fragment implements AddNewScheduleChildContract.View {

    private Chronometer mStartChronometer;
    private Chronometer mRestChronometer;
    private TextView mStartText;
    private TextView mRestText;
    private ConstraintLayout mStartBtn;
    private ConstraintLayout mRestBtn;
    private Button mAddNewBtn;
    private EditText mScheduleTitle;
    private EditText mScheduleWeight;
    private EditText mScheduleReps;
    private Button mScheduleCompleteBtn;
    private ImageView mClockBtn;
    private ImageView mAddBtn;
    private ImageView mNextBtn;
    private boolean isStartButtonClicked = false;
    private boolean isRestButtonClicked = false;
    private long mStartEscapeTime = 0;
    private long mRestEscapeTime = 0;
    private List<Schedule> mSchedules = new ArrayList<>();
    private int mPosition;

    private AddNewScheduleChildAdapter mAddNewScheduleChildAdapter;

    public static AddNewScheduleChildFragment newInstance() {
        return new AddNewScheduleChildFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSchedules = new ArrayList<>();
        mAddNewScheduleChildAdapter = new AddNewScheduleChildAdapter(mSchedules);
    }

    @Override
    public void onPause() {
        super.onPause();
//        mAddNewScheduleChildAdapter.clearData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.childfragment_addnewschedule, container, false);

        mStartChronometer = root.findViewById(R.id.addnew_timer);
        mStartText = root.findViewById(R.id.addnew_start_btn_text);
        mStartBtn = root.findViewById(R.id.addnew_start_btn);
        mRestChronometer = root.findViewById(R.id.addnew_rest_timer);
        mRestText = root.findViewById(R.id.addnew_rest_btn_text);
        mRestBtn = root.findViewById(R.id.addnew_rest_btn);
        mAddNewBtn = root.findViewById(R.id.addnew_schedule_btn);
        mScheduleTitle = root.findViewById(R.id.addnew_schedule_edittext);
        mScheduleWeight = root.findViewById(R.id.addnew_weight_edittext);
        mScheduleReps = root.findViewById(R.id.addnew_reps_edittext);
        mScheduleCompleteBtn = root.findViewById(R.id.addnew_schedule_complete_btn);
        mClockBtn = root.findViewById(R.id.addnew_clock);
        mAddBtn = root.findViewById(R.id.addnew_btn);
        mNextBtn = root.findViewById(R.id.addnew_schedule_next_btn);
        ConstraintLayout constraintLayout = root.findViewById(R.id.childfragment_addnewschedule);


        InputFilter typeFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int i, int i1, Spanned spanned, int i2, int i3) {
                Pattern pattern = Pattern.compile("[a-zA-Z|\u4e00-\u9fa5]+");
                Matcher matcher = pattern.matcher(source.toString());
                if (!matcher.matches()) return "";
                return null;
            }
        };

        mScheduleTitle.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10), typeFilter});
        mScheduleWeight.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
        mScheduleReps.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

        mScheduleTitle.setOnFocusChangeListener(focusChangeListener);
        mScheduleWeight.setOnFocusChangeListener(focusChangeListener);
        mScheduleReps.setOnFocusChangeListener(focusChangeListener);


//        mScheduleWeight.addTextChangedListener(textWatcher);
//        mScheduleReps.addTextChangedListener(textWatcher);

        constraintLayout.setOnClickListener(clickListener);
        mStartBtn.setOnClickListener(clickListener);
        mStartBtn.setOnLongClickListener(longClickListener);
        mRestBtn.setOnClickListener(clickListener);
        mRestBtn.setOnLongClickListener(longClickListener);
        mAddNewBtn.setOnClickListener(clickListener);
        mScheduleCompleteBtn.setOnClickListener(clickListener);
        mAddBtn.setOnClickListener(clickListener);
        mNextBtn.setOnClickListener(clickListener);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_addnew_schedule);
        recyclerView.setLayoutManager(new LinearLayoutManager(Fitnessch.getAppContext()));
        recyclerView.setAdapter(mAddNewScheduleChildAdapter);

        return root;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.addnew_start_btn) {
                if (!isStartButtonClicked) {
                    isStartButtonClicked = true;
                    mStartChronometer.setVisibility(View.VISIBLE);
                    mStartChronometer.setBase(SystemClock.elapsedRealtime() + mStartEscapeTime);
                    mStartChronometer.start();
                    mClockBtn.setVisibility(View.INVISIBLE);
//                    mStartText.setVisibility(View.INVISIBLE);
                } else {
                    isStartButtonClicked = false;
//                    mStartText.setVisibility(View.INVISIBLE);
                    mClockBtn.setVisibility(View.INVISIBLE);
                    mStartChronometer.setVisibility(View.VISIBLE);
                    mStartEscapeTime = mStartChronometer.getBase() - SystemClock.elapsedRealtime();
                    mStartChronometer.stop();
                }
            } else if (view.getId() == R.id.addnew_rest_btn) {
                if (!isRestButtonClicked) {
                    isRestButtonClicked = true;
                    mRestChronometer.setVisibility(View.VISIBLE);
                    mRestChronometer.setBase(SystemClock.elapsedRealtime() + mRestEscapeTime);
                    mRestChronometer.start();
                    mRestText.setVisibility(View.INVISIBLE);
                } else {
                    isRestButtonClicked = false;
                    mRestText.setVisibility(View.INVISIBLE);
                    mRestChronometer.setVisibility(View.VISIBLE);
                    mRestEscapeTime = mRestChronometer.getBase() - SystemClock.elapsedRealtime();
                    mRestChronometer.stop();
                }
            } else if (view.getId() == R.id.addnew_btn) {
                String scheduleTitle = mScheduleTitle.getText().toString();
                String scheduleWeight = mScheduleWeight.getText().toString() + " KG";
                String scheduleReps = "X " + mScheduleReps.getText().toString();

                InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(view.getWindowToken(), 0);

                if (!"".equals(scheduleTitle) && !scheduleWeight.equals("") && !scheduleWeight.startsWith("0")
                        && !scheduleReps.equals("") && !scheduleReps.startsWith("0")) {
                    Schedule schedule = new Schedule();
                    schedule.setScheduleTitle(scheduleTitle);
                    schedule.setScheduleWeight(scheduleWeight);
                    schedule.setScheduleReps(scheduleReps);
//                    mSchedules.add(schedule);
                    mAddNewScheduleChildAdapter.updateData(schedule);

                    mScheduleWeight.getText().clear();
                    mScheduleReps.getText().clear();
                } else {
                    Toast.makeText(getContext(), "請輸入項目、重量及次數。", Toast.LENGTH_SHORT).show();
                    mScheduleTitle.clearFocus();
                    mScheduleWeight.clearFocus();
                    mScheduleReps.clearFocus();
                }
                mScheduleTitle.clearFocus();
                mScheduleWeight.clearFocus();
                mScheduleReps.clearFocus();
            } else if (view.getId() == R.id.addnew_schedule_next_btn) {
                if (mAddNewScheduleChildAdapter.getScheduleList().size() > 0) {
                    mScheduleTitle.getText().clear();
                    mScheduleWeight.getText().clear();
                    mScheduleReps.getText().clear();

                    mScheduleTitle.clearFocus();
                    mScheduleWeight.clearFocus();
                    mScheduleReps.clearFocus();

                    ((FitnesschActivity) getActivity()).transToAddNewArticle(mAddNewScheduleChildAdapter.getScheduleList());
                } else if (mAddNewScheduleChildAdapter.getScheduleMap().size() > 0) {
                    mScheduleTitle.getText().clear();
                    mScheduleWeight.getText().clear();
                    mScheduleReps.getText().clear();

                    mScheduleTitle.clearFocus();
                    mScheduleWeight.clearFocus();
                    mScheduleReps.clearFocus();

                    ((FitnesschActivity) getActivity()).transToAddNewArticle(mAddNewScheduleChildAdapter.getNewScheduleList());
                } else {
                    Toast.makeText(getContext(), "請輸入課表。", Toast.LENGTH_SHORT).show();
                }
            } else if (view.getId() == R.id.childfragment_addnewschedule) {
                InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(view.getWindowToken(), 0);
                mScheduleTitle.clearFocus();
                mScheduleWeight.clearFocus();
                mScheduleReps.clearFocus();
            }

        }
    };

    private View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            if (view.getId() == R.id.addnew_start_btn) {
                isStartButtonClicked = false;
                mStartChronometer.setBase(SystemClock.elapsedRealtime());
                mStartChronometer.stop();
                mStartEscapeTime = 0;
                mStartChronometer.setVisibility(View.INVISIBLE);
                mClockBtn.setVisibility(View.VISIBLE);
//                mStartText.setVisibility(View.VISIBLE);
                return true;
            } else if (view.getId() == R.id.addnew_rest_btn) {
                isRestButtonClicked = false;
                mRestChronometer.setBase(SystemClock.elapsedRealtime());
                mRestChronometer.stop();
                mRestEscapeTime = 0;
                mRestChronometer.setVisibility(View.INVISIBLE);
                mRestText.setVisibility(View.VISIBLE);
                return true;
            }
            return false;
        }
    };

    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (!hasFocus) {
                ((FitnesschActivity) getActivity()).hideKeyboard(view);
            }
        }
    };

    @Override
    public void showScheduleItem(int position) {

    }

    @Override
    public void refreshUi() {
        mAddNewScheduleChildAdapter.clearData();
    }

    @Override
    public void setPresenter(AddNewScheduleChildContract.Presenter presenter) {

    }

//    private TextWatcher textWatcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {
//            String editText = editable.toString();
//            if (editText.startsWith("0")) {
//                mScheduleWeight.setText("");
//                mScheduleReps.setText("");
//            }
//        }
//    };

}
