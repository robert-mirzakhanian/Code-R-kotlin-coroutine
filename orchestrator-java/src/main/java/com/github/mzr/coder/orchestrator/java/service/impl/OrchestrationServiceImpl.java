package com.github.mzr.coder.orchestrator.java.service.impl;

import com.github.mzr.coder.model.ServiceRequest;
import com.github.mzr.coder.model.ServiceResponse;
import com.github.mzr.coder.model.orhestrator.OrchestratorRequest;
import com.github.mzr.coder.model.orhestrator.OrchestratorResponse;
import com.github.mzr.coder.orchestrator.java.service.DecisionService;
import com.github.mzr.coder.orchestrator.java.service.OrchestrationService;
import com.github.mzr.coder.orchestrator.java.service.RiskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrchestrationServiceImpl implements OrchestrationService {

    private final DecisionService decisionService;

    private final List<RiskService> riskServices;

    private final Executor executor;

    @Override
    public OrchestratorResponse getDecision(OrchestratorRequest request) {
        var strategy = decisionService.getStrategy(request.getName());
        var servicesByStrategy = getServiceByStrategy(strategy);
        var completableFutureList = getCompletableFutureList(request, servicesByStrategy);
        CompletableFuture.allOf(completableFutureList.toArray(CompletableFuture[]::new)).join();
        List<ServiceResponse> serviceResponses = mapCompletableFutureToServiceResponse(completableFutureList);
        ServiceResponse decision = decisionService.getDecision(request.getName(), serviceResponses);
        return OrchestratorResponse.builder().approve(decision.getApprove()).build();
    }

    private List<ServiceResponse> mapCompletableFutureToServiceResponse(
            List<CompletableFuture<ServiceResponse>> completableFutureList
    ) {
        return completableFutureList.stream().map(cf -> cf.join()).collect(Collectors.toList());

    }

    private List<RiskService> getServiceByStrategy(List<String> strategy) {
        return riskServices.stream().filter(rs -> strategy.contains(rs.getName())).collect(Collectors.toList());
    }

    private List<CompletableFuture<ServiceResponse>> getCompletableFutureList(
            OrchestratorRequest request,
            List<RiskService> services
    ) {
        var serviceRequest = ServiceRequest.builder().name(request.getName()).build();
        return services.stream()
                .map(service -> CompletableFuture.supplyAsync(() -> service.execute(serviceRequest), executor))
                .collect(Collectors.toList());
    }
}
