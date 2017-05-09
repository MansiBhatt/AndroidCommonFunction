package com.integratingdemo.upload_to_drive;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.skydoves.ElasticButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.integratingdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Janki on 31-01-2017.
 */

public class UploadResourceActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    @BindView(R.id.txtHeading)
    TextView txtHeading;
    @BindView(R.id.btnBrowse)
    ElasticButton btnBrowse;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.btnUploadImg)
    ElasticButton btnUploadImg;
    @BindView(R.id.btnUploadAudio)
    ElasticButton btnUploadAudio;
    @BindView(R.id.btnUploadVideo)
    ElasticButton btnUploadVideo;
    private static final int PICK_IMAGE_REQUEST = 1330;
    GoogleApiClient mGoogleApiClient;
    private static final String TAG = UploadResourceActivity.class.getSimpleName();
    private static final int REQUEST_CODE_RESOLUTION = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multipart_upload);
        ButterKnife.bind(this);
        if (mGoogleApiClient == null) {
            // Create the API client and bind it to an instance variable.
            // We use this instance as the callback for connection and connection
            // failures.
            // Since no account name is passed, the user is prompted to choose.
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Drive.API)
                    .addScope(Drive.SCOPE_FILE)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
    }

    @OnClick({R.id.btnBrowse, R.id.btnUploadImg, R.id.btnUploadAudio, R.id.btnUploadVideo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBrowse:
                browseGallery();
                break;
            case R.id.btnUploadImg:
                break;
            case R.id.btnUploadAudio:
                break;
            case R.id.btnUploadVideo:
                break;
        }
    }

    private void browseGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        saveFileToDrive();
    }

    @SuppressLint("Assert")
    private void saveFileToDrive() {

        DriveFile file = null;
        assert false;
        file.open(mGoogleApiClient, DriveFile.MODE_WRITE_ONLY, null).setResultCallback(new ResultCallback<DriveApi.DriveContentsResult>() {
            @Override
            public void onResult(DriveApi.DriveContentsResult result) {
                if (!result.getStatus().isSuccess()) {
                    // Handle error
                    return;
                }
                DriveContents driveContents = result.getDriveContents();
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
            } catch (IntentSender.SendIntentException e) {
                // Unable to resolve, message user appropriately
            }
        } else {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {

            case REQUEST_CODE_RESOLUTION:
                if (resultCode == RESULT_OK) {
                    mGoogleApiClient.connect();
                }
                break;
        }
    }
}
