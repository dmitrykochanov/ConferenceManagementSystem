package com.dmko.conferencemanagementsystem.data.conferences.remote;


import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.conferences.entities.BriefConference;
import com.dmko.conferencemanagementsystem.data.conferences.entities.Conference;
import com.dmko.conferencemanagementsystem.data.paging.PagingHelper;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class RetrofitConferencesSource implements RemoteConferencesSource {

    private ConferencesApi conferencesApi;
    private PagingHelper pagingHelper;

    public RetrofitConferencesSource(ConferencesApi conferencesApi, PagingHelper pagingHelper) {
        this.conferencesApi = conferencesApi;
        this.pagingHelper = pagingHelper;
    }

    @Override
    public Observable<PagedList<BriefConference>> getAllConferences() {
        return pagingHelper.createPagedList(page -> conferencesApi.getAllConferences(page).blockingFirst());
    }

    @Override
    public Observable<List<BriefConference>> getUserConferences(long userId) {
        return conferencesApi.getUserConferences(userId);
    }

    @Override
    public Observable<Conference> getConference(long conferenceId) {
        return conferencesApi.getConference(conferenceId);
    }

    @Override
    public Completable inviteToConference(long conferenceId, String email) {
        return conferencesApi.inviteToConference(conferenceId, email);
    }
}
