package com.integratingdemo.main_dashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
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
import com.integratingdemo.strawberry_logger.StrawberryHomeFragment;
import com.integratingdemo.vector_drawable.VectorDrawableFragment;
import com.integratingdemo.video_gallery.VideoGalleryFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Janki on 20-01-2017.
 */

public class BottomMainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    BottomSheet sheet;
    BottomNavigationView bottomNavigationView;
    Context mContext;
    public static TextView txtTitle;
    ImageButton imgbtnSignOut;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_bottom_bar);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        mContext = BottomMainActivity.this;
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        imgbtnSignOut = (ImageButton) findViewById(R.id.imgbtn_sign_out);
        Utility.setSharedKeyBoolean(getString(R.string.isLoggedOut), true, mContext);
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {

            // Do something for lollipop and above versions
            bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Handle navigation view item clicks here.
                    Log.e("TAG", " ABOVE ");
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

                    } else if (id == R.id.nav_more) {

                        sheet = new BottomSheet.Builder(mContext, R.style.BottomSheet_Dialog)
                                .title("Select from the Menu")
                                .grid() // <-- important part
                                .sheet(R.menu.activity_main_drawer)
                                .listener(new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO
                                        switch (which) {
                                            case R.id.nav_map_cluster:
                                                // Handle map clustering
                                                MapFrag mapFragment = new MapFrag();
                                                FragmentManager mapFragmentManager = getSupportFragmentManager();
                                                FragmentTransaction mapfragmentTransaction = mapFragmentManager.beginTransaction();
                                                mapfragmentTransaction.replace(R.id.container, mapFragment);
                                                mapfragmentTransaction.commit();
                                                break;
                                            case R.id.nav_gallery:
                                                //Handle Vector Drawable
                                                try {
                                                    VectorDrawableFragment vectorDrawableFragment = new VectorDrawableFragment();
                                                    FragmentManager vectorfragmentManager = getSupportFragmentManager();
                                                    FragmentTransaction vectorfragmentTransaction = vectorfragmentManager.beginTransaction();
                                                    vectorfragmentTransaction.replace(R.id.container, vectorDrawableFragment);
                                                    vectorfragmentTransaction.commit();
                                                } catch (Exception e) {
                                                    Toast.makeText(mContext, getString(R.string.feature_not_supported), Toast.LENGTH_SHORT).show();
                                                }
                                                break;
                                            case R.id.nav_multi_lang:
                                                MultiLangFrag multiLangFrag = new MultiLangFrag();
                                                FragmentManager multiLangfragmentManager = getSupportFragmentManager();
                                                FragmentTransaction multiLangfragmentTransaction = multiLangfragmentManager.beginTransaction();
                                                multiLangfragmentTransaction.replace(R.id.container, multiLangFrag);
                                                multiLangfragmentTransaction.commit();
                                                break;
                                            case R.id.nav_db:
                                                RealmMainFragment realmMainFragment = new RealmMainFragment();
                                                FragmentManager realmfragmentManager = getSupportFragmentManager();
                                                FragmentTransaction realmfragmentTransaction = realmfragmentManager.beginTransaction();
                                                realmfragmentTransaction.replace(R.id.container, realmMainFragment);
                                                realmfragmentTransaction.commit();
                                                break;
                                            case R.id.nav_videothumbnail:
                                                VideoGalleryFragment videoGalleryFragment = new VideoGalleryFragment();
                                                FragmentManager videoGalleryfragmentManager = getSupportFragmentManager();
                                                FragmentTransaction videoGalleryfragmentTransaction = videoGalleryfragmentManager.beginTransaction();
                                                videoGalleryfragmentTransaction.replace(R.id.container, videoGalleryFragment);
                                                videoGalleryfragmentTransaction.commit();
                                                break;
                                            case R.id.nav_placepicker:
                                                PlacePickerFragment placePickerFragment = new PlacePickerFragment();
                                                FragmentManager placepickerfragmentManager = getSupportFragmentManager();
                                                FragmentTransaction placepickerfragmentTransaction = placepickerfragmentManager.beginTransaction();
                                                placepickerfragmentTransaction.replace(R.id.container, placePickerFragment);
                                                placepickerfragmentTransaction.commit();
                                                break;
                                            case R.id.nav_upload:
                                                Intent i = new Intent(BottomMainActivity.this, UploadActivity.class);
                                                startActivity(i);
                                                break;
                                            case R.id.nav_collection:
                                                CollectionTypeFrag collectionTypeFrag = new CollectionTypeFrag();
                                                FragmentManager collectionTypefragmentManager = getSupportFragmentManager();
                                                FragmentTransaction collectionTypefragmentTransaction = collectionTypefragmentManager.beginTransaction();
                                                collectionTypefragmentTransaction.replace(R.id.container, collectionTypeFrag);
                                                collectionTypefragmentTransaction.commit();
                                                break;
                                            case R.id.nav_data_logger:
                                                StrawberryHomeFragment strawberryHomeFragment = new StrawberryHomeFragment();
                                                FragmentManager strawberryHomefragmentManager = getSupportFragmentManager();
                                                FragmentTransaction strawberryHomefragmentTransaction = strawberryHomefragmentManager.beginTransaction();
                                                strawberryHomefragmentTransaction.replace(R.id.container, strawberryHomeFragment);
                                                strawberryHomefragmentTransaction.commit();
                                                break;
                                            case R.id.nav_ad:
                                                AdMobMainFragment adMobMainFragment = new AdMobMainFragment();
                                                FragmentManager adMobfragmentManager = getSupportFragmentManager();
                                                FragmentTransaction adMobfragmentTransaction = adMobfragmentManager.beginTransaction();
                                                adMobfragmentTransaction.replace(R.id.container, adMobMainFragment);
                                                adMobfragmentTransaction.commit();
                                                break;
                                            case R.id.nav_alrm_srvice:
                                                AlaramServiceFragment alaramServiceFragment = new AlaramServiceFragment();
                                                FragmentManager fragmentManager = getSupportFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                fragmentTransaction.replace(R.id.container, alaramServiceFragment);
                                                fragmentTransaction.commit();
                                                break;
                                            case R.id.nav_payu_srvice:
                                                PayUFragment payUFragment = new PayUFragment();
                                                FragmentManager payUfragmentManager = getSupportFragmentManager();
                                                FragmentTransaction payUfragmentTransaction = payUfragmentManager.beginTransaction();
                                                payUfragmentTransaction.replace(R.id.container, payUFragment);
                                                payUfragmentTransaction.commit();
                                                break;
                                            case R.id.nav_image_cropper:
                                                ImageCropperFragment imageCropperFragment = new ImageCropperFragment();
                                                FragmentManager imFragmentManager = getSupportFragmentManager();
                                                FragmentTransaction fragmentTransaction1 = imFragmentManager.beginTransaction();
                                                fragmentTransaction1.replace(R.id.container, imageCropperFragment);
                                                fragmentTransaction1.commit();
                                                break;
                                            case R.id.nav_scroll_tab:
                                                ScrollingTabFragment scrollingTabFragment = new ScrollingTabFragment();
                                                FragmentManager scrollFragmentManager = getSupportFragmentManager();
                                                FragmentTransaction scrollfragmentTransaction = scrollFragmentManager.beginTransaction();
                                                scrollfragmentTransaction.replace(R.id.container, scrollingTabFragment);
                                                scrollfragmentTransaction.commit();
                                                break;
                                            case R.id.nav_chart:
                                                ChartFragment chartFrag = new ChartFragment();
                                                FragmentManager chartfragmentManager = getSupportFragmentManager();
                                                FragmentTransaction chartfragmentTransaction = chartfragmentManager.beginTransaction();
                                                chartfragmentTransaction.replace(R.id.container, chartFrag);
                                                chartfragmentTransaction.commit();
                                                break;
                                        }
                                    }
                                }).show();
                    }
                    return true;
                }
            });
        } else {
            // do something for phones running an SDK before lollipop
            Log.e("TAG", " BELOW ");
            bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
            bottomNavigationView.getMenu().clear(); //clear old inflated items.
            bottomNavigationView.inflateMenu(R.menu.bottom_menu_below_lollipop);

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                        try {
                            VideoGalleryFragment videoGalleryFragment = new VideoGalleryFragment();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container, videoGalleryFragment);
                            fragmentTransaction.commit();
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    } else if (id == R.id.nav_more) {

                        sheet = new BottomSheet.Builder(mContext, R.style.BottomSheet_Grid)
                                .title("Select from the Menu")
                                .grid() // <-- important part
                                .sheet(R.menu.activity_main_drawer)
                                .listener(new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO
                                        switch (which) {
                                            case R.id.nav_map_cluster:
                                                // Handle map clustering
                                                MapFrag mapFragment = new MapFrag();
                                                FragmentManager mapFragmentManager = getSupportFragmentManager();
                                                FragmentTransaction mapfragmentTransaction = mapFragmentManager.beginTransaction();
                                                mapfragmentTransaction.replace(R.id.container, mapFragment);
                                                mapfragmentTransaction.commit();
                                                break;
                                            case R.id.nav_gallery:
                                                //Handle Vector Drawable
                                                try {
                                                    VectorDrawableFragment vectorDrawableFragment = new VectorDrawableFragment();
                                                    FragmentManager vectorfragmentManager = getSupportFragmentManager();
                                                    FragmentTransaction vectorfragmentTransaction = vectorfragmentManager.beginTransaction();
                                                    vectorfragmentTransaction.replace(R.id.container, vectorDrawableFragment);
                                                    vectorfragmentTransaction.commit();
                                                } catch (Exception e) {
                                                    Toast.makeText(mContext, getString(R.string.feature_not_supported), Toast.LENGTH_SHORT).show();
                                                }
                                                break;
                                            case R.id.nav_multi_lang:
                                                MultiLangFrag multiLangFrag = new MultiLangFrag();
                                                FragmentManager multiLangfragmentManager = getSupportFragmentManager();
                                                FragmentTransaction multiLangfragmentTransaction = multiLangfragmentManager.beginTransaction();
                                                multiLangfragmentTransaction.replace(R.id.container, multiLangFrag);
                                                multiLangfragmentTransaction.commit();
                                                break;
                                            case R.id.nav_db:
                                                RealmMainFragment realmMainFragment = new RealmMainFragment();
                                                FragmentManager realmfragmentManager = getSupportFragmentManager();
                                                FragmentTransaction realmfragmentTransaction = realmfragmentManager.beginTransaction();
                                                realmfragmentTransaction.replace(R.id.container, realmMainFragment);
                                                realmfragmentTransaction.commit();
                                                break;
                                            case R.id.nav_videothumbnail:
                                                VideoGalleryFragment videoGalleryFragment = new VideoGalleryFragment();
                                                FragmentManager videoGalleryfragmentManager = getSupportFragmentManager();
                                                FragmentTransaction videoGalleryfragmentTransaction = videoGalleryfragmentManager.beginTransaction();
                                                videoGalleryfragmentTransaction.replace(R.id.container, videoGalleryFragment);
                                                videoGalleryfragmentTransaction.commit();
                                                break;
                                            case R.id.nav_placepicker:
                                                PlacePickerFragment placePickerFragment = new PlacePickerFragment();
                                                FragmentManager placepickerfragmentManager = getSupportFragmentManager();
                                                FragmentTransaction placepickerfragmentTransaction = placepickerfragmentManager.beginTransaction();
                                                placepickerfragmentTransaction.replace(R.id.container, placePickerFragment);
                                                placepickerfragmentTransaction.commit();
                                                break;
                                            case R.id.nav_upload:
                                                Intent i = new Intent(BottomMainActivity.this, UploadActivity.class);
                                                startActivity(i);
                                                break;
                                            case R.id.nav_collection:
                                                CollectionTypeFrag collectionTypeFrag = new CollectionTypeFrag();
                                                FragmentManager collectionTypefragmentManager = getSupportFragmentManager();
                                                FragmentTransaction collectionTypefragmentTransaction = collectionTypefragmentManager.beginTransaction();
                                                collectionTypefragmentTransaction.replace(R.id.container, collectionTypeFrag);
                                                collectionTypefragmentTransaction.commit();
                                                break;
                                            case R.id.nav_data_logger:
                                                StrawberryHomeFragment strawberryHomeFragment = new StrawberryHomeFragment();
                                                FragmentManager strawberryHomefragmentManager = getSupportFragmentManager();
                                                FragmentTransaction strawberryHomefragmentTransaction = strawberryHomefragmentManager.beginTransaction();
                                                strawberryHomefragmentTransaction.replace(R.id.container, strawberryHomeFragment);
                                                strawberryHomefragmentTransaction.commit();
                                                break;
                                            case R.id.nav_ad:
                                                AdMobMainFragment adMobMainFragment = new AdMobMainFragment();
                                                FragmentManager adMobfragmentManager = getSupportFragmentManager();
                                                FragmentTransaction adMobfragmentTransaction = adMobfragmentManager.beginTransaction();
                                                adMobfragmentTransaction.replace(R.id.container, adMobMainFragment);
                                                adMobfragmentTransaction.commit();
                                                break;
                                            case R.id.nav_alrm_srvice:
                                                AlaramServiceFragment alaramServiceFragment = new AlaramServiceFragment();
                                                FragmentManager fragmentManager = getSupportFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                fragmentTransaction.replace(R.id.container, alaramServiceFragment);
                                                fragmentTransaction.commit();
                                                break;
                                            case R.id.nav_payu_srvice:
                                                PayUFragment payUFragment = new PayUFragment();
                                                FragmentManager payUfragmentManager = getSupportFragmentManager();
                                                FragmentTransaction payUfragmentTransaction = payUfragmentManager.beginTransaction();
                                                payUfragmentTransaction.replace(R.id.container, payUFragment);
                                                payUfragmentTransaction.commit();
                                                break;
                                            case R.id.nav_image_cropper:
                                                ImageCropperFragment imageCropperFragment = new ImageCropperFragment();
                                                FragmentManager imFragmentManager = getSupportFragmentManager();
                                                FragmentTransaction fragmentTransaction1 = imFragmentManager.beginTransaction();
                                                fragmentTransaction1.replace(R.id.container, imageCropperFragment);
                                                fragmentTransaction1.commit();
                                                break;
                                            case R.id.nav_scroll_tab:
                                                ScrollingTabFragment scrollingTabFragment = new ScrollingTabFragment();
                                                FragmentManager scrollFragmentManager = getSupportFragmentManager();
                                                FragmentTransaction scrollfragmentTransaction = scrollFragmentManager.beginTransaction();
                                                scrollfragmentTransaction.replace(R.id.container, scrollingTabFragment);
                                                scrollfragmentTransaction.commit();
                                                break;
                                            case R.id.nav_chart:
                                                ChartFragment chartFrag = new ChartFragment();
                                                FragmentManager chartfragmentManager = getSupportFragmentManager();
                                                FragmentTransaction chartfragmentTransaction = chartfragmentManager.beginTransaction();
                                                chartfragmentTransaction.replace(R.id.container, chartFrag);
                                                chartfragmentTransaction.commit();
                                                break;
                                        }
                                    }
                                }).show();
                    }
                    return true;
                }
            });
        }


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
                                    Toast.makeText(mContext, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(BottomMainActivity.this, SocialSignInActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                    finish();
                                }
                            });
                }
            }
        });
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
    public void onBackPressed() {

        new AlertDialog.Builder(mContext)
                .setMessage(getResources().getString(R.string.exit_dialog))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        BottomMainActivity.this.finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), null)
                .show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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

