package com.dmko.conferencemanagementsystem.utils;

import android.support.annotation.NonNull;

import timber.log.Timber;

public class ProductionTree extends Timber.Tree {
    @Override
    protected void log(int priority, String tag, @NonNull String message, Throwable t) {
    }
}
