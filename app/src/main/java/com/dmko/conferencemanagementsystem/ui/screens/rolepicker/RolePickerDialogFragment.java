package com.dmko.conferencemanagementsystem.ui.screens.rolepicker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.dmko.conferencemanagementsystem.R;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles;
import com.dmko.conferencemanagementsystem.ui.base.mvp.impl.BaseDialogFragment;
import com.dmko.conferencemanagementsystem.utils.OnDismissListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timber.log.Timber;

import static com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles.Roles.ADMIN;
import static com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles.Roles.REVIEWER;

public class RolePickerDialogFragment extends BaseDialogFragment implements RolePickerContract.View {

    private static final String ARG_CONFERENCE_ID = "conference_id";
    private static final String ARG_USER_ID = "user_id";

    @BindView(R.id.check_admin) CheckBox checkAdmin;
    @BindView(R.id.check_reviewer) CheckBox checkReviewer;

    @Inject RolePickerContract.Presenter presenter;

    private Unbinder unbinder;
    private OnDismissListener onDismissListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getControllerComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_role_picker, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.attachView(this);

        if (getArguments() == null) {
            throw new IllegalArgumentException("arguments = null");
        }
        long conferenceId = getArguments().getLong(ARG_CONFERENCE_ID);
        long userId = getArguments().getLong(ARG_USER_ID);

        checkReviewer.setOnCheckedChangeListener((b, isChecked) -> {
            if (isChecked) {
                presenter.addRole(REVIEWER);
            } else {
                presenter.deleteRole(REVIEWER);
            }
        });

        checkAdmin.setOnCheckedChangeListener((b, isChecked) -> {
            if (isChecked) {
                presenter.addRole(ADMIN);
            } else {
                presenter.deleteRole(ADMIN);
            }
        });

        presenter.loadUser(conferenceId, userId);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        unbinder.unbind();
    }

    @OnClick(R.id.button_save)
    public void onSaveButtonClicked() {
        presenter.saveRoles();
    }

    @OnClick(R.id.button_cancel)
    public void onCancelButtonClicked() {
        getDialog().cancel();
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public void setOnDialogClosedListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void setUser(BriefUserRoles user) {
        checkAdmin.setChecked(user.getRoles().contains(ADMIN));
        checkReviewer.setChecked(user.getRoles().contains(REVIEWER));
    }

    @Override
    public void showLoading(boolean isLoading) {
        checkAdmin.setEnabled(!isLoading);
        checkReviewer.setEnabled(!isLoading);
    }

    @Override
    public void cancelDialog() {
        getDialog().cancel();
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public static RolePickerDialogFragment newInstance(long conferenceId, long userId) {
        Timber.d("Creating role picker dialog for conferenceId = %d, userId = %d", conferenceId, userId);
        Bundle args = new Bundle();
        args.putLong(ARG_CONFERENCE_ID, conferenceId);
        args.putLong(ARG_USER_ID, userId);
        RolePickerDialogFragment fragment = new RolePickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
