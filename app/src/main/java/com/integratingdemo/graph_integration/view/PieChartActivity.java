package com.integratingdemo.graph_integration.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.integratingdemo.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
/*
*
 * Created by Janki on 09-02-2017.
*/


public class PieChartActivity extends AppCompatActivity {
    @BindView(R.id.pieChart)
    PieChart mChart;
    Context mContext;
    // we're going to display pie chart for smartphones martket shares
    private float[] yData = {5, 10, 15, 30, 40};
    private String[] xData = {"Sony", "Huawei", "LG", "Apple", "Samsung"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.pie_chart));
        mContext = PieChartActivity.this;
        ButterKnife.bind(this);

        init();

    }

    private void init() {
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setDrawHoleEnabled(true);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(42f);
        mChart.setTransparentCircleRadius(45f);
        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setCenterText(getResources().getString(R.string.pie_chart));
        mChart.setCenterTextColor(getResources().getColor(R.color.white));
        mChart.setCenterTextSize(17f);
        addData();
      /*  Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setTextSize(11f);
        l.setFormSize(12f);*/
        mChart.setEntryLabelColor(getResources().getColor(R.color.indigo_500));
        mChart.setHoleColor(getResources().getColor(R.color.indigo_300));
        mChart.setEntryLabelTextSize(12f);
    }

    private void addData() {
        final ArrayList<PieEntry> yVals1 = new ArrayList<PieEntry>();

        for (int i = 0; i < yData.length; i++)
            yVals1.add(new PieEntry(yData[i], i));
        // add a lot of colors


        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);

        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);
        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();
        mChart.refreshDrawableState();
    }


}
