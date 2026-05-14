package app.project.InsuranceService.dto.request.Policy;

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
public class PolicyUpdateRequest {
    String name;
    PolicyType policyType;
    String description;
    BigDecimal coverageAmount;
    BigDecimal premiumAmount;
    int durationMonths;
    int claimLimit;
    PolicyStatus policyStatus;
}
