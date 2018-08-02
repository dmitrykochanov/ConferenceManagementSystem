package com.dmko.conferencemanagementsystem.data.user;


import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.user.entities.BriefUser;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles;
import com.dmko.conferencemanagementsystem.data.user.entities.SignInForm;
import com.dmko.conferencemanagementsystem.data.user.entities.SignUpForm;
import com.dmko.conferencemanagementsystem.data.user.entities.User;
import com.dmko.conferencemanagementsystem.data.user.local.LocalUserSource;
import com.dmko.conferencemanagementsystem.data.user.remote.RemoteUserSource;

import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import timber.log.Timber;

import static com.dmko.conferencemanagementsystem.utils.LogTags.LOG_NET;

public class UserRepositoryImpl implements UserRepository {
    private LocalUserSource localUserSource;
    private RemoteUserSource remoteUserSource;

    public UserRepositoryImpl(LocalUserSource localUserSource, RemoteUserSource remoteUserSource) {
        this.localUserSource = localUserSource;
        this.remoteUserSource = remoteUserSource;
    }

    @Override
    public Completable signIn(SignInForm form) {
        return Completable.fromObservable(
                remoteUserSource.signIn(form)
                        .doOnNext(userWithToken -> {
                            localUserSource.saveUserWithToken(userWithToken);
                        }));
    }

    @Override
    public Completable signUp(SignUpForm form) {
        return Completable.fromObservable(
                remoteUserSource.signUp(form)
                        .doOnNext(userWithToken -> {
                            localUserSource.saveUserWithToken(userWithToken);
                        }));
    }

    @Override
    public boolean isSignedIn() {
        return localUserSource.getToken() != null;
    }

    @Override
    public void signOut() {
        localUserSource.deleteUserWithToken();
    }

    @Override
    public User getUser() {
        return localUserSource.getUser();
    }

    @Override
    public String getToken() {
        return localUserSource.getToken();
    }

    @Override
    public Observable<PagedList<BriefUser>> getReviewers(long conferenceId) {
        return remoteUserSource.getReviewers(conferenceId);
    }

    @Override
    public Completable addReviewerToConference(long conferenceId, List<Long> reviewers) {
        return remoteUserSource.addReviewerToConference(conferenceId, reviewers);
    }

    @Override
    public Completable addReviewerToConference(long conferenceId, long reviewerId) {
        return remoteUserSource.addReviewerToConference(conferenceId, Collections.singletonList(reviewerId));
    }

    @Override
    public Completable deleteReviewerFromConference(long conferenceId, List<Long> reviewers) {
        return remoteUserSource.deleteReviewerFromConference(conferenceId, reviewers);
    }

    @Override
    public Completable deleteReviewerFromConference(long conferenceId, long reviewerId) {
        return remoteUserSource.deleteReviewerFromConference(conferenceId, Collections.singletonList(reviewerId));
    }

    @Override
    public Observable<PagedList<BriefUserRoles>> getUsersWithRoles(long conferenceId) {
        return remoteUserSource.getUsersWithRoles(conferenceId);
    }

    @Override
    public Observable<PagedList<BriefUserRoles>> getUsersWithRoles(long conferenceId, Long role) {
        return remoteUserSource.getUsersWithRoles(conferenceId, role);
    }

    @Override
    public Observable<BriefUserRoles> getUserWithRoles(long userId, long conferenceId) {
        return remoteUserSource.getUserWithRoles(userId, conferenceId);
    }

    @Override
    public Completable updateRoles(long conferenceId, long userId, List<Long> roles) {
        return remoteUserSource.updateRoles(conferenceId, userId, roles);
    }

    @Override
    public Completable addReviewerToSubmission(long submissionId, List<Long> reviewers) {
        return remoteUserSource.addReviewerToSubmission(submissionId, reviewers);
    }

    @Override
    public Completable addReviewerToSubmission(long submissionId, long reviewerId) {
        return remoteUserSource.addReviewerToSubmission(submissionId, Collections.singletonList(reviewerId));
    }

    @Override
    public Completable deleteReviewerFromSubmission(long submissionId, List<Long> reviewers) {
        return remoteUserSource.deleteReviewerFromSubmission(submissionId, reviewers);
    }

    @Override
    public Completable deleteReviewerFromSubmission(long submissionId, long reviewerId) {
        return remoteUserSource.deleteReviewerFromSubmission(submissionId, Collections.singletonList(reviewerId));
    }
}
