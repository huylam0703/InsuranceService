package app.project.InsuranceService.dto.request.Policy;

import app.project.InsuranceService.enums.PolicyStatus;
import app.project.InsuranceService.enums.PolicyType;
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
public class PolicyCreationRequest {

    @NotBlank(message = "POLICY_NAME")
    String name;

    @NotNull(message = "POLICY_TYPE")
    PolicyType policyType;

    @NotBlank(message = "POLICY_DESCRIPTION")
    String description;

    @NotNull(message = "COVERAGE_AMOUNT")
    @DecimalMin(
            value = "0.0",
            inclusive = false,
            message = "COVERAGE_AMOUNT_VALID"
    )
    @Digits(integer = 15, fraction = 2,
            message = "COVERAGE_AMOUNT_FORMAT")
    BigDecimal coverageAmount;

    @NotNull(message = "PREMIUM_AMOUNT")
    @DecimalMin(
            value = "0.0",
            inclusive = false,
            message = "PREMIUM_AMOUNT_VALID"
    )
    @Digits(integer = 15, fraction = 2,
            message = "PREMIUM_AMOUNT_FORMAT")
    BigDecimal premiumAmount;

    @Min(value = 1,
            message = "DURATION_MONTHS_MIN")
    @Max(value = 600,
            message = "DURATION_MONTHS_MAX")
    int durationMonths;

    @Min(value = 1,
            message = "CLAIM_LIMIT_MIN")
    @Max(value = 1000,
            message = "CLAIM_LIMIT_MAX")
    int claimLimit;

    @AssertTrue(message = "IS_PREMIUM_VALID")
    public boolean isPremiumValid() {
        if (premiumAmount == null || coverageAmount == null) {
            return true;
        }

        return premiumAmount.compareTo(coverageAmount) <= 0;
    }

}
