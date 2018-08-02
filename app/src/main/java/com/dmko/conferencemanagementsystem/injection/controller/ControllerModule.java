package com.dmko.conferencemanagementsystem.injection.controller;


import com.dmko.conferencemanagementsystem.injection.scopes.ControllerScope;
import com.dmko.conferencemanagementsystem.ui.screens.addeditconferencerequest.recyclerview.CommentsAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class ControllerModule {

    @Provides
    @ControllerScope
    public CommentsAdapter provideCommentsAdapter() {
        return new CommentsAdapter();
    }
}
