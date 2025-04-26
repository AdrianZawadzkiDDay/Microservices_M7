package com.example.xrp;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class XRPController {
    private final XRPService xrpService;

    public XRPController(XRPService xrpService) {
        this.xrpService = xrpService;
    }

    @GetMapping("/call-xrp")
    @CircuitBreaker(name = "xrpPriceCall", fallbackMethod = "fallbackMethodGetXrpPrice")
    public ResponseEntity<Map<String, Object>> getXrpPrice() {
        return ResponseEntity.ok(xrpService.getXrpPrice());
    }

    public ResponseEntity<Map<String, Object>> fallbackMethodGetXrpPrice(Throwable throwable) {
        return ResponseEntity.ok(xrpService.getCachedXrpPrice());
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("XRP service health");
    }
}
