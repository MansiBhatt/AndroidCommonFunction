package com.integratingdemo.graph_integration.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.integratingdemo.R;
import com.integratingdemo.common.MyMarkerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Janki on 09-02-2017.
 */

public class LineChartActivity extends AppCompatActivity {
    @BindView(R.id.lineChart)
    LineChart mChart;
    ArrayList<String> monthArrayList;
    ArrayList<String> spentArrayList;
    ArrayList<String> personArraylist;
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.line_chart));

        mContext = LineChartActivity.this;
        ButterKnife.bind(this);

        monthArrayList = new ArrayList<>();
        monthArrayList.add("JAN");
        monthArrayList.add("FEB");
        monthArrayList.add("MAR");
        monthArrayList.add("APR");
        monthArrayList.add("MAY");
        monthArrayList.add("JUN");
        spentArrayList = new ArrayList<>();
        spentArrayList.add("5");
        spentArrayList.add("15");
        spentArrayList.add("25");
        spentArrayList.add("65");
        spentArrayList.add("35");
        spentArrayList.add("75");
        personArraylist = new ArrayList<>();
        personArraylist.add("ABC");
        personArraylist.add("XYZ");
        personArraylist.add("OPQ");
        personArraylist.add("MNO");
        personArraylist.add("LMN");
        personArraylist.add("FAB");
        setLineChart(monthArrayList, spentArrayList);
    }

    public void setLineChart(final ArrayList<String> namearray, ArrayList<String> hrsarray) {
        MyMarkerView mv = new MyMarkerView(mContext, namearray);
        mChart.setMarker(mv);
   /*     mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);*/
        mChart.animateY(2000);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);


        mChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);
        mChart.getLegend().setEnabled(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setAxisMaxValue((float) (namearray.size() - 0.9));
        xAxis.setAxisMinValue((float) -0.09);

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
        ArrayList<Entry> yVals1 = new ArrayList<>();

        for (int i = 0; i < namearray.size(); i++) {
            yVals1.add(new Entry(i, Float.parseFloat(hrsarray.get(i))));
        }

        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(yVals1, "");
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1);

            LineData data = new LineData(dataSets);
            data.setValueTextSize(10f);
            //  data.setBarWidth(0.9f);
            // mChart.setFitBars(true);
            mChart.setExtraBottomOffset(10f);

            mChart.invalidate();
            mChart.setData(data);
        }

    }
}
