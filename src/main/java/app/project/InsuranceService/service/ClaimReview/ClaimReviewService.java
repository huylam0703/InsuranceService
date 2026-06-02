package app.project.InsuranceService.service.ClaimReview;

import app.project.InsuranceService.dto.response.ClaimReview.ClaimReviewResponse;
import app.project.InsuranceService.dto.response.PageResponse;
import app.project.InsuranceService.entity.Claim;
import app.project.InsuranceService.enums.ClaimActionType;
import app.project.InsuranceService.enums.ClaimStatus;

import java.util.List;

public interface ClaimReviewService {
    void saveClaimReview(
            Claim claim,
            ClaimStatus previousStatus,
            ClaimStatus newStatus,
            ClaimActionType actionType,
            String comment
    );

    List<ClaimReviewResponse> getClaimReviewByClaimId(String claimId);

    PageResponse<ClaimReviewResponse> getAllClaimReview(int pageNo, int pageSize);
}
