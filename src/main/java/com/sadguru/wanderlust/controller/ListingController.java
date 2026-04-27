package com.sadguru.wanderlust.controller;

import com.sadguru.wanderlust.entity.Listing;
import com.sadguru.wanderlust.entity.User;
import com.sadguru.wanderlust.security.JwtUtil;
import com.sadguru.wanderlust.service.ListingService;
import com.sadguru.wanderlust.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listings")
public class ListingController {

    @Autowired
    private ListingService listingService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping
    public Listing create( @Valid @RequestBody Listing listing,
                          @RequestHeader("Authorization") String header) {

        String token = header.substring(7);
        String email = jwtUtil.extractEmail(token);

        User user = userService.findByEmail(email);

        return listingService.createListing(listing, user);
    }


    @GetMapping
    public List<Listing> getAll() {
        return listingService.getAllListings();
    }

    // Get single listing by ID
    @GetMapping("/{id}")
    public Listing getListingById(@PathVariable Long id) {
        Listing listing = listingService.getListingById(id);
        if (listing == null) {
            throw new RuntimeException("Listing not found with id: " + id);
        }
        return listing;
    }

    @PutMapping("/{id}")
    public Listing update(
            @PathVariable Long id,
            @RequestHeader("Authorization") String header,
            @Valid @RequestBody Listing listing
    ) {
        String token = header.substring(7);
        String email = jwtUtil.extractEmail(token);
        User user = userService.findByEmail(email);

        return listingService.updateListing(id, listing, user);
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id,
                         @RequestHeader("Authorization") String header) {

        String token = header.substring(7);
        String email = jwtUtil.extractEmail(token);

        User user = userService.findByEmail(email);

        listingService.deleteListing(id, user);

        return "Deleted successfully";
    }

    @GetMapping("/search")
    public List<Listing> search(@RequestParam String keyword) {
        return listingService.searchListings(keyword);
    }
}
