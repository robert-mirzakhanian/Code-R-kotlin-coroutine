package com.github.mzr.coder.orchestrator.java.reactive.service.impl;

import com.github.mzr.coder.model.ServiceRequest;
import com.github.mzr.coder.model.ServiceResponse;
import com.github.mzr.coder.model.orhestrator.OrchestratorRequest;
import com.github.mzr.coder.model.orhestrator.OrchestratorResponse;
import com.github.mzr.coder.orchestrator.java.reactive.service.DecisionService;
import com.github.mzr.coder.orchestrator.java.reactive.service.RiskService;
import com.github.mzr.coder.orchestrator.java.reactive.service.OrchestrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrchestrationServiceImpl implements OrchestrationService {

    private final DecisionService decisionService;

    private final List<RiskService> riskServices;

    @Override
    public Mono<OrchestratorResponse> getDecision(OrchestratorRequest request) {
        return decisionService.getStrategy(request.getName())
                .flatMapIterable(list -> getServicesByStrategy(list))
//                .parallel()
                .flatMap(service -> executeService(request, service))
//                .sequential()
                .collectList()
                .flatMap(list -> decisionService.getDecision(request.getName(), list))
                .map(decisionResponse -> OrchestratorResponse.builder().approve(decisionResponse.getApprove()).build());
    }

    private List<RiskService> getServicesByStrategy(List<String> strategy) {
        return riskServices.stream().filter(rs -> strategy.contains(rs.getName())).collect(Collectors.toList());
    }

    private Mono<ServiceResponse> executeService(OrchestratorRequest request, RiskService service) {
        return service.execute(ServiceRequest.builder().name(request.getName()).build());
    }

}
