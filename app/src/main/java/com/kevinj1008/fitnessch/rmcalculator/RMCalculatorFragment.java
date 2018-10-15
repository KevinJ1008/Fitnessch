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

public class RMCalculatorFragment extends Fragment implements View.OnClickListener {

    private EditText mSetRMWeight;
    private EditText mPerformedRep;
    private EditText mRM;
    private ImageView mRMWeightPlusBtn;
    private ImageView mRMWeightMinusBtn;
    private ImageView mPerformedPlusBtn;
    private ImageView mPerformedMinusBtn;
    private ImageView mRMPlusBtn;
    private ImageView mRMMinusBtn;
    private TextView mPerformWeight;
    private TextView mRMWeight;
    private TextView mRMRep;

    private int mSetWeight;

    public static RMCalculatorFragment newInstance() {
        return new RMCalculatorFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_rm_calculator, container, false);

        ConstraintLayout constraintLayout = root.findViewById(R.id.rm_calculator_page);
        mSetRMWeight = root.findViewById(R.id.set_weight_edittext);
        mPerformedRep = root.findViewById(R.id.performed_rep_edittext);
        mRM = root.findViewById(R.id.rm_edittext);
        mSetRMWeight.setText("" + 100);
        mPerformedRep.setText("" + 1);
        mRM.setText("" + 1);
        mPerformedRep.clearFocus();
        mSetRMWeight.clearFocus();
        mRM.clearFocus();

        mRMWeightPlusBtn = root.findViewById(R.id.plus_weight_btn);
        mRMWeightMinusBtn = root.findViewById(R.id.minus_weight_btn);
        mPerformedPlusBtn = root.findViewById(R.id.plus_rep_btn);
        mPerformedMinusBtn = root.findViewById(R.id.minus_rep_btn);
        mRMPlusBtn = root.findViewById(R.id.plus_rm_btn);
        mRMMinusBtn = root.findViewById(R.id.minus_rm_btn);

        mPerformWeight = root.findViewById(R.id.performed_weight_text);
        mRMWeight = root.findViewById(R.id.rm_weight_text);
        mRMRep = root.findViewById(R.id.rm_calculate_text);
        mPerformWeight.setText(mSetRMWeight.getText());
        mRMWeight.setText(mSetRMWeight.getText());
        mRMRep.setText("" + 1);

        mSetRMWeight.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

        constraintLayout.setOnClickListener(this);
        mRMWeightPlusBtn.setOnClickListener(this);
        mRMWeightMinusBtn.setOnClickListener(this);
        mPerformedPlusBtn.setOnClickListener(this);
        mPerformedMinusBtn.setOnClickListener(this);
        mRMPlusBtn.setOnClickListener(this);
        mRMMinusBtn.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.plus_weight_btn) {
            double rmWeightNum = Double.valueOf(mSetRMWeight.getText().toString());
            double rmWeight = 0;
            rmWeight = rmWeight + rmWeightNum + 5;
            displayRMWeight(rmWeight);
            mSetRMWeight.clearFocus();
        } else if (view.getId() == R.id.minus_weight_btn) {
            double rmWeightNum = Double.valueOf(mSetRMWeight.getText().toString());
            double rmWeight = 0;
            rmWeight = rmWeight + rmWeightNum - 5;
            displayRMWeight(rmWeight);
            mSetRMWeight.clearFocus();
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
            int rmNum = Integer.parseInt(mRM.getText().toString());
            int rm = 0;
            rm = rm + rmNum + 1;
            displayRM(rm);
            mRM.clearFocus();
        } else if (view.getId() == R.id.minus_rm_btn) {
            int rmNum = Integer.parseInt(mRM.getText().toString());
            int rm = 0;
            rm = rm + rmNum - 1;
            displayRM(rm);
            mRM.clearFocus();
        } else if (view.getId() == R.id.rm_calculator_page) {
            InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(view.getWindowToken(), 0);
            mSetRMWeight.clearFocus();
            mPerformedRep.clearFocus();
            mRM.clearFocus();
        }
    }

    private void displayRMWeight(double number) {
        mSetRMWeight.setText("" + number);
        mPerformWeight.setText(mSetRMWeight.getText());
        mRMWeight.setText(mSetRMWeight.getText());
    }

    private void displayPerformedRep(double number) {
        mPerformedRep.setText("" + number);
        double weight = Double.valueOf(mSetRMWeight.getText().toString());
        double performedWeight = weight * (36 / (37 - number));
        mPerformWeight.setText("" + performedWeight);
    }

    private void displayRM(int number) {
        mRM.setText("" + number);
        mRMRep.setText("" + number);
//        int weight = Integer.parseInt(mSetRMWeight.getText().toString());
//        int rmWeight =
    }


}
