package com.recommender.project.RecommenderPipeline;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import java.net.http.HttpRequest.BodyPublisher;

import com.google.gson.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.recommender.project.RecommenderPipeline.data_models.Anime;
import com.recommender.project.RecommenderPipeline.data_models.wrapper.APIUser;
import com.recommender.project.RecommenderPipeline.data_models.wrapper.APIRating;

public class AnimeAPI {
    
    private static final String API_ENDPOINT = "https://shikimori.one/api/graphql";
    private static final HttpClient client = HttpClient.newBuilder().build();
    private static final  ObjectMapper objectMapper = new ObjectMapper();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static AnimeAPI api;
    private static final Type animeType = new TypeToken<List<Anime>>(){}.getType();
    private static final Type userType = new TypeToken<List<APIUser>>(){}.getType();
    private static final Type userRateType = new TypeToken<List<APIRating>>(){}.getType();

    private AnimeAPI() {}

    public static AnimeAPI getAnimeAPI() {
        if (api == null) { api = new AnimeAPI();}
        return api;
    }

/*
* ===========================================================================================
*                                         Anime Queries
* ===========================================================================================
*/
    private static JsonObject requestData(String query) {
        try {
            String requestBody = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(new Query(query));
            
            BodyPublisher body = HttpRequest.BodyPublishers.ofString(requestBody);
            HttpRequest request = HttpRequest
                .newBuilder(new URI(API_ENDPOINT))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(body)
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return JsonParser
                .parseString(response.body())
                .getAsJsonObject()
                .getAsJsonObject("data");
        } catch (IOException e) {
            System.out.println("Could not send or receive data due to IO");
        } catch (InterruptedException e) {
            System.out.println("The thread making the request was interrupted");
        } catch (URISyntaxException e) {
            System.out.println("The given url is invalid");
            throw new RuntimeException();
        }
        throw new RuntimeException();
    }


    public List<Anime> getAnimesByTitle(String title, int limit) {
        assert limit > 0;
        String queryString = String.format("""
            query {
                animes(search: "%s", limit: %d) {
                    id
                    score
                    name
                    poster { mainAltUrl }
                    description
                }
            }
            """, 
            title, limit
        );
        var data = requestData(queryString);
        List<Anime> animes = gson.fromJson(data.get("animes"), animeType);
        return animes; 
    }

    public Anime getAnimeById(String id) {
        String queryString = String.format("""
            query {
                animes(ids: "%s") {
                    id
                    score
                    name
                    poster { mainAltUrl }
                    description
                }
            }
            """,
            id
        );
        
        var data = requestData(queryString);
        List<Anime> animes = gson.fromJson(data.get("animes"), animeType);
        return animes.get(0);
    }

    public List<Anime> getRandomAnimes(int limit) {
        assert limit > 0;

         String queryString = String.format("""
            query {
                animes(order: random, limit: %d) {
                    id
                    score
                    name
                    poster { mainAltUrl }
                    description
                }
            }
            """, limit
        );

        var data = requestData(queryString);
        List<Anime> animes = gson.fromJson(data.get("animes"), animeType);
        return animes;
    }

    public Anime getRecommendation() {
        return null;
    }

/*
* ===========================================================================================
*                                         User Queries
* ===========================================================================================
*/

    public List<APIUser> getUsers(int numPages, int limit) {
        assert numPages > 0 && limit > 0;
        List<APIUser> allUsers = new ArrayList<>(numPages * limit);
        for (int page = 1; page <= numPages; page++) {
            String queryString = String.format("""
                query {
                    users(page: %d, limit: %d) {
                        id
                        nickname
                    }
                }
                """, page, limit
            );

            var data = requestData(queryString);
            List<APIUser> users = gson.fromJson(data.get("users"), userType);
            allUsers.addAll(users);
        }
        return allUsers;
    }

    public List<APIRating> getUserRatings(String userId, int limit) {
        String queryString = String.format("""
            query {
                userRates(userId: "%s", page: 1, limit: %d, targetType: Anime, order: { field: updated_at, order: desc }) {
                    id
                    anime { id }
                    score
                }
            }
            """, userId, limit
        );
        
        var data = requestData(queryString);
        List<APIRating> rawRates = gson.fromJson(data.get("userRates"), userRateType);
        return rawRates;
    }

/*
* ===========================================================================================
*                                         Utility Class
* ===========================================================================================
*/
    static class Query {
        private final String query;
        private final String variables;

        public Query(String query, String variables) {
            this.query = query;
            this.variables = variables;
        }

        public Query(String query) {
            this(query, null);
        }

        public String getQuery() {
            return query;
        }

        public String getVariables() {
            return variables;
        }
    }

}