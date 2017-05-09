package com.integratingdemo.alaram_service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.skydoves.ElasticButton;
import com.integratingdemo.R;
import com.integratingdemo.alaram_service.receiver.AlertListener;
import com.integratingdemo.alaram_service.receiver.MyBroadcastReceiver;
import com.integratingdemo.main_dashboard.BottomMainActivity;
import com.integratingdemo.main_dashboard.NavDrawerMainActivity;
import com.tapadoo.alerter.Alerter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Mansi on 17-02-2017.
 * It provides Alarm Service
 */

public class AlaramServiceFragment extends Fragment implements AlertListener {

    Context mContext;
    @BindView(R.id.time)
    AppCompatEditText time;
    @BindView(R.id.btn_strawberry1)
    ElasticButton btnStrawberry1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_alrm_srvice, container, false);
        mContext = getActivity();
        try {
            NavDrawerMainActivity.txtTitle.setText(getResources().getString(R.string.alarm));
        } catch (Exception e) {
            BottomMainActivity.txtTitle.setText(getResources().getString(R.string.alarm));
        }
        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.btn_strawberry1)
    public void onClick() {
        startAlert();
    }

    public void startAlert() {
        try {
            MyBroadcastReceiver.setListener(this);
            int i = Integer.parseInt(time.getText().toString());
            Intent intent = new Intent(mContext, MyBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 234324243, intent, 0);
            AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i * 1000), pendingIntent);
            Toast.makeText(mContext, "Alarm set in " + i + " seconds", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callAlert() {
        Alerter.create(getActivity())
                .setTitle("Alarm")
                .setText("Alarm Called")
                //    .setIcon(R.drawable.ic_face)
                .setDuration(1500)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //   Toast.makeText(mContext, "Dismissed", Toast.LENGTH_LONG).show();
                    }
                })
                .setBackgroundColor(R.color.colorAccent)
                .show();
    }

}
