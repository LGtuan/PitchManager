package com.example.duan1_pro1121.fragment.adminfragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.activity.admin.ShowDetailsStatistical;
import com.example.duan1_pro1121.custom_barchart.custom_barchart.DayAxisValueFormatter;
import com.example.duan1_pro1121.custom_barchart.custom_barchart.MyAxisValueFormatter;
import com.example.duan1_pro1121.database.MyDatabase;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ThongKeFrgment extends Fragment{

    private BarChart chart;
    private TextView tvYear;
    private Button btnPre,btnNext,btnChiTiet;
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    private TextView tvLuongNv,tvDichVu,tvChiPhi,tvTotal,tvSanBong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_ke_frgment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chart = view.findViewById(R.id.barchar_thongke_fragment);
        tvYear = view.findViewById(R.id.tv_year_fragment_thongke);
        btnNext = view.findViewById(R.id.btn_next_thongke);
        btnChiTiet = view.findViewById(R.id.btn_details_thongke);
        btnChiTiet.setOnClickListener(v->{
            Intent intent = new Intent(getContext(),ShowDetailsStatistical.class);
            intent.putExtra("IS_YEAR",true);
            intent.putExtra("YEAR",currentYear);
            startActivity(intent);
        });
        btnNext.setOnClickListener(v->{
            currentYear++;
            setUp();
        });
        btnPre = view.findViewById(R.id.btn_pre_thongke);
        btnPre.setOnClickListener(v->{
            currentYear--;
            setUp();
        });

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        // chart.setDrawYLabels(false);

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter();

        XAxis xAxis = chart.getXAxis();
        xAxis.setAxisLineWidth(1.5f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(12);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"T1","T2","T3","T4","T5","T6","T7","T8","T9","T10","T11","T12"}));

        ValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisLineWidth(1.5f);
        leftAxis.setLabelCount(5, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);

        setUp();

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Intent intent = new Intent(getContext(), ShowDetailsStatistical.class);
                intent.putExtra("YEAR",currentYear);
                intent.putExtra("MONTH",(int)e.getX());
                startActivity(intent);
            }
            @Override
            public void onNothingSelected() {
            }
        });
    }

    public void setUp(){
        btnNext.setEnabled(currentYear < Calendar.getInstance().get(Calendar.YEAR));
        tvYear.setText(currentYear+"");
        setData();
    }

    private void setData() {
        ArrayList<BarEntry> values = new ArrayList<>();
        for(int i = 1;i<=12;i++) {
            int total = MyDatabase.getInstance(getContext()).orderDAO().getDoanhThuWithDate("%-" + i + "-"+currentYear);
            float f = (float) total / 1000000;
            if (f % 1 == 0) {
                int value = (int) f;
                values.add(new BarEntry(i,value));
            } else{
                values.add(new BarEntry(i, f));
            }
        }

        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);

            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(values, "Doanh thu mỗi tháng");
            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            ValueFormatter custom = new MyAxisValueFormatter();
            data.setValueFormatter(custom);
            data.setValueTextSize(14f);
            data.setBarWidth(0.9f);

            chart.setData(data);
        }
        chart.animateY(1200);
    }
}