package app.project.InsuranceService.entity;

import app.project.InsuranceService.enums.ClaimActionType;
import app.project.InsuranceService.enums.ClaimStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_claim_reviews")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClaimReview {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_id", nullable = false)
    Claim claim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    User reviewer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    ClaimActionType actionType;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    ClaimStatus previousStatus;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    ClaimStatus newStatus;

    @Column(columnDefinition = "TEXT")
    String comment;

    LocalDateTime reviewedAt;
}
