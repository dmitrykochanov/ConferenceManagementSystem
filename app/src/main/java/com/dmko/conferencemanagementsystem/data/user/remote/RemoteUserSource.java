package com.dmko.conferencemanagementsystem.data.user.remote;


import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.user.entities.BriefUser;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles;
import com.dmko.conferencemanagementsystem.data.user.entities.SignInForm;
import com.dmko.conferencemanagementsystem.data.user.entities.SignUpForm;
import com.dmko.conferencemanagementsystem.data.user.entities.UserWithToken;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface RemoteUserSource {

    Observable<UserWithToken> signIn(SignInForm form);

    Observable<UserWithToken> signUp(SignUpForm form);

    Observable<PagedList<BriefUser>> getReviewers(long conferenceId);

    Completable addReviewerToConference(long conferenceId, List<Long> reviewers);

    Completable deleteReviewerFromConference(long conferenceId, List<Long> reviewers);

    Observable<PagedList<BriefUserRoles>> getUsersWithRoles(long conferenceId);

    Observable<PagedList<BriefUserRoles>> getUsersWithRoles(long conferenceId, Long role);

    Observable<BriefUserRoles> getUserWithRoles(long userId, long conferenceId);

    Completable updateRoles(long conferenceId, long userId, List<Long> roles);

    Completable addReviewerToSubmission(long submissionId, List<Long> reviewers);

    Completable deleteReviewerFromSubmission(long submissionId, List<Long> reviewers);
}
