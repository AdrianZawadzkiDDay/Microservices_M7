package com.example.xrp;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class XRPService {
    private final RestClient restClient;
    private final String url = "https://api.coingecko.com/api/v3/simple/price?ids=ripple&vs_currencies=usd";
    private Map<String, Object> cache = new HashMap<>();
    private boolean shouldFail = false;


    public XRPService(RestClient restClient) {
        this.restClient = restClient;
    }

    public Map<String, Object> getXrpPrice() {
        if (shouldFail) {
            shouldFail = false;
            throw new RuntimeException("Simulated XPR API error.");
        }
        shouldFail = true;
        Map<String, Object> response = restClient.get()
                .uri(url)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
        cache = response;
        return response;
    }

    public Map<String, Object> getCachedXrpPrice() {
        return cache.isEmpty() ? Map.of("error", "Empty cache") : cache;

    }
}
