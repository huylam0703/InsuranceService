package app.project.InsuranceService.service.Policy;

import app.project.InsuranceService.dto.request.Policy.PolicyCreationRequest;
import app.project.InsuranceService.dto.request.Policy.PolicyUpdateRequest;
import app.project.InsuranceService.dto.response.Policy.PolicyResponse;

import java.util.List;

public interface PolicyService {

    PolicyResponse createPolicy(PolicyCreationRequest request);

    PolicyResponse getPolicy(String policyId);

    List<PolicyResponse> getAllPolicies();

    PolicyResponse updatePolicy(PolicyUpdateRequest request, String policyId);

    void deletePolicy(String policyId);
}
