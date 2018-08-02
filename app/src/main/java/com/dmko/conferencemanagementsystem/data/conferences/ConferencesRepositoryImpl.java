package com.dmko.conferencemanagementsystem.data.conferences;


import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.conferences.entities.BriefConference;
import com.dmko.conferencemanagementsystem.data.conferences.entities.Conference;
import com.dmko.conferencemanagementsystem.data.conferences.remote.RemoteConferencesSource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class ConferencesRepositoryImpl implements ConferencesRepository {
    private RemoteConferencesSource remoteConferencesSource;

    public ConferencesRepositoryImpl(RemoteConferencesSource remoteConferencesSource) {
        this.remoteConferencesSource = remoteConferencesSource;
    }

    @Override
    public Observable<PagedList<BriefConference>> getAllConferences() {
        return remoteConferencesSource.getAllConferences();
    }

    @Override
    public Observable<List<BriefConference>> getUserConferences(long userId) {
        return remoteConferencesSource.getUserConferences(userId);
    }

    @Override
    public Observable<Conference> getConference(long conferenceId) {
        return remoteConferencesSource.getConference(conferenceId);
    }

    @Override
    public Completable inviteToConference(long conferenceId, String email) {
        return remoteConferencesSource.inviteToConference(conferenceId, email);
    }

}
