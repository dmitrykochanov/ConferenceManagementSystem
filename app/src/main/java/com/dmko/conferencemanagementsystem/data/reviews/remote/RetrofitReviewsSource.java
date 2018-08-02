package com.dmko.conferencemanagementsystem.data.reviews.remote;

import com.dmko.conferencemanagementsystem.data.reviews.entities.Review;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class RetrofitReviewsSource implements RemoteReviewsSource {

    private ReviewsApi reviewsApi;

    public RetrofitReviewsSource(ReviewsApi reviewsApi) {
        this.reviewsApi = reviewsApi;
    }

    @Override
    public Completable createReview(long documentId, Review review) {
        return reviewsApi.createReview(documentId, review);
    }

    @Override
    public Completable deleteReview(long reviewId) {
        return reviewsApi.deleteReview(reviewId);
    }

    @Override
    public Observable<Review> getReview(long reviewId) {
        return reviewsApi.getReview(reviewId);
    }

    @Override
    public Completable updateReview(Review review) {
        return reviewsApi.updateReview(review);
    }

    @Override
    public Completable submitReview(long reviewId) {
        return reviewsApi.submitReview(reviewId);
    }
}
