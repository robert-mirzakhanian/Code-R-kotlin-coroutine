package com.github.mzr.coder.orchestrator.java.service.impl;

import com.github.mzr.coder.model.ServiceRequest;
import com.github.mzr.coder.model.ServiceResponse;
import com.github.mzr.coder.orchestrator.java.config.UrlConfig;
import com.github.mzr.coder.orchestrator.java.service.RiskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class RiskService3Impl implements RiskService {

    private final RestTemplate restTemplate;
    private final UrlConfig urlConfig;

    @Override
    public String getName() {
        return "service3";
    }

    @Override
    public ServiceResponse execute(ServiceRequest request) {
        var name = request.getName();
        log.info("Trying get info from {} for request with name {}", getName(), name);
        var responseEntity = restTemplate.postForEntity(
                urlConfig.getService3() + "/info",
                request,
                ServiceResponse.class
        );
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            var body = responseEntity.getBody();
            log.info("Got info from {} for request with name {} and approve {}", getName(), name, body.getApprove());
            return body;

        }
        log.info("Got unexpected code from {} for request with name {}", getName(), name);
        return null;
    }
}
