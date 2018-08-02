package com.dmko.conferencemanagementsystem.data.user;


import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.user.entities.BriefUser;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles;
import com.dmko.conferencemanagementsystem.data.user.entities.SignInForm;
import com.dmko.conferencemanagementsystem.data.user.entities.SignUpForm;
import com.dmko.conferencemanagementsystem.data.user.entities.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface UserRepository {

    Completable signIn(SignInForm form);

    Completable signUp(SignUpForm form);

    boolean isSignedIn();

    void signOut();

    User getUser();

    String getToken();

    Observable<PagedList<BriefUser>> getReviewers(long conferenceId);

    Completable addReviewerToConference(long conferenceId, List<Long> reviewers);

    Completable addReviewerToConference(long conferenceId, long reviewerId);

    Completable deleteReviewerFromConference(long conferenceId, List<Long> reviewers);

    Completable deleteReviewerFromConference(long conferenceId, long reviewerId);

    Observable<PagedList<BriefUserRoles>> getUsersWithRoles(long conferenceId);

    Observable<PagedList<BriefUserRoles>> getUsersWithRoles(long conferenceId, Long role);

    Observable<BriefUserRoles> getUserWithRoles(long userId, long conferenceId);

    Completable updateRoles(long conferenceId, long userId, List<Long> roles);

    Completable addReviewerToSubmission(long submissionId, List<Long> reviewers);

    Completable addReviewerToSubmission(long submissionId, long reviewerId);

    Completable deleteReviewerFromSubmission(long submissionId, List<Long> reviewers);

    Completable deleteReviewerFromSubmission(long submissionId, long reviewerId);
}
