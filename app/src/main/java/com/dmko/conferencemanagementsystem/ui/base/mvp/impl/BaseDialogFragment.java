package com.dmko.conferencemanagementsystem.ui.base.mvp.impl;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.dmko.conferencemanagementsystem.injection.controller.ControllerComponent;
import com.dmko.conferencemanagementsystem.injection.controller.ControllerModule;
import com.dmko.conferencemanagementsystem.injection.controller.PresenterModule;
import com.dmko.conferencemanagementsystem.ui.App;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BaseView;
import com.dmko.conferencemanagementsystem.ui.screens.errordialog.UnknownErrorDialog;

public class BaseDialogFragment extends DialogFragment implements BaseView {
    protected ControllerComponent getControllerComponent() {
        return ((App) getActivity().getApplication())
                .getApplicationComponent()
                .newControllerComponent(new PresenterModule(), new ControllerModule());
    }

    @Override
    public void showUnknownErrorDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        UnknownErrorDialog dialog = new UnknownErrorDialog();
        dialog.show(ft, "dialog");
    }
}
