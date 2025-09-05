package com.recommender.project.RecommenderPipeline.database.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.recommender.project.RecommenderPipeline.data_models.Rating;

public interface RatingRepository extends CrudRepository <Rating, Long>{
    Optional<Rating> findById(Long id);
    List<Rating> findByUserId(Long userId);
    List<Rating> findByAnimeId(String animeId);
    List<Rating> findByAnimeDbId(Long animeDbId);
    Optional<Rating> findByAnimeIdAndUserId(String animeId, Long userId);
}
