package com.integratingdemo.image_cropper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.integratingdemo.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by Mansi on 9/15/2016.
 */

public class CropFragment extends Fragment {

    private CropImageView mCropImageView;
    ImageButton iv_rotate, iv_crop;
    public static final String PROFILE_PHOTO = "photo";
    Context mcontext;
    Activity activity;

    public static CropFragment newInstance() {
        return new CropFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_oval, container, false);

        mcontext = getActivity();
        mCropImageView = (CropImageView) v.findViewById(R.id.cropImageView);
        iv_rotate = (ImageButton) v.findViewById(R.id.iv_rotate);
        iv_crop = (ImageButton) v.findViewById(R.id.iv_crop);

        mCropImageView.setOnSetImageUriCompleteListener(new CropImageView.OnSetImageUriCompleteListener() {
            @Override
            public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
                if (error == null) {
                    Toast.makeText(getActivity(), R.string.image_load_success_msg, Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("AIC", getString(R.string.load_img_failure_msg), error);
                    Toast.makeText(getActivity(), getString(R.string.image_load_failed_msg) + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        mCropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
            @Override
            public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
                handleCropResult(result);
            }
        });

        iv_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropImageView.rotateImage(90);
            }
        });

        iv_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropImageView.getCroppedImageAsync();
            }
        });

        mCropImageView.setImageUriAsync(ImageCropperFragment.urinew);
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a = null;

        if (context instanceof Activity) {
            a = (Activity) context;
        }
        ImageCropperFragment.setCurrentFragment(this);
    }

    public void setImageUri(Uri imageUri) {
        mCropImageView.setImageUriAsync(imageUri);
        //        CropImage.activity(imageUri)
        //                .start(getContext(), this);
    }

    private void handleCropResult(CropImageView.CropResult result) {
        Bitmap bitmap;
        if (result.getError() == null) {
            ImageCropperFragment imageCropperFragment = new ImageCropperFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(getString(R.string.sample_size), result.getSampleSize());



          /*  Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra("SAMPLE_SIZE", result.getSampleSize());*/
            if (result.getUri() != null) {
              /*  intent.putExtra("URI", result.getUri());*/
                bundle.putString("URI", result.getUri().toString());
            } else {
                bitmap = mCropImageView.getCropShape() == CropImageView.CropShape.OVAL
                        ? CropImage.toOvalBitmap(result.getBitmap())
                        : result.getBitmap();
                /*MainActivity.circularImageView.setImageBitmap(bitmap);
                MainActivity.img_container.setVisibility(View.GONE);
*/
                ImageCropperFragment.profilePic.setImageBitmap(bitmap);
                ImageCropperFragment.img_container.setVisibility(View.GONE);

                //Convert image to string
                String str_bitmap = BitMapToString(bitmap);
                bundle.putString("URI_STRING", str_bitmap);
                //__________create two method setDefaults() and getDefaults()
                setDefaults(PROFILE_PHOTO, str_bitmap, getActivity());
                getDefaults(PROFILE_PHOTO, getActivity());
            }
            imageCropperFragment.setArguments(bundle);
            FragmentManager imFragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction1 = imFragmentManager.beginTransaction();
            fragmentTransaction1.replace(R.id.container, imageCropperFragment);
            fragmentTransaction1.commit();
            //   startActivity(intent);
        } else {
            Log.e("AIC", getString(R.string.crop_img_fail_msg), result.getError());
            Toast.makeText(getActivity(), getString(R.string.crop_image_failure_msg) + result.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String BitMapToString(Bitmap profile_image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        profile_image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] arr = baos.toByteArray();
        return Base64.encodeToString(arr, Base64.DEFAULT);
    }

    public static String getDefaults(String profilePhoto, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(CropFragment.PROFILE_PHOTO, null);
    }

    public void setDefaults(String profilePhoto, String str_bitmap, Context context) {
        SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = shre.edit();
        edit.putString(profilePhoto, str_bitmap);
        edit.apply();
    }

}
