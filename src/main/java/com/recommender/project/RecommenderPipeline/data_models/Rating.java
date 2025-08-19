package com.recommender.project.RecommenderPipeline.data_models;

import com.recommender.project.RecommenderPipeline.data_models.wrapper.UserRate;

public record Rating(String id, String userId, String animeId, int score) {
    public Rating (String userId, UserRate rate) {
        this(rate.id(), userId, rate.anime().id(), rate.score());
    }
}
