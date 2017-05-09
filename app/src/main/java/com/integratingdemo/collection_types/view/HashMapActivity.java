package com.integratingdemo.collection_types.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.integratingdemo.R;
import com.integratingdemo.collection_types.adapter.HashMapAdapterListView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Janki on 02-02-2017.
 */

public class HashMapActivity extends AppCompatActivity {

    HashMap<String, String> countryMap = new HashMap<>();
    HashMapAdapterListView hashMapAdapter;
    Context mContext;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.txt_country_title)
    TextView txtCountryTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.hash_map));
        ButterKnife.bind(this);
        txtCountryTitle.setText(getResources().getString(R.string.listbyhashmap));
        prepareData();

        hashMapAdapter = new HashMapAdapterListView(countryMap, mContext);
        listView.setAdapter(hashMapAdapter);
    }


    private void prepareData() {
        countryMap.put("afghan", getResources().getString(R.string.country_afghan));
        countryMap.put("bhutan", getString(R.string.country_bhutan));
        countryMap.put("china", getString(R.string.country_china));
        countryMap.put("georgia", getString(R.string.country_georg));
        countryMap.put("india", getString(R.string.country_in));
        countryMap.put("japan", getString(R.string.country_jap));
        countryMap.put("kuwait", getString(R.string.country_kuwa));
        countryMap.put("lebanon", getString(R.string.country_leb));
        countryMap.put("malaysia", getString(R.string.country_mal));
        countryMap.put("nepal", getString(R.string.country_nep));
        countryMap.put("oman", getString(R.string.country_oman));
        countryMap.put("pak", getString(R.string.country_pak));
        countryMap.put("qatar", getString(R.string.country_qatar));
        countryMap.put("russia", getString(R.string.country_rus));
        countryMap.put("singapore", getString(R.string.country_sigap));
        countryMap.put("taiwan", getString(R.string.country_taiw));
        countryMap.put("uzbekistan", getString(R.string.country_uzb));
        countryMap.put("vietnam", getString(R.string.country_viet));
        countryMap.put("yemen", getString(R.string.country_yema));

    }

}
