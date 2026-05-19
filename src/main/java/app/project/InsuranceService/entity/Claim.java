package app.project.InsuranceService.entity;

import app.project.InsuranceService.enums.ClaimStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_claims")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(nullable = false, unique = true, length = 50)
    String claimCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    Contract contract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by")
    User reviewedBy;

    String title;

    String description;

    LocalDate incidentDate;

    BigDecimal claimAmount;

    BigDecimal approvedAmount;

    @Enumerated(EnumType.STRING)
    ClaimStatus status;

    String rejectionReason;

    Boolean fraudFlag;

    BigDecimal fraudScore;

    @Version
    Long version;

    LocalDateTime submittedAt;

    LocalDateTime closedAt;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
