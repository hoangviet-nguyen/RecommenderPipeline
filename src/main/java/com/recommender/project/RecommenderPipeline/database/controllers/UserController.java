package com.recommender.project.RecommenderPipeline.database.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recommender.project.RecommenderPipeline.data_models.User;
import com.recommender.project.RecommenderPipeline.database.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/nickname")
    public List<User> findByNickName(@RequestParam("nickname") String nickName) {
        return service.findByNickName(nickName);
    }

    @GetMapping("/apiId")
    public User findByApiId(@RequestParam("id") String id) {
        return service.findByApiId(id);
    }

    @GetMapping("/id")
    public Optional<User> findById(@RequestParam("id") Long id) {
        return service.findById(id);
    }

}
