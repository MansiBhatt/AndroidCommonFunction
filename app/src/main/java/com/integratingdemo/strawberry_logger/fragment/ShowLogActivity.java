package com.integratingdemo.strawberry_logger.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import com.github.skydoves.ElasticButton;
import com.integratingdemo.R;
import com.integratingdemo.common.Load_Fragment;
import com.integratingdemo.common.MainDBAdapter;
import com.integratingdemo.strawberry_logger.adapter.ShowLogAdapter;
import com.integratingdemo.strawberry_logger.model.StrawberryData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment for "Show Log"
 */

public class ShowLogActivity extends AppCompatActivity {

    MainDBAdapter mainDBAdapter;
    ShowLogAdapter adapter;
    ArrayList<StrawberryData> list, listnew;
    String position;

    Context mContext;
    @BindView(R.id.lst_showEntry)
    ListView lst_show_log;
    @BindView(R.id.btn_return)
    ElasticButton btn_return;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_log);
        ButterKnife.bind(this);
        mContext = ShowLogActivity.this;
        hideKeyboard(mContext);
        mainDBAdapter = new MainDBAdapter(mContext);
        init();
    }

    public static void hideKeyboard(Context mContext) {
        try {
            // Check if no view has focus:
            View view = ((Activity) mContext).getCurrentFocus();
            if (view != null) {
                InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void init() {
        mainDBAdapter.open();
        Bundle bundle = getIntent().getExtras();
        position = bundle.getString(getResources().getString(R.string.position));
        listnew = new ArrayList<>();
        list = mainDBAdapter.getAllStrawberryData();
        for (int i = 0; i < list.size(); i++) {
            String[] strawberryType = list.get(i).getM_date().split("_");
            if (position.equalsIgnoreCase(strawberryType[0])) {
                listnew.add(list.get(i));
            }
        }

        adapter = new ShowLogAdapter(mContext, listnew, position);
        lst_show_log.setAdapter(adapter);

        if (position.equalsIgnoreCase("1")) {
            btn_return.setText(R.string.btn_return);
        } else if (position.equalsIgnoreCase("2")) {
            btn_return.setText(R.string.btn_return_2);
        } else if (position.equalsIgnoreCase("3")) {
            btn_return.setText(R.string.btn_return_3);
        } else if (position.equalsIgnoreCase("4")) {
            btn_return.setText(R.string.btn_return_4);
        } else if (position.equalsIgnoreCase("5")) {
            btn_return.setText(R.string.btn_return_5);
        }

    }


    @OnClick(R.id.btn_return)
    public void onClick() {
        try {
            Bundle b = null;
            Fragment fragment = null;
            Load_Fragment lf;
            switch (position) {
                case "1":
                    b = new Bundle();
                    b.putString(getResources().getString(R.string.position), "1");
                    fragment = new DataEntryFragment();
                    fragment.setArguments(b);
                    lf = new Load_Fragment(getSupportFragmentManager());//loads fragment for entering data
                    lf.initializeFragment(fragment);
                    break;
                case "2":
                    b = new Bundle();
                    b.putString(getResources().getString(R.string.position), "2");
                    fragment = new DataEntryFragment();
                    fragment.setArguments(b);
                    lf = new Load_Fragment(getSupportFragmentManager());//loads fragment for entering data
                    lf.initializeFragment(fragment);
                    break;
                case "3":
                    b = new Bundle();
                    b.putString(getResources().getString(R.string.position), "3");
                    fragment = new DataEntryFragment();
                    fragment.setArguments(b);
                    lf = new Load_Fragment(getSupportFragmentManager());//loads fragment for entering data
                    lf.initializeFragment(fragment);
                    break;
                case "4":
                    b = new Bundle();
                    b.putString(getResources().getString(R.string.position), "4");
                    fragment = new DataEntryFragment();
                    fragment.setArguments(b);
                    lf = new Load_Fragment(getSupportFragmentManager());//loads fragment for entering data
                    lf.initializeFragment(fragment);
                    break;
                case "5":
                    b = new Bundle();
                    b.putString(getResources().getString(R.string.position), "5");
                    fragment = new DataEntryFragment();
                    fragment.setArguments(b);
                    lf = new Load_Fragment(getSupportFragmentManager());//loads fragment for entering data
                    lf.initializeFragment(fragment);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
