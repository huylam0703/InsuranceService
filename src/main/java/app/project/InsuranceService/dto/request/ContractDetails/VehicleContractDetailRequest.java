package app.project.InsuranceService.dto.request.ContractDetails;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehicleContractDetailRequest {
    String vehicleType;

    String licensePlate;

    String brand;

    String model;

    Integer manufactureYear;

    String chassisNumber;

    String engineNumber;

    String vehicleImageUrl;
}
