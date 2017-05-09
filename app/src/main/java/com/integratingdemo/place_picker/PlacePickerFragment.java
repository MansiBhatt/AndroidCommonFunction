package com.integratingdemo.place_picker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.integratingdemo.R;
import com.integratingdemo.common.Utility;
import com.integratingdemo.main_dashboard.BottomMainActivity;
import com.integratingdemo.main_dashboard.NavDrawerMainActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Janki on 30-01-2017.
 * Current location and place picker
 */

public class PlacePickerFragment extends Fragment {
    int PLACE_PICKER_REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            NavDrawerMainActivity.txtTitle.setText(getResources().getString(R.string.place_picker));
        } catch (Exception e) {
            BottomMainActivity.txtTitle.setText(getResources().getString(R.string.place_picker));
        }
        if (Utility.isNetworkAvailable(getActivity())) {
            if (Utility.isLocationEnabled(getActivity())) {
                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                return super.onCreateView(inflater, container, savedInstanceState);
            } else {
                Toast.makeText(getActivity(), getString(R.string.enable_location), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getActivity(), data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

}
