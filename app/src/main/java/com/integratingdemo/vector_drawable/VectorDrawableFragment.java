package com.integratingdemo.vector_drawable;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.integratingdemo.R;
import com.integratingdemo.main_dashboard.BottomMainActivity;
import com.integratingdemo.main_dashboard.NavDrawerMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Janki on 20-01-2017.
 */

public class VectorDrawableFragment extends Fragment {

    AnimatedVectorDrawable avstar, avlogo;
    @BindView(R.id.vclogo)
    ImageView vclogo;
    @BindView(R.id.star)
    ImageView star;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            View v = inflater.inflate(R.layout.fragment_vector_drawable, container, false);
            try {
                NavDrawerMainActivity.txtTitle.setText(getResources().getString(R.string.vector_drawable));
            } catch (Exception e) {
                BottomMainActivity.txtTitle.setText(getResources().getString(R.string.vector_drawable));
            }
            init(v);
            ButterKnife.bind(this, v);
            return v;
        } catch (Exception e) {

            Toast.makeText(getActivity(), getString(R.string.feature_not_supported), Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    private void init(View v) {
        try {
            avstar = (AnimatedVectorDrawable) v.getResources().getDrawable(R.drawable.animated_star);
            avlogo = (AnimatedVectorDrawable) v.getResources().getDrawable(R.drawable.animated_logo);
        } catch (Exception e) {
            Toast.makeText(getActivity(), getString(R.string.feature_not_supported), Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void morphlogo() {
        vclogo.setImageDrawable(avlogo);
        avlogo.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void morphstar() {
        star.setImageDrawable(avstar);
        avstar.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.vclogo, R.id.star})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vclogo:
                morphlogo();
                break;
            case R.id.star:
                morphstar();
                break;
        }
    }

}
