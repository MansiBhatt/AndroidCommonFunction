package com.integratingdemo.strawberry_logger;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.integratingdemo.R;
import com.integratingdemo.strawberry_logger.fragment.DataEntryFragment;

/**
 * Created by Janki on 10-02-2017.
 */

public class LoggerDataEntryActivity extends AppCompatActivity {
    Context mContext;
    String position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        mContext = LoggerDataEntryActivity.this;
        try {
            Bundle bundle = getIntent().getExtras();
            position = bundle.getString(getResources().getString(R.string.position));
            DataEntryFragment fragment1 = new DataEntryFragment();
            fragment1.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, fragment1);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
