package com.recommender.project.RecommenderPipeline.data_models.wrapper;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.Embeddable;

@Embeddable
public record Poster(@SerializedName("mainAltUrl") String imgSrc) {} 