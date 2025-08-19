package com.recommender.project.RecommenderPipeline.data_models.wrapper;

import com.recommender.project.RecommenderPipeline.data_models.Anime;

public record UserRate(String id, Anime anime, int score) {}
