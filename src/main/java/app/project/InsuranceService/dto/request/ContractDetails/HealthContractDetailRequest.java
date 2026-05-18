package app.project.InsuranceService.dto.request.ContractDetails;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HealthContractDetailRequest {

    @NotBlank(message = "HEALTH_HEIGHT_CONTRACT")
    @DecimalMin(value = "50.0", message = "MIN_HEIGHT_HEALTH")
    @DecimalMax(value = "300.0", message = "MAX_HEIGHT_HEALTH")
    Double heightCm;

    @NotBlank(message = "HEALTH_WEIGHT_CONTRACT")
    Double weightKg;

    @NotBlank(message = "BLOOD_TYPE_HEALTH")
    @Pattern(
            regexp = "^(A|B|AB|O)[+-]$",
            message = "BLOOD_TYPE"
    )
    String bloodType;

    @Size(max = 1000, message = "MEDICAL_HISTORY_HEALTH")
    String medicalHistory;

    @Size(max = 500, message = "ALLERGIES_HEALTH")
    String allergies;

    @NotNull(message = "SMOKING_HEALTH")
    Boolean smoking;

    @NotNull(message = "ALCOHOL_USE_HEALTH")
    Boolean alcoholUse;
}
