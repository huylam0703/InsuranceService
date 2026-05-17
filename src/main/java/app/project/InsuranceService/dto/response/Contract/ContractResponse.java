package app.project.InsuranceService.dto.response.Contract;

import app.project.InsuranceService.enums.ContractStatus;
import app.project.InsuranceService.enums.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractResponse {
    String contractId;

    String contractCode;

    String policyCode;

    String policyName;

    BigDecimal premiumAmount;

    BigDecimal coverageAmount;

    BigDecimal remainingCoverage;

    ContractStatus contractStatus;

    PaymentStatus paymentStatus;

    LocalDate startDate;

    LocalDate endDate;

    Object detail;
}
