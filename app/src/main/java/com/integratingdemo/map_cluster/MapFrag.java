package com.integratingdemo.map_cluster;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;
import com.integratingdemo.R;
import com.integratingdemo.common.MainDBAdapter;
import com.integratingdemo.common.MyApplication;
import com.integratingdemo.common.Utility;
import com.integratingdemo.main_dashboard.BottomMainActivity;
import com.integratingdemo.main_dashboard.NavDrawerMainActivity;
import com.integratingdemo.map_cluster.model.MyItemSqlite;

/**
 * Created by Janki on 20-01-2017.
 */

public class MapFrag extends Fragment implements OnMapReadyCallback {
    private ClusterManager<MyItemSqlite> mClusterManager;
    private GoogleMap mMap;
    private Context mContext;
    SupportMapFragment mapFragment;
    MainDBAdapter mainDBAdapter;
    private static final String TAG = MapFrag.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_maps, container, false);
        mContext = getActivity();
        try {
            NavDrawerMainActivity.txtTitle.setText(getResources().getString(R.string.map_cluster));
        } catch (Exception e) {
            BottomMainActivity.txtTitle.setText(getResources().getString(R.string.map_cluster));
        }
        if (Utility.isNetworkAvailable(mContext)) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            mainDBAdapter = new MainDBAdapter(mContext);
            mainDBAdapter.open();
        } else {
            Toast.makeText(mContext, getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
        }
        return v;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mMap != null) {
            return;
        }
        mMap = googleMap;
        startDemo();
    }

    private void startDemo() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        mClusterManager = new ClusterManager<>(mContext, mMap);

        mMap.setOnCameraIdleListener(mClusterManager);
        try {
            readItems();
        } catch (Exception e) {
            e.getMessage();
            Toast.makeText(mContext, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }
    }

    private void readItems() throws Exception {

        mClusterManager.addItems(mainDBAdapter.getAllData());
        Log.e(TAG, "items " + mainDBAdapter.getAllData().toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            MyApplication.getInstance().trackScreenView("Map Cluster Fragment");
        } catch (Exception e) {
            MyApplication.getInstance().trackException(e);
        }
    }
}
