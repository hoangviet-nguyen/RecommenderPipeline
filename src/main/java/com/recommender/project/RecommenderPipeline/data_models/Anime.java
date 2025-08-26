package com.recommender.project.RecommenderPipeline.data_models;

import com.google.gson.annotations.SerializedName;
import com.recommender.project.RecommenderPipeline.data_models.wrapper.Poster;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated DB ID
    private Long dbId;

    @SerializedName("id")
    @Column(unique = true)
    private String apiId;

    @SerializedName("score")    
    private float rating;

    @SerializedName("name")     
    private String title;
    
    @SerializedName("poster")
    @Embedded
    private Poster poster;

    @Lob
    private String description;

    @Override
    public String toString() {
        return String.format("""
        {
            dbId: %d
            apiId: %s,
            title: %s,
            poster: %s,
            score: %.2f
        }
        """, dbId, apiId, title, poster.imgSrc(), rating);
    }
}