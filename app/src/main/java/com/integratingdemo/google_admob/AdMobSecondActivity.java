package com.integratingdemo.google_admob;
// How to Add Full screen Ad (Interstitial Ad) of AdMob in Android Application and Game

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.integratingdemo.R;

public class AdMobSecondActivity extends AppCompatActivity
{

    InterstitialAd m_AdMobInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_admob);

        m_AdMobInterstitialAd = new InterstitialAd(this);

        m_AdMobInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen_ad));

        AdRequest m_adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("4DD0986B8BB49093161F4F00CF61B887")// Add your real device id here
                .build();

        m_AdMobInterstitialAd.loadAd(m_adRequest);

        m_AdMobInterstitialAd.setAdListener(new AdListener()
        {
            public void onAdLoaded()
            {
                showInterstitialAd();
            }
        });
    }

    private void showInterstitialAd()
    {
        if (m_AdMobInterstitialAd.isLoaded())
        {
            m_AdMobInterstitialAd.show();
        }
    }
}