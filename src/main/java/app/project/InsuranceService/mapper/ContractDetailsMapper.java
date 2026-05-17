package app.project.InsuranceService.mapper;

import app.project.InsuranceService.dto.request.ContractDetails.HealthContractDetailRequest;
import app.project.InsuranceService.dto.request.ContractDetails.LifeContractDetailRequest;
import app.project.InsuranceService.dto.request.ContractDetails.TravelContractDetailRequest;
import app.project.InsuranceService.dto.request.ContractDetails.VehicleContractDetailRequest;
import app.project.InsuranceService.dto.response.ContractDetails.HealthContractDetailResponse;
import app.project.InsuranceService.dto.response.ContractDetails.LifeContractDetailResponse;
import app.project.InsuranceService.dto.response.ContractDetails.TravelContractDetailResponse;
import app.project.InsuranceService.dto.response.ContractDetails.VehicleContractDetailResponse;
import app.project.InsuranceService.entity.DetailsContractType.HealthContractDetails;
import app.project.InsuranceService.entity.DetailsContractType.LifeContractDetails;
import app.project.InsuranceService.entity.DetailsContractType.TravelContractDetails;
import app.project.InsuranceService.entity.DetailsContractType.VehicleContractDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContractDetailsMapper {
    //Heal Contract Details

    HealthContractDetails toHealthContractDetail(HealthContractDetailRequest request);

    @Mapping(source = "contract.contractId", target = "contractId")
    HealthContractDetailResponse toHealthResponse(HealthContractDetails detail);

    // Life Contract Details
    LifeContractDetails toLifeContractDetail(LifeContractDetailRequest request);

    @Mapping(source = "contract.contractId", target = "contractId")
    LifeContractDetailResponse toLifeResponse(LifeContractDetails detail);

    //vehicle contract details
    VehicleContractDetails toVehicleContractDetail(VehicleContractDetailRequest request);

    @Mapping(source = "contract.contractId", target = "contractId")
    VehicleContractDetailResponse toVehicleResponse(VehicleContractDetails detail);

    //travel contract details
    TravelContractDetails toTravelContractDetail(TravelContractDetailRequest request);

    @Mapping(source = "contract.contractId", target = "contractId")
    TravelContractDetailResponse toTravelResponse(TravelContractDetails detail);
}
