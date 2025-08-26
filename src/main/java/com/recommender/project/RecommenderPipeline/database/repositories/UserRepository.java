package com.recommender.project.RecommenderPipeline.database.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.recommender.project.RecommenderPipeline.data_models.User;

public interface UserRepository extends CrudRepository <User, Long> {
    Optional<User> findById(Long id);
    List<User> findByNickName(String nickName);
    User findByApiId(String apiId);
}
