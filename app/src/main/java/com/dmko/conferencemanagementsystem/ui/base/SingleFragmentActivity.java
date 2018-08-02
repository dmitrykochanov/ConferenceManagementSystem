package com.dmko.conferencemanagementsystem.ui.base;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BaseActivity;

public abstract class SingleFragmentActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.layout_fragment_container);
        if (fragment == null) {
            fm.beginTransaction()
                    .add(R.id.layout_fragment_container, newFragment())
                    .commit();
        }
    }

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.acitivity_single_fragment;
    }

    protected abstract Fragment newFragment();

}
