package com.example.bmicalculator;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etHeight;
    private EditText etWeight;
    private Group grpResult;
    private TextView tvResultValue;
    private Button btnCalculate;
    private TextView tvResultHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setListeners();

    }

    private void initViews() {
        etHeight = findViewById(R.id.et_height);
        etWeight = findViewById(R.id.et_weight);
        grpResult = findViewById(R.id.grp_result);
        tvResultValue = findViewById(R.id.tv_result_value);
        btnCalculate = findViewById(R.id.btn_calculate);
        tvResultHead = findViewById(R.id.tv_result_head);
    }

    private void setListeners() {
        btnCalculate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_calculate) {
            handleOnCalculateClick();
        }
    }

    private void handleOnCalculateClick() {
        if (TextUtils.isEmpty(etHeight.getText().toString().trim())) {
            Toast.makeText(getBaseContext(),
                    getString(R.string.height_error),
                    Toast.LENGTH_SHORT).show();
            grpResult.setVisibility(View.GONE);
        } else if (TextUtils.isEmpty(etWeight.getText().toString().trim())) {
            Toast.makeText(getBaseContext(),
                    getString(R.string.weight_error),
                    Toast.LENGTH_SHORT).show();
            grpResult.setVisibility(View.GONE);
        } else {
            hideKeyboard();
            float height = Float.parseFloat(etHeight.getText().toString()) / 100;
            float weight = Float.parseFloat(etWeight.getText().toString());
            float bmiValue = weight / (height * height);
            tvResultHead.setText(getResultBasedOnBMI(bmiValue));
            grpResult.setVisibility(View.VISIBLE);
            tvResultValue.setText(String.format(Locale.getDefault(), "Score- %.2f", bmiValue));
        }
    }

    private String getResultBasedOnBMI(float bmiResult) {
        if (bmiResult < 18.5) {
            return getString(R.string.underweight);
        } else if (bmiResult >= 18.5 && bmiResult < 25) {
            return getString(R.string.healthy);
        } else if (bmiResult >= 25 && bmiResult < 30) {
            return getString(R.string.overweight);
        } else {
            return getString(R.string.obese);
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            view.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}