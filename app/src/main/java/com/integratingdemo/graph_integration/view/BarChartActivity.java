package com.integratingdemo.graph_integration.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.integratingdemo.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Janki on 08-02-2017.
 * Shows bar chart
 */

public class BarChartActivity extends AppCompatActivity {
    Context mContext;
    ArrayList<String> monthArrayList;
    ArrayList<Integer> spentArrayList;
    @BindView(R.id.barChart)
    BarChart mChart;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.bar_chart));
        mContext = BarChartActivity.this;
        ButterKnife.bind(this);
        monthArrayList = new ArrayList<>();
        monthArrayList.add("JAN");
        monthArrayList.add("FEB");
        monthArrayList.add("MAR");
        monthArrayList.add("APR");
        monthArrayList.add("MAY");
        monthArrayList.add("JUN");
        spentArrayList = new ArrayList<>();
        spentArrayList.add(5);
        spentArrayList.add(15);
        spentArrayList.add(25);
        spentArrayList.add(65);
        spentArrayList.add(35);
        spentArrayList.add(75);
        setBarData(monthArrayList, spentArrayList);
    }

    private void setBarData(final ArrayList<String> namearray, ArrayList<Integer> hrsarray) {

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setDescription(new Description());
        mChart.animateY(2000);

        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        mChart.getLegend().setEnabled(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                try {
                    return namearray.get((int) value % namearray.size());
                } catch (Exception e) {
                    e.getMessage();
                }
                return "";
            }

        });


        mChart.getAxisRight().setEnabled(false);
        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        for (int i = 0; i < namearray.size(); i++) {
            yVals1.add(new BarEntry(i, Float.parseFloat(String.valueOf(hrsarray.get(i)))));
        }
        BarDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            if (namearray.size() < 3) {
                mChart.getData().setBarWidth(0.5f);
            }
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "");
            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            mChart.setFitBars(true);
            mChart.setExtraBottomOffset(10f);
            mChart.invalidate();

            mChart.setData(data);
        }
        if (namearray.size() < 3) {
            mChart.setScaleMinima(1f, 1f);
        } else if (namearray.size() < 10) {
            mChart.setScaleMinima(2f, 1f);
        } else {
            mChart.setScaleMinima(5f, 1f);
        }
    }
}
