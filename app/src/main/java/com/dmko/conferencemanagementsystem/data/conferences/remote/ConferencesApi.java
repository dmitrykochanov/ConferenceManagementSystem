package com.dmko.conferencemanagementsystem.data.conferences.remote;


import com.dmko.conferencemanagementsystem.data.paging.Page;
import com.dmko.conferencemanagementsystem.data.conferences.entities.BriefConference;
import com.dmko.conferencemanagementsystem.data.conferences.entities.Conference;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConferencesApi {

    @GET("conferences")
    Observable<Page<BriefConference>> getAllConferences(@Query("page") int page);

    @GET("users/{userId}/conferences")
    Observable<List<BriefConference>> getUserConferences(@Path("userId") long userId);

    @GET("conferences/{conferenceId}")
    Observable<Conference> getConference(@Path("conferenceId") long conferenceId);

    @POST("conferences/{conferenceId}/invite")
    Completable inviteToConference(@Path("conferenceId") long conferenceId, @Body String email);
}
