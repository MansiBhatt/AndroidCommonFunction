package com.integratingdemo.realm_demo.model;

import io.realm.RealmObject;

/**
 * Created by Janki on 09-01-2017.
 */

public class Email extends RealmObject {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
