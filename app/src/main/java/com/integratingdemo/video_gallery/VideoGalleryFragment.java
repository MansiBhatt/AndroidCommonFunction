package com.integratingdemo.video_gallery;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.integratingdemo.R;
import com.integratingdemo.common.AccelerometerListener;
import com.integratingdemo.common.AccelerometerManager;
import com.integratingdemo.main_dashboard.BottomMainActivity;
import com.integratingdemo.main_dashboard.NavDrawerMainActivity;
import com.integratingdemo.video_gallery.adapter.VideoAdapter;
import com.mukesh.permissions.AppPermissions;
import com.nguyenhoanglam.progresslayout.ProgressLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Janki on 23-01-2017.
 * Opens a Video Gallery with shake on Refresh and pull to refresh feature
 */

public class VideoGalleryFragment extends Fragment implements AccelerometerListener {

    AppPermissions runtimePermission;
    private static final int STORAGE_REQUEST_CODE = 3;
    ArrayList<String> videolist;
    @BindView(R.id.gridview)
    GridView gridView;
    @BindView(R.id.progressLayout)
    ProgressLayout progressLayout;
    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srl_refresh;
    private static final String TAG = VideoGalleryFragment.class.getSimpleName();
    Context mContext;
    Handler handler;
    ProgressDialog pdilaog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video_gallery, container, false);
        ButterKnife.bind(this, v);
        mContext = getActivity();
        try {
            NavDrawerMainActivity.txtTitle.setText(getResources().getString(R.string.video_thumbnail));
        } catch (Exception e) {
            BottomMainActivity.txtTitle.setText(getResources().getString(R.string.video_thumbnail));
        }
        pdilaog = new ProgressDialog(mContext);
        init();
        srl_refresh.setColorSchemeResources(R.color.blue_800,
                R.color.light_blue_800,
                R.color.blue_A400,
                R.color.blue_A200);
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        srl_refresh.setRefreshing(false);
                        init();
                    }
                }, 3000);
            }
        });

        return v;
    }


    private void init() {
        gridView.setNumColumns(2);
        try {
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                // Do something for lollipop and above versions
                if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            STORAGE_REQUEST_CODE);
                } else {
                    try {
                        if (!pdilaog.isShowing()) {
                            try {
                                pdilaog.setMessage(mContext.getResources().getString(R.string.loading));
                                pdilaog.show();
                                pdilaog.setCancelable(false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    handler = new Handler();
                    final Runnable r = new Runnable() {
                        public void run() {
                            try {
                                if (pdilaog.isShowing())
                                    pdilaog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    handler.postDelayed(r, 2000);
                    printNamesToLogCat(getContext());
                }
            } else {
                printNamesToLogCat(getContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_REQUEST_CODE) { //The request code you passed along with the request.
            //grantResults holds a list of all the results for the permissions requested.
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(getActivity(), R.string.storage_permission_denied, Toast.LENGTH_SHORT).show();
            } else {
                try {
                    if (!pdilaog.isShowing()) {
                        try {
                            pdilaog.setMessage(mContext.getResources().getString(R.string.loading));
                            pdilaog.show();
                            pdilaog.setCancelable(false);
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
                handler = new Handler();
                final Runnable r = new Runnable() {
                    public void run() {
                        try {
                            if (pdilaog.isShowing())
                                pdilaog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                handler.postDelayed(r, 2000);
                printNamesToLogCat(getContext());
            }

        }
    }

    public void printNamesToLogCat(Context context) {
        try {
            videolist = new ArrayList<>();
            Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            String[] projection = {MediaStore.Video.VideoColumns.DATA};
            Cursor c = context.getContentResolver().query(uri, projection, null, null, null);
            if (c != null) {
                c.moveToFirst();
                for (int i = 0; i < c.getCount(); i++) {
                    Log.d("VIDEO", c.getString(0));
                    videolist.add(c.getString(0));
                    c.moveToNext();
                }
                c.close();
            }
            Log.e(TAG, "Video List....." + videolist.toString());
            gridView.setAdapter(new VideoAdapter(getContext(), videolist));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAccelerationChanged(float x, float y, float z) {

    }

    @Override
    public void onShake(float force) {
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {

            if (AccelerometerManager.isSupported(mContext)) {
                AccelerometerManager.startListening(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            if (AccelerometerManager.isListening()) {
                AccelerometerManager.stopListening();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (AccelerometerManager.isListening()) {
                AccelerometerManager.stopListening();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
