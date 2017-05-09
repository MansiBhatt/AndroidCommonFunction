package com.integratingdemo.collection_types.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.integratingdemo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Janki on 02-02-2017.
 */

public class HashMapAdapterListView extends BaseAdapter {

    Context mContext;
    private final ArrayList mData;


    public HashMapAdapterListView(HashMap<String, String> countryList, Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList();
        mData.addAll(countryList.entrySet());
        Collections.sort(mData, new Comparator<HashMap.Entry< String,String >>() {

            @Override
            public int compare(HashMap.Entry<String, String> lhs,
                               HashMap.Entry<String, String> rhs) {
                return lhs.getValue().compareTo(rhs.getValue());
            }
        });
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, String> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;
        TextView txt_country;
        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_row_country_list, parent, false);
        } else {
            result = convertView;
        }

        Map.Entry<String, String> item = getItem(position);

        txt_country= (TextView) result.findViewById(R.id.txt_country);

        txt_country.setText(item.getValue());
        return result;
    }
}
