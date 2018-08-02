package com.dmko.conferencemanagementsystem.data.paging;

import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;
import android.arch.paging.RxPagedListBuilder;
import android.support.annotation.NonNull;

import com.dmko.conferencemanagementsystem.utils.SchedulersFacade;

import io.reactivex.Observable;
import timber.log.Timber;

public class PagingHelper {

    public interface PageLoader<T> {
        Page<T> loadPage(int page);
    }

    private PagedList.Config config;
    private SchedulersFacade schedulers;

    public PagingHelper(PagedList.Config config, SchedulersFacade schedulers) {
        this.config = config;
        this.schedulers = schedulers;
    }

    public <T> Observable<PagedList<T>> createPagedList(PageLoader<T> pageLoader) {
        return new RxPagedListBuilder<>(new DataSource.Factory<Integer, T>() {
            @Override
            public DataSource<Integer, T> create() {
                return new PageKeyedDataSource<Integer, T>() {
                    @Override
                    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, T> callback) {
                        Timber.d("Load initial");
                        Page<T> page = pageLoader.loadPage(0);
                        callback.onResult(page.getContent(), null, 1);
                    }

                    @Override
                    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, T> callback) {
                        Timber.d("Load before");
                        Page<T> page = pageLoader.loadPage(params.key);
                        Integer adjacentPageKey = params.key > 0 ? params.key - 1 : null;
                        callback.onResult(page.getContent(), adjacentPageKey);
                    }

                    @Override
                    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, T> callback) {
                        Timber.d("Load after");
                        Page<T> page = pageLoader.loadPage(params.key);
                        Integer adjacentPageKey = page.isLast() ? null : params.key + 1;
                        callback.onResult(page.getContent(), adjacentPageKey);
                    }
                };
            }
        }, config)
                .setFetchScheduler(schedulers.io())
                .setNotifyScheduler(schedulers.ui())
                .buildObservable();
    }
}
