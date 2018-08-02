package com.dmko.conferencemanagementsystem.utils;


import io.reactivex.Scheduler;

public interface SchedulersFacade {
    Scheduler io();

    Scheduler ui();
}
