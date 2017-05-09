package com.integratingdemo.google_admob;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.integratingdemo.R;
import com.integratingdemo.main_dashboard.BottomMainActivity;
import com.integratingdemo.main_dashboard.NavDrawerMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdMobMainFragment extends Fragment {
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.admob_adview)
    AdView m_AdMobAdView;

    Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admob_main, container, false);
        mContext = getActivity();
        try {
            NavDrawerMainActivity.txtTitle.setText(getResources().getString(R.string.adMob));
        } catch (Exception e) {
            BottomMainActivity.txtTitle.setText(getResources().getString(R.string.adMob));
        }

        ButterKnife.bind(this, v);
        AdRequest m_adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).
                addTestDevice("4DD0986B8BB49093161F4F00CF61B887")// Add your real device id here
                .build();
        m_AdMobAdView.loadAd(m_adRequest);
        return v;
    }

    @OnClick(R.id.button)
    public void onClick() {
        startActivity(new Intent(getActivity().getApplicationContext(), AdMobSecondActivity.class));
    }
}
