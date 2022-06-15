package com.github.mzr.coder.orchestrator.java.reactive.service.impl;

import com.github.mzr.coder.model.ServiceRequest;
import com.github.mzr.coder.model.ServiceResponse;
import com.github.mzr.coder.orchestrator.java.reactive.config.UrlConfig;
import com.github.mzr.coder.orchestrator.java.reactive.service.RiskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class RiskService1Impl implements RiskService {

    private final WebClient.Builder webClientBuilder;
    private final UrlConfig urlConfig;

    @Override
    public String getName() {
        return "service1";
    }

    @Override
    public Mono<ServiceResponse> execute(ServiceRequest request) {
        var name = request.getName();
        var mono = Mono.just(request);
        log.info("Trying get info from {} for request with name {}", getName(), name);
        return webClientBuilder.baseUrl(urlConfig.getService1())
                .build()
                .post()
                .uri("/info")
                .body(mono, ServiceRequest.class)
                .retrieve()
                .bodyToMono(ServiceResponse.class)
                .doOnSuccess(sr ->
                        log.info("Got info from {} for request with name {} and approve {}",
                                getName(),
                                name,
                                sr.getApprove()
                        )
                );
    }
}
