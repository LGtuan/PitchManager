package com.example.duan1_pro1121.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.duan1_pro1121.MyApplication;
import com.example.duan1_pro1121.R;
import com.example.duan1_pro1121.custom_barchart.custom_barchart.customPieChart.MyPercentFormatter;
import com.example.duan1_pro1121.database.MyDatabase;
import com.example.duan1_pro1121.model.OrderDetails;
import com.example.duan1_pro1121.model.ServiceBall;
import com.example.duan1_pro1121.model.statistical.ServicePopular;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowDetailsStatistical extends AppCompatActivity {

    private PieChart serviceDoanhThuChart, servicePopularChart;
    private PieChart pitchDoanhThuChart, pitchPopularChart, timePopularChart, pitchCategoryChart;

    private LinearLayout layoutServiceDoanhThu,layoutServicePopular,layoutPitchDoanhThu;
    private LinearLayout layoutPitchPopular,layoutKhunggioPopular,layoutCategoryPopular;
    private TextView tvTongDoanhThuService,tvTongDoanhThuPitch,tvTongPopularService;
    private TextView tvTongPopularPitch,tvTongPopularCategoryPitch,tvTongKhungGioPopular;
    private TextView tvSingleDoanhThuService,tvSingleDoanhThuPitch,tvSinglePopularService;
    private TextView tvSinglePopularPitch,tvSinglePopularCategoryPitch,tvSingleKhungGioPopular;

    private TextView tvNameDoanhThuServiceSingle,tvNameDoanhThuPitchSingle,tvNameServicePopularSingle,
    tvNamePitchPopularSingle,tvNameCategoryPopularSingle,tvNameKhungGioPopularSingle;

    private int totalDoanhThuService,totalDoanhThuPitch,totalPopularService
            ,totalPopularPitch,totalPopularKhungGio,totalPopularCategoryPitch;

    private boolean isYear;
    private int yearPos;
    private int monthPos;

    public static ArrayList<Integer> myListColor = new ArrayList<>();
    static {
        myListColor.add(Color.rgb(115, 255, 0));
        myListColor.add(Color.rgb(255, 56, 56));
        myListColor.add(Color.rgb(174, 201, 0));
        myListColor.add(Color.rgb(0, 196, 154));
        myListColor.add(Color.rgb(255, 168, 28));
        myListColor.add(Color.rgb(0, 48, 143));
        myListColor.add(Color.rgb(115, 255, 0));
        myListColor.add(Color.rgb(143, 29, 3));
        myListColor.add(Color.rgb(0, 139, 181));
        myListColor.add(Color.rgb(101, 0, 189));
        myListColor.add(Color.rgb(0, 173, 98));
        myListColor.add(Color.rgb(250, 93, 2));
        myListColor.add(Color.rgb(184, 0, 194));
        myListColor.add(Color.rgb(176, 0, 111));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_statistical);
        initView();

        isYear = getIntent().getBooleanExtra("IS_YEAR", false);
        yearPos = getIntent().getIntExtra("YEAR", -1);
        monthPos = getIntent().getIntExtra("MONTH", -1);

        setUpServiceChart();
    }

    public void setUpServiceChart() {
        if (isYear) {
            setUpServiceDoanhThu();
            setUpServicePopular();
        } else {

        }
    }

    public void setUpServiceDoanhThu(){
        List<OrderDetails> listOrderDetails = MyDatabase.getInstance(this).orderDetailsDAO().getOrderDetailsWithTime("%-"+yearPos);
        for (OrderDetails orderDetails : listOrderDetails){
            totalDoanhThuService += orderDetails.getTongTien();
        }
        tvTongDoanhThuService.setText("+"+MyApplication.convertMoneyToString(totalDoanhThuService)+" VNĐ");
        List<PieEntry> list = new ArrayList<>();
        for(int i = 0;i<listOrderDetails.size();i++){
            int serviceId = listOrderDetails.get(i).getServiceId();
            ServiceBall serviceBall = MyDatabase.getInstance(this).serviceDAO().getServiceWithId(serviceId).get(0);
            list.add(new PieEntry((float)listOrderDetails.get(i).getTongTien()/totalDoanhThuService,serviceBall.getName()));
        }
        PieDataSet pieDataSet = new PieDataSet(list,"");
        Collections.shuffle(myListColor);
        pieDataSet.setColors(myListColor);

        PieData data = new PieData(pieDataSet);
        data.setValueFormatter(new MyPercentFormatter());
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.WHITE);

        serviceDoanhThuChart.setData(data);
    }

    public void setUpServicePopular(){
        List<ServicePopular> list = new ArrayList<>();

        Cursor cursor = MyDatabase.getInstance(this).orderDetailsDAO().getInfoServiceWithDate("%-"+yearPos);
        while (cursor.moveToNext()){
            ServicePopular servicePopular = new ServicePopular(cursor.getInt(0),cursor.getInt(1));
            totalPopularService += servicePopular.getCount();
            list.add(servicePopular);
        }
        tvTongPopularService.setText(totalPopularService+" lượt sử dụng");

        List<PieEntry> list1 = new ArrayList<>();
        for(int i = 0;i<list.size();i++){
            int serviceId = list.get(i).getServiceId();
            ServiceBall serviceBall = MyDatabase.getInstance(this).serviceDAO().getServiceWithId(serviceId).get(0);
            list1.add(new PieEntry((float)list.get(i).getCount()/totalPopularService,serviceBall.getName()));
        }

        PieDataSet pieDataSet = new PieDataSet(list1,"");
        Collections.shuffle(myListColor);
        pieDataSet.setColors(myListColor);

        PieData data = new PieData(pieDataSet);
        data.setValueFormatter(new MyPercentFormatter());
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.WHITE);

        servicePopularChart.setData(data);
    }

    public void initView() {
        serviceDoanhThuChart = findViewById(R.id.piechar_doanhthu_service);
        serviceDoanhThuChart.setDrawEntryLabels(false);
        serviceDoanhThuChart.getDescription().setEnabled(false);
        Legend l = serviceDoanhThuChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setFormSize(20);
        l.setTextSize(14);
        l.setTextColor(getResources().getColor(R.color.dark_blue));
        serviceDoanhThuChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                layoutServiceDoanhThu.setVisibility(View.VISIBLE);
                tvNameDoanhThuServiceSingle.setText(((PieEntry) e).getLabel());
                double singleTotal = totalDoanhThuService*((PieEntry) e).getValue();
                if(singleTotal%1000!=0){
                    double f = singleTotal/1000;
                    f = Math.round(f);
                    singleTotal = (double) (f*1000);
                }
                tvSingleDoanhThuService.setText("+"+MyApplication.convertMoneyToString((int)singleTotal)+" VNĐ");
            }
            @Override
            public void onNothingSelected() {
            }
        });

        servicePopularChart = findViewById(R.id.piechar_service_popular);
        Legend l2 = servicePopularChart.getLegend();
        l2.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l2.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l2.setOrientation(Legend.LegendOrientation.VERTICAL);
        l2.setDrawInside(false);
        l2.setFormSize(20);
        l2.setTextSize(14);
        l2.setTextColor(getResources().getColor(R.color.dark_blue));
        servicePopularChart.setDrawEntryLabels(false);
        servicePopularChart.getDescription().setEnabled(false);
        servicePopularChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                layoutServicePopular.setVisibility(View.VISIBLE);
                tvNameServicePopularSingle.setText(((PieEntry)e).getLabel());
                tvSinglePopularService.setText(Math.round(((PieEntry) e).getValue() * totalPopularService)+" lượt sử dụng");
            }

            @Override
            public void onNothingSelected() {

            }
        });

        pitchCategoryChart = findViewById(R.id.piechar_category_pitch_popular);
        Legend l3 = pitchCategoryChart.getLegend();
        l3.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l3.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l3.setOrientation(Legend.LegendOrientation.VERTICAL);
        l3.setDrawInside(false);
        l3.setFormSize(20);
        l3.setTextSize(14);
        l3.setTextColor(getResources().getColor(R.color.dark_blue));

        pitchDoanhThuChart = findViewById(R.id.piechar_doanhthu_pitch);
        Legend l4 = pitchDoanhThuChart.getLegend();
        l4.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l4.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l4.setOrientation(Legend.LegendOrientation.VERTICAL);
        l4.setDrawInside(false);
        l4.setFormSize(20);
        l4.setTextSize(14);
        l4.setTextColor(getResources().getColor(R.color.dark_blue));

        pitchPopularChart = findViewById(R.id.piechar_pitch_popular);
        Legend l5 = pitchPopularChart.getLegend();
        l5.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l5.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l5.setOrientation(Legend.LegendOrientation.VERTICAL);
        l5.setDrawInside(false);
        l5.setFormSize(20);
        l5.setTextSize(14);
        l5.setTextColor(getResources().getColor(R.color.dark_blue));

        timePopularChart = findViewById(R.id.piechar_time_popular);
        Legend l6 = timePopularChart.getLegend();
        l6.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l6.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l6.setOrientation(Legend.LegendOrientation.VERTICAL);
        l6.setDrawInside(false);
        l6.setFormSize(20);
        l6.setTextSize(14);
        l6.setTextColor(getResources().getColor(R.color.dark_blue));

        tvTongDoanhThuService = findViewById(R.id.tv_tongdoanhthu_service);
        tvTongDoanhThuPitch = findViewById(R.id.tv_tongdoanhthu_pitch);
        tvTongKhungGioPopular = findViewById(R.id.tv_khunggio_popular);
        tvTongPopularPitch = findViewById(R.id.tv_tong_sanbong_popular);
        tvTongPopularService = findViewById(R.id.tv_tong_service_popular);
        tvTongPopularCategoryPitch = findViewById(R.id.tv_loaisan_popular);

        tvSingleDoanhThuService = findViewById(R.id.tv_single_doanhthu_service);
        tvSingleDoanhThuPitch = findViewById(R.id.tv_single_doanhthu_pitch);
        tvSingleKhungGioPopular = findViewById(R.id.tv_single_khunggio_popular);
        tvSinglePopularPitch = findViewById(R.id.tv_single_pitch_popular);
        tvSinglePopularService = findViewById(R.id.tv_single_service_popular);
        tvSinglePopularCategoryPitch = findViewById(R.id.tv_single_loaisan_popular);

        tvNameCategoryPopularSingle = findViewById(R.id.tv_name_category_popular);
        tvNameDoanhThuServiceSingle = findViewById(R.id.tv_name_service_doanhthu);
        tvNamePitchPopularSingle = findViewById(R.id.tv_name_pitch_popular);
        tvNameServicePopularSingle = findViewById(R.id.tv_name_popular_service);
        tvNameDoanhThuPitchSingle = findViewById(R.id.tv_name_picth_doanhthu);
        tvNameKhungGioPopularSingle = findViewById(R.id.tv_name_khungio_popular);

        layoutCategoryPopular = findViewById(R.id.layout_category_popular);
        layoutKhunggioPopular = findViewById(R.id.layout_khunggio_pupular);
        layoutPitchDoanhThu = findViewById(R.id.layout_doanhthu_pitch);
        layoutPitchPopular = findViewById(R.id.layout_popular_pitch);
        layoutServicePopular = findViewById(R.id.layout_popular_service);
        layoutServiceDoanhThu = findViewById(R.id.layout_doanhthu_service);
    }
}