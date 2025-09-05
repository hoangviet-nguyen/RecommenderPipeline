package com.recommender.project.RecommenderPipeline.database.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recommender.project.RecommenderPipeline.data_models.Rating;
import com.recommender.project.RecommenderPipeline.database.services.RatingService;

@RestController
@RequestMapping("/rating")
public class RatingController {
    
    @Autowired
    private RatingService service;

    @GetMapping("/user")
    public List<Rating> getUserRating(@RequestParam("userId") Long userID) {
        return service.findByUserId(userID);
    }

    @GetMapping("/anime")
    public List<Rating> getAnimeRating(@RequestParam("animeId") String animeId) {
        return service.findByAnimeId(animeId);
    }

    @GetMapping("/anime/db")
    public List<Rating> getAnimeRating(@RequestParam("animeId") Long animeId) {
        return service.findByAnimeDbId(animeId);
    }

    @PostMapping
    public String postRating(@RequestBody Rating rating) {
        service.save(rating);
        String message = String.format("The rating with user: %d and anime %d with score %d was saved",
            rating.getUserId(),
            rating.getAnimeId(),
            rating.getScore()
        );
        return message;
    }

}