package com.example.reccomendation_ms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class RecommendationServiceTest {

    @Mock
    private RestClient.Builder restClientBuilder;

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    RecommendationService recommendationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recommendationService = new RecommendationService(restClientBuilder);
        when(restClientBuilder.build()).thenReturn(restClient);

        when(restClient.get()).thenReturn(requestHeadersUriSpec);

        when(requestHeadersUriSpec.uri(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.<Object[]>any()

        )).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    public void shouldReturnListOfReccomendationWhenTagIsProvided() {
        // given
        List<Map<String, String>> mockResponse = List.of(
                Map.of("id", "1", "title", "AkademiaSpring.pl", "tag", "java")
        );
        when(responseSpec.body(List.class)).thenReturn(mockResponse);
        // when
        List<Map<String, String>> result = recommendationService.getRecommendationByTag("java");
        // then
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).get("id"));
        assertEquals("AkademiaSpring.pl", result.get(0).get("title"));
        assertEquals("java", result.get(0).get("tag"));
    }

}
