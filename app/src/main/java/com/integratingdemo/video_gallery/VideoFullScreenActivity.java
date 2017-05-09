package com.integratingdemo.video_gallery;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.RelativeLayout;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.integratingdemo.R;
import com.integratingdemo.common.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Janki on 23-01-2017.
 *
 */
public class VideoFullScreenActivity extends FragmentActivity implements EasyVideoCallback {
    String from, path;
    int pos;
    @BindView(R.id.player)
    EasyVideoPlayer player;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.video_fullscreen);
        ButterKnife.bind(this);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        try {
            if (player.isPlaying())
                player.stop();

            Bundle bundle = getIntent().getExtras();

            if (bundle != null) {
                from = bundle.getString("from");
                pos = bundle.getInt("pause_time");
                path = bundle.getString("path");
            }

            String[] params = Utility.getScreenResolution(this).split(",");
            assert player != null;
            player.setCallback(this);
            player.setAutoPlay(true);
            player.setLayoutParams(new RelativeLayout.LayoutParams(Integer.parseInt(params[0]), Integer.parseInt(params[1])));
            player.setSource(Uri.parse(path));


        } catch (Exception e) {
            e.getMessage();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    public void onStarted(EasyVideoPlayer player) {

    }

    @Override
    public void onPaused(EasyVideoPlayer player) {

    }

    @Override
    public void onPreparing(EasyVideoPlayer player) {

    }

    @Override
    public void onPrepared(EasyVideoPlayer player) {

    }

    @Override
    public void onBuffering(int percent) {

    }

    @Override
    public void onError(EasyVideoPlayer player, Exception e) {

    }

    @Override
    public void onCompletion(EasyVideoPlayer player) {

    }

    @Override
    public void onRetry(EasyVideoPlayer player, Uri source) {

    }

    @Override
    public void onSubmit(EasyVideoPlayer player, Uri source) {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            if (player.isPlaying())
                player.stop();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
