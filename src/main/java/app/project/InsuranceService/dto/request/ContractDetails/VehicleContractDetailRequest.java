package app.project.InsuranceService.dto.request.ContractDetails;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Year;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehicleContractDetailRequest {

    @NotBlank(message = "VEHICLE_TYPE")
    String vehicleType;

    @NotBlank(message = "LICENSE_PLATE")
    String licensePlate;

    @NotBlank(message = "BRAND_VEHICLE")
    String brand;

    @NotBlank(message = "MODEL_VEHICLE")

    String model;

    @NotNull(message = "MANUFACTURE_YEAR_VEHICLE")
    @Min(value = 1900,
            message = "MANUFACTURE_YEAR_VALID")
    Integer manufactureYear;

    @NotBlank(message = "CHASSIS_NUMBER_VEHICLE")
    String chassisNumber;

    @NotBlank(message = "ENGINE_NUMBER_VEHICLE")
    String engineNumber;

    @AssertTrue(message = "IS_MANUFACTURE_YEAR_VALID")
    public boolean isManufactureYearValid() {
        if (manufactureYear == null) {
            return true;
        }
        return manufactureYear <= Year.now().getValue();
    }
}
