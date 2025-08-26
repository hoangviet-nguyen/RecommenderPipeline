package com.recommender.project.RecommenderPipeline.database.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recommender.project.RecommenderPipeline.AnimeAPI;
import com.recommender.project.RecommenderPipeline.data_models.User;
import com.recommender.project.RecommenderPipeline.data_models.wrapper.APIUser;
import com.recommender.project.RecommenderPipeline.database.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repository;
    private AnimeAPI api = AnimeAPI.getAnimeAPI();

    public List<User> getAllUsers() {
        Iterable<User> found = repository.findAll();
        List<User> allUsers = new ArrayList<>();
        found.forEach(user -> allUsers.add(user));
        return allUsers;
    }

    public List<User> findByNickName(String nickName) {
        return repository.findByNickName(nickName);
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    public User findByApiId(String apiId) {
        return repository.findByApiId(apiId);
    }

    public void initDataBase(int numPages, int limit) {
        if (repository.count() == 0) {
            List<APIUser> apiUsers = api.getUsers(numPages, limit);
            apiUsers.forEach(user -> { repository.save(new User(user));});
        }
    }
}
