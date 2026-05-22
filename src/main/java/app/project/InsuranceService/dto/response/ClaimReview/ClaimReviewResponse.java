package app.project.InsuranceService.dto.response.ClaimReview;

import app.project.InsuranceService.enums.ClaimActionType;
import app.project.InsuranceService.enums.ClaimStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClaimReviewResponse {
    String id;

    String claimId;

    String reviewerId;

    String reviewerUsername;

    ClaimActionType actionType;

    ClaimStatus previousStatus;

    ClaimStatus newStatus;

    String comment;

    LocalDateTime reviewedAt;
}
