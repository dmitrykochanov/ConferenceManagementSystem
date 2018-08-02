package com.dmko.conferencemanagementsystem.injection.controller;


import com.dmko.conferencemanagementsystem.data.conferences.ConferencesRepository;
import com.dmko.conferencemanagementsystem.data.requests.RequestsRepository;
import com.dmko.conferencemanagementsystem.data.reviews.ReviewsRepository;
import com.dmko.conferencemanagementsystem.data.submissions.SubmissionsRepository;
import com.dmko.conferencemanagementsystem.data.user.UserRepository;
import com.dmko.conferencemanagementsystem.injection.scopes.ControllerScope;
import com.dmko.conferencemanagementsystem.ui.screens.addeditconferencerequest.AddEditConferenceRequestPresenter;
import com.dmko.conferencemanagementsystem.ui.screens.addeditconferencerequest.AddEditConferenceRequestContract;
import com.dmko.conferencemanagementsystem.ui.screens.addeditreview.AddEditReviewContract;
import com.dmko.conferencemanagementsystem.ui.screens.conferencecomment.ConferenceCommentContract;
import com.dmko.conferencemanagementsystem.ui.screens.conferencecomment.ConferenceCommentPresenter;
import com.dmko.conferencemanagementsystem.ui.screens.conferenceinformation.ConferenceInformationContract;
import com.dmko.conferencemanagementsystem.ui.screens.conferenceinformation.ConferenceInformationPresenter;
import com.dmko.conferencemanagementsystem.ui.screens.conferenceparticipants.ConferenceParticipantsContract;
import com.dmko.conferencemanagementsystem.ui.screens.conferenceparticipants.ConferenceParticipantsPresenter;
import com.dmko.conferencemanagementsystem.ui.screens.conferencerequests.ConferenceRequestsContract;
import com.dmko.conferencemanagementsystem.ui.screens.conferencerequests.ConferenceRequestsPresenter;
import com.dmko.conferencemanagementsystem.ui.screens.conferences.ConferencesContract;
import com.dmko.conferencemanagementsystem.ui.screens.conferences.ConferencesPresenter;
import com.dmko.conferencemanagementsystem.ui.screens.conferencesubmissions.ConferenceSubmissionsContract;
import com.dmko.conferencemanagementsystem.ui.screens.conferencesubmissions.ConferenceSubmissionsPresenter;
import com.dmko.conferencemanagementsystem.ui.screens.main.MainContract;
import com.dmko.conferencemanagementsystem.ui.screens.main.MainPresenter;
import com.dmko.conferencemanagementsystem.ui.screens.navigation.NavigationContract;
import com.dmko.conferencemanagementsystem.ui.screens.navigation.NavigationPresenter;
import com.dmko.conferencemanagementsystem.ui.screens.addeditreview.AddEditReviewPresenter;
import com.dmko.conferencemanagementsystem.ui.screens.reviewerpicker.ReviewerPickerContract;
import com.dmko.conferencemanagementsystem.ui.screens.reviewerpicker.ReviewerPickerPresenter;
import com.dmko.conferencemanagementsystem.ui.screens.rolepicker.RolePickerContract;
import com.dmko.conferencemanagementsystem.ui.screens.rolepicker.RolePickerPresenter;
import com.dmko.conferencemanagementsystem.ui.screens.signin.SignInContract;
import com.dmko.conferencemanagementsystem.ui.screens.signin.SignInPresenter;
import com.dmko.conferencemanagementsystem.ui.screens.signup.SignUpContract;
import com.dmko.conferencemanagementsystem.ui.screens.signup.SignUpPresenter;
import com.dmko.conferencemanagementsystem.ui.screens.submission.SubmissionContract;
import com.dmko.conferencemanagementsystem.ui.screens.submission.SubmissionPresenter;
import com.dmko.conferencemanagementsystem.utils.SchedulersFacade;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    @ControllerScope
    public SignInContract.Presenter provideLoginPresenter(SchedulersFacade schedulers, UserRepository userRepository) {
        return new SignInPresenter(schedulers, userRepository);
    }

    @Provides
    @ControllerScope
    public SignUpContract.Presenter provideSignupPresenter(SchedulersFacade schedulers, UserRepository userRepository) {
        return new SignUpPresenter(schedulers, userRepository);
    }

    @Provides
    @ControllerScope
    public MainContract.Presenter provideMainPresenter() {
        return new MainPresenter();
    }

    @Provides
    @ControllerScope
    public ConferencesContract.Presenter provideConferencesPresenter(SchedulersFacade schedulers, ConferencesRepository conferencesRepository) {
        return new ConferencesPresenter(schedulers, conferencesRepository);
    }

    @Provides
    @ControllerScope
    public NavigationContract.Presenter provideNavigationPresenter(SchedulersFacade schedulers, UserRepository userRepository, ConferencesRepository conferencesRepository) {
        return new NavigationPresenter(schedulers, userRepository, conferencesRepository);
    }

    @Provides
    @ControllerScope
    public ConferenceInformationContract.Presenter provideConferenceInformationPresenter(SchedulersFacade schedulers, ConferencesRepository conferencesRepository) {
        return new ConferenceInformationPresenter(schedulers, conferencesRepository);
    }

    @Provides
    @ControllerScope
    public ConferenceParticipantsContract.Presenter provideConferenceParticipantsPresenter(SchedulersFacade schedulers, UserRepository userRepository) {
        return new ConferenceParticipantsPresenter(schedulers, userRepository);
    }

    @Provides
    @ControllerScope
    public ConferenceSubmissionsContract.Presenter provideConferenceSubmissionsPresenter(SchedulersFacade schedulers, UserRepository userRepository, SubmissionsRepository submissionsRepository) {
        return new ConferenceSubmissionsPresenter(schedulers, userRepository, submissionsRepository);
    }

    @Provides
    @ControllerScope
    public SubmissionContract.Presenter provideSubmissionPresenter(SchedulersFacade schedulers, UserRepository userRepository, SubmissionsRepository submissionsRepository, ReviewsRepository reviewsRepository) {
        return new SubmissionPresenter(schedulers, userRepository, submissionsRepository, reviewsRepository);
    }

    @Provides
    @ControllerScope
    public AddEditReviewContract.Presenter provideReviewPresenter(SchedulersFacade schedulers, UserRepository userRepository, ReviewsRepository reviewsRepository) {
        return new AddEditReviewPresenter(schedulers, userRepository, reviewsRepository);
    }

    @Provides
    @ControllerScope
    public ReviewerPickerContract.Presenter provideReviewerPickerPresenter(SchedulersFacade schedulers, UserRepository userRepository, SubmissionsRepository submissionsRepository) {
        return new ReviewerPickerPresenter(schedulers, userRepository, submissionsRepository);
    }

    @Provides
    @ControllerScope
    public RolePickerContract.Presenter provideRolePickerPresenter(SchedulersFacade schedulers, UserRepository userRepository) {
        return new RolePickerPresenter(schedulers, userRepository);
    }

    @Provides
    @ControllerScope
    public ConferenceRequestsContract.Presenter provideConferenceRequestsPresenter(SchedulersFacade schedulers, RequestsRepository requestsRepository) {
        return new ConferenceRequestsPresenter(schedulers, requestsRepository);
    }

    @Provides
    @ControllerScope
    public AddEditConferenceRequestContract.Presenter provideAddEditConferenceRequestPresenter(SchedulersFacade schedulers, UserRepository userRepository, RequestsRepository requestsRepository) {
        return new AddEditConferenceRequestPresenter(schedulers, userRepository, requestsRepository);
    }

    @Provides
    @ControllerScope
    public ConferenceCommentContract.Presenter provideConferenceCommentPresenter(SchedulersFacade schedulers, RequestsRepository requestsRepository) {
        return new ConferenceCommentPresenter(schedulers, requestsRepository);
    }
}
