package com.dmko.conferencemanagementsystem.ui.base.mvp.impl;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.dmko.conferencemanagementsystem.injection.controller.ControllerComponent;
import com.dmko.conferencemanagementsystem.injection.controller.ControllerModule;
import com.dmko.conferencemanagementsystem.injection.controller.PresenterModule;
import com.dmko.conferencemanagementsystem.ui.App;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BaseView;
import com.dmko.conferencemanagementsystem.ui.screens.errordialog.UnknownErrorDialog;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    protected ControllerComponent getControllerComponent() {
        return ((App) getApplication())
                .getApplicationComponent()
                .newControllerComponent(new PresenterModule(), new ControllerModule());
    }

    @Override
    public void showUnknownErrorDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        UnknownErrorDialog dialog = new UnknownErrorDialog();
        dialog.show(ft, "dialog");
    }
}
