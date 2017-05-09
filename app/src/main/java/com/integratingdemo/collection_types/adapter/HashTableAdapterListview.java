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
import java.util.Hashtable;

/**
 * Created by Janki on 03-02-2017.
 */

public class HashTableAdapterListview extends BaseAdapter {
    private Context mContext;
    private final ArrayList mData;


    public HashTableAdapterListview(Hashtable<String, String> countryList, Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList();
        mData.addAll(countryList.entrySet());
        Collections.sort(mData, new Comparator<Hashtable.Entry< String,String >>() {

            @Override
            public int compare(Hashtable.Entry<String, String> lhs,
                               Hashtable.Entry<String, String> rhs) {
                int i = lhs.getValue().compareTo(rhs.getValue());
                return i;
            }
        });
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Hashtable.Entry<String,String> getItem(int position) {
        return (Hashtable.Entry<String, String>) mData.get(position);
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

        Hashtable.Entry<String, String> item = getItem(position);
        txt_country= (TextView) result.findViewById(R.id.txt_country);

        txt_country.setText(item.getValue());
        return result;
    }
}
