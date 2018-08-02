package com.dmko.conferencemanagementsystem.data.submissions;

import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.submissions.entities.BriefSubmission;
import com.dmko.conferencemanagementsystem.data.submissions.entities.Submission;
import com.dmko.conferencemanagementsystem.data.submissions.remote.RemoteSubmissionsSource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class SubmissionsRepositoryImpl implements SubmissionsRepository {

    private RemoteSubmissionsSource remoteSubmissionsSource;

    public SubmissionsRepositoryImpl(RemoteSubmissionsSource remoteSubmissionsSource) {
        this.remoteSubmissionsSource = remoteSubmissionsSource;
    }

    @Override
    public Observable<PagedList<BriefSubmission>> getSubmissions(long conferenceId) {
        return remoteSubmissionsSource.getSubmissions(conferenceId);
    }

    @Override
    public Observable<Submission> getSubmission(long submissionId) {
        return remoteSubmissionsSource.getSubmission(submissionId);
    }

    @Override
    public Observable<PagedList<BriefSubmission>> getUserSubmissions(long conferenceId, long userId) {
        return remoteSubmissionsSource.getUserSubmissions(conferenceId, userId);
    }

    @Override
    public Observable<PagedList<BriefSubmission>> getReviewerSubmissions(long conferenceId, long userId) {
        return remoteSubmissionsSource.getReviewerSubmissions(conferenceId, userId);
    }

    @Override
    public Completable createSubmission(Submission submission) {
        return remoteSubmissionsSource.createSubmission(submission);
    }

    @Override
    public Completable setSubmissionReviewable(long submissionId) {
        return remoteSubmissionsSource.setSubmissionReviewable(submissionId);
    }
}
