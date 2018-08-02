package com.dmko.conferencemanagementsystem.ui.screens.signup;


import android.support.v4.app.Fragment;

import com.dmko.conferencemanagementsystem.ui.base.SingleFragmentActivity;

public class SignUpActivity extends SingleFragmentActivity {
    @Override
    protected Fragment newFragment() {
        return new SignUpFragment();
    }
}
