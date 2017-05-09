package com.integratingdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.hujiaweibujidao.wava.Techniques;
import com.github.hujiaweibujidao.wava.YoYo;
import com.integratingdemo.jazzy_viewpager.ViewPagerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vc-15 on 12/24/2015.
 */
public class SplashActivity extends Activity {


    Context mContext;

    @BindView(R.id.progressBar2)
    ProgressBar progressBar2;
    @BindView(R.id.loading)
    TextView loading;
    @BindView(R.id.tag)
    TextView tag;
    @BindView(R.id.img1)
    ImageView img1;
    private YoYo.YoYoString rope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        mContext = SplashActivity.this;
        rope = YoYo.with(Techniques.DropOut).duration(5000).playOn(img1);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(SplashActivity.this, ViewPagerActivity.class);
                    startActivity(i);
                    finish();
                    StartActivity();
                }
            }
        };
        timerThread.start();
    }

    public void StartActivity() {
    }
}

