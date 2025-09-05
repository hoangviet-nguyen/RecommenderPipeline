package com.recommender.project.RecommenderPipeline.data_models;

import com.google.gson.annotations.SerializedName;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Rating { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long userId;
    private String animeId;
    private Long animeDbId; 
    private int score;
    
    public Rating (Long userId, APIRating rating) {
        this.animeId = rating.anime().getApiId();
        this.animeDbId = rating.anime().getDbId();
        this.userId = userId;
    }

    @Override
    public String toString() {
        return String.format("[UserId: %s, AnimeId: %s, Score: %d]", userId, animeId, score);
    }

    public record APIRating(@SerializedName("id") String userId, Anime anime, int score) {}
}
