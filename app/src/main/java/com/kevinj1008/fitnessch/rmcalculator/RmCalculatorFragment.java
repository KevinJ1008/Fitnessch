package com.kevinj1008.fitnessch.rmcalculator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevinj1008.fitnessch.R;

public class RmCalculatorFragment extends Fragment implements View.OnClickListener {

    private EditText mSetRmWeight;
    private EditText mPerformedRep;
    private EditText mRm;
    private ImageView mRmWeightPlusBtn;
    private ImageView mRmWeightMinusBtn;
    private ImageView mPerformedPlusBtn;
    private ImageView mPerformedMinusBtn;
    private ImageView mRmPlusBtn;
    private ImageView mRmMinusBtn;
    private TextView mPerformWeight;
    private TextView mRmWeight;
    private TextView mRmRep;

    private int mSetWeight;

    public static RmCalculatorFragment newInstance() {
        return new RmCalculatorFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_rm_calculator, container, false);

        mSetRmWeight = root.findViewById(R.id.set_weight_edittext);
        mPerformedRep = root.findViewById(R.id.performed_rep_edittext);
        mRm = root.findViewById(R.id.rm_edittext);
        mSetRmWeight.setText("" + 100);
        mPerformedRep.setText("" + 1);
        mRm.setText("" + 1);
        mPerformedRep.clearFocus();
        mSetRmWeight.clearFocus();
        mRm.clearFocus();

        mRmWeightPlusBtn = root.findViewById(R.id.plus_weight_btn);
        mRmWeightMinusBtn = root.findViewById(R.id.minus_weight_btn);
        mPerformedPlusBtn = root.findViewById(R.id.plus_rep_btn);
        mPerformedMinusBtn = root.findViewById(R.id.minus_rep_btn);
        mRmPlusBtn = root.findViewById(R.id.plus_rm_btn);
        mRmMinusBtn = root.findViewById(R.id.minus_rm_btn);

        mPerformWeight = root.findViewById(R.id.performed_weight_text);
        mRmWeight = root.findViewById(R.id.rm_weight_text);
        mRmRep = root.findViewById(R.id.rm_calculate_text);
        mPerformWeight.setText(mSetRmWeight.getText());
        mRmWeight.setText(mSetRmWeight.getText());
        mRmRep.setText("" + 1);

        mSetRmWeight.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

        ConstraintLayout constraintLayout = root.findViewById(R.id.rm_calculator_page);
        constraintLayout.setOnClickListener(this);
        mRmWeightPlusBtn.setOnClickListener(this);
        mRmWeightMinusBtn.setOnClickListener(this);
        mPerformedPlusBtn.setOnClickListener(this);
        mPerformedMinusBtn.setOnClickListener(this);
        mRmPlusBtn.setOnClickListener(this);
        mRmMinusBtn.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.plus_weight_btn) {
            double rmWeightNum = Double.valueOf(mSetRmWeight.getText().toString());
            double rmWeight = 0;
            rmWeight = rmWeight + rmWeightNum + 5;
            displayRmWeight(rmWeight);
            mSetRmWeight.clearFocus();
        } else if (view.getId() == R.id.minus_weight_btn) {
            double rmWeightNum = Double.valueOf(mSetRmWeight.getText().toString());
            double rmWeight = 0;
            rmWeight = rmWeight + rmWeightNum - 5;
            displayRmWeight(rmWeight);
            mSetRmWeight.clearFocus();
        } else if (view.getId() == R.id.plus_rep_btn) {
            int performedRepNum = Integer.parseInt(mPerformedRep.getText().toString());
            int performRep = 0;
            performRep = performRep + performedRepNum + 1;
            displayPerformedRep(performRep);
            mPerformedRep.clearFocus();
        } else if (view.getId() == R.id.minus_rep_btn) {
            int performedRepNum = Integer.parseInt(mPerformedRep.getText().toString());
            int performRep = 0;
            performRep = performRep + performedRepNum - 1;
            displayPerformedRep(performRep);
            mPerformedRep.clearFocus();
        } else if (view.getId() == R.id.plus_rm_btn) {
            int rmNum = Integer.parseInt(mRm.getText().toString());
            int rm = 0;
            rm = rm + rmNum + 1;
            displayRm(rm);
            mRm.clearFocus();
        } else if (view.getId() == R.id.minus_rm_btn) {
            int rmNum = Integer.parseInt(mRm.getText().toString());
            int rm = 0;
            rm = rm + rmNum - 1;
            displayRm(rm);
            mRm.clearFocus();
        } else if (view.getId() == R.id.rm_calculator_page) {
            InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(view.getWindowToken(), 0);
            mSetRmWeight.clearFocus();
            mPerformedRep.clearFocus();
            mRm.clearFocus();
        }
    }

    private void displayRmWeight(double number) {
        mSetRmWeight.setText("" + number);
        mPerformWeight.setText(mSetRmWeight.getText());
        mRmWeight.setText(mSetRmWeight.getText());
    }

    private void displayPerformedRep(double number) {
        mPerformedRep.setText("" + number);
        double weight = Double.valueOf(mSetRmWeight.getText().toString());
        double performedWeight = weight * (36 / (37 - number));
        mPerformWeight.setText("" + performedWeight);
    }

    private void displayRm(int number) {
        mRm.setText("" + number);
        mRmRep.setText("" + number);
//        int weight = Integer.parseInt(mSetRMWeight.getText().toString());
//        int rmWeight =
    }


}
