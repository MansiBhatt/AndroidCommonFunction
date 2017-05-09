package com.integratingdemo.jazzy_viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import static com.integratingdemo.jazzy_viewpager.ViewPagerActivity.NUM_PAGES;

/**
 * Created by Janki on 24-01-2017.
 */

/**
 * A simple pager adapter that represents all ScreenSlidePageFragment objects, in
 * sequence.
 */
class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {


    ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        final Bundle bundle = new Bundle();
        bundle.putInt(SlideFragment.EXTRA_POSITION, position);
        final SlideFragment fragment = new SlideFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}