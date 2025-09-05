package com.recommender.project.RecommenderPipeline.data_models;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.annotations.SerializedName;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
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
    @Convert(converter = PosterConverter.class)
    private Poster poster;

    @Lob
    private String description;

    @SerializedName("genres")
    @Convert(converter = GenresConverter.class)
    private List<Genres> genres;

    @Override
    public String toString() {
        String genreList = genres.stream()
            .map(Genres::name)   // extract the name from each record
            .collect(Collectors.joining(", "));

        return String.format("""
        {
            dbId: %d
            apiId: %s,
            title: %s,
            poster: %s,
            score: %.2f,
            genres: %s
        }
        """, dbId, apiId, title, poster.imgSrc(), rating, genreList);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Anime)) return false;
        Anime anime = (Anime) o;
        return apiId != null && apiId.equals(anime.apiId);
    }

    @Override
    public int hashCode() {
        return apiId != null ? apiId.hashCode() : 0;
    }

    public record Genres (String name) {}
    public record Poster(@SerializedName("mainAltUrl") String imgSrc) {}

    @Converter
    static class GenresConverter implements AttributeConverter<List<Genres>, String> {

        @Override
        public String convertToDatabaseColumn(List<Genres> genres) {
            return genres.stream().map(Genres::name).collect(Collectors.joining(","));
        }

        @Override
        public List<Genres> convertToEntityAttribute(String dbData) {
            return Arrays.stream(dbData.split(","))
                        .map(name -> new Genres(name))
                        .collect(Collectors.toList());
        }
    }

    @Converter
    static class PosterConverter implements AttributeConverter<Poster, String> {
        @Override
        public String convertToDatabaseColumn(Poster poster) {
            if (poster == null) return null;
            return poster.imgSrc();  // speichert nur den String
        }

        @Override
        public Poster convertToEntityAttribute(String dbData) {
            if (dbData == null || dbData.isEmpty()) return null;
            return new Poster(dbData);  // erstellt ein Poster-Objekt aus dem String
        }
    }
}