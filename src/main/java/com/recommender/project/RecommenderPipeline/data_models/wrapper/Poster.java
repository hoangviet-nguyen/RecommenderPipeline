package com.recommender.project.RecommenderPipeline.data_models.wrapper;

import com.google.gson.annotations.SerializedName;

public record Poster(
    @SerializedName("mainAltUrl") String imgSrc
) {}
