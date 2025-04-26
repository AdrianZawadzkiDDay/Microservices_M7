package com.example.spring_cloud_config_server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
//@EnableScheduling
public class RefreshScheduler {
    private final RestTemplate restTemplate;

    @Autowired
    public RefreshScheduler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//    @Scheduled(fixedRate = 60000)
    public void refreshEndpoints() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            restTemplate.exchange(
                    "http://localhost:8080/actuator/refresh",
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            restTemplate.exchange(
                    "http://localhost:8888/actuator/refresh",
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            System.out.println("Refresh executed successfully at: " + java.time.LocalDateTime.now());

        } catch (Exception e) {
            System.err.println("Error during refresh: " + e.getMessage());
        }
    }
}
