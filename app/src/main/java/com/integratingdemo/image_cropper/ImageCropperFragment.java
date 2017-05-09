package com.integratingdemo.image_cropper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.skydoves.ElasticButton;
import com.integratingdemo.R;
import com.integratingdemo.common.Utility;
import com.integratingdemo.main_dashboard.BottomMainActivity;
import com.integratingdemo.main_dashboard.NavDrawerMainActivity;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Janki on 20-02-2017.
 */

public class ImageCropperFragment extends Fragment {

    public static String tag = "ImageCropperFragment";

    @BindView(R.id.txtHeading)
    TextView txtHeading;
    // @BindView(R.id.profilePic)
    public static CircularImageView profilePic;
    @BindView(R.id.btnUploadImg)
    ElasticButton btnUploadImg;
    Context mContext;


    public static CropFragment mCurrentFragment;
    //  public static FrameLayout container;
    // @BindView(R.id.img_container)
    public static FrameLayout img_container;
    private int CAMERA_PERMISSION_CODE = 23, READ_STORAGE_CODE = 24, WRITE_STORAGE_CODE = 25;

    public static Uri mCropImageUri, urinew;
    String str;
    public static final int ALL_REQUEST_CODE = 26;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_cropper, container, false);
        mContext = getActivity();
        try {
            NavDrawerMainActivity.txtTitle.setText(getResources().getString(R.string.image_crop));
        } catch (Exception e) {
            BottomMainActivity.txtTitle.setText(getResources().getString(R.string.image_crop));
        }
        profilePic = (CircularImageView) view.findViewById(R.id.profilePic);
        img_container = (FrameLayout) view.findViewById(R.id.img_container);
        ButterKnife.bind(this, view);

        try {
            str = getArguments().getString("URI_STRING");
            profilePic.setImageBitmap(Utility.StringToBitMap(str));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @OnClick(R.id.btnUploadImg)
    public void onClick() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // Do something for lollipop and above versions

            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        ALL_REQUEST_CODE);
            } else {
                CropImage.startPickImageActivity(getActivity());
            }
        } else {
            CropImage.startPickImageActivity(getActivity());
        }

        return;

    }

    public static void setCurrentFragment(CropFragment fragment) {
        mCurrentFragment = fragment;
    }

    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //   super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(mContext, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage,
            // but we don't know if we need to for the URI so the simplest is to try open the stream and see if we get error.
            boolean requirePermissions = false;
            if (CropImage.isReadExternalStoragePermissionsRequired(mContext, imageUri)) {


                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                //profile_image = BitmapFactory.decodeFile(imageUri.getPath());
                img_container.setVisibility(View.VISIBLE);

                urinew = imageUri;

                CropFragment imageCropperFragment = new CropFragment();
                FragmentManager imFragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction1 = imFragmentManager.beginTransaction();
                fragmentTransaction1.replace(R.id.container, imageCropperFragment);
                fragmentTransaction1.commit();

                //  CropFragment.newInstance().setImageUri(imageUri);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ALL_REQUEST_CODE:
                List<Integer> permissionResults = new ArrayList<>();
                for (int grantResult : grantResults) {
                    permissionResults.add(grantResult);
                }
                if (permissionResults.contains(PackageManager.PERMISSION_DENIED)) {
                    CropImage.startPickImageActivity(getActivity());
                } else {
                    Toast.makeText(getActivity(), "Location Permissions not granted", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
