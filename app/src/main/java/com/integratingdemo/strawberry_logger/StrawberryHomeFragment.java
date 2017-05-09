package com.integratingdemo.strawberry_logger;

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
import com.integratingdemo.common.MainDBAdapter;
import com.integratingdemo.common.MyApplication;
import com.integratingdemo.main_dashboard.BottomMainActivity;
import com.integratingdemo.main_dashboard.NavDrawerMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Janki on 09-02-2017.
 */

public class StrawberryHomeFragment extends Fragment {
    @BindView(R.id.btn_strawberry1)
    ElasticButton btnStrawberry1;
    @BindView(R.id.btn_strawberry2)
    ElasticButton btnStrawberry2;
    @BindView(R.id.btn_strawberry3)
    ElasticButton btnStrawberry3;
    @BindView(R.id.btn_strawberry4)
    ElasticButton btnStrawberry4;
    @BindView(R.id.btn_strawberry5)
    ElasticButton btnStrawberry5;
    @BindView(R.id.btn_prev)
    ElasticButton btnPrev;
    @BindView(R.id.btn_next)
    ElasticButton btnNext;
    @BindView(R.id.btn_home)
    ElasticButton btnHome;
    Context mContext;
    MainDBAdapter mainDBAdapter;
    Intent i;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_datalogger_home, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, v);
        try {
            NavDrawerMainActivity.txtTitle.setText(getResources().getString(R.string.data_logger));
        } catch (Exception e) {
            BottomMainActivity.txtTitle.setText(getResources().getString(R.string.data_logger));
        }
        mainDBAdapter = new MainDBAdapter(mContext);
        mainDBAdapter.createDatabase();
        return v;
    }


    @OnClick({R.id.btn_strawberry1, R.id.btn_strawberry2, R.id.btn_strawberry3, R.id.btn_strawberry4, R.id.btn_strawberry5, R.id.btn_prev, R.id.btn_next, R.id.btn_home})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_strawberry1:
                MyApplication.getInstance().trackEvent("Logger", "Download", "First Strawberry example");
                i = new Intent(getActivity(), LoggerDataEntryActivity.class);
                i.putExtra(getResources().getString(R.string.position), "1");
                startActivity(i);


                break;
            case R.id.btn_strawberry2:

                i = new Intent(getActivity(), LoggerDataEntryActivity.class);
                i.putExtra(getResources().getString(R.string.position), "2");
                startActivity(i);

                break;
            case R.id.btn_strawberry3:

                i = new Intent(getActivity(), LoggerDataEntryActivity.class);
                i.putExtra(getResources().getString(R.string.position), "3");
                startActivity(i);

                break;
            case R.id.btn_strawberry4:

                i = new Intent(getActivity(), LoggerDataEntryActivity.class);
                i.putExtra(getResources().getString(R.string.position), "4");
                startActivity(i);

                break;
            case R.id.btn_strawberry5:

                i = new Intent(getActivity(), LoggerDataEntryActivity.class);
                i.putExtra(getResources().getString(R.string.position), "5");
                startActivity(i);

                break;
            case R.id.btn_prev:

                i = new Intent(getActivity(), LoggerDataEntryActivity.class);
                i.putExtra(getResources().getString(R.string.position), "5");
                startActivity(i);

                break;
            case R.id.btn_next:

                i = new Intent(getActivity(), LoggerDataEntryActivity.class);
                i.putExtra(getResources().getString(R.string.position), "1");
                startActivity(i);

                break;
            case R.id.btn_home:
                break;
        }
    }


}
