package com.recommender.project.RecommenderPipeline.database.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recommender.project.RecommenderPipeline.AnimeAPI;
import com.recommender.project.RecommenderPipeline.data_models.Anime;
import com.recommender.project.RecommenderPipeline.database.repositories.AnimeRepository;

@Service
public class AnimeService {

    @Autowired
    private AnimeRepository repository;
    private AnimeAPI api = AnimeAPI.getAnimeAPI();

    public List<Anime> findAnimeByTitel(String title) {
        List<Anime> found = repository.searchByTitle(title);
        // Anime was not found in db
        if (found.isEmpty()) {
            List<Anime> pulled = api.getAnimesByTitle(title, 10);
            pulled.forEach(anime -> repository.save(anime));
            return repository.searchByTitle(title);
        }
        return found;
    }

    public Optional<Anime> findAnimeById(Long id) {
        return repository.findById(id);
    }
    
    public List<Anime> getAllAnimes() {
        return repository.findAll();
    }

    public void save(Anime anime) {
        repository.save(anime);
    }

}
