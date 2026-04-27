package com.sadguru.wanderlust.repository;

import com.sadguru.wanderlust.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
