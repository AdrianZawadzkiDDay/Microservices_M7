package com.example.content;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    private final ContentRepository contentRepository;
    private final RestClient ms1RestClient;
    private final Tracer tracer;

    public ContentController(ContentRepository contentRepository, RestClient ms1RestClient, Tracer tracer) {
        this.contentRepository = contentRepository;
        this.ms1RestClient = ms1RestClient;
        this.tracer = tracer;
    }

    private List<Map<String, String>> mapList
            = List.of(
            Map.of("id", "1", "title", "AkademiaSpring.pl", "tag", "spring"),
            Map.of("id", "2", "title", "ArchitektIT.pl", "tag", "cloud"),
            Map.of("id", "3", "title", "Docker i Spring Cloud", "tag", "cloud")
    );

    @GetMapping
    public List<Content> getContent() {
        return contentRepository.findAll();
    }

    @GetMapping("/{tag}")
    public List<Content> getContentByTag(@PathVariable String tag) {
        return contentRepository.findByTagIgnoreCase(tag);
    }

    @GetMapping("/call-xrp")
    public ResponseEntity<Map<String, Object>> callXrpService(@RequestParam String item) {
        Span span = tracer.nextSpan().name("get-xrp-price").start();
        span.tag("item", item);

        try (Tracer.SpanInScope ws = tracer.withSpan(span)) {
            Map response = ms1RestClient.get()
                    .uri("/service-1")
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

        Span ms2Span = tracer.nextSpan().name("ms2-health").start();
        try {
            Thread.sleep(500);
            String response = ms1RestClient.get()
                    .uri("http://localhost:8085/health")
                    .retrieve()
                    .body(String.class);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            ms2Span.end();
        }

        Span span = tracer.nextSpan().name("health-check").start();
        span.tag("ms1-tag", "xrp-ms-call");

        try (Tracer.SpanInScope ws = tracer.withSpan(span)) {
            String response = ms1RestClient.get()
                    .uri("/health")
                    .retrieve()
                    .body(String.class);
            return ResponseEntity.ok(response); // Zwraca "XRP service health"
        } catch (Exception e) {
            span.tag("error", e.getMessage());
            throw e;
        } finally {
            span.end();
        }
    }
}
