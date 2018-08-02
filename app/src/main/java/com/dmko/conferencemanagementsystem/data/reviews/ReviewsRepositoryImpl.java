package com.dmko.conferencemanagementsystem.data.reviews;

import com.dmko.conferencemanagementsystem.data.reviews.entities.Review;
import com.dmko.conferencemanagementsystem.data.reviews.remote.RemoteReviewsSource;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class ReviewsRepositoryImpl implements ReviewsRepository {

    private RemoteReviewsSource remoteReviewsSource;

    public ReviewsRepositoryImpl(RemoteReviewsSource remoteReviewsSource) {
        this.remoteReviewsSource = remoteReviewsSource;
    }

    @Override
    public Completable createReview(long documentId, Review review) {
        return remoteReviewsSource.createReview(documentId, review);
    }

    @Override
    public Completable deleteReview(long reviewId) {
        return remoteReviewsSource.deleteReview(reviewId);
    }

    @Override
    public Observable<Review> getReview(long reviewId) {
        return remoteReviewsSource.getReview(reviewId);
    }

    @Override
    public Completable updateReview(Review review) {
        return remoteReviewsSource.updateReview(review);
    }

    @Override
    public Completable submitReview(long reviewId) {
        return remoteReviewsSource.submitReview(reviewId);
    }
}
