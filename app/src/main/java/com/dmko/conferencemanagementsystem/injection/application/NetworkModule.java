package com.dmko.conferencemanagementsystem.injection.application;

import android.arch.paging.PagedList;

import com.dmko.conferencemanagementsystem.data.conferences.remote.ConferencesApi;
import com.dmko.conferencemanagementsystem.data.conferences.remote.RemoteConferencesSource;
import com.dmko.conferencemanagementsystem.data.conferences.remote.RetrofitConferencesSource;
import com.dmko.conferencemanagementsystem.data.paging.PagingHelper;
import com.dmko.conferencemanagementsystem.data.requests.remote.RemoteRequestsSource;
import com.dmko.conferencemanagementsystem.data.requests.remote.RequestsApi;
import com.dmko.conferencemanagementsystem.data.requests.remote.RetrofitRequestsSource;
import com.dmko.conferencemanagementsystem.data.reviews.remote.RemoteReviewsSource;
import com.dmko.conferencemanagementsystem.data.reviews.remote.RetrofitReviewsSource;
import com.dmko.conferencemanagementsystem.data.reviews.remote.ReviewsApi;
import com.dmko.conferencemanagementsystem.data.submissions.remote.RemoteSubmissionsSource;
import com.dmko.conferencemanagementsystem.data.submissions.remote.RetrofitSubmissionsSource;
import com.dmko.conferencemanagementsystem.data.submissions.remote.SubmissionsApi;
import com.dmko.conferencemanagementsystem.data.user.local.LocalUserSource;
import com.dmko.conferencemanagementsystem.data.user.remote.RemoteUserSource;
import com.dmko.conferencemanagementsystem.data.user.remote.RetrofitUserSource;
import com.dmko.conferencemanagementsystem.data.user.remote.UserApi;
import com.dmko.conferencemanagementsystem.injection.scopes.ApplicationScope;
import com.dmko.conferencemanagementsystem.utils.RxErrorHandlingCallAdapterFactory;
import com.dmko.conferencemanagementsystem.utils.SchedulersFacade;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    //private static final String BASE_URL = "http://demo4031203.mockable.io/";
    private static final String BASE_URL = "http://10.0.2.2:8080/api/";

    @Provides
    @ApplicationScope
    public Interceptor provideAuthInterceptor(LocalUserSource userSource) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String token = userSource.getToken();
                Request original = chain.request();
                if (token == null) return chain.proceed(original);

                Request request = original.newBuilder()
                        .header("Authorization", "Bearer " + token)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        };
    }

    @Provides
    @ApplicationScope
    public OkHttpClient provideClient(Interceptor authInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    @Provides
    @ApplicationScope
    public Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .build();
    }

    @Provides
    @ApplicationScope
    public PagedList.Config providePagedListConfig() {
        return new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(50)
                .setInitialLoadSizeHint(10)
                .setPageSize(10)
                .build();
    }

    @Provides
    @ApplicationScope
    public PagingHelper providePagingHelper(SchedulersFacade schedulers, PagedList.Config config) {
        return new PagingHelper(config, schedulers);
    }

    @Provides
    @ApplicationScope
    public ConferencesApi provideConferencesApi(Retrofit retrofit) {
        return retrofit.create(ConferencesApi.class);
    }

    @Provides
    @ApplicationScope
    public RequestsApi provideRequestsApi(Retrofit retrofit) {
        return retrofit.create(RequestsApi.class);
    }

    @Provides
    @ApplicationScope
    public ReviewsApi provideReviewsApi(Retrofit retrofit) {
        return retrofit.create(ReviewsApi.class);
    }

    @Provides
    @ApplicationScope
    public SubmissionsApi provideSubmissionsApi(Retrofit retrofit) {
        return retrofit.create(SubmissionsApi.class);
    }

    @Provides
    @ApplicationScope
    public UserApi provideUserApi(Retrofit retrofit) {
        return retrofit.create(UserApi.class);
    }

    @Provides
    @ApplicationScope
    public RemoteConferencesSource provideRemoteConferencesSource(ConferencesApi conferencesApi, PagingHelper pagingHelper) {
        return new RetrofitConferencesSource(conferencesApi, pagingHelper);
    }

    @Provides
    @ApplicationScope
    public RemoteRequestsSource provideRemoteRequestsSource(RequestsApi requestsApi, PagingHelper pagingHelper) {
        return new RetrofitRequestsSource(requestsApi, pagingHelper);
    }

    @Provides
    @ApplicationScope
    public RemoteReviewsSource provideRemoteReviewsSource(ReviewsApi reviewsApi) {
        return new RetrofitReviewsSource(reviewsApi);
    }

    @Provides
    @ApplicationScope
    public RemoteSubmissionsSource provideRemoteSubmissionsSource(SubmissionsApi submissionsApi, PagingHelper pagingHelper) {
        return new RetrofitSubmissionsSource(submissionsApi, pagingHelper);
    }

    @Provides
    @ApplicationScope
    public RemoteUserSource provideRemoteUserSource(UserApi userApi, PagingHelper pagingHelper) {
        return new RetrofitUserSource(userApi, pagingHelper);
    }
}
