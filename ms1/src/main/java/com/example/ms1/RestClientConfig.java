package com.example.ms1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.Span;

@Configuration
public class RestClientConfig {
    private final Tracer tracer;

    public RestClientConfig(Tracer tracer) {
        this.tracer = tracer;
    }

    @Bean
    public RestClient RestClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8010")
                .requestInterceptor((request, body, execution) -> {
                    Span span = tracer.currentSpan();
                    if (span != null) {
                        request.getHeaders().add("X-B3-TraceId", span.context().traceId());
                        request.getHeaders().add("X-B3-SpanId", span.context().spanId());
                        request.getHeaders().add("X-B3-Sampled", "1");
                    }
                    return execution.execute(request, body);
                })
                .build();
    }
}
