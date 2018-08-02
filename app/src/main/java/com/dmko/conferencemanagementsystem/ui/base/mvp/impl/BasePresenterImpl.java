package com.dmko.conferencemanagementsystem.ui.base.mvp.impl;


import com.dmko.conferencemanagementsystem.ui.base.mvp.BasePresenter;
import com.dmko.conferencemanagementsystem.ui.base.mvp.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static com.dmko.conferencemanagementsystem.utils.LogTags.LOG_DATA;

public class BasePresenterImpl<T extends BaseView> implements BasePresenter<T> {
    private T view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
        clearSubscriptions();
    }

    @Override
    public void clearSubscriptions() {
        compositeDisposable.clear();
    }

    @Override
    public void handleThrowable(Throwable throwable) {

        Timber.tag(LOG_DATA);
        Timber.e(throwable);

        if (isViewAttached()) {
            getView().showUnknownErrorDialog();
        }
    }

    protected T getView() {
        return view;
    }

    protected boolean isViewAttached() {
        return view != null;
    }

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }
}
