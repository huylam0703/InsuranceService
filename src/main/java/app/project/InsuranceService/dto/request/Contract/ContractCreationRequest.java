package app.project.InsuranceService.dto.request.Contract;

import app.project.InsuranceService.dto.request.ContractDetails.HealthContractDetailRequest;
import app.project.InsuranceService.dto.request.ContractDetails.LifeContractDetailRequest;
import app.project.InsuranceService.dto.request.ContractDetails.TravelContractDetailRequest;
import app.project.InsuranceService.dto.request.ContractDetails.VehicleContractDetailRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractCreationRequest {
    String policy_id;

    HealthContractDetailRequest healthDetail;

    LifeContractDetailRequest lifeDetail;

    VehicleContractDetailRequest vehicleDetail;

    TravelContractDetailRequest travelDetail;
}
