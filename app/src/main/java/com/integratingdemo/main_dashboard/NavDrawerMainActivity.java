package com.integratingdemo.main_dashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.integratingdemo.R;
import com.integratingdemo.alaram_service.AlaramServiceFragment;
import com.integratingdemo.collection_types.CollectionTypeFrag;
import com.integratingdemo.common.LocaleHelper;
import com.integratingdemo.common.MainDBAdapter;
import com.integratingdemo.common.PermissionInterface;
import com.integratingdemo.common.Utility;
import com.integratingdemo.google_admob.AdMobMainFragment;
import com.integratingdemo.graph_integration.ChartFragment;
import com.integratingdemo.image_cropper.ImageCropperFragment;
import com.integratingdemo.map_cluster.MapFrag;
import com.integratingdemo.multi_lang.MultiLangFrag;
import com.integratingdemo.upload_to_drive.UploadActivity;
import com.integratingdemo.payu.PayUFragment;
import com.integratingdemo.place_picker.PlacePickerFragment;
import com.integratingdemo.realm_demo.RealmMainFragment;
import com.integratingdemo.scroll_tab.ScrollingTabFragment;
import com.integratingdemo.social_login.SocialSignInActivity;
import com.integratingdemo.social_login.model.SignInDetail;
import com.integratingdemo.strawberry_logger.StrawberryHomeFragment;
import com.integratingdemo.vector_drawable.VectorDrawableFragment;
import com.integratingdemo.video_gallery.VideoGalleryFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavDrawerMainActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener {
    MainDBAdapter mainDBAdapter;
    Context mContext;
    @BindView(R.id.toolbar)

    Toolbar toolbar;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)

    DrawerLayout drawer;
    GoogleApiClient mGoogleApiClient;
    public static TextView txtTitle;
    ImageButton imgbtnSignOut;
    SignInDetail signInDetail;
    PermissionInterface permissionInterface;

    public NavDrawerMainActivity() {
    }

    public NavDrawerMainActivity(PermissionInterface permissionInterface) {
        this.permissionInterface = permissionInterface;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        mContext=NavDrawerMainActivity.this;
         Utility.setSharedKeyBoolean(getString(R.string.isLoggedOut), false, mContext);
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // Do something for lollipop and above versions
            navigationView = (NavigationView) findViewById(R.id.nav_view);

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Handle navigation view item clicks here.
                    int id = item.getItemId();

                    if (id == R.id.nav_map_cluster) {
                        // Handle map clustering
                        MapFrag mapFragment = new MapFrag();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, mapFragment);
                        fragmentTransaction.commit();
                    } else if (id == R.id.nav_gallery) {
                        //Handle Vector Drawable
                        try {
                            VectorDrawableFragment vectorDrawableFragment = new VectorDrawableFragment();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container, vectorDrawableFragment);
                            fragmentTransaction.commit();
                        } catch (Exception e) {
                            Toast.makeText(mContext, getString(R.string.feature_not_supported), Toast.LENGTH_SHORT).show();
                        }
                    } else if (id == R.id.nav_multi_lang) {
                        MultiLangFrag multiLangFrag = new MultiLangFrag();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, multiLangFrag);
                        fragmentTransaction.commit();

                    } else if (id == R.id.nav_db) {
                        RealmMainFragment fragment1 = new RealmMainFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, fragment1);
                        fragmentTransaction.commit();

                    } else if (id == R.id.nav_videothumbnail) {
                        VideoGalleryFragment videoGalleryFragment = new VideoGalleryFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, videoGalleryFragment);
                        fragmentTransaction.commit();
                    } else if (id == R.id.nav_placepicker) {
                        PlacePickerFragment placePickerFragment = new PlacePickerFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, placePickerFragment);
                        fragmentTransaction.commit();
                    } else if (id == R.id.nav_upload) {
                        Intent i = new Intent(NavDrawerMainActivity.this, UploadActivity.class);
                        startActivity(i);
                    } else if (id == R.id.nav_collection) {
                        CollectionTypeFrag collectionTypeFrag = new CollectionTypeFrag();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, collectionTypeFrag);
                        fragmentTransaction.commit();
                    } else if (id == R.id.nav_chart) {
                        ChartFragment chartFragment = new ChartFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, chartFragment);
                        fragmentTransaction.commit();
                    } else if (id == R.id.nav_data_logger) {
                        StrawberryHomeFragment strawberryHomeFragment = new StrawberryHomeFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, strawberryHomeFragment);
                        fragmentTransaction.commit();
                    } else if (id == R.id.nav_ad) {
                        AdMobMainFragment adMobMainFragment = new AdMobMainFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, adMobMainFragment);
                        fragmentTransaction.commit();
                    } else if (id == R.id.nav_alrm_srvice) {
                        AlaramServiceFragment alaramServiceFragment = new AlaramServiceFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, alaramServiceFragment);
                        fragmentTransaction.commit();
                    } else if (id == R.id.nav_payu_srvice) {
                        PayUFragment alaramServiceFragment = new PayUFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, alaramServiceFragment);
                        fragmentTransaction.commit();
                    } else if (id == R.id.nav_image_cropper) {
                        ImageCropperFragment imageCropperFragment = new ImageCropperFragment();
                        FragmentManager imFragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = imFragmentManager.beginTransaction();
                        fragmentTransaction1.replace(R.id.container, imageCropperFragment);
                        fragmentTransaction1.commit();
                    }else if (id == R.id.nav_scroll_tab) {
                        ScrollingTabFragment scrollingTabFragment = new ScrollingTabFragment();
                        FragmentManager scrollFragmentManager = getSupportFragmentManager();
                        FragmentTransaction scrollfragmentTransaction = scrollFragmentManager.beginTransaction();
                        scrollfragmentTransaction.replace(R.id.container, scrollingTabFragment);
                        scrollfragmentTransaction.commit();
                    }
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
            });
        } else {
            // do something for phones running an SDK before lollipop
            hideItem();
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Handle navigation view item clicks here.
                    int id = item.getItemId();
                    if (id == R.id.nav_map_cluster) {
                        // Handle map clustering
                        MapFrag mapFragment = new MapFrag();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, mapFragment);
                        fragmentTransaction.commit();
                    } else if (id == R.id.nav_multi_lang) {
                        MultiLangFrag multiLangFrag = new MultiLangFrag();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, multiLangFrag);
                        fragmentTransaction.commit();

                    } else if (id == R.id.nav_db) {
                        RealmMainFragment fragment1 = new RealmMainFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, fragment1);
                        fragmentTransaction.commit();

                    } else if (id == R.id.nav_videothumbnail) {
                        VideoGalleryFragment videoGalleryFragment = new VideoGalleryFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, videoGalleryFragment);
                        fragmentTransaction.commit();
                    } else if (id == R.id.nav_placepicker) {
                        PlacePickerFragment placePickerFragment = new PlacePickerFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, placePickerFragment);
                        fragmentTransaction.commit();
                    } else if (id == R.id.nav_upload) {
                        Intent i = new Intent(NavDrawerMainActivity.this, UploadActivity.class);
                        startActivity(i);
                    } else if (id == R.id.nav_collection) {
                        CollectionTypeFrag collectionTypeFrag = new CollectionTypeFrag();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, collectionTypeFrag);
                        fragmentTransaction.commit();
                    } else if (id == R.id.nav_chart) {
                        ChartFragment chartFrag = new ChartFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, chartFrag);
                        fragmentTransaction.commit();
                    }  else if (id == R.id.nav_data_logger) {
                        StrawberryHomeFragment strawberryHomeFragment = new StrawberryHomeFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, strawberryHomeFragment);
                        fragmentTransaction.commit();
                    } else if (id == R.id.nav_ad) {
                        AdMobMainFragment adMobMainFragment = new AdMobMainFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, adMobMainFragment);
                        fragmentTransaction.commit();
                    } else if (id == R.id.nav_alrm_srvice) {
                        AlaramServiceFragment alaramServiceFragment = new AlaramServiceFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, alaramServiceFragment);
                        fragmentTransaction.commit();
                    } else if (id == R.id.nav_payu_srvice) {
                        PayUFragment alaramServiceFragment = new PayUFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, alaramServiceFragment);
                        fragmentTransaction.commit();
                    } else if (id == R.id.nav_image_cropper) {
                        ImageCropperFragment imageCropperFragment = new ImageCropperFragment();
                        FragmentManager imFragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = imFragmentManager.beginTransaction();
                        fragmentTransaction1.replace(R.id.container, imageCropperFragment);
                        fragmentTransaction1.commit();
                    } else if (id == R.id.nav_scroll_tab) {
                        ScrollingTabFragment scrollingTabFragment = new ScrollingTabFragment();
                        FragmentManager scrollFragmentManager = getSupportFragmentManager();
                        FragmentTransaction scrollfragmentTransaction = scrollFragmentManager.beginTransaction();
                        scrollfragmentTransaction.replace(R.id.container, scrollingTabFragment);
                        scrollfragmentTransaction.commit();
                    }
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
            });


        }
        ButterKnife.bind(this);
        mContext = NavDrawerMainActivity.this;
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        txtTitle = (TextView) findViewById(R.id.txt_title);
        imgbtnSignOut = (ImageButton) findViewById(R.id.imgbtn_sign_out);
        txtTitle.setText(getResources().getString(R.string.app_name));
        imgbtnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(@NonNull Status status) {
                                    Utility.setSharedKeyBoolean(getString(R.string.isLoggedOut), true, mContext);
                                    Toast.makeText(mContext, getString(R.string.logged_out_msg), Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(NavDrawerMainActivity.this, SocialSignInActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                    finish();
                                }
                            });
                } /*else {
                    //fb
                    LoginManager.getInstance().logOut();
                }*/
            }
        });
        mainDBAdapter = new MainDBAdapter(this);
        mainDBAdapter.open();

        View header = navigationView.getHeaderView(0);
        TextView headername = (TextView) header.findViewById(R.id.txt_user_name);
        TextView headeremail = (TextView) header.findViewById(R.id.txt_user_email);
        ImageView headerprofilepic = (ImageView) header.findViewById(R.id.iv_profile_pic);
        Log.e("TAG", "Name " + mainDBAdapter.getSignInData().getMuserName());
        headername.setText(mainDBAdapter.getSignInData().getMuserName());
        headeremail.setText(mainDBAdapter.getSignInData().getmEmail());
      /*  Glide
                .with(this)
                .load(mainDBAdapter.getSignInData().getMprofilePic())
                .centerCrop()
                .crossFade()
                .into(headerprofilepic);*/
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void hideItem() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_gallery).setVisible(false);
        //  nav_Menu.findItem(R.id.nav_videothumbnail).setVisible(false);
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(mContext)
                .setMessage(getResources().getString(R.string.exit_dialog))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        NavDrawerMainActivity.this.finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false);
        super.onPrepareOptionsMenu(menu);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("FAIL", "Connection failed...");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

}
