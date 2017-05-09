package com.integratingdemo.alaram_service.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import com.integratingdemo.R;

/**
 * Created by Mansi on 16-02-2017.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    MediaPlayer mp;
    public static AlertListener alertListener;

    public MyBroadcastReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        mp = MediaPlayer.create(context, R.raw.alrm);
        mp.start();
        if (alertListener != null) {
            alertListener.callAlert();
        }
    }

    public static void setListener(AlertListener listener) {
        alertListener = listener;
    }

}
