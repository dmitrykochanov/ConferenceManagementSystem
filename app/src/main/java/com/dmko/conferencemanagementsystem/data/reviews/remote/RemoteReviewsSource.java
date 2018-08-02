package com.dmko.conferencemanagementsystem.data.reviews.remote;

import com.dmko.conferencemanagementsystem.data.reviews.entities.Review;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface RemoteReviewsSource {

    Completable createReview(long documentId, Review review);

    Completable deleteReview(long reviewId);

    Observable<Review> getReview(long reviewId);

    Completable updateReview(Review review);

    Completable submitReview(long reviewId);
}
