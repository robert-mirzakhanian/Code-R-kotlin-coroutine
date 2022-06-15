package com.github.mzr.coder.orchestrator.java.reactive.controller;

import com.github.mzr.coder.model.orhestrator.OrchestratorRequest;
import com.github.mzr.coder.model.orhestrator.OrchestratorResponse;
import com.github.mzr.coder.orchestrator.java.reactive.service.OrchestrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrchestrationController {

    private final OrchestrationService service;

    @PostMapping("/decision")
    public Mono<OrchestratorResponse> getDecision(@RequestBody OrchestratorRequest orchestratorRequest) {
        log.info("Get request with name {} for decision", orchestratorRequest.getName());
        var orchestratorResponse = service.getDecision(orchestratorRequest);
        return orchestratorResponse
                .doOnSuccess(x ->
                        log.info("Got decision for name {}, decision {}", orchestratorRequest.getName(), x.getApprove())
                );
    }
}
