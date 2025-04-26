package com.example.ms1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.Span;

import java.util.Map;

@RestController
public class Ms1Controller {
    private final RestClient restClient;
    private final Tracer tracer;

    public Ms1Controller(RestClient restClient, Tracer tracer) {
        this.restClient = restClient;
        this.tracer = tracer;
    }

    @GetMapping("/service-1")
    public ResponseEntity<Map<String, Object>> callXrpService() {
        Span span = tracer.nextSpan().name("get-xrp-price").start();
        span.tag("ms1-tag", "xrp-ms-call");

        try (Tracer.SpanInScope ws = tracer.withSpan(span)) {
            Map response = restClient.get()
                    .uri("/call-xrp")
                    .retrieve()
                    .body(Map.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            span.tag("error", e.getMessage());
            throw e;
        } finally {
            span.end();
        }
    }
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        Span span = tracer.nextSpan().name("health-check").start();
        span.tag("ms1-tag", "xrp-ms-call");

        try (Tracer.SpanInScope ws = tracer.withSpan(span)) {
            String response = restClient.get()
                    .uri("/health")
                    .retrieve()
                    .body(String.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            span.tag("error", e.getMessage());
            throw e;
        } finally {
            span.end();
        }
    }
}

