package com.dmko.conferencemanagementsystem.ui.screens.rolepicker;

import com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BasePresenter;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BaseView;

public interface RolePickerContract {

    interface View extends BaseView {

        void setUser(BriefUserRoles user);

        void showLoading(boolean isLoading);

        void cancelDialog();
    }

    interface Presenter extends BasePresenter<View> {

        void loadUser(long conferenceId, long userId);

        void addRole(Long role);

        void deleteRole(Long role);

        void saveRoles();
    }
}
