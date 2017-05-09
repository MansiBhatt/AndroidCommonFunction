package com.integratingdemo.collection_types.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.integratingdemo.R;
import com.integratingdemo.collection_types.model.Country;

import java.util.List;

/**
 * Created by Janki on 02-02-2017.
 */

public class HashTableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Country> countryList;
    Context mContext;

    public HashTableAdapter(List<Country> countryList, Context mContext) {
        this.countryList = countryList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_row_country_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Country country = countryList.get(position);
        ((MyViewHolder) holder).txtcountry.setText(country.getCountry());
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtcountry;

        MyViewHolder(View view) {
            super(view);
            //Bind view in components
            txtcountry = (TextView) view.findViewById(R.id.txt_country);

        }


    }
}
