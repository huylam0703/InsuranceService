package app.project.InsuranceService.dto.response.Policy;

import app.project.InsuranceService.enums.PolicyStatus;
import app.project.InsuranceService.enums.PolicyType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PolicyResponse {
    String id;

    String policyCode;

    String name;

    PolicyType policyType;

    String description;

    BigDecimal coverageAmount;

    BigDecimal premiumAmount;

    Integer durationMonths;

    Integer claimLimit;

    PolicyStatus status;
}
