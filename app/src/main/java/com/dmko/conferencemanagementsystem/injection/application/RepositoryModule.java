package com.dmko.conferencemanagementsystem.injection.application;

import com.dmko.conferencemanagementsystem.data.conferences.ConferencesRepository;
import com.dmko.conferencemanagementsystem.data.conferences.ConferencesRepositoryImpl;
import com.dmko.conferencemanagementsystem.data.conferences.remote.RemoteConferencesSource;
import com.dmko.conferencemanagementsystem.data.requests.RequestsRepository;
import com.dmko.conferencemanagementsystem.data.requests.RequestsRepositoryImpl;
import com.dmko.conferencemanagementsystem.data.requests.remote.RemoteRequestsSource;
import com.dmko.conferencemanagementsystem.data.reviews.ReviewsRepository;
import com.dmko.conferencemanagementsystem.data.reviews.ReviewsRepositoryImpl;
import com.dmko.conferencemanagementsystem.data.reviews.remote.RemoteReviewsSource;
import com.dmko.conferencemanagementsystem.data.submissions.SubmissionsRepository;
import com.dmko.conferencemanagementsystem.data.submissions.SubmissionsRepositoryImpl;
import com.dmko.conferencemanagementsystem.data.submissions.remote.RemoteSubmissionsSource;
import com.dmko.conferencemanagementsystem.data.user.UserRepository;
import com.dmko.conferencemanagementsystem.data.user.UserRepositoryImpl;
import com.dmko.conferencemanagementsystem.data.user.local.LocalUserSource;
import com.dmko.conferencemanagementsystem.data.user.remote.RemoteUserSource;
import com.dmko.conferencemanagementsystem.injection.scopes.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @ApplicationScope
    public ConferencesRepository provideConferenceRepository(RemoteConferencesSource remoteConferencesSource) {
        return new ConferencesRepositoryImpl(remoteConferencesSource);
    }

    @Provides
    @ApplicationScope
    public RequestsRepository provideRequestsRepository(RemoteRequestsSource remoteRequestsSource) {
        return new RequestsRepositoryImpl(remoteRequestsSource);
    }

    @Provides
    @ApplicationScope
    public ReviewsRepository provideReviewsRepository(RemoteReviewsSource remoteReviewsSource) {
        return new ReviewsRepositoryImpl(remoteReviewsSource);
    }

    @Provides
    @ApplicationScope
    public SubmissionsRepository provideSubmissionsRepository(RemoteSubmissionsSource remoteSubmissionsSource) {
        return new SubmissionsRepositoryImpl(remoteSubmissionsSource);
    }

    @Provides
    @ApplicationScope
    public UserRepository provideUserRepository(LocalUserSource localUserSource, RemoteUserSource remoteUserSource) {
        return new UserRepositoryImpl(localUserSource, remoteUserSource);
    }
}
