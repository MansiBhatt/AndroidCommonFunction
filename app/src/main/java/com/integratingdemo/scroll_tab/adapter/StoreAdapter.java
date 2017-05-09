package com.integratingdemo.scroll_tab.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.integratingdemo.R;
import com.integratingdemo.common.Utility;
import com.integratingdemo.scroll_tab.modal.StoreDataSetGet;

import java.util.ArrayList;

/**
 * Created by Mansi on 20-02-2017.
 */

public class StoreAdapter extends BaseAdapter {

    private ArrayList<StoreDataSetGet> strlist;
    private Context mContext;
    private final LayoutInflater mInflater;
    private String vendr_id, str_name, str_otime, str_ctime, userid, token_id, cat_id, str2, pk_vendorId, fk_categoryId, id;
    private ArrayList<String> arrstr, arr;
    int countchecked = 0;
    private int mPosition;
    private MyViewHolder holder = null;

    public StoreAdapter(Context context,
                        ArrayList<StoreDataSetGet> strlist, int mPosition) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.strlist = strlist;
        arrstr = new ArrayList<>();
        arr = new ArrayList<>();
        this.mPosition = mPosition;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub

        return strlist.size();
    }

    @Override
    public StoreDataSetGet getItem(int position) {
        // TODO Auto-generated method stub
        return strlist.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // TODO Auto-generated method stub
        final StoreDataSetGet storesetget = getItem(position);
        if (convertView == null) {

            convertView = mInflater
                    .inflate(R.layout.listview_raw, parent, false);
            holder = new MyViewHolder(convertView);
            holder.tvstrname = (TextView) convertView.findViewById(R.id.storename);
            holder.tvstrtime = (TextView) convertView.findViewById(R.id.storetime);
            holder.root = (RelativeLayout) convertView.findViewById(R.id.root);

            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        try {
            cat_id = storesetget.getFk_categoryId();
            vendr_id = storesetget.getPk_vendorId();
            str_name = storesetget.getVendor_storeName();
            str_otime = storesetget.getStore_openingTime();
            str_ctime = storesetget.getStore_closingTime();

            holder.tvstrname.setText(str_name);
            holder.tvstrtime.setText("Time " + str_otime + " to " + str_ctime);
            userid = Utility.getSharedKey("user_id", mContext);
            token_id = Utility.getSharedKey("customer_tokenId", mContext);
            str2 = Utility.getSharedKey("PreferedStore2", mContext);

        } catch (Exception e) {
            e.getMessage();
        }

        return convertView;
    }


    private class MyViewHolder {
        TextView tvstrname, tvstrtime;
        RelativeLayout root;

        MyViewHolder(View item) {
        }
    }

}

