package com.integratingdemo.realm_demo.model;

import io.realm.RealmObject;

/**
 * Created by Janki on 05-01-2017.
 * To store data from model class to
 */

public class Person extends RealmObject {

    private String name;
    private int age;
    private Email email;

    public Person() {
    }

    public Email getEmail() {
        return email;
    }

    public Person(String name, int age, Email email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
