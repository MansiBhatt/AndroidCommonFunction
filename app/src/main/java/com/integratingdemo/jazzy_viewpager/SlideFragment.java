package com.integratingdemo.jazzy_viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.integratingdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Janki on 24-01-2017.
 */
public class SlideFragment extends Fragment {
    public static final String EXTRA_POSITION = "EXTRA_POSITION";
    @BindView(R.id.txtSearch)
    TextView textView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.activity_slide_page_1, container, false);
        ButterKnife.bind(this, rootView);
        int position = getArguments().getInt(EXTRA_POSITION);

        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

        if (position == 0) {
            int color = ContextCompat.getColor(getActivity(), R.color.light_blue_900);
            textView.setText(getResources().getString(R.string.WELCOME)); //edit the view text
            rootView.setBackgroundColor(color);
        } else if (position == 1) {
            int color = ContextCompat.getColor(getActivity(), R.color.green_700);
            textView.setText(getResources().getString(R.string.ABOUT)); //edit the view text
            rootView.setBackgroundColor(color);
        } else if (position == 2) {
            int color = ContextCompat.getColor(getActivity(), R.color.deep_purple_900);
            textView.setText(getResources().getString(R.string.Guide)); //edit the view text
            rootView.setBackgroundColor(color);
        }


        return rootView; //return the slide view
    }
}
