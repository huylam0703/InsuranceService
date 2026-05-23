package app.project.InsuranceService.entity;

import app.project.InsuranceService.enums.ContractPaymentStatus;
import app.project.InsuranceService.enums.ContractStatus;
import app.project.InsuranceService.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "tbl_contracts")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String contractId;

    String contractCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", nullable = false)
    Policy policy;

    LocalDate startDate;

    LocalDate endDate;

    BigDecimal premiumAmount;

    BigDecimal coverageAmount;

    BigDecimal remainingCoverage;

    @Enumerated(EnumType.STRING)
    ContractStatus contractStatus;

    @Enumerated(EnumType.STRING)
    ContractPaymentStatus paymentStatus;

}
