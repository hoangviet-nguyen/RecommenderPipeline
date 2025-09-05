package com.recommender.project.RecommenderPipeline.data_models;

import com.google.gson.annotations.SerializedName;

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
    private Long dbId;
    private String apiId;
    private String nickName;
    private String password;
    
    public User(APIUser user) {
        this.apiId = user.userId();
        this.nickName = user.nickname();
    }

    @Override
    public String toString() {
        return String.format("[DbId: %d, ApiID: %s, Nickname: %s]", dbId, apiId, nickName);
    }


    public record APIUser(@SerializedName("id") String userId, String nickname) {}
}
