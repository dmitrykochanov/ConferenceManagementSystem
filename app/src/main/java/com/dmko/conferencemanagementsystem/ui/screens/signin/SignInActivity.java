package com.dmko.conferencemanagementsystem.ui.screens.signin;


import android.support.v4.app.Fragment;

import com.dmko.conferencemanagementsystem.ui.base.SingleFragmentActivity;

public class SignInActivity extends SingleFragmentActivity {
    @Override
    protected Fragment newFragment() {
        return new SingInFragment();
    }
}
