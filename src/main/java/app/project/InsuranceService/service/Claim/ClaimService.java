package app.project.InsuranceService.service.Claim;

import app.project.InsuranceService.dto.request.Claim.ClaimAdminUpdateRequest;
import app.project.InsuranceService.dto.request.Claim.ClaimCreationRequest;
import app.project.InsuranceService.dto.request.Claim.ClaimUserUpdateRequest;
import app.project.InsuranceService.dto.response.Claim.ClaimResponse;
import app.project.InsuranceService.dto.response.PageResponse;
import app.project.InsuranceService.enums.ClaimStatus;

import java.util.List;


public interface ClaimService {

    ClaimResponse addClaim(ClaimCreationRequest claim);

    ClaimResponse getDetailClaim(String claimId);

    PageResponse<ClaimResponse> getAllClaimsCustomer(int pageNo, int pageSize, ClaimStatus status, String userId);

    PageResponse<ClaimResponse> getAllMyClaims(int pageNo, int pageSize, ClaimStatus status);

    PageResponse<ClaimResponse> getAllClaim(int pageNo, int pageSize, ClaimStatus status);

    ClaimResponse adminReviewClaim(String claimId);

    ClaimResponse adminNeedInfoClaim(String claimId);

    ClaimResponse adminApprovedClaim(ClaimAdminUpdateRequest request, String claimId);

    ClaimResponse adminRejectedClaim(ClaimAdminUpdateRequest request, String claimId);

    ClaimResponse adminPaidClaim(String claimId);

    ClaimResponse userUpdateClaim(ClaimUserUpdateRequest request, String claimId);
}
