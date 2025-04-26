package com.example.content;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Map;

//@RestController
//@RequestMapping("/api/xrp")
public class XRPController {
//    private final RestClient ms1RestClient;
//    private final Tracer tracer;
//
//    public XRPController(RestClient ms1RestClient, Tracer tracer) {
//        this.ms1RestClient = ms1RestClient;
//        this.tracer = tracer;
//    }
//
//    @GetMapping("/call-xrp")
//    public ResponseEntity<Map<String, Object>> callXrpService(@RequestParam String item) {
//        Span span = tracer.nextSpan().name("get-xrp-price").start();
//        span.tag("item", item);
//
//        try (Tracer.SpanInScope ws = tracer.withSpan(span)) {
//            Map response = ms1RestClient.get()
//                    .uri("/service-1")
//                    .retrieve()
//                    .body(Map.class);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            span.tag("error", e.getMessage());
//            throw e;
//        } finally {
//            span.end();
//        }
//    }
}
