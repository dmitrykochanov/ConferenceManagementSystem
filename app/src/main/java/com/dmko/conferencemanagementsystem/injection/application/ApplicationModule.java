package com.dmko.conferencemanagementsystem.injection.application;


import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.dmko.conferencemanagementsystem.injection.scopes.ApplicationScope;
import com.dmko.conferencemanagementsystem.utils.SchedulersFacade;
import com.dmko.conferencemanagementsystem.utils.SchedulersFacadeImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    public Application provideApplication() {
        return application;
    }

    @Provides
    @ApplicationScope
    public Context provideApplicationContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @ApplicationScope
    public SchedulersFacade provideSchedulersFacade() {
        return new SchedulersFacadeImpl();
    }

    @Provides
    @ApplicationScope
    public Resources provideResources(Context context) {
        return context.getResources();
    }
}
