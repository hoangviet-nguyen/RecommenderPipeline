package com.recommender.project.RecommenderPipeline.database.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recommender.project.RecommenderPipeline.data_models.Anime;
import com.recommender.project.RecommenderPipeline.database.services.AnimeService;

@RestController
@RequestMapping("/anime")
public class AnimeController {
    
    @Autowired
    private AnimeService service;

    @GetMapping("/search")    
    public List<Anime> findAnimeByTitle(@RequestParam("title") String title) {
        return service.findAnimeByTitel(title);
    }

    @GetMapping("/id")
    public Optional<Anime> findAnimeById(@RequestParam("id") Long id) {
        return service.findAnimeById(id);
    }

    @GetMapping("/all")
    public List<Anime> getAllAnimes() {
        return service.getAllAnimes();
    }
}
