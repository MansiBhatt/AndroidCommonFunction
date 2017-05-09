package com.integratingdemo.graph_integration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.skydoves.ElasticButton;
import com.integratingdemo.R;
import com.integratingdemo.graph_integration.view.BarChartActivity;
import com.integratingdemo.graph_integration.view.LineChartActivity;
import com.integratingdemo.graph_integration.view.PieChartActivity;
import com.integratingdemo.main_dashboard.BottomMainActivity;
import com.integratingdemo.main_dashboard.NavDrawerMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Janki on 09-02-2017.
 */

public class ChartFragment extends Fragment {
    Context mContext;
    @BindView(R.id.btn_bar_chart)
    ElasticButton btnBarChart;
    @BindView(R.id.btn_pie_chart)
    ElasticButton btnPieChart;
    @BindView(R.id.btn_line_chart)
    ElasticButton btnLineChart;
    Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chart, container, false);
        mContext = getActivity();
        try {
            NavDrawerMainActivity.txtTitle.setText(getResources().getString(R.string.chart));

        } catch (Exception e) {
            BottomMainActivity.txtTitle.setText(getResources().getString(R.string.chart));
        }
        ButterKnife.bind(this, v);
        return v;

    }

    @OnClick({R.id.btn_bar_chart, R.id.btn_pie_chart, R.id.btn_line_chart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_bar_chart:
                intent = new Intent(getActivity(), BarChartActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_pie_chart:
                intent = new Intent(getActivity(), PieChartActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_line_chart:
                intent = new Intent(getActivity(), LineChartActivity.class);
                startActivity(intent);
                break;
        }
    }


}
