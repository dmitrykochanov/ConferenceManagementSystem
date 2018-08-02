package com.dmko.conferencemanagementsystem.injection.controller;


import com.dmko.conferencemanagementsystem.injection.scopes.ControllerScope;
import com.dmko.conferencemanagementsystem.ui.screens.addeditconferencerequest.AddEditConferenceRequestFragment;
import com.dmko.conferencemanagementsystem.ui.screens.conferencecomment.ConferenceCommentDialogFragment;
import com.dmko.conferencemanagementsystem.ui.screens.conferenceinformation.ConferenceInformationFragment;
import com.dmko.conferencemanagementsystem.ui.screens.conferenceparticipants.ConferenceParticipantsFragment;
import com.dmko.conferencemanagementsystem.ui.screens.conferencerequests.ConferenceRequestsFragment;
import com.dmko.conferencemanagementsystem.ui.screens.conferences.ConferencesFragment;
import com.dmko.conferencemanagementsystem.ui.screens.conferencesubmissions.ConferenceSubmissionsFragment;
import com.dmko.conferencemanagementsystem.ui.screens.main.MainActivity;
import com.dmko.conferencemanagementsystem.ui.screens.navigation.NavigationFragment;
import com.dmko.conferencemanagementsystem.ui.screens.addeditreview.AddEditReviewFragment;
import com.dmko.conferencemanagementsystem.ui.screens.reviewerpicker.ReviewerPickerDialogFragment;
import com.dmko.conferencemanagementsystem.ui.screens.rolepicker.RolePickerDialogFragment;
import com.dmko.conferencemanagementsystem.ui.screens.signin.SingInFragment;
import com.dmko.conferencemanagementsystem.ui.screens.signup.SignUpFragment;
import com.dmko.conferencemanagementsystem.ui.screens.submission.SubmissionFragment;

import dagger.Subcomponent;

@ControllerScope
@Subcomponent(modules = {ControllerModule.class, PresenterModule.class})
public interface ControllerComponent {

    void inject(SingInFragment singInFragment);

    void inject(SignUpFragment signUpFragment);

    void inject(MainActivity mainActivity);

    void inject(ConferencesFragment conferencesFragment);

    void inject(NavigationFragment navigationFragment);

    void inject(ConferenceInformationFragment conferenceInformationFragment);

    void inject(ConferenceParticipantsFragment conferenceParticipantsFragment);

    void inject(ConferenceSubmissionsFragment conferenceSubmissionsFragment);

    void inject(SubmissionFragment submissionFragment);

    void inject(AddEditReviewFragment addEditReviewFragment);

    void inject(ReviewerPickerDialogFragment reviewerPickerDialogFragment);

    void inject(RolePickerDialogFragment rolePickerDialogFragment);

    void inject(ConferenceRequestsFragment conferenceRequestsFragment);

    void inject(AddEditConferenceRequestFragment addEditConferenceRequestFragment);

    void inject(ConferenceCommentDialogFragment conferenceCommentDialogFragment);
}
