package com.recommender.project.RecommenderPipeline;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.recommender.project.RecommenderPipeline.data_models.Anime;
import com.recommender.project.RecommenderPipeline.data_models.Rating;
import com.recommender.project.RecommenderPipeline.data_models.User;
import com.recommender.project.RecommenderPipeline.data_models.Rating.APIRating;
import com.recommender.project.RecommenderPipeline.database.repositories.AnimeRepository;
import com.recommender.project.RecommenderPipeline.database.repositories.RatingRepository;
import com.recommender.project.RecommenderPipeline.database.repositories.UserRepository;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private AnimeAPI api = AnimeAPI.getAnimeAPI();


    @Bean
    CommandLineRunner initDatabase(AnimeRepository animeRepo, UserRepository userRepo, RatingRepository ratingRepo) {
        return args -> {
            if (animeRepo.count() == 0 && userRepo.count() == 0 && ratingRepo.count() == 0) {
                // load users from api
                int userPages = 20;
                int userLimit = 50;
                List<User> users = api.getUsers(userPages, userLimit);
                log.info("Saving Users to db");
                userRepo.saveAll(users);

                // load ratings and animes from api
                int ratingPages = 1;
                int ratingLimit = 20;
                List<Rating> ratings = new ArrayList<>(ratingLimit * userPages * userLimit);
                Set<Anime> animes = new HashSet<>();
                int[] numUser = {1};
                Set<String> savedApiIds = new HashSet<>();
                users.forEach(user -> {
                    try {

                        if (numUser[0] % 10 == 0) {
                            log.info("Saving Animes to db");
                            animeRepo.saveAll(animes);
                            log.info("Saving Ratings to db");
                            ratingRepo.saveAll(ratings);
                            log.info("Anime count: {}", animeRepo.count());
                            log.info("User count: {}", userRepo.count());
                            log.info("Rating count: {}", ratingRepo.count());
                            animes.clear();
                            ratings.clear();
                        }

                        List<APIRating> userRatings = api.getUserRatings(user, ratingPages, ratingLimit);
                        Set<Anime> animesMapped = userRatings
                            .stream()
                            .map(APIRating::anime)
                            .filter(a -> savedApiIds.add(a.getApiId()))
                            .collect(Collectors.toSet()
                        );
                        List<Rating> ratingsMapped = userRatings.stream().map(rating -> new Rating(user.getDbId(), rating)).toList();
                        ratings.addAll(ratingsMapped);
                        animes.addAll(animesMapped);
                        log.info("User: " +numUser[0]+ " processed");
                        numUser[0]++;

                    } catch (InterruptedException e) {
                        System.out.println(e);
                        throw new  RuntimeException();
                    }
                });
            }
        };
    }
}
