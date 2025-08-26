package com.recommender.project.RecommenderPipeline.data_models;

import com.recommender.project.RecommenderPipeline.data_models.wrapper.APIUser;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String apiId;
    private String nickName;
    private String password;
    
    public User(APIUser user) {
        this.apiId = user.userId();
        this.nickName = user.nickname();
    }
}
