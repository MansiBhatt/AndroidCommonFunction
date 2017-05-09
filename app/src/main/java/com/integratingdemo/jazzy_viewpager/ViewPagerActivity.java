package com.integratingdemo.jazzy_viewpager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.github.skydoves.ElasticButton;
import com.integratingdemo.R;
import com.integratingdemo.common.Utility;
import com.integratingdemo.main_dashboard.BottomMainActivity;
import com.integratingdemo.main_dashboard.NavDrawerMainActivity;
import com.integratingdemo.main_dashboard.NavViewSelectionActivity;
import com.integratingdemo.social_login.SocialSignInActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Janki on 24-01-2017.
 */

public class ViewPagerActivity extends AppCompatActivity {
    boolean welcomeScreenShown, isGoogleSignedIn, isViewPager;
    public static final int NUM_PAGES = 3;
    Intent intent;

    Context mContext;

    public boolean DrawerActivity_boolean, BottomActivity_boolean, isLoggedOut;
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */

    @BindView(R.id.pager)
    ViewPager mPager;

    @BindView(R.id.btn_skip)
    ElasticButton btnSkip;
    @BindView(R.id.btn_done)
    ElasticButton btnDone;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_viewpager);
        mContext = ViewPagerActivity.this;
        ButterKnife.bind(this);


        welcomeScreenShown = Utility.getSharedKeyBoolean("welcomeScreenShownPref", mContext);
        isGoogleSignedIn = Utility.getSharedKeyBoolean(getResources().getString(R.string.google_signIn), mContext);
        DrawerActivity_boolean = Utility.getSharedKeyBoolean(getResources().getString(R.string.drawerActivity), mContext);
        BottomActivity_boolean = Utility.getSharedKeyBoolean(getResources().getString(R.string.bottomActivity), mContext);
        isLoggedOut = Utility.getSharedKeyBoolean(getString(R.string.isLoggedOut), mContext);
        int selectedPage = 0;
        if (savedInstanceState != null) { //if app was paused, you can reopen the same page
            selectedPage = savedInstanceState.getInt("SELECTED_PAGE");
        }
        mPager.setCurrentItem(selectedPage); //set the current page
        if (isLoggedOut)
        {
            Utility.setSharedKeyBoolean(getResources().getString(R.string.google_signIn), true, mContext);
            intent = new Intent(ViewPagerActivity.this, SocialSignInActivity.class);
            startActivity(intent);
            finish();
        } else {
            if (isGoogleSignedIn)
            {//Sign in
                 if (welcomeScreenShown)
                 {//Splash
                    if (DrawerActivity_boolean)
                    {//Drawer
                        Utility.setSharedKeyBoolean("welcomeScreenShownPref", true, mContext);
                        intent = new Intent(mContext, NavDrawerMainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (BottomActivity_boolean)
                    {//Bottom
                        Utility.setSharedKeyBoolean("welcomeScreenShownPref", true, mContext);
                        intent = new Intent(mContext, BottomMainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Utility.setSharedKeyBoolean("welcomeScreenShownPref", true, mContext);
                    intent = new Intent(ViewPagerActivity.this, NavViewSelectionActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else
            {
                isViewPager = Utility.getSharedKeyBoolean(getResources().getString(R.string.view_pager), mContext);
                if (isViewPager)
                {
                    Utility.setSharedKeyBoolean(getResources().getString(R.string.view_pager), true, mContext);
                    Utility.setSharedKeyBoolean(getResources().getString(R.string.google_signIn), true, mContext);
                    intent = new Intent(ViewPagerActivity.this, SocialSignInActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
            init();
        }
    }


    public void init() {

        Utility.setSharedKeyBoolean(getResources().getString(R.string.view_pager), false, mContext);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new CubeOutTransformer()); //set the animation

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    btnDone.setVisibility(View.INVISIBLE);
                    btnSkip.setVisibility(View.VISIBLE);
                } else if (position == 1) {
                    btnDone.setVisibility(View.INVISIBLE);
                    btnSkip.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    btnSkip.setVisibility(View.INVISIBLE);
                    btnDone.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @OnClick({R.id.btn_skip, R.id.btn_done})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_skip:
                Utility.setSharedKeyBoolean("welcomeScreenShownPref", true, mContext);
                //  intent= new Intent(ViewPagerActivity.this, SocialSignInActivity.class);
                intent = new Intent(ViewPagerActivity.this, SocialSignInActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_done:
                Utility.setSharedKeyBoolean("welcomeScreenShownPref", true, mContext);
                // intent= new Intent(ViewPagerActivity.this, SocialSignInActivity.class);
                intent = new Intent(ViewPagerActivity.this, SocialSignInActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
