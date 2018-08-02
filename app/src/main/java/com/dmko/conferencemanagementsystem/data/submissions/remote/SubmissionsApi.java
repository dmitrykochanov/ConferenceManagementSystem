package com.dmko.conferencemanagementsystem.data.submissions.remote;

import com.dmko.conferencemanagementsystem.data.paging.Page;
import com.dmko.conferencemanagementsystem.data.submissions.entities.BriefSubmission;
import com.dmko.conferencemanagementsystem.data.submissions.entities.Submission;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SubmissionsApi {

    @GET("conferences/{conferenceId}/submissions")
    Observable<Page<BriefSubmission>> getSubmissions(@Path("conferenceId") long conferenceId, @Query("page") int page);

    @GET("submissions/{submissionId}")
    Observable<Submission> getSubmission(@Path("submissionId") long submissionId);

    @GET("conferences/{conferenceId}/submissions/users/{userId}")
    Observable<Page<BriefSubmission>> getUserSubmissions(@Path("conferenceId") long conferenceId, @Path("userId") long userId, @Query("page") int page);

    @GET("conferences/{conferenceId}/submissions/reviewers/{userId}")
    Observable<Page<BriefSubmission>> getReviewerSubmissions(@Path("conferenceId") long conferenceId, @Path("userId") long userId, @Query("page") int page);

    @POST("conferences/{conferenceId}/submissions")
    Completable createSubmission(@Body Submission submission);

    @PUT("submissions/{submissionId}/reviewable")
    Completable setSubmissionReviewable(@Path("submissionId") long submissionId, @Body boolean reviewable);
}
