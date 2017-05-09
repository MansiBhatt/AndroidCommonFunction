package com.integratingdemo.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


/**
 * This is common file used to load fragment
 */
public class Load_Fragment {
    private FragmentManager mfragmentManager;

    public Load_Fragment(FragmentManager mfragmentManager2) {
        this.mfragmentManager = mfragmentManager2;
    }

    public void initializeFragment(Fragment resultFragment) {
        FragmentTransaction fragmentTransaction = mfragmentManager
                .beginTransaction();
        fragmentTransaction.add(android.R.id.content, resultFragment);
        fragmentTransaction.commit();

    }

}
