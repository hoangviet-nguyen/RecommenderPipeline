package com.recommender.project.RecommenderPipeline.data_models.wrapper;

import com.google.gson.annotations.SerializedName;
import com.recommender.project.RecommenderPipeline.data_models.Anime;

public record APIRating(@SerializedName("id") String userId, Anime anime, int score) {}
