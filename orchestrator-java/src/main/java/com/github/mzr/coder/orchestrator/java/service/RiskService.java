package com.github.mzr.coder.orchestrator.java.service;

import com.github.mzr.coder.model.ServiceRequest;
import com.github.mzr.coder.model.ServiceResponse;

public interface RiskService {
    String getName();
    ServiceResponse execute(ServiceRequest request);
}
