package com.dmko.conferencemanagementsystem.data.user.remote;


import com.dmko.conferencemanagementsystem.data.paging.Page;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUser;
import com.dmko.conferencemanagementsystem.data.user.entities.BriefUserRoles;
import com.dmko.conferencemanagementsystem.data.user.entities.SignInForm;
import com.dmko.conferencemanagementsystem.data.user.entities.SignUpForm;
import com.dmko.conferencemanagementsystem.data.user.entities.UserWithToken;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {

    @POST("auth/signin")
    Observable<UserWithToken> signIn(@Body SignInForm form);

    @POST("auth/signup")
    Observable<UserWithToken> signUp(@Body SignUpForm form);

    @GET("conferences/{conferenceId}/reviewers")
    Observable<Page<BriefUser>> getReviewers(@Path("conferenceId") long conferenceId, @Query("page") int page);

    @POST("conferences/{conferenceId}/reviewers")
    Completable addReviewerToConference(@Path("conferenceId") long conferenceId, @Body List<Long> reviewers);

    @HTTP(method = "DELETE", path = "conferences/{conferenceId}/reviewers", hasBody = true)
    Completable deleteReviewerFromConference(@Path("conferenceId") long conferenceId, @Body List<Long> reviewers);

    @GET("conferences/{conferenceId}/users")
    Observable<Page<BriefUserRoles>> getUsersWithRoles(@Path("conferenceId") long conferenceId, @Query("role") Long role, @Query("page") int page);

    @GET("conferences/{conferenceId}/users/{userId}")
    Observable<BriefUserRoles> getUserWithRoles(@Path("userId") long userId, @Path("conferenceId") long conferenceId);

    @PUT("conferences/{conferenceId}/users/{userId}")
    Completable updateRoles(@Path("conferenceId") long conferenceId, @Path("userId") long userId, @Body List<Long> roles);

    @POST("submissions/{submissionId}/reviewers")
    Completable addReviewerToSubmission(@Path("submissionId") long submissionId, @Body List<Long> reviewers);

    @HTTP(method = "DELETE", path = "submissions/{submissionId}/reviewers", hasBody = true)
    Completable deleteReviewerFromSubmission(@Path("submissionId") long submissionId, @Body List<Long> reviewers);
}
