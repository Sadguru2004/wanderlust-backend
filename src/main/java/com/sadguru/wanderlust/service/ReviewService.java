package com.sadguru.wanderlust.service;

import com.sadguru.wanderlust.entity.Listing;
import com.sadguru.wanderlust.entity.Review;
import com.sadguru.wanderlust.entity.User;
import com.sadguru.wanderlust.exception.ResourceNotFoundException;
import com.sadguru.wanderlust.exception.UnauthorizedException;
import com.sadguru.wanderlust.repository.ListingRepository;
import com.sadguru.wanderlust.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ListingRepository listingRepository;

    public Review addReview(Long listingId, Review review, User user) {

        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Listing not found with id: " + listingId)
                );

        review.setUser(user);
        review.setListing(listing);

        return reviewRepository.save(review);
    }
    public void deleteReview(Long reviewId, User user) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Review not found with id: " + reviewId)
                );

        if (!review.getListing().getOwner().getId().equals(user.getId())) {
            throw new UnauthorizedException("Only listing owner can delete reviews");
        }
        reviewRepository.delete(review);
    }
}