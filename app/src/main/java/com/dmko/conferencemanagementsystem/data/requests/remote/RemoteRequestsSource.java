package com.dmko.conferencemanagementsystem.data.requests.remote;

import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.requests.entities.BriefConferenceRequest;
import com.dmko.conferencemanagementsystem.data.requests.entities.ConferenceRequest;
import com.dmko.conferencemanagementsystem.data.requests.entities.ConferenceRequestComment;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface RemoteRequestsSource {

    Observable<PagedList<BriefConferenceRequest>> getConferenceRequests();

    Observable<PagedList<BriefConferenceRequest>> getConferenceRequests(Integer status);

    Observable<PagedList<BriefConferenceRequest>> getUserConferenceRequests(long userId, Integer status);

    Observable<PagedList<BriefConferenceRequest>> getUserConferenceRequests(long userId);

    Observable<ConferenceRequest> getConferenceRequest(long conferenceRequestId);

    Completable createConferenceRequest(ConferenceRequest conferenceRequest);

    Completable updateConferenceRequest(ConferenceRequest conferenceRequest);

    Completable addComment(long requestId, ConferenceRequestComment comment);
}
