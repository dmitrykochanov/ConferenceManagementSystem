package com.dmko.conferencemanagementsystem.data.submissions.remote;

import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.submissions.entities.BriefSubmission;
import com.dmko.conferencemanagementsystem.data.submissions.entities.Submission;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface RemoteSubmissionsSource {

    Observable<PagedList<BriefSubmission>> getSubmissions(long conferenceId);

    Observable<Submission> getSubmission(long submissionId);

    Observable<PagedList<BriefSubmission>> getUserSubmissions(long conferenceId, long userId);

    Observable<PagedList<BriefSubmission>> getReviewerSubmissions(long conferenceId, long userId);

    Completable createSubmission(Submission submission);

    Completable setSubmissionReviewable(long submissionId);
}
