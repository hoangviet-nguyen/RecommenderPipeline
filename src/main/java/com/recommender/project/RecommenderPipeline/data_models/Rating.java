package com.recommender.project.RecommenderPipeline.data_models;

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
    private Long animeId; 
    private int score;
    
    @Override
    public String toString() {
        return String.format("[UserId: %s, AnimeId: %s, Score: %d]", userId, animeId, score);
    }
}
