package com.integratingdemo.strawberry_logger.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.skydoves.ElasticButton;
import com.integratingdemo.R;
import com.integratingdemo.common.Load_Fragment;
import com.integratingdemo.common.MainDBAdapter;
import com.integratingdemo.strawberry_logger.model.StrawberryData;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Data Entry Fragment common for all 5 strawberries
 */

public class DataEntryFragment extends Fragment {
    String position;
    boolean check = false;
    MainDBAdapter mainDBAdapter;
    Load_Fragment lf;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.s_id)
    TextView txt_s_id;
    @BindView(R.id.edt_s_id)
    AppCompatEditText edt_s_id;
    @BindView(R.id.s_weight)
    TextView txt_s_weight;
    @BindView(R.id.edt_s_weight)
    AppCompatEditText edt_s_weight;
    @BindView(R.id.s_sunlight)
    TextView txt_s_sunlight;
    @BindView(R.id.edt_s_sunlight)
    AppCompatEditText edt_s_sunlight;
    @BindView(R.id.s_compost)
    TextView txt_s_compost;
    @BindView(R.id.edt_s_compost)
    AppCompatEditText edt_s_compost;
    @BindView(R.id.s_water)
    TextView txt_s_water;
    @BindView(R.id.edt_s_water)
    AppCompatEditText edt_s_water;
    @BindView(R.id.btn_save_log)
    ElasticButton btn_save_log;
    @BindView(R.id.btn_show_log)
    ElasticButton btn_show_log;
    @BindView(R.id.btn_prev)
    ElasticButton btn_prev;
    @BindView(R.id.btn_next)
    ElasticButton btn_next;
    @BindView(R.id.btn_home)
    ElasticButton btn_home;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_data_entry, container, false);
        ButterKnife.bind(this, v);
        init();
        return v;

    }

    private void init() {
        mainDBAdapter = new MainDBAdapter(getActivity());
        try {
            position = getArguments().getString(getResources().getString(R.string.position));
            assert position != null;
            switch (position) {
                case "1":
                    txtTitle.setText(R.string.strawberry1);
                    break;
                case "2":
                    txtTitle.setText(R.string.strawberry2);
                    break;
                case "3":
                    txtTitle.setText(R.string.strawberry3);
                    break;
                case "4":
                    txtTitle.setText(R.string.strawberry4);
                    break;
                case "5":
                    txtTitle.setText(R.string.strawberry5);

                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP || keyCode == KeyEvent.KEYCODE_BACK) {
                    // exitApp();

                    getActivity().finish();

                }
                return false;
            }
        });
    }

    @OnClick({R.id.btn_save_log, R.id.btn_show_log, R.id.btn_prev, R.id.btn_next, R.id.btn_home})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save_log:
                if (MySubmitValidation()) {
                    Calendar c = Calendar.getInstance();
                    System.out.println("Current time => " + c.getTime());
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String formattedDate = df.format(c.getTime());
                    mainDBAdapter.open();
                    if (mainDBAdapter.RecordAlreadyExist_data(edt_s_id.getText().toString().trim())) {
                        Toast.makeText(getContext(), getResources().getString(R.string.msg_already_exist), Toast.LENGTH_SHORT).show();
                    } else {
                        mainDBAdapter.addStrawberryData(new StrawberryData(Integer.parseInt(edt_s_id.getText().toString().trim()), position + "_" + formattedDate,
                                Float.parseFloat(edt_s_weight.getText().toString().trim()),
                                Integer.parseInt(edt_s_sunlight.getText().toString().trim()),
                                Float.parseFloat(edt_s_compost.getText().toString().trim()), Float.parseFloat(edt_s_water.getText().toString().trim())));
                        Toast.makeText(getContext(), getResources().getString(R.string.success_msg), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_show_log:
                disableListner();
                Intent intent = new Intent(getActivity(), ShowLogActivity.class);
                intent.putExtra(getResources().getString(R.string.position), position);
                startActivity(intent);
            /*    Fragment fragment = new ShowLogActivity();
                Bundle bundle = new Bundle();
                bundle.putString(getResources().getString(R.string.position), position);
                fragment.setArguments(bundle);
                lf = new Load_Fragment(getActivity().getSupportFragmentManager());//loads fragment for entering data
                lf.initializeFragment(fragment);*/
                break;
            case R.id.btn_prev:
                try {
                    position = getArguments().getString(getResources().getString(R.string.position));
                    Bundle b;
                    Fragment fragmentPrev;
                    Load_Fragment lf;
                    switch (position) {
                        case "1":
                            if (TextUtils.isEmpty(edt_s_id.getText().toString()) &&
                                    TextUtils.isEmpty(edt_s_compost.getText().toString())
                                    && TextUtils.isEmpty(edt_s_water.getText().toString())
                                    && TextUtils.isEmpty(edt_s_weight.getText().toString())
                                    && TextUtils.isEmpty(edt_s_sunlight.getText().toString())) {
                                disableListner();
                                b = new Bundle();
                                b.putString(getResources().getString(R.string.position), "5");
                                fragmentPrev = new DataEntryFragment();
                                fragmentPrev.setArguments(b);
                                lf = new Load_Fragment(getActivity().getSupportFragmentManager());//loads fragment for entering data
                                lf.initializeFragment(fragmentPrev);
                            } else {
                                new AlertDialog.Builder(getActivity())
                                        .setMessage(getResources().getString(R.string.log_dialog))
                                        .setCancelable(false)
                                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setNegativeButton(getResources().getString(R.string.no), null)
                                        .show();
                            }
                            break;
                        case "2":
                            if (TextUtils.isEmpty(edt_s_id.getText().toString()) &&
                                    TextUtils.isEmpty(edt_s_compost.getText().toString())
                                    && TextUtils.isEmpty(edt_s_water.getText().toString())
                                    && TextUtils.isEmpty(edt_s_weight.getText().toString())
                                    && TextUtils.isEmpty(edt_s_sunlight.getText().toString())) {
                                disableListner();
                                b = new Bundle();
                                b.putString(getResources().getString(R.string.position), "1");
                                fragmentPrev = new DataEntryFragment();
                                fragmentPrev.setArguments(b);
                                lf = new Load_Fragment(getActivity().getSupportFragmentManager());//loads fragment for entering data
                                lf.initializeFragment(fragmentPrev);
                            } else {
                                new AlertDialog.Builder(getActivity())
                                        .setMessage(getResources().getString(R.string.log_dialog))
                                        .setCancelable(false)
                                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setNegativeButton(getResources().getString(R.string.no), null)
                                        .show();
                            }
                            break;
                        case "3":
                            if (TextUtils.isEmpty(edt_s_id.getText().toString()) &&
                                    TextUtils.isEmpty(edt_s_compost.getText().toString())
                                    && TextUtils.isEmpty(edt_s_water.getText().toString())
                                    && TextUtils.isEmpty(edt_s_weight.getText().toString())
                                    && TextUtils.isEmpty(edt_s_sunlight.getText().toString())) {
                                disableListner();
                                b = new Bundle();
                                b.putString(getResources().getString(R.string.position), "2");
                                fragmentPrev = new DataEntryFragment();
                                fragmentPrev.setArguments(b);
                                lf = new Load_Fragment(getActivity().getSupportFragmentManager());//loads fragment for entering data
                                lf.initializeFragment(fragmentPrev);
                            } else {
                                new AlertDialog.Builder(getActivity())
                                        .setMessage(getResources().getString(R.string.log_dialog))
                                        .setCancelable(false)
                                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setNegativeButton(getResources().getString(R.string.no), null)
                                        .show();
                            }
                            break;
                        case "4":
                            if (TextUtils.isEmpty(edt_s_id.getText().toString()) &&
                                    TextUtils.isEmpty(edt_s_compost.getText().toString())
                                    && TextUtils.isEmpty(edt_s_water.getText().toString())
                                    && TextUtils.isEmpty(edt_s_weight.getText().toString())
                                    && TextUtils.isEmpty(edt_s_sunlight.getText().toString())) {
                                disableListner();
                                b = new Bundle();
                                b.putString(getResources().getString(R.string.position), "3");
                                fragmentPrev = new DataEntryFragment();
                                fragmentPrev.setArguments(b);
                                lf = new Load_Fragment(getActivity().getSupportFragmentManager());//loads fragment for entering data
                                lf.initializeFragment(fragmentPrev);
                            } else {
                                new AlertDialog.Builder(getActivity())
                                        .setMessage(getResources().getString(R.string.log_dialog))
                                        .setCancelable(false)
                                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setNegativeButton(getResources().getString(R.string.no), null)
                                        .show();
                            }
                            break;
                        case "5":
                            if (TextUtils.isEmpty(edt_s_id.getText().toString()) &&
                                    TextUtils.isEmpty(edt_s_compost.getText().toString())
                                    && TextUtils.isEmpty(edt_s_water.getText().toString())
                                    && TextUtils.isEmpty(edt_s_weight.getText().toString())
                                    && TextUtils.isEmpty(edt_s_sunlight.getText().toString())) {
                                disableListner();
                                b = new Bundle();
                                b.putString(getResources().getString(R.string.position), "4");
                                fragmentPrev = new DataEntryFragment();
                                fragmentPrev.setArguments(b);
                                lf = new Load_Fragment(getActivity().getSupportFragmentManager());//loads fragment for entering data
                                lf.initializeFragment(fragmentPrev);
                            } else {
                                new AlertDialog.Builder(getActivity())
                                        .setMessage(getResources().getString(R.string.log_dialog))
                                        .setCancelable(false)
                                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setNegativeButton(getResources().getString(R.string.no), null)
                                        .show();
                            }
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_next:
                try {
                    position = getArguments().getString(getResources().getString(R.string.position));
                    Bundle b;
                    Fragment fragmentNext;

                    assert position != null;
                    switch (position) {
                        case "1":
                            if (TextUtils.isEmpty(edt_s_id.getText().toString()) &&
                                    TextUtils.isEmpty(edt_s_compost.getText().toString())
                                    && TextUtils.isEmpty(edt_s_water.getText().toString())
                                    && TextUtils.isEmpty(edt_s_weight.getText().toString())
                                    && TextUtils.isEmpty(edt_s_sunlight.getText().toString())) {

                                disableListner();
                                b = new Bundle();
                                b.putString(getResources().getString(R.string.position), "2");
                                fragmentNext = new DataEntryFragment();
                                fragmentNext.setArguments(b);
                                lf = new Load_Fragment(getActivity().getSupportFragmentManager());//loads fragment for entering data
                                lf.initializeFragment(fragmentNext);
                            } else {
                                new AlertDialog.Builder(getActivity())
                                        .setMessage(getResources().getString(R.string.log_dialog))
                                        .setCancelable(false)
                                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setNegativeButton(getResources().getString(R.string.no), null)
                                        .show();
                            }
                            break;
                        case "2":
                            if (TextUtils.isEmpty(edt_s_id.getText().toString()) &&
                                    TextUtils.isEmpty(edt_s_compost.getText().toString())
                                    && TextUtils.isEmpty(edt_s_water.getText().toString())
                                    && TextUtils.isEmpty(edt_s_weight.getText().toString())
                                    && TextUtils.isEmpty(edt_s_sunlight.getText().toString())) {

                                disableListner();
                                b = new Bundle();
                                b.putString(getResources().getString(R.string.position), "3");
                                fragmentNext = new DataEntryFragment();
                                fragmentNext.setArguments(b);
                                lf = new Load_Fragment(getActivity().getSupportFragmentManager());//loads fragment for entering data
                                lf.initializeFragment(fragmentNext);
                            } else {
                                new AlertDialog.Builder(getActivity())
                                        .setMessage(getResources().getString(R.string.log_dialog))
                                        .setCancelable(false)
                                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setNegativeButton(getResources().getString(R.string.no), null)
                                        .show();
                            }
                            break;
                        case "3":
                            if (TextUtils.isEmpty(edt_s_id.getText().toString()) &&
                                    TextUtils.isEmpty(edt_s_compost.getText().toString())
                                    && TextUtils.isEmpty(edt_s_water.getText().toString())
                                    && TextUtils.isEmpty(edt_s_weight.getText().toString())
                                    && TextUtils.isEmpty(edt_s_sunlight.getText().toString())) {

                                disableListner();
                                b = new Bundle();
                                b.putString(getResources().getString(R.string.position), "4");
                                fragmentNext = new DataEntryFragment();
                                fragmentNext.setArguments(b);
                                lf = new Load_Fragment(getActivity().getSupportFragmentManager());//loads fragment for entering data
                                lf.initializeFragment(fragmentNext);
                            } else {
                                new AlertDialog.Builder(getActivity())
                                        .setMessage(getResources().getString(R.string.log_dialog))
                                        .setCancelable(false)
                                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setNegativeButton(getResources().getString(R.string.no), null)
                                        .show();
                            }
                            break;
                        case "4":
                            if (TextUtils.isEmpty(edt_s_id.getText().toString()) &&
                                    TextUtils.isEmpty(edt_s_compost.getText().toString())
                                    && TextUtils.isEmpty(edt_s_water.getText().toString())
                                    && TextUtils.isEmpty(edt_s_weight.getText().toString())
                                    && TextUtils.isEmpty(edt_s_sunlight.getText().toString())) {

                                disableListner();
                                b = new Bundle();
                                b.putString(getResources().getString(R.string.position), "5");
                                fragmentNext = new DataEntryFragment();
                                fragmentNext.setArguments(b);
                                lf = new Load_Fragment(getActivity().getSupportFragmentManager());//loads fragment for entering data
                                lf.initializeFragment(fragmentNext);
                            } else {
                                new AlertDialog.Builder(getActivity())
                                        .setMessage(getResources().getString(R.string.log_dialog))
                                        .setCancelable(false)
                                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setNegativeButton(getResources().getString(R.string.no), null)
                                        .show();
                            }
                            break;
                        case "5":
                            if (TextUtils.isEmpty(edt_s_id.getText().toString()) &&
                                    TextUtils.isEmpty(edt_s_compost.getText().toString())
                                    && TextUtils.isEmpty(edt_s_water.getText().toString())
                                    && TextUtils.isEmpty(edt_s_weight.getText().toString())
                                    && TextUtils.isEmpty(edt_s_sunlight.getText().toString())) {

                                disableListner();
                                b = new Bundle();
                                b.putString(getResources().getString(R.string.position), "1");
                                fragmentNext = new DataEntryFragment();
                                fragmentNext.setArguments(b);
                                lf = new Load_Fragment(getActivity().getSupportFragmentManager());//loads fragment for entering data
                                lf.initializeFragment(fragmentNext);
                            } else {
                                new AlertDialog.Builder(getActivity())
                                        .setMessage(getResources().getString(R.string.log_dialog))
                                        .setCancelable(false)
                                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setNegativeButton(getResources().getString(R.string.no), null)
                                        .show();
                            }
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_home:
                disableListner();
               /* StrawberryHomeFragment strawberryHomeFragment = new StrawberryHomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, strawberryHomeFragment);
                fragmentTransaction.commit();*/
                getActivity().finish();
                break;
        }
    }

    public void disableListner() {
      /*  edt_s_id.setEnabled(false);
        edt_s_compost.setEnabled(false);
        edt_s_sunlight.setEnabled(false);
        edt_s_water.setEnabled(false);
        edt_s_weight.setEnabled(false);
        btn_save_log.setClickable(false);
        btn_show_log.setClickable(false);
        btn_prev.setClickable(false);
        btn_next.setClickable(false);
        btn_home.setClickable(false);*/
    }

    public boolean MySubmitValidation() {
        if (TextUtils.isEmpty(edt_s_id.getText().toString())) {
            Toast.makeText(getContext(), getResources().getString(R.string.error_empty_id), Toast.LENGTH_SHORT).show();
            check = false;
        } else {
            final int id = Integer.valueOf(edt_s_id.getText().toString());
            if (id < 1111 || id > 9999) {
                Toast.makeText(getContext(), getResources().getString(R.string.error_id), Toast.LENGTH_SHORT).show();
                check = false;
            } else {
                if (TextUtils.isEmpty(edt_s_weight.getText().toString())) {
                    Toast.makeText(getContext(), getResources().getString(R.string.error_empty_weight), Toast.LENGTH_SHORT).show();
                    check = false;
                } else {
                    final int weight = Integer.valueOf(edt_s_weight.getText().toString());
                    if (weight < 0 || weight > 10000) {
                        Toast.makeText(getContext(), getResources().getString(R.string.error_weight), Toast.LENGTH_SHORT).show();
                        check = false;
                    } else {
                        if (TextUtils.isEmpty(edt_s_sunlight.getText().toString())) {
                            Toast.makeText(getContext(), getResources().getString(R.string.error_empty_sunlight), Toast.LENGTH_SHORT).show();
                            check = false;
                        } else {
                            final int sunlight = Integer.valueOf(edt_s_sunlight.getText().toString());
                            if (sunlight < 0 || sunlight > 4) {
                                Toast.makeText(getContext(), getResources().getString(R.string.error_sunlight), Toast.LENGTH_SHORT).show();
                                check = false;
                            } else {
                                if (TextUtils.isEmpty(edt_s_compost.getText().toString())) {
                                    Toast.makeText(getContext(), getResources().getString(R.string.error_empty_compost), Toast.LENGTH_SHORT).show();
                                    check = false;
                                } else {
                                    final int compost = Integer.valueOf(edt_s_compost.getText().toString());
                                    if (compost < 0 || compost > 1000) {
                                        Toast.makeText(getContext(), getResources().getString(R.string.error_compost), Toast.LENGTH_SHORT).show();
                                        check = false;
                                    } else {
                                        if (TextUtils.isEmpty(edt_s_water.getText().toString())) {
                                            Toast.makeText(getContext(), getResources().getString(R.string.error_empty_water), Toast.LENGTH_SHORT).show();
                                            check = false;
                                        } else {
                                            final int water = Integer.valueOf(edt_s_water.getText().toString());
                                            if (water < 0 || water > 200) {
                                                Toast.makeText(getContext(), getResources().getString(R.string.error_water), Toast.LENGTH_SHORT).show();
                                                check = false;
                                            } else {
                                                check = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        return check;
    }
}
