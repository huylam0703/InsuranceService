package app.project.InsuranceService.service.ContractDetails;

import app.project.InsuranceService.dto.response.ContractDetails.HealthContractDetailResponse;
import app.project.InsuranceService.dto.response.ContractDetails.LifeContractDetailResponse;
import app.project.InsuranceService.dto.response.ContractDetails.TravelContractDetailResponse;
import app.project.InsuranceService.dto.response.ContractDetails.VehicleContractDetailResponse;

public interface ContractDetailsService {

    HealthContractDetailResponse getHealthContractDetail(String id);

    LifeContractDetailResponse getLifeContractDetail(String id);

    TravelContractDetailResponse getTravelContractDetail(String id);

    VehicleContractDetailResponse getVehicleContractDetail(String id);

}
