package com.dmko.conferencemanagementsystem.data.conferences.remote;

import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.conferences.entities.BriefConference;
import com.dmko.conferencemanagementsystem.data.conferences.entities.Conference;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface RemoteConferencesSource {

    Observable<PagedList<BriefConference>> getAllConferences();

    Observable<List<BriefConference>> getUserConferences(long userId);

    Observable<Conference> getConference(long conferenceId);

    Completable inviteToConference(long conferenceId, String email);
}
