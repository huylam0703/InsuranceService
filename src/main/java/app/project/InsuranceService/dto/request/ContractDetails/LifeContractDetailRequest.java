package app.project.InsuranceService.dto.request.ContractDetails;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LifeContractDetailRequest {
    String occupation;

    BigDecimal annualIncome;

    String beneficiaryName;

    String beneficiaryRelationship;

    String beneficiaryPhone;

    String medicalHistory;

    Boolean smoking;
}
