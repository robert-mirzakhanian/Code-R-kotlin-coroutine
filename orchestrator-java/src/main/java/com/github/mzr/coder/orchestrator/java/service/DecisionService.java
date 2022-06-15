package com.github.mzr.coder.orchestrator.java.service;

import com.github.mzr.coder.model.ServiceResponse;

import java.util.List;

public interface DecisionService {
    List<String> getStrategy(String name);
    ServiceResponse getDecision(String name, List<ServiceResponse> serviceResponses);
}
