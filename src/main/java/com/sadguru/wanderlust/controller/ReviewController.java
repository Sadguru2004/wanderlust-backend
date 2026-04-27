package com.sadguru.wanderlust.controller;

import com.sadguru.wanderlust.entity.Review;
import com.sadguru.wanderlust.entity.User;
import com.sadguru.wanderlust.security.JwtUtil;
import com.sadguru.wanderlust.service.ReviewService;
import com.sadguru.wanderlust.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/{listingId}")
    public Review addReview(@PathVariable Long listingId,
                            @Valid
                            @RequestBody Review review,
                            @RequestHeader("Authorization") String header) {

        String token = header.substring(7);
        String email = jwtUtil.extractEmail(token);

        User user = userService.findByEmail(email);

        return reviewService.addReview(listingId, review, user);
    }


    @DeleteMapping("/{reviewId}")
    public String deleteReview(@PathVariable Long reviewId,
                               @RequestHeader("Authorization") String header) {

        String token = header.substring(7);
        String email = jwtUtil.extractEmail(token);

        User user = userService.findByEmail(email);

        reviewService.deleteReview(reviewId, user);

        return "Review deleted";
    }
}
