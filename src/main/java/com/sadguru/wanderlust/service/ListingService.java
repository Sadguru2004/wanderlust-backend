package com.sadguru.wanderlust.service;

import com.sadguru.wanderlust.entity.Listing;
import com.sadguru.wanderlust.entity.User;
import com.sadguru.wanderlust.exception.ResourceNotFoundException;
import com.sadguru.wanderlust.exception.UnauthorizedException;
import com.sadguru.wanderlust.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

    public Listing createListing(Listing listing, User user) {

        listing.setOwner(user);

        return listingRepository.save(listing);
    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public Listing getListingById(Long id) {
        return listingRepository.findById(id).orElse(null);
    }

    public Listing updateListing(Long id, Listing updatedListing, User user) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found with id: " + id));

        if (!listing.getOwner().getId().equals(user.getId())) {
            throw new UnauthorizedException("You are not allowed to update this listing");
        }

        // Update fields
        listing.setTitle(updatedListing.getTitle());
        listing.setDescription(updatedListing.getDescription());
        listing.setImageUrl(updatedListing.getImageUrl());
        listing.setPrice(updatedListing.getPrice());
        listing.setLocation(updatedListing.getLocation());
        listing.setCountry(updatedListing.getCountry());

        return listingRepository.save(listing);
    }

    public List<Listing> searchListings(String keyword) {
        return listingRepository.search(keyword);
    }

    public void deleteListing(Long id, User user) {

        Listing listing = listingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Listing not found with id: " + id)
                );

        if (!listing.getOwner().getId().equals(user.getId())) {
            throw new UnauthorizedException("You are not allowed to delete this listing");
        }

        listingRepository.delete(listing);
    }
}

