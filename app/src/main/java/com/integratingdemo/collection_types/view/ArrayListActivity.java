package com.integratingdemo.collection_types.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.integratingdemo.R;
import com.integratingdemo.collection_types.adapter.ArrayListAdapter;
import com.integratingdemo.collection_types.model.Country;
import com.integratingdemo.common.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Janki on 02-02-2017. It is an activity used to display data with the help of array list
 */

public class ArrayListActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    List<Country> countryList = new ArrayList<>();
    ArrayListAdapter arrayListAdapter;
    Context mContext;
    @BindView(R.id.txt_country_title)
    TextView txtCountryTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.array_list));
        ButterKnife.bind(this);

        txtCountryTitle.setText(getResources().getString(R.string.listbyarraylist));
        //Add data in array
        prepareData();
        //set data from adapter to recyclerview
        arrayListAdapter = new ArrayListAdapter(countryList, mContext);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(null);
        recyclerView.setAdapter(arrayListAdapter);
        //set recyclerview property
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        arrayListAdapter.notifyDataSetChanged();
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
    }

    private void prepareData() {
        Country afghan = new Country(getString(R.string.country_afghan));
        countryList.add(afghan);
        Country bhutan = new Country(getString(R.string.country_bhutan));
        countryList.add(bhutan);
        Country china = new Country(getString(R.string.country_china));
        countryList.add(china);
        Country georgia = new Country(getString(R.string.country_georg));
        countryList.add(georgia);
        Country india = new Country(getString(R.string.country_in));
        countryList.add(india);
        Country japan = new Country(getString(R.string.country_jap));
        countryList.add(japan);
        Country kuwait = new Country(getString(R.string.country_kuwa));
        countryList.add(kuwait);
        Country lebanon = new Country(getString(R.string.country_leb));
        countryList.add(lebanon);
        Country malaysia = new Country(getString(R.string.country_mal));
        countryList.add(malaysia);
        Country nepal = new Country(getString(R.string.country_nep));
        countryList.add(nepal);
        Country oman = new Country(getString(R.string.country_oman));
        countryList.add(oman);
        Country pak = new Country(getString(R.string.country_pak));
        countryList.add(pak);
        Country qatar = new Country(getString(R.string.country_qatar));
        countryList.add(qatar);
        Country russia = new Country(getString(R.string.country_rus));
        countryList.add(russia);
        Country singapore = new Country(getString(R.string.country_sigap));
        countryList.add(singapore);
        Country taiwan = new Country(getString(R.string.country_taiw));
        countryList.add(taiwan);
        Country uzbekistan = new Country(getString(R.string.country_uzb));
        countryList.add(uzbekistan);
        Country vietnam = new Country(getString(R.string.country_viet));
        countryList.add(vietnam);
        Country yemen = new Country(getString(R.string.country_yema));
        countryList.add(yemen);
    }
}
