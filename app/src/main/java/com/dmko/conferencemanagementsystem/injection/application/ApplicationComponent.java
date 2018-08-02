package com.dmko.conferencemanagementsystem.injection.application;


import com.dmko.conferencemanagementsystem.injection.controller.ControllerComponent;
import com.dmko.conferencemanagementsystem.injection.controller.ControllerModule;
import com.dmko.conferencemanagementsystem.injection.controller.PresenterModule;
import com.dmko.conferencemanagementsystem.injection.scopes.ApplicationScope;

import dagger.Component;

@ApplicationScope
@Component(modules = {ApplicationModule.class, NetworkModule.class, DatabaseModule.class, RepositoryModule.class})
public interface ApplicationComponent {
    ControllerComponent newControllerComponent(PresenterModule presenterModule, ControllerModule controllerModule);
}
