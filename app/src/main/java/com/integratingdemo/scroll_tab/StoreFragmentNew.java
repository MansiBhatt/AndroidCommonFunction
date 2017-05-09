package com.integratingdemo.scroll_tab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.integratingdemo.R;
import com.integratingdemo.scroll_tab.adapter.StoreAdapter;
import com.integratingdemo.scroll_tab.modal.StoreDataSetGet;

import java.util.ArrayList;

/**
 * Created by Mansi on 3/5/2016.
 */
public class StoreFragmentNew extends ScrollTabHolderFragment {

    private static final String ARG_POSITION = "position";
    private static final String tag = "StoreFragment";
    private ListView mListView;
    private ArrayList<String> mListItems, arrstr_id, arrcat_id;
    String cat_id;
    public int mPosition;
    ArrayList<StoreDataSetGet> storeArrayList;


    public static Fragment newInstance(int position) {
        StoreFragmentNew f = new StoreFragmentNew();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);

        storeArrayList = ScrollingTabFragment.categorySetGet.getResponseObject().getCategoryDetailSetGets().get(mPosition).getStoreDataSetGets();
        //Toast.makeText(getActivity(), "Position:"+mPosition, Toast.LENGTH_SHORT).show();

        mListItems = new ArrayList<>();
        arrstr_id = new ArrayList<>();
        arrcat_id = new ArrayList<>();
        StoreDataSetGet store;
        for (int i = 0; i < storeArrayList.size(); i++) {
            store = storeArrayList.get(i);
            mListItems.add(store.getVendor_storeName() + "\n" + store.getStore_openingTime() + "\n" + store.getStore_closingTime());
            cat_id = ScrollingTabFragment.categorySetGet.getResponseObject().getCategoryDetailSetGets().get(mPosition).getPk_categoryId();

            arrcat_id.add(cat_id);
            arrstr_id.add(store.getPk_vendorId());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.fragment_list, null);

        mListView = (ListView) v.findViewById(R.id.listView);

        View placeHolderView = inflater.inflate(R.layout.view_header_placeholder, mListView, false);
        placeHolderView.setBackgroundColor(0xFFFFFFFF);
        mListView.addHeaderView(placeHolderView);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListView.setOnScrollListener(new OnScroll());
        //	mListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.listview_raw, R.id.storename, mListItems));

        mListView.setAdapter(new StoreAdapter(getActivity(), storeArrayList, mPosition));
        if (ScrollingTabFragment.NEEDS_PROXY) {//in my moto phone(android 2.1),setOnScrollListener do not work well
            mListView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (mScrollTabHolder != null)
                        mScrollTabHolder.onScroll(mListView, 0, 0, 0, mPosition);
                    return false;
                }
            });
        }

    }

    @Override
    public void adjustScroll(int scrollHeight) {
        if (scrollHeight == 0 && mListView.getFirstVisiblePosition() >= 1) {
            return;
        }

        mListView.setSelectionFromTop(1, scrollHeight);

    }

    private class OnScroll implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            if (mScrollTabHolder != null)
                mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, mPosition);
        }

    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount, int pagePosition) {
    }

}