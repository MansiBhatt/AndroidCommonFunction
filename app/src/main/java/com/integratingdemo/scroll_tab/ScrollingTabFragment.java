package com.integratingdemo.scroll_tab;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.integratingdemo.R;
import com.integratingdemo.common.MyListener;
import com.integratingdemo.common.PagerSlidingTabStrip;
import com.integratingdemo.common.Utility;
import com.integratingdemo.main_dashboard.BottomMainActivity;
import com.integratingdemo.main_dashboard.NavDrawerMainActivity;
import com.integratingdemo.scroll_tab.modal.CategoryDetailSetGet;
import com.integratingdemo.scroll_tab.modal.CategorySetGet;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mansi on 20-02-2017.
 */

public class ScrollingTabFragment extends Fragment implements ScrollTabHolder, ViewPager.OnPageChangeListener {

    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.info_header)
    ImageView infoHeader;
    @BindView(R.id.info)
    ImageView info;
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pref_store_header)
    FrameLayout prefStoreHeader;
    @BindView(R.id.main_frame)
    FrameLayout mainFrame;

    private int mMinHeaderHeight;
    private int prefStoreHeaderHeight;
    private int mMinHeaderTranslation;
    Context mContext;
    String[] categoryNames, catImages;
    int mDestWidth, mDestHeight;
    private PagerAdapter mPagerAdapter;
    private int mLastY;
    public static CategorySetGet categorySetGet;
    public static final boolean NEEDS_PROXY = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tabstrip, container, false);
        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.min_header_height);
        prefStoreHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mMinHeaderHeight;
        mContext = getActivity();
        try {
            NavDrawerMainActivity.txtTitle.setText(getResources().getString(R.string.scroll_tab));
        } catch (Exception e) {
            BottomMainActivity.txtTitle.setText(getResources().getString(R.string.scroll_tab));
        }
        ButterKnife.bind(this, v);
        if (Utility.isNetworkAvailable(mContext)) {
            getAllCategories();
        } else {
            Utility.showToastMessage(mContext, getResources().getString(R.string.no_network));
        }

        return v;
    }

    private void getAllCategories() {

        String url = "http://sendkardo.iprojectlab.com/demo/x/API_Dropdown/getCategoryWiceStore";

        Map<String, String> params = new HashMap<>();
        params.put(getResources().getString(R.string.application_token), Utility.application_token);
        params.put("token_id", "7ba092b1351bd3cf5b6ba005299eb50a");
        params.put("user_id", "55");
        params.put("area_id", "1");
        Utility.CallApi_ProgressDialog(mContext, url, params);
        new Utility(new MyListener() {
            @Override
            public void onResponse(String response) {
                try {
                    Gson gson = new Gson();
                    categorySetGet = gson.fromJson(response, CategorySetGet.class);
                    if (Utility.success == categorySetGet.getSucsess()) {
                        categoryNames = CategoryDetailSetGet.getCategoryNames(categorySetGet.getResponseObject().getCategoryDetailSetGets());
                        catImages = CategoryDetailSetGet.getCategoryImages(categorySetGet.getResponseObject().getCategoryDetailSetGets());
                        initScreenItems();
                    } else {
                        pb.setVisibility(View.GONE);
                        Utility.showToastMessage(mContext, categorySetGet.getMsg());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse() {
                pb.setVisibility(View.GONE);
            }
        });
    }

    private void initScreenItems() {

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mDestWidth = size.x;
        final float scale = getResources().getDisplayMetrics().density;
        int imageHeight = info.getMeasuredHeight();
        mDestHeight = (int) (imageHeight * scale + 0.5f);


        pager.setOffscreenPageLimit(categoryNames.length);

        mPagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        mPagerAdapter.setTabHolderScrollingContent(this);

        pager.setAdapter(mPagerAdapter);
        tabs.setIndicatorColor(Color.parseColor("#FEEED4"));
        tabs.setViewPager(pager);
        tabs.setOnPageChangeListener(this);
        mLastY = 0;

        Utility.Imageupload_progressbar(mContext, categorySetGet.getResponseObject().getCategoryDetailSetGets().get(0).getImage(), info, pb);

    }

    private class PagerAdapter extends FragmentPagerAdapter {

        private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
        private ScrollTabHolder mListener;

        PagerAdapter(FragmentManager fm) {
            super(fm);
            mScrollTabHolders = new SparseArrayCompat<>();
        }

        void setTabHolderScrollingContent(ScrollTabHolder listener) {
            mListener = listener;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return categoryNames[position];
        }

        @Override
        public int getCount() {
            return categoryNames.length;
        }

        @Override
        public Fragment getItem(int position) {
            ScrollTabHolderFragment fragment = (ScrollTabHolderFragment) StoreFragmentNew.newInstance(position);

            mScrollTabHolders.put(position, fragment);
            if (mListener != null) {
                fragment.setScrollTabHolder(mListener);
            }

            return fragment;
        }

        SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
            return mScrollTabHolders;
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // nothing
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (positionOffsetPixels > 0) {
            int currentItem = pager.getCurrentItem();

            SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
            ScrollTabHolder currentHolder;

            if (position < currentItem) {
                currentHolder = scrollTabHolders.valueAt(position);
            } else {
                currentHolder = scrollTabHolders.valueAt(position + 1);
            }

            if (NEEDS_PROXY) {
                // TODO is not good
                currentHolder.adjustScroll(prefStoreHeader.getHeight() - mLastY);
                prefStoreHeader.postInvalidate();
            } else {
                try {
                    currentHolder.adjustScroll((int) (prefStoreHeader.getHeight() + prefStoreHeader.getTranslationY()));
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }

    }

    @Override
    public void onPageSelected(int position) {
        SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
        ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
        if (NEEDS_PROXY) {
            //TODO is not good
            currentHolder.adjustScroll(prefStoreHeader.getHeight() - mLastY);
            prefStoreHeader.postInvalidate();
        } else {
            try {
                currentHolder.adjustScroll((int) (prefStoreHeader.getHeight() + prefStoreHeader.getTranslationY()));
            } catch (Exception e) {
                e.getMessage();
            }
        }

        //  imgloader.DisplayImage(image, info, mDestWidth, mDestHeight);

        Utility.Imageupload_progressbar(mContext, categorySetGet.getResponseObject().getCategoryDetailSetGets().get(position).getImage(), info, pb
        );


    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
        if (pager.getCurrentItem() == pagePosition) {
            int scrollY = getScrollY(view);
            if (NEEDS_PROXY) {
                //TODO is not good
                mLastY = -Math.max(-scrollY, mMinHeaderTranslation);
                //   info.setText(String.valueOf(scrollY));
                prefStoreHeader.scrollTo(0, mLastY);
                prefStoreHeader.postInvalidate();
            } else {
                prefStoreHeader.setTranslationY(Math.max(-scrollY, mMinHeaderTranslation));
            }
        }
    }

    @Override
    public void adjustScroll(int scrollHeight) {
        // nothing
    }

    public int getScrollY(AbsListView view) {
        View c = view.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = prefStoreHeaderHeight;
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

}
