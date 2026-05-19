package app.project.InsuranceService.dto.response.Claim;

import app.project.InsuranceService.dto.response.ClaimDocument.ClaimDocumentResponse;
import app.project.InsuranceService.enums.ClaimStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClaimResponse {
    String id;

    String claimCode;

    String contractId;

    String contractCode;

    String customerId;

    String customerUsername;

    String title;

    String description;

    LocalDate incidentDate;

    BigDecimal claimAmount;

    BigDecimal approvedAmount;

    ClaimStatus status;

    String rejectionReason;

    Boolean fraudFlag;

    BigDecimal fraudScore;

    LocalDateTime submittedAt;

    LocalDateTime closedAt;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    List<ClaimDocumentResponse> documents;
}
