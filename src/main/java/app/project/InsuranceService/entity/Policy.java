package app.project.InsuranceService.entity;

import app.project.InsuranceService.enums.PolicyStatus;
import app.project.InsuranceService.enums.PolicyType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "tbl_policies")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String policyCode;

    String name;

    @Enumerated(EnumType.STRING)
    PolicyType policyType;

    String description;

    BigDecimal coverageAmount;

    BigDecimal premiumAmount;

    int durationMonths;

    int claimLimit;

    @Enumerated(EnumType.STRING)
    PolicyStatus status;
}
