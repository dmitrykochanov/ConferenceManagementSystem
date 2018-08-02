package com.dmko.conferencemanagementsystem.injection.application;


import android.content.Context;
import android.content.SharedPreferences;

import com.dmko.conferencemanagementsystem.data.user.local.LocalUserSource;
import com.dmko.conferencemanagementsystem.data.user.local.SharedPreferencesUserSource;
import com.dmko.conferencemanagementsystem.injection.scopes.ApplicationScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    private static final String PREFS_USER = "prefs_user";

    @Provides
    @ApplicationScope
    @Named("user_prefs")
    public SharedPreferences provideUserSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFS_USER, Context.MODE_PRIVATE);
    }

    @Provides
    @ApplicationScope
    public LocalUserSource provideLocalUserSource(@Named("user_prefs") SharedPreferences prefs) {
        return new SharedPreferencesUserSource(prefs);
    }
}
