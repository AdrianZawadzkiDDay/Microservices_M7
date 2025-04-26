package com.example.reccomendation_ms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class RecommendationService {

//    @Autowired
    private RestClient.Builder builder;

//    @Autowired
    public RecommendationService(RestClient.Builder builder) {
        this.builder = builder;
    }

    public List<Map<String, String>> getRecommendationByTag(String tag) {
        return builder.build()
                .get()
                .uri("http://CONTENT/api/content/{tag}", tag)
                .retrieve()
                .body(List.class);
    }
}
