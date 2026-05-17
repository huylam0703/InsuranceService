package app.project.InsuranceService.dto.response.ContractDetails;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehicleContractDetailResponse {
    String id;

    String contractId;

    String vehicleType;

    String licensePlate;

    String brand;

    String model;

    Integer manufactureYear;

    String chassisNumber;

    String engineNumber;

    String vehicleImageUrl;
}
