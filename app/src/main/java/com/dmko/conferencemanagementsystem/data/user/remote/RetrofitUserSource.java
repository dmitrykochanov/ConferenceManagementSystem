package com.dmko.conferencemanagementsystem.data.user.remote;


import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.paging.PagingHelper;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUser;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles;
import com.dmko.conferencemanagementsystem.data.user.entities.SignInForm;
import com.dmko.conferencemanagementsystem.data.user.entities.SignUpForm;
import com.dmko.conferencemanagementsystem.data.user.entities.UserWithToken;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class RetrofitUserSource implements RemoteUserSource {

    private UserApi userApi;
    private PagingHelper pagingHelper;

    public RetrofitUserSource(UserApi userApi, PagingHelper pagingHelper) {
        this.userApi = userApi;
        this.pagingHelper = pagingHelper;
    }

    @Override
    public Observable<UserWithToken> signIn(SignInForm form) {
        return userApi.signIn(form);
    }

    @Override
    public Observable<UserWithToken> signUp(SignUpForm form) {
        return userApi.signUp(form);
    }

    @Override
    public Observable<PagedList<BriefUser>> getReviewers(long conferenceId) {
        return pagingHelper.createPagedList(page -> userApi.getReviewers(conferenceId, page).blockingFirst());
    }

    @Override
    public Completable addReviewerToConference(long conferenceId, List<Long> reviewers) {
        return userApi.addReviewerToConference(conferenceId, reviewers);
    }

    @Override
    public Completable deleteReviewerFromConference(long conferenceId, List<Long> reviewers) {
        return userApi.deleteReviewerFromConference(conferenceId, reviewers);
    }

    @Override
    public Observable<PagedList<BriefUserRoles>> getUsersWithRoles(long conferenceId) {
        return pagingHelper.createPagedList(page -> userApi.getUsersWithRoles(conferenceId, null, page).blockingFirst());
    }

    @Override
    public Observable<PagedList<BriefUserRoles>> getUsersWithRoles(long conferenceId, Long role) {
        return pagingHelper.createPagedList(page -> userApi.getUsersWithRoles(conferenceId, role, page).blockingFirst());
    }

    @Override
    public Observable<BriefUserRoles> getUserWithRoles(long userId, long conferenceId) {
        return userApi.getUserWithRoles(userId, conferenceId);
    }

    @Override
    public Completable updateRoles(long conferenceId, long userId, List<Long> roles) {
        return userApi.updateRoles(conferenceId, userId, roles);
    }

    @Override
    public Completable addReviewerToSubmission(long submissionId, List<Long> reviewers) {
        return userApi.addReviewerToSubmission(submissionId, reviewers);
    }

    @Override
    public Completable deleteReviewerFromSubmission(long submissionId, List<Long> reviewers) {
        return userApi.deleteReviewerFromSubmission(submissionId, reviewers);
    }
}
