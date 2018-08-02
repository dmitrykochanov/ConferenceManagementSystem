package com.dmko.conferencemanagementsystem.data.reviews.remote;

import com.dmko.conferencemanagementsystem.data.reviews.entities.Review;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReviewsApi {

    @POST("documents/{documentId}/reviews")
    Completable createReview(@Path("documentId") long documentId, @Body Review review);

    @DELETE("reviews/{reviewId}")
    Completable deleteReview(@Path("reviewId") long reviewId);

    @GET("reviews/{reviewId}")
    Observable<Review> getReview(@Path("reviewId") long reviewId);

    @PUT("reviews")
    Completable updateReview(@Body Review review);

    @POST("reviews/{reviewId}/submit")
    Completable submitReview(@Path("reviewId") long reviewId);
}
