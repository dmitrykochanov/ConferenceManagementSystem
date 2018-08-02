package com.dmko.conferencemanagementsystem.data.submissions.remote;

import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.paging.PagingHelper;
import com.dmko.conferencemanagementsystem.data.submissions.entities.BriefSubmission;
import com.dmko.conferencemanagementsystem.data.submissions.entities.Submission;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class RetrofitSubmissionsSource implements RemoteSubmissionsSource {

    private SubmissionsApi submissionsApi;
    private PagingHelper pagingHelper;

    public RetrofitSubmissionsSource(SubmissionsApi submissionsApi, PagingHelper pagingHelper) {
        this.submissionsApi = submissionsApi;
        this.pagingHelper = pagingHelper;
    }

    @Override
    public Observable<PagedList<BriefSubmission>> getSubmissions(long conferenceId) {
        return pagingHelper.createPagedList(page -> submissionsApi.getSubmissions(conferenceId, page).blockingFirst());
    }

    @Override
    public Observable<Submission> getSubmission(long submissionId) {
        return submissionsApi.getSubmission(submissionId);
    }

    @Override
    public Observable<PagedList<BriefSubmission>> getUserSubmissions(long conferenceId, long userId) {
        return pagingHelper.createPagedList(page -> submissionsApi.getUserSubmissions(conferenceId, userId, page).blockingFirst());
    }

    @Override
    public Observable<PagedList<BriefSubmission>> getReviewerSubmissions(long conferenceId, long userId) {
        return pagingHelper.createPagedList(page -> submissionsApi.getReviewerSubmissions(conferenceId, userId, page).blockingFirst());
    }

    @Override
    public Completable createSubmission(Submission submission) {
        return submissionsApi.createSubmission(submission);
    }

    @Override
    public Completable setSubmissionReviewable(long submissionId) {
        return submissionsApi.setSubmissionReviewable(submissionId, true);
    }
}
