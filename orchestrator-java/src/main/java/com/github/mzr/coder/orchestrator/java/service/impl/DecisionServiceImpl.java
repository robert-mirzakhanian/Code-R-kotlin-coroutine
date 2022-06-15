package com.github.mzr.coder.orchestrator.java.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mzr.coder.model.ServiceResponse;
import com.github.mzr.coder.orchestrator.java.config.UrlConfig;
import com.github.mzr.coder.orchestrator.java.service.DecisionService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DecisionServiceImpl implements DecisionService {

    private final RestTemplate restTemplate;

    private final UrlConfig urlConfig;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public List<String> getStrategy(String name) {
        log.info("Trying get strategy for request with name {}", name);
        var responseEntity = restTemplate.getForEntity(
                urlConfig.getDecisionService() + "/strategy",
                String[].class
        );
        log.info("Got strategy for request with name {}", name);
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            var body = responseEntity.getBody();
            log.info("List services {}", objectMapper.writeValueAsString(body));
            return Arrays.stream(body).toList();
        }
        return null;
    }

    @Override
    public ServiceResponse getDecision(String name, List<ServiceResponse> serviceResponses) {
        log.info("Trying get decision for request with name {}", name);
        var responseEntity = restTemplate.postForEntity(
                urlConfig.getDecisionService() + "/decision",
                serviceResponses,
                ServiceResponse.class
        );
        log.info("Got decision for request with name {}", name);
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            return responseEntity.getBody();
        }
        return null;
    }
}
