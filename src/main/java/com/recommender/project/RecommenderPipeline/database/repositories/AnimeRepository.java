package com.recommender.project.RecommenderPipeline.database.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.recommender.project.RecommenderPipeline.data_models.Anime;

public interface AnimeRepository extends CrudRepository<Anime, Long> {
    @Query("SELECT a FROM Anime a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Anime> searchByTitle(@Param("title") String title);
    Optional<Anime> findById(Long id);
    Iterable<Anime> findAll();
}