package com.github.mzr.coder.orchestrator.java.reactive.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mzr.coder.model.ServiceResponse;
import com.github.mzr.coder.orchestrator.java.reactive.config.UrlConfig;
import com.github.mzr.coder.orchestrator.java.reactive.service.DecisionService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DecisionServiceImpl implements DecisionService {

    private final WebClient.Builder webClientBuilder;

    private final UrlConfig urlConfig;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public Mono<List<String>> getStrategy(String name) {
        log.info("Trying get strategy for request with name {}", name);
        return webClientBuilder.baseUrl(urlConfig.getDecisionService())
                .build()
                .get()
                .uri("/strategy")
                .retrieve()
                .bodyToMono(String[].class)
                .map(array -> Arrays.stream(array).collect(Collectors.toList()))
                .doOnSuccess(services -> {
                            try {
                                log.info("Got strategy for request with name {}. List services {}",
                                        name,
                                        objectMapper.writeValueAsString(services)
                                );
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
    }

    @Override
    public Mono<ServiceResponse> getDecision(String name, List<ServiceResponse> serviceResponses) {
        var monoBody = Mono.just(serviceResponses)
                .doOnSuccess(x -> log.info("Trying get decision for request with name {}", name));
        return webClientBuilder.baseUrl(urlConfig.getDecisionService())
                .build()
                .post()
                .uri("/decision")
                .body(monoBody, List.class)
                .retrieve()
                .bodyToMono(ServiceResponse.class)
                .doOnSuccess(sr -> log.info("Got decision for request with name {}, approve {}", name, sr.getApprove()));

    }
}
