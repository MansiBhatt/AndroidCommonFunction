package com.integratingdemo.realm_demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.integratingdemo.R;
import com.integratingdemo.common.Utility;
import com.integratingdemo.main_dashboard.BottomMainActivity;
import com.integratingdemo.main_dashboard.NavDrawerMainActivity;
import com.integratingdemo.realm_demo.adapter.ListViewDetailsAdapter;
import com.integratingdemo.realm_demo.model.Email;
import com.integratingdemo.realm_demo.model.Person;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Janki on 20-01-2017.
 */

public class RealmMainFragment extends Fragment {
    private static final String TAG = RealmMainFragment.class.getSimpleName();
    boolean check = false;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_age)
    EditText edtAge;
    @BindView(R.id.edt_address)
    EditText edtAddress;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_sort)
    Button btnSort;
    @BindView(R.id.btn_del)
    Button btnDel;

    @BindView(R.id.db_detail_listview)
    ListView dbDetailListview;
    @BindView(R.id.txt_datalist)
    TextView txtDatalist;
    @BindView(R.id.label_name)
    TextView labelName;
    @BindView(R.id.label_email)
    TextView labelEmail;
    @BindView(R.id.label_age)
    TextView labelAge;
    @BindView(R.id.ll_label)
    LinearLayout llLabel;
    private Realm realm;
    ListViewDetailsAdapter adapter;
    ArrayList<Person> personArrayList;
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_realm, container, false);
        mContext = getActivity();
        try {
            NavDrawerMainActivity.txtTitle.setText(getResources().getString(R.string.realm_db));
        } catch (Exception e) {
            BottomMainActivity.txtTitle.setText(getResources().getString(R.string.realm_db));
        }
        personArrayList = new ArrayList<>();
        init();
        ButterKnife.bind(this, v);
        return v;

    }

    private void init() {
        realm = Realm.getDefaultInstance();//Obtain Realm instance
    }

    public boolean MySubmitValidation() {
        if (TextUtils.isEmpty(edtName.getText().toString())) {
            Toast.makeText(getContext(), getResources().getString(R.string.error_name), Toast.LENGTH_SHORT).show();
            check = false;
        } else {
            if (TextUtils.isEmpty(edtAge.getText().toString())) {
                Toast.makeText(getContext(), getResources().getString(R.string.error_age), Toast.LENGTH_SHORT).show();
                check = false;
            } else {
                if (TextUtils.isEmpty(edtAddress.getText().toString())) {
                    Toast.makeText(getContext(), getResources().getString(R.string.error_email), Toast.LENGTH_SHORT).show();
                    check = false;
                } else if (!Utility.isValidEmail(edtAddress.getText().toString())) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
                    check = false;
                } else {
                    check = true;
                }
            }
        }

        return check;
    }

    public void save_into_db(final String name, final int age, final String address) {
        //There are many options to do this but the easiest one is using asynchronous transactions blocks
        //executeTransactionAsync is used to perform CRUD operations
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Email email = bgRealm.createObject(Email.class);
                email.setAddress(address);
                Person person = bgRealm.createObject(Person.class);
                person.setName(name);
                person.setAge(age);
                person.setEmail(email);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                refresh_views();
                // Transaction was a success.
                Toast.makeText(getActivity(), "Saved Successfully..!!", Toast.LENGTH_SHORT).show();
                Log.v(TAG, ">>>>>>> Stored successfully...<<<<<<");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.v(TAG, error.getMessage());
            }
        });
    }

    public void refresh_views() {
        // Build the query looking at all Persons
        //RealmResults are used to perform basic queries like count,equalto etc
        RealmResults<Person> personRealmResults = realm.where(Person.class).findAll();//findall is a link query operation
        String output = "";
        personArrayList = new ArrayList<>();

        for (Person person : personRealmResults) {
            output += person.toString();
            personArrayList.add(new Person(person.getName(), person.getAge(), person.getEmail()));

        }

        adapter = new ListViewDetailsAdapter(mContext, personArrayList);
        dbDetailListview.setAdapter(adapter);
        setListViewHeightBasedOnChildren(dbDetailListview);
        llLabel.setVisibility(View.VISIBLE);
        txtDatalist.setVisibility(View.VISIBLE);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @OnClick({R.id.btn_save, R.id.btn_sort, R.id.btn_del})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                try {
                    if (MySubmitValidation()) {
                        save_into_db(edtName.getText().toString().trim(), Integer.parseInt(edtAge.getText().toString().trim()), edtAddress.getText().toString().trim());
                        //    refresh_views();
                        Log.i(TAG, "Realm Path.." + realm.getPath());//to obtain the absolute path of a Realm
                        edittextNull();
                    }
                } catch (Exception e) {
                    e.getMessage();
                }

                break;
            case R.id.btn_sort:
                RealmResults<Person> result = realm.where(Person.class).findAll();
                result = result.sort("name"); // Sort ascending
                String output = "";
                personArrayList = new ArrayList<>();

                for (Person person : result) {
                    output += person.toString();
                    personArrayList.add(new Person(person.getName(), person.getAge(), person.getEmail()));
                }
                adapter = new ListViewDetailsAdapter(mContext, personArrayList);
                dbDetailListview.setAdapter(adapter);
                setListViewHeightBasedOnChildren(dbDetailListview);
                llLabel.setVisibility(View.VISIBLE);
                txtDatalist.setVisibility(View.VISIBLE);
                edittextNull();
                break;
            case R.id.btn_del:
                // obtain the results of a query
                final RealmResults<Person> results = realm.where(Person.class).findAll();
                final RealmResults<Email> resultemail = realm.where(Email.class).findAll();
                // All changes to data must happen in a transaction
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                     /*   //remove single object
                        Person person = results.get(0);
                        person.deleteFromRealm();*/
                        // Delete all matches
                        results.deleteAllFromRealm();
                        resultemail.deleteAllFromRealm();
                        personArrayList.clear();
                        adapter.notifyDataSetChanged();
                        txtDatalist.setVisibility(View.GONE);
                        llLabel.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Data Successfully deleted", Toast.LENGTH_SHORT).show();
                        edittextNull();
                    }
                });
                break;
        }
    }

    private void edittextNull() {
        edtName.setText(" ");
        edtAddress.setText(" ");
        edtAge.setText(" ");
        getView().clearFocus();
        Utility.hideKeyboard(getActivity());
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
