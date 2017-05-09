package com.integratingdemo.realm_demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.integratingdemo.R;
import com.integratingdemo.realm_demo.model.Person;

import java.util.ArrayList;

/**
 * Created by Janki on 03-02-2017.
 * List view Adapter to store  realm data in listview
 */

public class ListViewDetailsAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Person> personArrayList;

    public ListViewDetailsAdapter(Context mContext, ArrayList<Person> personArrayList) {
        this.mContext = mContext;
        this.personArrayList = personArrayList;
        LayoutInflater inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return personArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return personArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;
        TextView txt_name, txt_email, txt_age;
        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_row_realmdb, parent, false);
        } else {
            result = convertView;
        }

        txt_name = (TextView) result.findViewById(R.id.txt_name);
        txt_email = (TextView) result.findViewById(R.id.txt_email);
        txt_age = (TextView) result.findViewById(R.id.txt_age);
        txt_name.setText(personArrayList.get(position).getName());
        txt_email.setText(personArrayList.get(position).getEmail().getAddress());
        txt_age.setText(Integer.toString(personArrayList.get(position).getAge()));
        return result;
    }
}
