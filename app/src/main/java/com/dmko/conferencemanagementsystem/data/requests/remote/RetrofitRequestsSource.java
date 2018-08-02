package com.dmko.conferencemanagementsystem.data.requests.remote;

import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.paging.PagingHelper;
import com.dmko.conferencemanagementsystem.data.requests.entities.BriefConferenceRequest;
import com.dmko.conferencemanagementsystem.data.requests.entities.ConferenceRequest;
import com.dmko.conferencemanagementsystem.data.requests.entities.ConferenceRequestComment;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class RetrofitRequestsSource implements RemoteRequestsSource {

    private RequestsApi requestsApi;
    private PagingHelper pagingHelper;

    public RetrofitRequestsSource(RequestsApi requestsApi, PagingHelper pagingHelper) {
        this.requestsApi = requestsApi;
        this.pagingHelper = pagingHelper;
    }

    @Override
    public Observable<PagedList<BriefConferenceRequest>> getConferenceRequests() {
        return pagingHelper.createPagedList(page -> requestsApi.getConferenceRequests(null, page).blockingFirst());
    }

    @Override
    public Observable<PagedList<BriefConferenceRequest>> getConferenceRequests(Integer status) {
        return pagingHelper.createPagedList(page -> requestsApi.getConferenceRequests(status, page).blockingFirst());
    }

    @Override
    public Observable<PagedList<BriefConferenceRequest>> getUserConferenceRequests(long userId, Integer status) {
        return pagingHelper.createPagedList(page -> requestsApi.getUserConferenceRequests(userId, status, page).blockingFirst());
    }

    @Override
    public Observable<PagedList<BriefConferenceRequest>> getUserConferenceRequests(long userId) {
        return pagingHelper.createPagedList(page -> requestsApi.getUserConferenceRequests(userId, null, page).blockingFirst());
    }

    @Override
    public Observable<ConferenceRequest> getConferenceRequest(long conferenceRequestId) {
        return requestsApi.getConferenceRequest(conferenceRequestId);
    }

    @Override
    public Completable createConferenceRequest(ConferenceRequest conferenceRequest) {
        return requestsApi.createConferenceRequest(conferenceRequest);
    }

    @Override
    public Completable updateConferenceRequest(ConferenceRequest conferenceRequest) {
        return requestsApi.updateConferenceRequest(conferenceRequest);
    }

    @Override
    public Completable addComment(long requestId, ConferenceRequestComment comment) {
        return requestsApi.addComment(requestId, comment);
    }
}
