package com.dmko.conferencemanagementsystem.data.requests;

import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.requests.entities.BriefConferenceRequest;
import com.dmko.conferencemanagementsystem.data.requests.entities.ConferenceRequest;
import com.dmko.conferencemanagementsystem.data.requests.entities.ConferenceRequestComment;
import com.dmko.conferencemanagementsystem.data.requests.remote.RemoteRequestsSource;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class RequestsRepositoryImpl implements RequestsRepository {

    private RemoteRequestsSource remoteRequestsSource;

    public RequestsRepositoryImpl(RemoteRequestsSource remoteRequestsSource) {
        this.remoteRequestsSource = remoteRequestsSource;
    }


    @Override
    public Observable<PagedList<BriefConferenceRequest>> getConferenceRequests() {
        return remoteRequestsSource.getConferenceRequests();
    }

    @Override
    public Observable<PagedList<BriefConferenceRequest>> getConferenceRequests(Integer status) {
        return remoteRequestsSource.getConferenceRequests(status);
    }

    @Override
    public Observable<PagedList<BriefConferenceRequest>> getUserConferenceRequests(long userId, Integer status) {
        return remoteRequestsSource.getUserConferenceRequests(userId, status);
    }

    @Override
    public Observable<PagedList<BriefConferenceRequest>> getUserConferenceRequests(long userId) {
        return remoteRequestsSource.getUserConferenceRequests(userId);
    }

    @Override
    public Observable<ConferenceRequest> getConferenceRequest(long conferenceRequestId) {
        return remoteRequestsSource.getConferenceRequest(conferenceRequestId);
    }

    @Override
    public Completable createConferenceRequest(ConferenceRequest conferenceRequest) {
        return remoteRequestsSource.createConferenceRequest(conferenceRequest);
    }

    @Override
    public Completable updateConferenceRequest(ConferenceRequest conferenceRequest) {
        return remoteRequestsSource.updateConferenceRequest(conferenceRequest);
    }

    @Override
    public Completable addComment(long requestId, ConferenceRequestComment comment) {
        return remoteRequestsSource.addComment(requestId, comment);
    }
}
