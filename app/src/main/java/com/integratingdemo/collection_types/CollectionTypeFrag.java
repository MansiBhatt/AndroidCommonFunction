package com.integratingdemo.collection_types;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.skydoves.ElasticButton;
import com.integratingdemo.R;
import com.integratingdemo.collection_types.view.ArrayListActivity;
import com.integratingdemo.collection_types.view.HashMapActivity;
import com.integratingdemo.collection_types.view.HashTableActivity;
import com.integratingdemo.collection_types.view.StringArrayActivity;
import com.integratingdemo.main_dashboard.BottomMainActivity;
import com.integratingdemo.main_dashboard.NavDrawerMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Janki on 02-02-2017.
 */

public class CollectionTypeFrag extends Fragment {

    Intent intent;
    Context mContext;
    @BindView(R.id.btn_hash_map)
    ElasticButton btnHashMap;
    @BindView(R.id.btn_hash_table)
    ElasticButton btnHashTable;
    @BindView(R.id.btn_array_list)
    ElasticButton btnArrayList;
    @BindView(R.id.btn_string_array)
    ElasticButton btnStringArray;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_collection, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, v);
        try {
            NavDrawerMainActivity.txtTitle.setText(getResources().getString(R.string.collection));
        } catch (Exception e) {
            BottomMainActivity.txtTitle.setText(getResources().getString(R.string.collection));
        }
        return v;
    }


    @OnClick({R.id.btn_hash_map, R.id.btn_hash_table, R.id.btn_array_list, R.id.btn_string_array})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_hash_map:
                intent = new Intent(getActivity(), HashMapActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_hash_table:
                intent = new Intent(getActivity(), HashTableActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_array_list:
                intent = new Intent(getActivity(), ArrayListActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_string_array:
                intent = new Intent(getActivity(), StringArrayActivity.class);
                startActivity(intent);
                break;
        }
    }
}
