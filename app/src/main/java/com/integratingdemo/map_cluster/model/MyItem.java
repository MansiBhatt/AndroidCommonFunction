package com.integratingdemo.map_cluster.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Janki on 10-01-2017.
 */

public class MyItem implements ClusterItem {
    private final LatLng mPosition;
    private final String mTitle;
    private final String mSnippet;


    public MyItem(double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }


    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return null;
    }

}
