package com.sadguru.wanderlust.repository;

import com.sadguru.wanderlust.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing,Long> {

    @Query("SELECT l FROM Listing l WHERE " +
            "LOWER(l.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.location) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.country) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Listing> search(@Param("keyword") String keyword);
}
