package app.project.InsuranceService.service.ContractDetails;

import app.project.InsuranceService.dto.response.ContractDetails.HealthContractDetailResponse;

public interface ContractDetailsService {

    HealthContractDetailResponse getHealthContractDetail(String id);

}
