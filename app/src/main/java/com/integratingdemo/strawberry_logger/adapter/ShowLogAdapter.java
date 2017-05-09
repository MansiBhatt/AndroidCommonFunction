package com.integratingdemo.strawberry_logger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.integratingdemo.R;
import com.integratingdemo.strawberry_logger.model.StrawberryData;

import java.util.ArrayList;

/**
 * Base Adapter for Show log file
 */

public class ShowLogAdapter extends BaseAdapter {
    private ArrayList<StrawberryData> list;
    private Context context;
    LayoutInflater layoutInflater;
    private StrawberryData strawberryData;
    private String position;

    public ShowLogAdapter(Context context, ArrayList<StrawberryData> list, String position) {
        this.context = context;
        this.list = list;
        this.position = position;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyViewHolder mViewHolder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_show_log, viewGroup, false);
        }
        mViewHolder = new MyViewHolder(view);
        strawberryData = list.get(i);
        String[] strawberryType = list.get(i).getM_date().split("_");

        String data = strawberryType[1] + " " + strawberryData.getM_strawberrry_id() + " " + strawberryData.getM_weight() + " " + strawberryData.getM_compost() + " " + strawberryData.getM_sunlight() + " " + strawberryData.getM_water();
        view.setTag(mViewHolder);
        mViewHolder.txt_data.setText(data);

        return view;
    }

    private class MyViewHolder {
        TextView txt_data;

        MyViewHolder(View view) {
            txt_data = (TextView) view.findViewById(R.id.txt_entrylog);
        }
    }
}
