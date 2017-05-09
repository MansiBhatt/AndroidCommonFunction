package com.integratingdemo.collection_types.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.integratingdemo.R;
import com.integratingdemo.collection_types.adapter.StringArrayAdapter;
import com.integratingdemo.collection_types.model.Country;
import com.integratingdemo.common.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Janki on 02-02-2017.
 */

public class StringArrayActivity extends AppCompatActivity {

    String[] countries;
    StringArrayAdapter stringArrayAdapter;
    Context mContext;
    List<Country> countryList;
    @BindView(R.id.txt_country_title)
    TextView txtCountryTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.string_array));
        ButterKnife.bind(this);
        txtCountryTitle.setText(getResources().getString(R.string.listbyarray));
        countries = getResources().getStringArray(R.array.countries);
        countryList = new ArrayList<>();
        for (int i = 0; i < countries.length; i++) {
            Country country = new Country(countries[i]);
            countryList.add(country);
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        stringArrayAdapter = new StringArrayAdapter(countryList, StringArrayActivity.this);
        recyclerView.setAdapter(null);
        recyclerView.setAdapter(stringArrayAdapter);
        //set recyclerview property
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        stringArrayAdapter.notifyDataSetChanged();
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
    }

}
