package com.integratingdemo.main_dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.integratingdemo.R;
import com.integratingdemo.common.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Janki on 20-01-2017.
 */

public class NavViewSelectionActivity extends AppCompatActivity {
    Context mContext;
    boolean DrawerActivity_boolean, BottomActivity_boolean;
    @BindView(R.id.btn_nav_view)
    Button btnNav;
    @BindView(R.id.btn_bottom_nav)
    Button btnBottom;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_view_selection);
        mContext = NavViewSelectionActivity.this;
        //Utility.setSharedKeyBoolean("Splash Activity", false, mContext);
        ButterKnife.bind(this);
        init();

    }

    private void init() {

        DrawerActivity_boolean = Utility.getSharedKeyBoolean(getResources().getString(R.string.drawerActivity), mContext);
        BottomActivity_boolean = Utility.getSharedKeyBoolean(getResources().getString(R.string.bottomActivity), mContext);

        if (DrawerActivity_boolean) {
            Utility.setSharedKeyBoolean("welcomeScreenShownPref", true, mContext);
            //  Utility.setSharedKeyBoolean("Splash Activity", true, mContext);
            intent = new Intent(mContext, NavDrawerMainActivity.class);
            startActivity(intent);
            finish();
        } else if (BottomActivity_boolean) {
            Utility.setSharedKeyBoolean("welcomeScreenShownPref", true, mContext);
            //   Utility.setSharedKeyBoolean("Splash Activity", true, mContext);
            intent = new Intent(mContext, BottomMainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @OnClick({R.id.btn_nav_view, R.id.btn_bottom_nav})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_nav_view:
                Utility.setSharedKeyBoolean(getResources().getString(R.string.drawerActivity), true, mContext);
                intent = new Intent(NavViewSelectionActivity.this, NavDrawerMainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_bottom_nav:
                Utility.setSharedKeyBoolean(getResources().getString(R.string.bottomActivity), true, mContext);
                intent = new Intent(NavViewSelectionActivity.this, BottomMainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}

