package com.example.duan1_pro1121.custom_barchart.custom_barchart;

import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class MyAxisValueFormatter extends ValueFormatter {

    private DecimalFormat decimalFormat;

    public MyAxisValueFormatter(){
        decimalFormat = new DecimalFormat("###.#");
    }

    @Override
    public String getFormattedValue(float value) {
        if (value % 1 == 0)
            return (int) value + "tr";
        else return decimalFormat.format(value) + "tr";
    }
}