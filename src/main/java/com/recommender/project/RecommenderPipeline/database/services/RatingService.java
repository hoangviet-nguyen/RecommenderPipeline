package com.recommender.project.RecommenderPipeline.database.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recommender.project.RecommenderPipeline.data_models.Rating;
import com.recommender.project.RecommenderPipeline.database.repositories.RatingRepository;

@Service
public class RatingService {

    @Autowired
    private RatingRepository repository;

    public Optional<Rating> findById(Long id) {
        return repository.findById(id);
    }

    public List<Rating> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    public List<Rating> findByAnimeId(String animeId) {
        return repository.findByAnimeId(animeId);
    }

    public List<Rating> findByAnimeDbId(Long animeDbId) {
        return repository.findByAnimeDbId(animeDbId);
    }

    public void save(Rating rating) {
        var dbRating = repository.findByAnimeIdAndUserId(rating.getAnimeId(), rating.getUserId());
        if (dbRating.isPresent()) {
            var r = dbRating.get();
            r.setScore(rating.getScore());
            repository.save(r);
        } else {
            repository.save(rating);
        }
    }
}
