package com.integratingdemo.map_cluster.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Janki on 25-01-2017.
 */

public class MyItemSqlite implements ClusterItem {
    private LatLng mPosition;
    private String mTitle;
    private String mSnippet;

    public MyItemSqlite(double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;

    }

    public MyItemSqlite() {

    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getSnippet() {
        return null;
    }

    public LatLng getmPosition() {
        return mPosition;
    }

    public void setmPosition(LatLng mPosition) {
        this.mPosition = mPosition;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmSnippet(String mSnippet) {
        this.mSnippet = mSnippet;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmSnippet() {
        return mSnippet;
    }

}
