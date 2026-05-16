package app.project.InsuranceService.service.Policy.Impl;

import app.project.InsuranceService.dto.request.Policy.PolicyCreationRequest;
import app.project.InsuranceService.dto.request.Policy.PolicyUpdateRequest;
import app.project.InsuranceService.dto.response.Policy.PolicyResponse;
import app.project.InsuranceService.entity.Policy;
import app.project.InsuranceService.enums.PolicyStatus;
import app.project.InsuranceService.enums.PolicyType;
import app.project.InsuranceService.exception.AppException;
import app.project.InsuranceService.exception.ErrorCode;
import app.project.InsuranceService.mapper.PolicyMapper;
import app.project.InsuranceService.repository.PolicyRepository;
import app.project.InsuranceService.service.Policy.PolicyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PolicyServiceImpl implements PolicyService {
    PolicyRepository policyRepository;
    PolicyMapper policyMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PolicyResponse createPolicy(PolicyCreationRequest request) {
        if(policyRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.NAME_POLICY_EXISTS);

        Policy policy = policyMapper.toPolicy(request);

            String policeCode =  generatePolicyCode(request.getPolicyType());
            if(policyRepository.existsByPolicyCode(policeCode))
                throw new AppException(ErrorCode.POLICY_CODE_EXISTS);
            else
                policy.setPolicyCode(policeCode);

        policy.setStatus(PolicyStatus.ACTIVE);

        return policyMapper.toPolicyResponse(policyRepository.save(policy));
    }

    @Override
    public PolicyResponse getPolicy(String policyId) {
        return policyMapper.toPolicyResponse(policyRepository.findById(policyId)
                .orElseThrow(()-> new AppException(ErrorCode.POLICY_NOT_FOUND)));
    }

    @Override
    public List<PolicyResponse> getAllPolicies() {
        return policyRepository.findAll().stream()
                .map(policyMapper::toPolicyResponse).toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PolicyResponse updatePolicy(PolicyUpdateRequest request, String policeId) {
        Policy policy = policyRepository.findById(policeId)
                .orElseThrow(()-> new AppException(ErrorCode.POLICY_NOT_FOUND));

        policyMapper.updatePolicy(request, policy);

        return policyMapper.toPolicyResponse(policyRepository.save(policy));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePolicy(String policyId) {
        policyRepository.deleteById(policyId);
    }


    private String generatePolicyCode(PolicyType type) {

        String prefix = switch (type){
            case HEALTH -> "HLT";
            case LIFE -> "LIF";
            case VEHICLE -> "VEH";
            case TRAVEL -> "TRV";
        };

        long count = policyRepository.count() + 1;

        return prefix + "-" + String.format("%03d", count);
    }
}
