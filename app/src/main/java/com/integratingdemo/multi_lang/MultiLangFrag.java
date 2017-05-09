package com.integratingdemo.multi_lang;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.integratingdemo.R;
import com.integratingdemo.common.LocaleHelper;
import com.integratingdemo.main_dashboard.BottomMainActivity;
import com.integratingdemo.main_dashboard.NavDrawerMainActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Janki on 20-01-2017.
 */

public class MultiLangFrag extends Fragment {
    Context mcontext;
    @BindView(R.id.txtlang)
    TextView txtlang;
    @BindView(R.id.txtSearch)
    TextView txtSearch;
    @BindView(R.id.txtOn)
    TextView txtOn;
    @BindView(R.id.txtOff)
    TextView txtOff;
    @BindView(R.id.txtFooter)
    TextView txtFooter;
    Locale locale;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_multi_lang, container, false);
        mcontext = getActivity();
        setHasOptionsMenu(true);
        try {
            NavDrawerMainActivity.txtTitle.setText(getResources().getString(R.string.multi_language));
        } catch (Exception e) {
            BottomMainActivity.txtTitle.setText(getResources().getString(R.string.multi_language));
        }
        init();
        ButterKnife.bind(this, v);
        return v;

    }

    private void init() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Configuration config = getActivity().getBaseContext().getResources().getConfiguration();

        String lang = settings.getString("LANG", "");
        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {
            locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
        }

    }

    public void setLangRecreate(String langval) {
        mcontext = LocaleHelper.setLocale(getActivity(), langval);
        txtSearch.setText(getResources().getString(R.string.search));
        txtlang.setText(getResources().getString(R.string.LOG_WAP_TC));
        txtOn.setText(getResources().getString(R.string.on));
        txtOff.setText(getResources().getString(R.string.off));
        txtFooter.setText(getResources().getString(R.string.PS_alReadyTicker));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.eng:
                setLangRecreate("en");
                return true;
            case R.id.guj:
                setLangRecreate("gu");
                return true;
            case R.id.urdu:
                setLangRecreate("ur");
                return true;
            case R.id.hin:
                setLangRecreate("hi");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
