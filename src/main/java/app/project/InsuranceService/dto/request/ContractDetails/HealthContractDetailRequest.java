package app.project.InsuranceService.dto.request.ContractDetails;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HealthContractDetailRequest {
    Double heightCm;

    Double weightKg;

    String bloodType;

    String medicalHistory;

    String allergies;

    Boolean smoking;

    Boolean alcoholUse;
}
