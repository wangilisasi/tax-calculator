package com.wangitech.tanzaniapayecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private DecimalFormat numberFormat;

    private EditText salary;
    private TextView tax, takeHome;
    private Button calculateBtn;
    double enteresSalary = 0.0;
    double calculatedTax;
    double takehome;
    private Button swahili;
    private Spinner spinnerLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        salary = findViewById(R.id.salary);
        tax = findViewById(R.id.tax);
        takeHome = findViewById(R.id.take_home);
        calculateBtn = findViewById(R.id.calculate_btn);


        formatNumber();
        btnClicks();
        textListenrer();
    }

    private void btnClicks() {
        calculateBtn.setOnClickListener(view -> {
            takeHome.setText("");
            tax.setText("");
            salary.setText("");
        });

    }

    private void textListenrer() {
        salary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (salary.getText().toString() != "" && salary.getText().toString().length() > 0) {
                    try {
                        enteresSalary = Double.parseDouble(charSequence.toString());
                        Log.d(TAG, "onTextChanged: " + enteresSalary);
                        calculatedTax = calculateTax(enteresSalary);
                        takehome = enteresSalary - calculateTax(enteresSalary);
                        tax.setText("Tax : " + numberFormat.format(calculatedTax));
                        takeHome.setText("Salary After Tax: " + numberFormat.format(takehome));
                    } catch (NumberFormatException n) {
                        Log.d(TAG, "onTextChanged: " + n.getMessage());
                        Toast.makeText(MainActivity.this, "Enter Valid salary", Toast.LENGTH_LONG).show();
                        salary.setText("");
                        tax.setText("");
                        takeHome.setText("");
                    }

                    if (enteresSalary < 0.0) {
                        Toast.makeText(MainActivity.this, "Enter Valid Salary", Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    tax.setText("");
                    takeHome.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private Double calculateTax(Double salary) {
        double tax = 0.0;
        if (salary <= 270000) {
            tax = 0.0;
        } else if (salary > 270000 && salary <= 520000) {
            tax = 0.08 * salary;
        } else if (salary > 520000 && salary <= 760000) {
            tax = 20000 + 0.2 * (salary - 520000);
        } else if (salary > 760000 && salary <= 1000000) {
            tax = 68000 + 0.25 * (salary - 760000);
        } else if (salary > 1000000) {
            tax = 128000 + 0.3 * (salary - 1000000);
        }
        return tax;
    }

    private void formatNumber() {
        numberFormat = new DecimalFormat("0.##");
        numberFormat.setGroupingUsed(true);
        numberFormat.setGroupingSize(3);

    }
}
