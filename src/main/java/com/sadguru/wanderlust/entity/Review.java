package com.sadguru.wanderlust.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Comment is required")
    private String comment;

    @Min(value = 1, message = "Rating min 1")
    @Max(value = 5, message = "Rating max 5")
    private int rating;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonBackReference
    private Listing listing;

}