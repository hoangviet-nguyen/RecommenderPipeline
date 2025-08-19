package com.recommender.project.RecommenderPipeline.data_models;

import com.google.gson.annotations.SerializedName;
import com.recommender.project.RecommenderPipeline.data_models.wrapper.Poster;

public record Anime(
    @SerializedName("id") String id,
    @SerializedName("score") float rating,
    @SerializedName("name") String title,
    @SerializedName("poster") Poster poster,
    String description
) {}