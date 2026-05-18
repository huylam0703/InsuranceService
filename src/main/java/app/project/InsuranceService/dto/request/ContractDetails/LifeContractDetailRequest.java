package app.project.InsuranceService.dto.request.ContractDetails;

import jakarta.validation.constraints.*;
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

    @NotBlank(message = "OCCUPATION_LIFE")
    String occupation;

    @NotNull(message = "ANNUAL_INCOME_LIFE")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "ANNUAL_INCOME")
    @Digits(integer = 15, fraction = 2,
            message = "ANNUAL_INCOME_INVALID")
    BigDecimal annualIncome;

    @NotBlank(message = "BENEFICIARY_NAME_LIFE")
    String beneficiaryName;

    @NotBlank(message = "BENEFICIARY_RELATIONSHIP_LIFE")
    String beneficiaryRelationship;

    @NotBlank(message = "BENEFICIARY_PHONE_LIFE")
    @Pattern(
            regexp = "^(0|\\+84)[0-9]{9,10}$",
            message = "BENEFICIARY_PHONE_INVALID"
    )
    String beneficiaryPhone;

    @Size(max = 1000,
            message = "MEDICAL_HISTORY_LIFE")

    String medicalHistory;

    @NotNull(message = "SMOKING_LIFE")
    Boolean smoking;
}
