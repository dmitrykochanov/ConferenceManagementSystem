package com.dmko.conferencemanagementsystem.data.requests.remote;

import com.dmko.conferencemanagementsystem.data.paging.Page;
import com.dmko.conferencemanagementsystem.data.requests.entities.BriefConferenceRequest;
import com.dmko.conferencemanagementsystem.data.requests.entities.ConferenceRequest;
import com.dmko.conferencemanagementsystem.data.requests.entities.ConferenceRequestComment;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestsApi {

    @GET("requests")
    Observable<Page<BriefConferenceRequest>> getConferenceRequests(@Query("status") Integer status, @Query("page") int page);

    @GET("users/{userId}/conference-requests")
    Observable<Page<BriefConferenceRequest>> getUserConferenceRequests(@Path("userId") long userId, @Query("status") Integer status, @Query("page") int page);

    @GET("requests/{requestId}")
    Observable<ConferenceRequest> getConferenceRequest(@Path("requestId") long conferenceRequestId);

    @POST("requests")
    Completable createConferenceRequest(@Body ConferenceRequest conferenceRequest);

    @PUT("requests")
    Completable updateConferenceRequest(@Body ConferenceRequest conferenceRequest);

    @POST("requests/{requestId}/comments")
    Completable addComment(@Path("requestId") long requestId, @Body ConferenceRequestComment comment);
}
